package org.jboss.ddoyle.osc2013.infinispan.demo.cache.info;

import java.util.Map.Entry;
import java.util.Set;

public interface CacheInfoService {

	public abstract int getCacheSize();
	
	public abstract Set<Entry<String, Object>> getCacheEntries();
}
