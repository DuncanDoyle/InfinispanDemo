package org.jboss.ddoyle.osc2013.infinispan.demo.client.listener;

import java.io.Serializable;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCustomEvent;
import org.jboss.ddoyle.osc2013.infinispan.demo.model.event.CustomEvent;



@ClientListener(converterFactoryName = "static-converter")
public class CustomEventPrintListener implements Serializable {
	
	
	/**
	 * SerialVersionUID.
	 */
	private static final long serialVersionUID = 1L;

	@ClientCacheEntryCreated
	@ClientCacheEntryModified
	@ClientCacheEntryRemoved
	public void handleCreatedEvent(ClientCacheEntryCustomEvent<CustomEvent> e) {
		System.out.println("Created: " + e);
	}
	
	public void handleModifiedEvent(ClientCacheEntryCustomEvent<CustomEvent> e) {
		System.out.println("Modified: " + e);
	}
	
	public void handleDeletedEvent(ClientCacheEntryCustomEvent<CustomEvent> e) {
		System.out.println("Deleted: " + e)
	}
	
	/*
	public void handleRemovedEvent(ClientCacheEntryCustomEvent<CustomEvent> e) {
		System.out.println("Removed: " + e);
	}
	*/
	
}
