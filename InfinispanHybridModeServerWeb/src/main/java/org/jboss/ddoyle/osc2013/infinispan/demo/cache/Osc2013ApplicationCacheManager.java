package org.jboss.ddoyle.osc2013.infinispan.demo.cache;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@ApplicationScoped
public class Osc2013ApplicationCacheManager implements ApplicationCacheManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(Osc2013ApplicationCacheManager.class); 
	
	private static final String APPLICATION_DEFAULT_CACHE_NAME = "MyCoolCache";
	
	@Inject @LocalCacheContainer
	private CacheContainer cacheContainer;
	
	public Osc2013ApplicationCacheManager() {
	}
	
	public <K, V> Cache<K, V> getCache() {
		return cacheContainer.getCache(APPLICATION_DEFAULT_CACHE_NAME);
	}
	
	public <K, V> Cache<K, V> getCache(String cacheName) {
		return cacheContainer.getCache(APPLICATION_DEFAULT_CACHE_NAME);
	}

	public void startCache() {
		getCache().start();
	}

	public void startCache(String cacheName) {
		getCache(cacheName).start();
		
	}

	public void stopCache() {
		getCache().stop();
	}

	public void stopCache(String cacheName) {
		getCache(cacheName).stop();
	}
	
	@Override
	public CacheContainer getCacheContainer() {
		return cacheContainer;
	}
	
	private void addListeners(Cache cache) {
		LOGGER.info("Registering Infinispan EventListeners with Cache: " + cache.getName());
		
	}

}
