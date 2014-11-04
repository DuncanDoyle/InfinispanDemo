package org.jboss.ddoyle.infinispan.demo.cache;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;

public interface ApplicationCacheManager {
	
	public abstract <K, V> Cache<K,V> getCache();
	
	public abstract <K, V> Cache<K,V> getCache(String name);
	
	public abstract void startCache();
	
	public abstract void startCache(final String cacheName);
	
	public abstract void stopCache();
	
	public abstract void stopCache(final String cacheName);
	
	//public abstract void stopCacheContainer();
	
	public abstract CacheContainer getCacheContainer();
	
}
