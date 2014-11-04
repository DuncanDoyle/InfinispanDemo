package org.jboss.ddoyle.infinispan.demo.xsite.event;

/**
 * 
 * @author <a href="mailto:duncan.doyle@redhat.com">Duncan Doyle</a>
 *
 */
public class LoggingXSiteFailureEvent implements XSiteFailureEvent {
	
	private final String message;
	
	public LoggingXSiteFailureEvent(final String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
