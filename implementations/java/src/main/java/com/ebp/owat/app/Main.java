package com.ebp.owat.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created by Greg Stewart on 5/27/17.
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args){
		LOGGER.info("OWAT");
		
		LOGGER.info("Info level log");
		LOGGER.debug("Debug level log");
		LOGGER.warn("Warn level log");
		LOGGER.error("Error level log");
		LOGGER.trace("Trace level log");
	}
}
