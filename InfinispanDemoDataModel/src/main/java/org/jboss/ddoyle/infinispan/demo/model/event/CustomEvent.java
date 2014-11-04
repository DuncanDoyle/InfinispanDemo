package org.jboss.ddoyle.infinispan.demo.model.event;

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
	
	public String toString() {
		return new StringBuilder().append("key: ").append(key).append(", value: ").append(value).toString();
	}

}
