package org.jboss.ddoyle.infinispan.demo.xsite;

import java.util.List;

import org.infinispan.xsite.CustomFailurePolicy;
import org.jboss.ddoyle.infinispan.demo.xsite.event.XSiteFailureEvent;

public interface EventLoggingFailurePolicy<K, V> extends CustomFailurePolicy<K, V> { 
	
	public abstract List<XSiteFailureEvent> getFailureEvents(String site);

}
