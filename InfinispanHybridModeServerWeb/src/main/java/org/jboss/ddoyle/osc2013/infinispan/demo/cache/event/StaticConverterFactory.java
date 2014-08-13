package org.jboss.ddoyle.osc2013.infinispan.demo.cache.event;

import java.io.Serializable;

import org.infinispan.filter.Converter;
import org.infinispan.filter.ConverterFactory;
import org.infinispan.filter.NamedFactory;
import org.jboss.ddoyle.osc2013.infinispan.demo.model.event.CustomEvent;

@NamedFactory(name = "static-converter")
public class StaticConverterFactory implements ConverterFactory, Serializable {

	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@Override
	public Converter<String, String, CustomEvent> getConverter(Object[] params) {
		return new StaticConverter();
	}
	
}
