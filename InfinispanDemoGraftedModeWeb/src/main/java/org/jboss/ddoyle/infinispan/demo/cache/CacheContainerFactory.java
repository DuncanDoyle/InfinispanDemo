package org.jboss.ddoyle.infinispan.demo.cache;

import org.infinispan.manager.CacheContainer;

public interface CacheContainerFactory {

	public abstract CacheContainer getCacheContainer();
	
}
