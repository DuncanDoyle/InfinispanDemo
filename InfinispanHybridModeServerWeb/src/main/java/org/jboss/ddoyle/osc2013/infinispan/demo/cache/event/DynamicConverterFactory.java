package org.jboss.ddoyle.osc2013.infinispan.demo.cache.event;

import java.io.Serializable;

import org.infinispan.filter.Converter;
import org.infinispan.server.hotrod.event.ConverterFactory;
import org.jboss.ddoyle.osc2013.infinispan.demo.model.event.CustomEvent;

public class DynamicConverterFactory implements ConverterFactory, Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Converter<String, String, CustomEvent> getConverter(Object[] params) {
		return new DynamicConverter();
	}
	
}
