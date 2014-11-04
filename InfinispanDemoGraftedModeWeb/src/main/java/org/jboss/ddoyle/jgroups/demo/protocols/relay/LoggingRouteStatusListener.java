package org.jboss.ddoyle.jgroups.demo.protocols.relay;

import org.jgroups.protocols.relay.RouteStatusListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author <a href="mailto:ddoyle@redhat.com">Duncan Doyle</a>
 */
public class LoggingRouteStatusListener implements RouteStatusListener {

	/**
	 * Logger.
	 */
	private static final Logger LOGGER = LoggerFactory.getLogger(LoggingRouteStatusListener.class);

	@Override
	public void sitesUp(String... sites) {
		StringBuilder logBuilder = new StringBuilder("Sites up: ");

		for (int i = 0; i < sites.length; i++) {
			logBuilder.append(sites[i]);
			if (i < (sites.length - 1)) {
				logBuilder.append(", ");
			} else {
				logBuilder.append(".");
			}
		}
		LOGGER.warn(logBuilder.toString());

	}

	@Override
	public void sitesDown(String... sites) {
		StringBuilder logBuilder = new StringBuilder("Sites down: ");

		for (int i = 0; i < sites.length; i++) {
			logBuilder.append(sites[i]);
			if (i < (sites.length - 1)) {
				logBuilder.append(", ");
			} else {
				logBuilder.append(".");
			}
		}
		LOGGER.warn(logBuilder.toString());

	}

}
