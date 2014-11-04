package org.jboss.ddoyle.infinispan.demo.xsite;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;

import org.jboss.ddoyle.infinispan.demo.xsite.event.XSiteFailureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;



/**
 * Keeps track of the status of the x-site backup nodes. 
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
@ApplicationScoped
public class XSiteFailureManager {

	private static final Logger LOGGER = LoggerFactory.getLogger(XSiteFailureManager.class);
	
	private static Map<String, EventLoggingFailurePolicy<Object, Object>> failurePolicies = new HashMap<>();
	
	static void registerFailurePolicy(String cache, EventLoggingFailurePolicy<Object, Object> failurePolicy) {
		LOGGER.info("Registered new failure-policy for cache: " + cache);
		failurePolicies.put(cache, failurePolicy);
	}
	
	public List<XSiteFailureEvent> getXSiteFailures(String cache, String site)  {
		return failurePolicies.get(cache).getFailureEvents(site);
	}
	
	
	
	
}
