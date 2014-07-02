package org.jboss.ddoyle.osc2013.infinispan.demo.cache.event;

import java.io.Serializable;

import org.infinispan.filter.Converter;
import org.infinispan.metadata.Metadata;
import org.jboss.ddoyle.osc2013.infinispan.demo.model.event.CustomEvent;

public class StaticConverter implements Converter<String, String, CustomEvent>, Serializable {

	/**
	 * SerialVersionUID
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public CustomEvent convert(String key, String value, Metadata metadata) {
		return new CustomEvent(key, value);
	}

}
