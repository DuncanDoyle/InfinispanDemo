package org.jboss.ddoyle.infinispan.demo.xsite.event;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.builder.ToStringBuilder;

public class PutAllXSiteFailureEvent implements XSiteFailureEvent {
	
	private Map<Object, Object> map = new HashMap<>();
	
	public PutAllXSiteFailureEvent(final Map<Object, Object> map) {
		this.map = map;
	}

	public Map<Object, Object> getMap() {
		return map;
	}
	
	public String toString() {
		return new ToStringBuilder(this).append("map", getMap()).toString();
	}
	
	
}
