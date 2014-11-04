package org.jboss.ddoyle.infinispan.demo.server.hotrod;

import java.util.Properties;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.manager.CacheContainer;
import org.infinispan.manager.EmbeddedCacheManager;
//import org.infinispan.server.core.Main;
import org.infinispan.server.core.transport.Transport;
import org.infinispan.server.hotrod.HotRodServer;
import org.jboss.ddoyle.infinispan.demo.cache.ApplicationCacheManager;
import org.jboss.ddoyle.infinispan.demo.server.ProtocolServerManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Manages various aspects of the {@link HotRodServer}, including configuration setup, starting the server, stopping the server, etc.
 * <p/>
 * Code is based on the JBoss Data Grid Server 6.1.0 <code>ProtocolServerService</code> component.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
//@ApplicationScoped
//@HotRod
public class HotRodServerManager {//implements ProtocolServerManager {

//	public static final Logger LOGGER = LoggerFactory.getLogger(HotRodServerManager.class);
//
//	private static final String DEFAULT_WORKER_THREADS = "160";
//
//	private static final String JBOSS_BIND_ADDRESS_PROPERTY = "jboss.bind.address";
//	
//	private HotRodServer hotRodServer;
//
//	private final Properties connectorProperties = new Properties();
//
//	private final Properties topologyStateTransferProperties = new Properties();
//
//	private Transport transport;
//
//	@Inject
//	private ApplicationCacheManager appCacheManager;
//
//	@Override
//	public void bootstrapProtocolServer() {
//		
//		
//		LOGGER.info("Starting the HotRodServer.");
//
//		assert connectorProperties.isEmpty();
//		assert topologyStateTransferProperties.isEmpty();
//
//		boolean done = false;
//		try {
//			Properties connProps = null;
//			Properties topStateTransferProps = null;
//			loadConnectorProperties(connProps);
//			loadTopologyStateTransferProperties(topStateTransferProps);
//			validateConfiguration();
//
//			// Start the connector
//			startProtocolServer();
//
//			//SocketBinding binding = socketBinding.getValue();
//			//ROOT_LOGGER.endpointStarted(serverName, NetworkUtils.formatAddress(binding.getAddress()), binding.getAbsolutePort());
//			LOGGER.info("HotRodServer connector started.");
//
//			done = true;
//		} catch (Exception e) {
//			String message = "Failed to start the HotRodServer.";
//			LOGGER.error(message, e);
//			throw new RuntimeException(message, e);
//		} finally {
//			if (!done) {
//				doStop();
//			}
//		}
//	}
//
//	private void loadConnectorProperties(Properties props) {
//		// TODO: Implement property loading ....
//		/*
//		if (config.hasDefined(ModelKeys.SOCKET_BINDING)) {
//	         SocketBinding socketBinding = getSocketBinding().getValue();
//	         InetSocketAddress socketAddress = socketBinding.getSocketAddress();
//	         connectorProperties.setProperty(Main.PROP_KEY_HOST(), socketAddress.getAddress().getHostAddress());
//	         connectorProperties.setProperty(Main.PROP_KEY_PORT(), String.valueOf(socketAddress.getPort()));
//	      }
//	      */
//		//SocketBinding props.
//		if (true) {
//			final String bindAddress = System.getProperty(JBOSS_BIND_ADDRESS_PROPERTY);
//			if (bindAddress == null || "".equals(bindAddress)) {
//				throw new IllegalStateException("Unable to determine bind address for HotRod Server. No '" + JBOSS_BIND_ADDRESS_PROPERTY + "' property found.");
//			}
//			connectorProperties.setProperty(Main.PROP_KEY_HOST(), bindAddress);
//			connectorProperties.setProperty(Main.PROP_KEY_PORT(), "11222");
//		}
//			/*
//	      if (config.hasDefined(ModelKeys.WORKER_THREADS)) {
//	         connectorProperties.setProperty(Main.PROP_KEY_WORKER_THREADS(), config.get(ModelKeys.WORKER_THREADS)
//	               .asString());
//	      } else {
//	         connectorProperties.setProperty(Main.PROP_KEY_WORKER_THREADS(), DEFAULT_WORKER_THREADS);
//	      }
//	      */
//		//WorkerThreads
//		if (false) {
//			connectorProperties.setProperty(Main.PROP_KEY_WORKER_THREADS(), "160");
//		} else {
//			connectorProperties.setProperty(Main.PROP_KEY_WORKER_THREADS(), DEFAULT_WORKER_THREADS);
//		}
//		
//		/*
//		 
//	      if (config.hasDefined(ModelKeys.IDLE_TIMEOUT)) {
//	         connectorProperties.setProperty(Main.PROP_KEY_IDLE_TIMEOUT(), config.get(ModelKeys.IDLE_TIMEOUT).asString());
//	      }
//	      */
//		//Idle timeout
//		if (false) {
//			connectorProperties.setProperty(Main.PROP_KEY_IDLE_TIMEOUT(),  "10000");
//		}
//		
//		
//		/*
//	      if (config.hasDefined(ModelKeys.TCP_NODELAY)) {
//	         connectorProperties.setProperty(Main.PROP_KEY_TCP_NO_DELAY(), config.get(ModelKeys.TCP_NODELAY).asString());
//	      }
//	      */
//		//TCP NODELAY
//		if (false) {
//			connectorProperties.setProperty(Main.PROP_KEY_TCP_NO_DELAY(), "true");
//		}
//			
//		/*
//	      if (config.hasDefined(ModelKeys.SEND_BUFFER_SIZE)) {
//	         connectorProperties.setProperty(Main.PROP_KEY_SEND_BUF_SIZE(), config.get(ModelKeys.SEND_BUFFER_SIZE)
//	               .asString());
//	      }
//	      */
//		//Send Buffer Size.
//		if (false) {
//			connectorProperties.setProperty(Main.PROP_KEY_SEND_BUF_SIZE(), "10000");
//		}
//	      /*
//	      if (config.hasDefined(ModelKeys.RECEIVE_BUFFER_SIZE)) {
//	         connectorProperties.setProperty(Main.PROP_KEY_RECV_BUF_SIZE(), config.get(ModelKeys.RECEIVE_BUFFER_SIZE)
//	               .asString());
//	      }
//	      */
//		//RECEIVE Buffer Size
//		if (false) {
//			connectorProperties.setProperty(Main.PROP_KEY_RECV_BUF_SIZE(), "10000");
//		}
//			
//	}
//
//	private void loadTopologyStateTransferProperties(Properties props) {
//		// TODO: Implement property loading ...
//		/*
//		if (!config.hasDefined(ModelKeys.TOPOLOGY_STATE_TRANSFER)) {
//	         return;
//	      }
//	      */
//		//Test whether there is a configuration element for the TOPOLOGY_STATE_TRANSFER config.
//		if (false) {
//			return;
//		}
//		
//		
//		/*
//	      config = config.get(ModelKeys.TOPOLOGY_STATE_TRANSFER);
//	      if (config.hasDefined(ModelKeys.LOCK_TIMEOUT)) {
//	         topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_LOCK_TIMEOUT(),
//	               config.get(ModelKeys.LOCK_TIMEOUT).asString());
//	      }
//	      */
//		//LOCK TIMEOUT
//		if (true) {
//			topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_LOCK_TIMEOUT(), "1000");
//		}
//		
//		/*      
//	      if (config.hasDefined(ModelKeys.REPLICATION_TIMEOUT)) {
//	         topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_REPL_TIMEOUT(),
//	               config.get(ModelKeys.REPLICATION_TIMEOUT).asString());
//	      }
//	     */
//		//REPLICATION TIMEOUT
//		if (true) {
//			topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_REPL_TIMEOUT(), "5000");
//		}
//		
//		/*
//	      if (config.hasDefined(ModelKeys.UPDATE_TIMEOUT)) {
//	         topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_UPDATE_TIMEOUT(),
//	               config.get(ModelKeys.UPDATE_TIMEOUT).asString());
//	      }
//	    */
//		//UPDATE TIMEOUT
//		if (false) {
//			topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_UPDATE_TIMEOUT(), "1000");
//		}
//		
//		
//		/*
//	      if (config.hasDefined(ModelKeys.EXTERNAL_HOST)) {
//	         topologyStateTransferProperties.setProperty(Main.PROP_KEY_PROXY_HOST(), config.get(ModelKeys.EXTERNAL_HOST)
//	               .asString());
//	      }
//	      */
//		//EXTERNAL_HOST
//		if (false) {
//			topologyStateTransferProperties.setProperty(Main.PROP_KEY_PROXY_HOST(), "127.0.0.1");
//		}
//		/*
//		  if (config.hasDefined(ModelKeys.EXTERNAL_PORT)) {
//	         topologyStateTransferProperties.setProperty(Main.PROP_KEY_PROXY_PORT(), config.get(ModelKeys.EXTERNAL_PORT)
//	               .asString());
//	      }
//	      */
//		//EXTERNAL PORT
//		if (false) {
//			topologyStateTransferProperties.setProperty(Main.PROP_KEY_PROXY_PORT(), "11223");
//		}
//		
//		/*
//	      if (config.hasDefined(ModelKeys.LAZY_RETRIEVAL)) {
//	         topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_STATE_TRANSFER(),
//	               Boolean.toString(!config.get(ModelKeys.LAZY_RETRIEVAL).asBoolean(false)));
//	      }
//	      */
//		if (true) {
//			topologyStateTransferProperties.setProperty(Main.PROP_KEY_TOPOLOGY_STATE_TRANSFER(), "false");
//		}
//	}
//
//	private void validateConfiguration() {
//		if (connectorProperties.isEmpty()) {
//			String message = "HotRodServer connector has not been defined.";
//			LOGGER.error(message);
//			throw new IllegalStateException(message);
//	     }
//	}
//
//	private void startProtocolServer() {
//		hotRodServer = new HotRodServer();
//
//		Properties props = copy(connectorProperties);
//		if (props == null) {
//			/*
//			 * TODO: Shouldn't we throw an exception here when there are no properties defined? Or does that just imply that no HotRod
//			 * configuration is set, so we don't start the server?
//			 */
//
//			return;
//		}
//
//		// Merge topology state transfer settings
//		props.putAll(topologyStateTransferProperties);
//
//		// Start the server and record it
//		LOGGER.info("Starting the HotRodServer connector.");
//		hotRodServer.start(props, getCacheManager());
//
//		/*
//		try {
//			transport = (Transport) ReflectionUtil.getValue(hotRodServer, "transport");
//		} catch (Exception e) {
//			String message = "Failed to instantiate HotRodServer transport.";
//			LOGGER.error(message, e);
//			throw new RuntimeException(message, e);
//		}
//		*/
//	}
//
//	@Override
//	public synchronized void stop() {
//		doStop();
//	}
//
//	private void doStop() {
//		try {
//			if (hotRodServer != null) {
//				LOGGER.info("HotRodServer connector is stopping.");
//				try {
//					hotRodServer.stop();
//				} catch (Exception e) {
//					LOGGER.warn("Failed to stop the HotRodServer connector.");
//				}
//			}
//		} finally {
//			connectorProperties.clear();
//			topologyStateTransferProperties.clear();
//			LOGGER.info("Stopped HotRodServer connector.");
//		}
//	}
//
//	private static Properties copy(Properties p) {
//		if (p == null) {
//			return null;
//		}
//		Properties newProps = new Properties();
//		newProps.putAll(p);
//		return newProps;
//	}
//
//	private EmbeddedCacheManager getCacheManager() {
//		CacheContainer cacheContainer = appCacheManager.getCacheContainer();
//		// We only support EmbeddedCacheContainers to be exposed via the HotRodServer.
//		if (!(cacheContainer instanceof EmbeddedCacheManager)) {
//			String message = "Only EmbeddedCacheManagers can be exposed via the HotRodServer. The configured CacheContainer implementation is: '"
//					+ cacheContainer.getClass().getCanonicalName() + "'";
//			LOGGER.error(message);
//			throw new IllegalStateException(message);
//		}
//		return (EmbeddedCacheManager) cacheContainer;
//	}

}
