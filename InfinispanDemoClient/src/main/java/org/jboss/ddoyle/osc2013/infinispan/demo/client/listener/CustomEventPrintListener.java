package org.jboss.ddoyle.osc2013.infinispan.demo.client.listener;

import java.io.Serializable;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCustomEvent;
import org.jboss.ddoyle.osc2013.infinispan.demo.model.event.CustomEvent;



@ClientListener(converterFactoryName = "dynamic-converter")
public class CustomEventPrintListener implements Serializable {
	
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@ClientCacheEntryCreated
	@ClientCacheEntryModified
	@ClientCacheEntryRemoved
	public void handleCreatedEvent(ClientCacheEntryCustomEvent<CustomEvent> e) {
		System.out.println("Created and Modified: " + e);
	}
	
	/*
	public void handleRemovedEvent(ClientCacheEntryCustomEvent<CustomEvent> e) {
		System.out.println("Removed: " + e);
	}
	*/
	
}
