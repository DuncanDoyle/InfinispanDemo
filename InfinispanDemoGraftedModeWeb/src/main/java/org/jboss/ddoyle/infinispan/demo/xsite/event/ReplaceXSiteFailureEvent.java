package org.jboss.ddoyle.infinispan.demo.xsite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class ReplaceXSiteFailureEvent extends AbstractKeyXSiteFailureEvent {
	
	private final Object oldValue;
	
	private final Object newValue;
	
	public ReplaceXSiteFailureEvent(Object key, Object oldValue, Object newValue) {
		super(key);
		this.oldValue = oldValue;
		this.newValue = newValue;
	}

	public Object getOldValue() {
		return oldValue;
	}

	public Object getNewValue() {
		return newValue;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("key", getKey()).append("oldValue", getOldValue()).append("newValue", getNewValue()).toString();
	}
	
}
