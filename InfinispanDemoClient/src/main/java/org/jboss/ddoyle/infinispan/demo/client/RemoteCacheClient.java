package org.jboss.ddoyle.infinispan.demo.client;

import java.io.Console;
import java.util.Set;
import java.util.UUID;

import org.infinispan.client.hotrod.RemoteCache;
import org.infinispan.client.hotrod.RemoteCacheManager;
import org.jboss.ddoyle.infinispan.demo.client.listener.CustomEventPrintListener;
import org.jboss.ddoyle.infinispan.demo.client.listener.EventPrintListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class RemoteCacheClient {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(RemoteCacheClient.class);

	// private RemoteCacheManager cacheManager;

	private RemoteCache<String, String> cache;
	
	private EventPrintListener listener = new EventPrintListener();
	
	private CustomEventPrintListener customListener = new CustomEventPrintListener();

	public RemoteCacheClient() {
		LOGGER.info("Initializing HotRod Cache Manager.");
		RemoteCacheManager cacheManager = new RemoteCacheManager();
		cache = cacheManager.getCache("MyCoolCache");
	}

	public static void main(String[] args) {
		LOGGER.info("Initializing RemoteCacheClient.");
		RemoteCacheClient client = new RemoteCacheClient();

		Console console = System.console();
		String inputString = null;
		while (!"quit".equals(inputString)) {
			System.out.println("Please select one of these options:");
			System.out.println("- populate (populates the cache with random values");
			System.out.println("- put (put a key-value pair in the cache)");
			System.out.println("- get (get a key-value pair from the cache)");
			System.out.println("- addListener (adds a remote listener to the cache.)");
			System.out.println("- removeListener (adds a remote listener to the cache.)");
			System.out.println("- addCustomListener (adds a remote listener to the cache.)");
			System.out.println("- removeCustomListener (adds a remote listener to the cache.)");
			System.out.println("- keySet (returns the keyset of the cache.)");
			System.out.println("- quit (exit this application)");
			inputString = console.readLine();
			if ("populate".equals(inputString)) {
				client.populateCache();
			} else if ("put".equals(inputString)) {
				System.out.println("Enter key:");
				String key = System.console().readLine();
				System.out.println("Enter value:");
				String value = System.console().readLine();
				client.put(key, value);
			} else if ("get".equals(inputString)) {
				System.out.println("Enter key:");
				String key = System.console().readLine();
				System.out.println("Value: " + client.get(key.trim()));
			} else if ("addListener".equals(inputString)) {
				client.addClientListener(client.listener);
				//client.addClientListener(client.customListener);
			} else if ("removeListener".equals(inputString)) {
				client.removeClientListener(client.listener);
				//client.removeClientListener(client.customListener);
			} else if ("addCustomListener".equals(inputString)) {
				client.addClientListener(client.customListener);
				//client.addClientListener(client.customListener);
			} else if ("removeCustomListener".equals(inputString)) {
				client.removeClientListener(client.customListener);
				//client.removeClientListener(client.customListener);
			} else if ("keySet".equals(inputString)) {
				System.out.println("KeySet: \n");
				Set keySet = client.getKeySet();
				for (Object nextKey: keySet) {
					System.out.println(nextKey + "\n");
				}
			}else if ("quit".equals(inputString)) {
				LOGGER.info("Shutting down RemoteCacheClient.");
			} else {
				System.out.println("Unrecognized option: '" + inputString + "'.");
			}
		}
	}

	private void populateCache() {
		LOGGER.info("Populating cache with random values.");
		int numberOfEntries = 10;
		for (int counter = 0; counter < numberOfEntries; counter++) {
			String uuid = UUID.randomUUID().toString();
			cache.put(uuid, "My data object for uuid: " + uuid);
		}
	}

	private void put(String key, String value) {
		LOGGER.info("Storing key:value pair '" + key + ":" + value + "' in the cache.");
		cache.put(key, value);
	}
	
	private String get(Object key) {
		LOGGER.info("Getting value for key: " + key);
		return cache.get(key);
	}
	
	private void addClientListener(Object listener) {
		cache.addClientListener(listener);
	}
	
	private void removeClientListener(Object listener) {
		cache.removeClientListener(listener);
	}
	
	private Set getKeySet() {
		return cache.keySet();
	}

	/*
	 * Does not work in DIST Cache with 'compatibility-mode enabled, See ISPN-3785. private int getNumberOfEntriesInCache() {
	 * RemoteCache<String, String> remoteCache = cacheManager.getCache("MyCoolCache"); long startTime = System.currentTimeMillis();
	 * Set<String> keySet = remoteCache.keySet(); System.out.println("Got remote keyset in: " + (System.currentTimeMillis() - startTime));
	 * return keySet.size(); }
	 */
}
