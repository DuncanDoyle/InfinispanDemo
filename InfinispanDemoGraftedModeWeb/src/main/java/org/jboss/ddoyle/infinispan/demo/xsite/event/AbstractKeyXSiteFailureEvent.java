package org.jboss.ddoyle.infinispan.demo.xsite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 * 
 */
public abstract class AbstractKeyXSiteFailureEvent implements XSiteFailureEvent {

	private final Object key;

	public AbstractKeyXSiteFailureEvent(final Object key) {
		this.key = key;
	}

	public Object getKey() {
		return key;
	}

	public String toString() {
		return new ToStringBuilder(this).append("key", getKey()).toString();
	}

}
