package org.jboss.ddoyle.infinispan.demo.xsite.event;

import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class PutXSiteFailureEvent extends AbstractKeyValueXSiteFailureEvent {
	
	private boolean putIfAbsent;
	
	public PutXSiteFailureEvent(final Object key, final Object value, final boolean putIfAbsent) {
		super(key, value);
		
		this.putIfAbsent = putIfAbsent;
	}

	public boolean isPutIfAbsent() {
		return putIfAbsent;
	}
	
	public String toString() {
		return new ToStringBuilder(this).
			       append("key", getKey()).
			       append("value", getValue()).
			       append("putIfAbsent", isPutIfAbsent()).
			       toString(); 
	}

}
