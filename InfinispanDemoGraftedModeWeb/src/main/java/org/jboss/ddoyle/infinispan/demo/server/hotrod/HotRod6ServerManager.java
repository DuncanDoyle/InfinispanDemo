package org.jboss.ddoyle.infinispan.demo.server.hotrod;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.server.core.configuration.ProtocolServerConfiguration;
import org.infinispan.server.core.transport.Transport;
import org.infinispan.server.hotrod.HotRodServer;
import org.infinispan.server.hotrod.configuration.HotRodServerConfigurationBuilder;
import org.jboss.ddoyle.infinispan.demo.cache.ApplicationCacheManager;
import org.jboss.ddoyle.infinispan.demo.cache.event.StaticCacheEventConverterFactory;
import org.jboss.ddoyle.infinispan.demo.server.ProtocolServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@HotRod
public class HotRod6ServerManager implements ProtocolServerManager {
	public static final Logger LOGGER = LoggerFactory.getLogger(HotRod6ServerManager.class);

	private static final int DEFAULT_HOTROD_PORT = 11222;

	private static final int DEFAULT_WORKER_THREADS = 160;

	private static final String JBOSS_BIND_ADDRESS_PROPERTY = "jboss.bind.address";

	private static final String ISPN_HOTROD_PROXY_HOST = "infinispan.hotrod.proxy.host";

	private static final String ISPN_HOTROD_PROXY_PORT = "infinispan.hotrod.proxy.port";

	private HotRodServer hotRodServer;

	private Transport transport;

	@Inject
	private ApplicationCacheManager appCacheManager;

	@Override
	public void bootstrapProtocolServer() {

		LOGGER.info("Starting the HotRodServer.");

		boolean done = false;
		try {
			// Configure the configuration builder.
			HotRodServerConfigurationBuilder builder = new HotRodServerConfigurationBuilder();
			// Start the connector
			// startProtocolServer(setConnectorProperties(setConverter(setTopologyStateTransferProperties(builder))).build());
			startProtocolServer(setConnectorProperties(setTopologyStateTransferProperties(builder)).build());

			LOGGER.info("HotRodServer connector started on host '" + transport.getHostName() + "' and port '" + transport.getPort() + "'.");

			done = true;
		} catch (Exception e) {
			String message = "Failed to start the HotRodServer.";
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		} finally {
			if (!done) {
				doStop();
			}
		}
	}

	private HotRodServerConfigurationBuilder setConnectorProperties(HotRodServerConfigurationBuilder builder) {
		// SocketBinding props.
		final String bindAddress = System.getProperty(JBOSS_BIND_ADDRESS_PROPERTY);
		if (bindAddress == null || "".equals(bindAddress)) {
			throw new IllegalStateException("Unable to determine bind address for HotRod Server. No '" + JBOSS_BIND_ADDRESS_PROPERTY
					+ "' property found.");
		}
		builder.host(bindAddress).port(DEFAULT_HOTROD_PORT);
		
		// Configure the external proxy for remote clients. Required in, for example, cloud environments.
		final String proxyHost = System.getProperty(ISPN_HOTROD_PROXY_HOST);
		if (proxyHost != null && !("".equals(proxyHost))) {
			builder.proxyHost(proxyHost);
		}
		final String proxyPort = System.getProperty(ISPN_HOTROD_PROXY_PORT);
		if (proxyPort != null && !("".equals(proxyPort))) {
			int proxyPortNumber;
			try {
				proxyPortNumber = Integer.parseInt(proxyPort);
			} catch (NumberFormatException nfe) {
				throw new IllegalArgumentException("Infinspan Proxy portNumber should be an integer.", nfe);
			}
			builder.proxyPort(proxyPortNumber);
		}

		builder.workerThreads(DEFAULT_WORKER_THREADS).idleTimeout(10000).tcpNoDelay(true).sendBufSize(10000).recvBufSize(10000);

		return builder;

	}

	private HotRodServerConfigurationBuilder setTopologyStateTransferProperties(HotRodServerConfigurationBuilder builder) {

		builder.topologyLockTimeout(1000L);
		builder.topologyReplTimeout(5000L);

		if (true) {
			final String bindAddress = System.getProperty(JBOSS_BIND_ADDRESS_PROPERTY);
			if (bindAddress == null || "".equals(bindAddress)) {
				throw new IllegalStateException("Unable to determine bind address for HotRod Server. No '" + JBOSS_BIND_ADDRESS_PROPERTY
						+ "' property found.");
			}
			builder.proxyHost(bindAddress).proxyPort(DEFAULT_HOTROD_PORT);
		}
		// TODO: No topology update timeout.
		// TODO: Configure topology state transfer.
		// builder.topologyStateTransfer(true);
		return builder;
	}

	/*
	 * private HotRodServerConfigurationBuilder setConverter(HotRodServerConfigurationBuilder builder) { return
	 * builder.converterFactory("static-converter", new StaticConverterFactory()); }
	 */

	private void startProtocolServer(ProtocolServerConfiguration configuration) {
		hotRodServer = new HotRodServer();

		// Start the server and record it
		LOGGER.info("Starting the HotRodServer connector.");
		hotRodServer.start(configuration, getCacheManager());
		
		// Add Converter factories.
		// Seems not needed anymore in ISPN 7.0.0.CR1s
		hotRodServer.addCacheEventConverterFactory("static-converter", new StaticCacheEventConverterFactory());
		//hotRodServer.add
		
		
		
		try {
			// transport = (Transport) ReflectionUtil.getValue(hotRodServer, "transport");
			transport = hotRodServer.transport();
		} catch (Exception e) {
			String message = "Failed to instantiate HotRodServer transport.";
			LOGGER.error(message, e);
			throw new RuntimeException(message, e);
		}
	
	}

	@Override
	public synchronized void stop() {
		doStop();
	}

	private void doStop() {
		
		try {
			if (hotRodServer != null) {
				LOGGER.info("HotRodServer connector is stopping.");
				try {
					transport.stop();
					hotRodServer.stop();
				} catch (Exception e) {
					LOGGER.warn("Failed to stop the HotRodServer connector.");
				}
			}
		} finally {
			transport = null;
			hotRodServer = null;
			LOGGER.info("Stopped HotRodServer connector.");
		}
		//Sleep for 15 seconds to give the HotRod Worker Threads time to shut down.
		//TODO: Actually check whether the NettyTransport's workerGroup has closed. The problem is that I currently can't find a hook into that code.
		try {
			Thread.currentThread().sleep(15000);
		} catch(InterruptedException ie) {
			//reset the interrupt.
			Thread.currentThread().interrupt();
		}
	}

	private EmbeddedCacheManager getCacheManager() {
		CacheContainer cacheContainer = appCacheManager.getCacheContainer();
		// We only support EmbeddedCacheContainers to be exposed via the HotRodServer.
		if (!(cacheContainer instanceof EmbeddedCacheManager)) {
			String message = "Only EmbeddedCacheManagers can be exposed via the HotRodServer. The configured CacheContainer implementation is: '"
					+ cacheContainer.getClass().getCanonicalName() + "'";
			LOGGER.error(message);
			throw new IllegalStateException(message);
		}
		return (EmbeddedCacheManager) cacheContainer;
	}

}
