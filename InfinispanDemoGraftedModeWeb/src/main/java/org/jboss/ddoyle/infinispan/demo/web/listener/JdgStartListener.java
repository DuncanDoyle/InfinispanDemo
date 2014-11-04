package org.jboss.ddoyle.infinispan.demo.web.listener;

import javax.inject.Inject;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

import org.jboss.ddoyle.infinispan.demo.cache.ApplicationCacheManager;
import org.jboss.ddoyle.infinispan.demo.cache.management.CacheStatisticsMBean;
import org.jboss.ddoyle.infinispan.demo.server.ProtocolServerManager;
import org.jboss.ddoyle.infinispan.demo.server.hotrod.HotRod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Application Lifecycle Listener implementation class CacheStartListener
 * 
 */

@WebListener
public class JdgStartListener implements ServletContextListener {

	private static final Logger LOGGER = LoggerFactory.getLogger(JdgStartListener.class);
	
	@Inject
	private ApplicationCacheManager cacheManager;
	
	@Inject
	@HotRod
	private ProtocolServerManager protocolServerManager;
	
	@Inject
	private CacheStatisticsMBean cacheStatistics;
	
	//TODO: Make this configurable or remove this option.
	private boolean startCachesOnStartup = true;
	
	/**
	 * Default constructor.
	 */
	public JdgStartListener() {
	}

	/**
	 * @see ServletContextListener#contextInitialized(ServletContextEvent)
	 */
	public void contextInitialized(ServletContextEvent scEvent) {
		if (startCachesOnStartup == true) {
			LOGGER.info("Starting default cache."); 
			cacheManager.startCache();
		}
		//Fire up the Hotrod Server
		protocolServerManager.bootstrapProtocolServer();
	}
	

	/**
	 * @see ServletContextListener#contextDestroyed(ServletContextEvent)
	 */
	public void contextDestroyed(ServletContextEvent arg0) {
		LOGGER.info("Stopping the HotRod server.");
		//Cache Container stop is managed by CDI.
		//cacheManager.stopCacheContainer();
		//cacheManager.stopCache();
		protocolServerManager.stop();
	}

}
