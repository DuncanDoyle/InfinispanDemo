package org.jboss.ddoyle.infinispan.demo.cache.event;

import java.io.Serializable;

import org.infinispan.metadata.Metadata;
import org.infinispan.notifications.cachelistener.filter.CacheEventConverter;
import org.infinispan.notifications.cachelistener.filter.EventType;
import org.jboss.ddoyle.infinispan.demo.model.event.CustomEvent;

public class StaticCacheEventConverter implements CacheEventConverter<String, String, CustomEvent>, Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public CustomEvent convert(String key, String oldValue, Metadata oldMetaData, String newValue, Metadata newMetaData, EventType eventType) {
		return new CustomEvent(key, newValue);
	}

}
