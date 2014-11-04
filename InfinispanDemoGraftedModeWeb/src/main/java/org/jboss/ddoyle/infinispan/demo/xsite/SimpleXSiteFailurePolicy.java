package org.jboss.ddoyle.infinispan.demo.xsite;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.transaction.Transaction;

import org.infinispan.Cache;
import org.infinispan.xsite.AbstractCustomFailurePolicy;
import org.jboss.ddoyle.infinispan.demo.xsite.event.ClearXSiteFailureEvent;
import org.jboss.ddoyle.infinispan.demo.xsite.event.LoggingXSiteFailureEvent;
import org.jboss.ddoyle.infinispan.demo.xsite.event.PutAllXSiteFailureEvent;
import org.jboss.ddoyle.infinispan.demo.xsite.event.PutXSiteFailureEvent;
import org.jboss.ddoyle.infinispan.demo.xsite.event.RemoveXSiteFailureEvent;
import org.jboss.ddoyle.infinispan.demo.xsite.event.ReplaceXSiteFailureEvent;
import org.jboss.ddoyle.infinispan.demo.xsite.event.XSiteFailureEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * Sample implementation of a x-site failure policy implementation.
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 */
public class SimpleXSiteFailurePolicy extends AbstractCustomFailurePolicy<Object, Object> implements EventLoggingFailurePolicy<Object, Object>{

	private static final Logger LOGGER = LoggerFactory.getLogger(SimpleXSiteFailurePolicy.class);
	
	private Map<String, List<XSiteFailureEvent>> failureEvents = new HashMap<>();
	
	//private boolean initialized = false;
	
	public SimpleXSiteFailurePolicy() {
		LOGGER.info("Constructing SimpleXSiteFailurePolicy.");
	}
	
	@Override
	public void init(Cache cache) {
		LOGGER.info("Initializing SimpleXSiteFailurePolicy for cache: "+ cache.getName());
		super.init(cache);
		//Registering via a static method. CDI BeanManager is not available, so can't lookup the proper @ApplicationScoped XSiteFailureManager bean.
		XSiteFailureManager.registerFailurePolicy(cache.getName(), this);
		//initialized = true;
	}
	
	private void add(String site, XSiteFailureEvent event) {
		/*
		if (!initialized) {
			this.init(null);
		}
		*/
			
		List<XSiteFailureEvent> failureEventsList = failureEvents.get(site);
		if (failureEventsList == null) {
			failureEventsList = new ArrayList<>();
			failureEvents.put(site,failureEventsList);
		}
		failureEventsList.add(event);
	}
	
	public List<XSiteFailureEvent> getFailureEvents(String site) {
		return failureEvents.get(site);
	}

	@Override
	public void handlePutFailure(String site, Object key, Object value, boolean putIfAbsent) {
		LOGGER.warn("Unable to send 'put' operation to remote site: " + site);
		add(site, new PutXSiteFailureEvent(key, value, putIfAbsent));
	}

	@Override
	public void handleRemoveFailure(String site, Object key, Object oldValue) {
		LOGGER.warn("Unable to send 'remove' operation to remote site: " + site);
		add(site, new RemoveXSiteFailureEvent(key, oldValue));
	}

	@Override
	public void handleReplaceFailure(String site, Object key, Object oldValue, Object newValue) {
		LOGGER.warn("Unable to send 'replace' operation to remote site: " + site);
		add(site, new ReplaceXSiteFailureEvent(key, oldValue, newValue));
	}

	@Override
	public void handleClearFailure(String site) {
		LOGGER.warn("Unable to send 'clear' operation to remote site: " + site);
		add(site, new ClearXSiteFailureEvent());
	}

	@Override
	public void handlePutAllFailure(String site, Map<Object, Object> map) {
		LOGGER.warn("Unable to send 'putAll' operation to remote site: " + site);
		add(site, new PutAllXSiteFailureEvent(map));
	}

	@Override
	public void handlePrepareFailure(String site, Transaction transaction) {
		LOGGER.warn("Unable to send 'prepare' operation to remote site: " + site);
		add(site, new LoggingXSiteFailureEvent("Prepation failed for transaction: " + transaction.toString()));
	}

	@Override
	public void handleRollbackFailure(String site, Transaction transaction) {
		LOGGER.warn("Unable to send 'rollback' operation to remote site: " + site);
		add(site, new LoggingXSiteFailureEvent("Rollback failed for transaction: " + transaction.toString()));
	}

	@Override
	public void handleCommitFailure(String site, Transaction transaction) {
		LOGGER.warn("Unable to send 'commit' operation to remote site: " + site);
		add(site, new LoggingXSiteFailureEvent("Commit failed for transaction: " + transaction.toString()));
	}

	
}
