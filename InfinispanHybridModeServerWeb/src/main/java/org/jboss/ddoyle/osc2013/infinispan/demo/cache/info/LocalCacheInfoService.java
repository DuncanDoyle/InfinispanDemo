package org.jboss.ddoyle.osc2013.infinispan.demo.cache.info;

import java.util.Map.Entry;
import java.util.Set;

import javax.inject.Inject;

import org.infinispan.Cache;
import org.jboss.ddoyle.osc2013.infinispan.demo.cache.ApplicationCacheManager;

public class LocalCacheInfoService implements CacheInfoService {

	@Inject
	private ApplicationCacheManager cacheManager;
	
	@Override
	public int getCacheSize() {
		return cacheManager.getCache().size();
	}

	@Override
	public Set<Entry<String, Object>> getCacheEntries() {
		Cache<String, Object> cache = cacheManager.getCache();
		return cache.entrySet();
	}
	

}
