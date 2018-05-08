package com.ebp.owat.lib;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * These are miscellaneous tests that do not necessarily have anything to do with actual OWAT code.
 *
 * Created by Greg Stewart on 7/8/2017.
 */
public class OtherTests {
	private static final Logger LOGGER = LoggerFactory.getLogger(OtherTests.class);

	/** The location of the properties file in the resources folder. */
	public static final String PROPERTY_FILE_LOC = "project.properties";
	
	@Test
	public void testLogger(){
		try(InputStream is = OtherTests.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_LOC)){
			if(is == null){
				throw new FileNotFoundException("Input stream was null, assuming file is not present.");
			}
			Properties properties = new Properties();
			properties.load(is);

			LOGGER.info(
				"Library Version - v{} Built: {}",
				properties.getProperty("lib.version"),
				properties.getProperty("lib.buildtime")
			);
		} catch (FileNotFoundException e) {
			LOGGER.error("Properties file not found. Cannot read properties in. Error: ", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("Error reading properties file. Cannot read properties in. Error: ", e);
			throw new RuntimeException(e);
		}

		LOGGER.info("Testing for the configuration of the logs.");
		LOGGER.info("Info level log");
		LOGGER.debug("Debug level log");
		LOGGER.warn("Warn level log");
		LOGGER.error("Error level log");
		LOGGER.trace("Trace level log");
	}
	
}
