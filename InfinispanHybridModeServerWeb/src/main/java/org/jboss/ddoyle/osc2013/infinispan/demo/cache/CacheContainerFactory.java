package org.jboss.ddoyle.osc2013.infinispan.demo.cache;

import org.infinispan.manager.CacheContainer;

public interface CacheContainerFactory {

	public abstract CacheContainer getCacheContainer();
	
}
