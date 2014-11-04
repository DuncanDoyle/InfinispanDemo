package org.jboss.ddoyle.infinispan.demo.cache;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;
import java.util.Set;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.Disposes;
import javax.enterprise.inject.Produces;

import org.infinispan.commons.util.Util;
import org.infinispan.manager.DefaultCacheManager;
import org.infinispan.manager.EmbeddedCacheManager;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Provides a local CacheContainer in Infinispan 'Library-Mode'.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class LocalCacheContainerFactory implements CacheContainerFactory {

	private static final Logger LOGGER = LoggerFactory.getLogger(LocalCacheContainerFactory.class);
	
	private static final String INFINISPAN_CONFIG_FILE_NAME = "infinispan/infinispan.xml";
	
	@Produces @ApplicationScoped @LocalCacheContainer
	public EmbeddedCacheManager getCacheContainer() {
		EmbeddedCacheManager cacheContainer;
		// Retrieve Infinispan config file.
		InputStream infinispanConfigStream = this.getClass().getClassLoader().getResourceAsStream(INFINISPAN_CONFIG_FILE_NAME);
		logJGroupsParams();
		try {
			try {
				
				cacheContainer = new DefaultCacheManager(infinispanConfigStream);
			} catch (IOException ioe) {
				throw new RuntimeException("Error loading Infinispan CacheManager.", ioe);
			}
		} finally {
			// Use Infinispan Util class to flush and close stream.
			Util.close(infinispanConfigStream);
		}
		return cacheContainer;
	}
	
	public void disposeCacheContainer(@Disposes @LocalCacheContainer EmbeddedCacheManager cacheContainer) {
		LOGGER.info("Stopping Caches and CacheContainer.");
		Set<String> cacheNames = cacheContainer.getCacheNames();
		for(String nextCacheName: cacheNames) {
			LOGGER.info("Stopping cache: " + nextCacheName);
			cacheContainer.getCache(nextCacheName).stop();
		}
		LOGGER.info("Stopping default cache.");
		cacheContainer.getCache().stop();
		LOGGER.info("Stopping CacheContainer.");
		cacheContainer.stop();
	}
	
	
	private void logJGroupsParams() {
		Properties props = System.getProperties();
		LOGGER.info("JGroups bind address: " + props.getProperty("jgroups.bind_addr"));
		LOGGER.info("JGroups tcp address: " + props.getProperty("jgroups.tcp.address"));
		LOGGER.info("JGroups tcp port: " + props.getProperty("jgroups.tcp.port"));
	}

}
