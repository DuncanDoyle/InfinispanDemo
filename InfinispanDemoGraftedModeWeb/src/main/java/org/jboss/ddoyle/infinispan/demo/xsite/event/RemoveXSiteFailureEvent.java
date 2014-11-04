package org.jboss.ddoyle.infinispan.demo.xsite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 *
 */
public class RemoveXSiteFailureEvent extends AbstractKeyXSiteFailureEvent {
	
	private final Object oldValue;
	
	public RemoveXSiteFailureEvent(final Object key, final Object oldValue) {
		super(key);
		this.oldValue = oldValue;
	}

	public Object getOldValue() {
		return oldValue;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("key", getKey()).append("oldValue", getOldValue()).toString();
	}
	
}
