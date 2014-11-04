package org.jboss.ddoyle.infinispan.demo.cache;

import javax.annotation.PostConstruct;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

import org.infinispan.Cache;
import org.infinispan.manager.CacheContainer;
import org.infinispan.remoting.transport.jgroups.JGroupsTransport;
import org.jboss.ddoyle.jgroups.demo.protocols.relay.LoggingRouteStatusListener;
import org.jgroups.protocols.relay.RELAY2;
import org.jgroups.stack.Protocol;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



@ApplicationScoped
public class InfinispanDemoApplicationCacheManager implements ApplicationCacheManager {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(InfinispanDemoApplicationCacheManager.class); 
	
	private static final String APPLICATION_DEFAULT_CACHE_NAME = "MyCoolCache";
	
	@Inject @LocalCacheContainer
	private CacheContainer cacheContainer;
	
	public InfinispanDemoApplicationCacheManager() {
	}
	
	/**
	 * Adds listeners to the cache and JGroups transport.
	 */
	@PostConstruct
	private void init() {
		Protocol relay2Protocol = ((JGroupsTransport)getCache().getAdvancedCache().getRpcManager().getTransport()).getChannel().getProtocolStack().findProtocol(RELAY2.class);
		if (relay2Protocol != null) {
			((RELAY2) relay2Protocol).setRouteStatusListener(new LoggingRouteStatusListener());
		}
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
