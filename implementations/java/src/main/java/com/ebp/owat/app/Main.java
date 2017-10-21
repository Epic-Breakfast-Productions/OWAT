package com.ebp.owat.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;

import static com.ebp.owat.app.Globals.PROPERTIES;

/**
 * Created by Greg Stewart on 5/27/17.
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	public static void main(String[] args){
		new Globals();//init globals, no need to keep object.
		
		LOGGER.debug("Properties read in:");
		Enumeration<?> e = PROPERTIES.propertyNames();
		while (e.hasMoreElements()) {
			String key = (String) e.nextElement();
			String value = PROPERTIES.getProperty(key);
			LOGGER.debug("Key : \"{}\", Value : \"{}\"", key, value);
		}
		
		LOGGER.info("{} - App v{}, Lib v{}", PROPERTIES.getProperty("app.name"), PROPERTIES.getProperty("app.version"), PROPERTIES.getProperty("lib.version"));
		
	}
}
