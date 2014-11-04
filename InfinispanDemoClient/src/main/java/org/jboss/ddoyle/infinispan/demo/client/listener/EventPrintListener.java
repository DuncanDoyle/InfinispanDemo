package org.jboss.ddoyle.infinispan.demo.client.listener;

import org.infinispan.client.hotrod.annotation.ClientCacheEntryCreated;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryModified;
import org.infinispan.client.hotrod.annotation.ClientCacheEntryRemoved;
import org.infinispan.client.hotrod.annotation.ClientListener;
import org.infinispan.client.hotrod.event.ClientCacheEntryCreatedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryModifiedEvent;
import org.infinispan.client.hotrod.event.ClientCacheEntryRemovedEvent;

@ClientListener
public class EventPrintListener {

	@ClientCacheEntryCreated
	public void handleCreatedEvent(ClientCacheEntryCreatedEvent e) {
		System.out.println(e);
	}

	@ClientCacheEntryModified
	public void handleModifiedEvent(ClientCacheEntryModifiedEvent e) {
		System.out.println(e);
	}

	@ClientCacheEntryRemoved
	public void handleRemovedEvent(ClientCacheEntryRemovedEvent e) {
		System.out.println(e);
	}

}
