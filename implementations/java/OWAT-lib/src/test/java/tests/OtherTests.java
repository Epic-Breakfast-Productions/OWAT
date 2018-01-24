package tests;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * These are miscellaneous tests that do not necessarily have anything to do with actual OWAT code.
 *
 * Created by Greg Stewart on 7/8/2017.
 */
public class OtherTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(OtherTests.class);
	
	@Test
	public void testLogger(){
		LOGGER.info("Testing for the configuration of the logs.");
		LOGGER.info("Info level log");
		LOGGER.debug("Debug level log");
		LOGGER.warn("Warn level log");
		LOGGER.error("Error level log");
		LOGGER.trace("Trace level log");
	}
	
}
