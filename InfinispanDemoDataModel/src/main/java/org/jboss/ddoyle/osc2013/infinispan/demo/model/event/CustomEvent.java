package org.jboss.ddoyle.osc2013.infinispan.demo.model.event;

import java.io.Serializable;

public class CustomEvent implements Serializable {
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;
	final String key;
	final String value;
	
	public CustomEvent(final String key, final String value) {
		this.key = key;
		this.value = value;
	}

}
