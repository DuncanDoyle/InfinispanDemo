package org.jboss.ddoyle.osc2013.infinispan.demo.server.hotrod;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.EmbeddedCacheManager;
import org.infinispan.server.core.configuration.ProtocolServerConfiguration;
import org.infinispan.server.core.transport.Transport;
import org.infinispan.server.hotrod.HotRodServer;
import org.infinispan.server.hotrod.configuration.HotRodServerConfigurationBuilder;
import org.jboss.ddoyle.osc2013.infinispan.demo.cache.ApplicationCacheManager;
import org.jboss.ddoyle.osc2013.infinispan.demo.cache.event.StaticConverterFactory;
import org.jboss.ddoyle.osc2013.infinispan.demo.server.ProtocolServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ApplicationScoped
@HotRod
public class HotRod6ServerManager implements ProtocolServerManager {
	public static final Logger LOGGER = LoggerFactory.getLogger(HotRod6ServerManager.class);

	private static final int DEFAULT_HOTROD_PORT = 11222;
	
	private static final int DEFAULT_WORKER_THREADS = 160;

	private static final String JBOSS_BIND_ADDRESS_PROPERTY = "jboss.bind.address";
	
	private HotRodServer hotRodServer;
	
	private Transport transport;

	@Inject
	private ApplicationCacheManager appCacheManager;

	@Override
	public void bootstrapProtocolServer() {
		
		
		LOGGER.info("Starting the HotRodServer.");

		boolean done = false;
		try {
			//Configure the configuration builder.
			HotRodServerConfigurationBuilder builder = new HotRodServerConfigurationBuilder();
			// Start the connector
			//startProtocolServer(setConnectorProperties(setConverter(setTopologyStateTransferProperties(builder))).build());
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
		//SocketBinding props.
		if (true) {
			final String bindAddress = System.getProperty(JBOSS_BIND_ADDRESS_PROPERTY);
			if (bindAddress == null || "".equals(bindAddress)) {
				throw new IllegalStateException("Unable to determine bind address for HotRod Server. No '" + JBOSS_BIND_ADDRESS_PROPERTY + "' property found.");
			}
			builder.host(bindAddress).port(DEFAULT_HOTROD_PORT);
		}
			
		builder.workerThreads(DEFAULT_WORKER_THREADS)
		.idleTimeout(10000)
		.tcpNoDelay(true)
		.sendBufSize(10000)
		.recvBufSize(10000); 
		
		return builder;
			
	}

	private HotRodServerConfigurationBuilder setTopologyStateTransferProperties(HotRodServerConfigurationBuilder builder) {
	
		builder.topologyLockTimeout(1000L);
		builder.topologyReplTimeout(5000L);
		
		if (true) {
			final String bindAddress = System.getProperty(JBOSS_BIND_ADDRESS_PROPERTY);
			if (bindAddress == null || "".equals(bindAddress)) {
				throw new IllegalStateException("Unable to determine bind address for HotRod Server. No '" + JBOSS_BIND_ADDRESS_PROPERTY + "' property found.");
			}
			builder.proxyHost(bindAddress).proxyPort(DEFAULT_HOTROD_PORT);
		}
		//TODO: No topology update timeout.
		//TODO: Configure topology state transfer.
		//builder.topologyStateTransfer(true);
		return builder;
	}
	
	/*
	private HotRodServerConfigurationBuilder setConverter(HotRodServerConfigurationBuilder builder) {
		return builder.converterFactory("static-converter", new StaticConverterFactory());
	}
	*/

	private void startProtocolServer(ProtocolServerConfiguration configuration) {
		hotRodServer = new HotRodServer();

		// Start the server and record it
		LOGGER.info("Starting the HotRodServer connector.");
		hotRodServer.start(configuration, getCacheManager());

		//Add Converter factories.
		hotRodServer.addConverterFactory("ddoyle-converter", new StaticConverterFactory());
		
		try {
			//transport = (Transport) ReflectionUtil.getValue(hotRodServer, "transport");
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
