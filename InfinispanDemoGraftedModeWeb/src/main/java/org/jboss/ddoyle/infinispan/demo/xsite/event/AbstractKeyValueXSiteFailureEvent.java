package org.jboss.ddoyle.infinispan.demo.xsite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;

public abstract class AbstractKeyValueXSiteFailureEvent extends AbstractKeyXSiteFailureEvent {
	
	private final Object value;
	
	public AbstractKeyValueXSiteFailureEvent(final Object key, final Object value) {
		super(key);
		this.value = value;
	}

	public Object getValue() {
		return value;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("key", getKey()).append("value", getValue()).toString();
	}

}
