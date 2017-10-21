package com.ebp.owat.app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Globals {
	private static final Logger LOGGER = LoggerFactory.getLogger(Globals.class);

	/** The location of the properties file in the resources folder. */
	public static final String PROPERTY_FILE_LOC = "properties.properties";
	
	/** The properties read from the properties file. To populate, call {@link Globals the constructor} */
	public static final Properties PROPERTIES = new Properties();
	
	/**
	 * Only need to call once. Populates the properties. If already populated, doesn't re-populate.
	 */
	public Globals(){
		if(!PROPERTIES.isEmpty()){
			return;
		}
		try(InputStream is = getClass().getClassLoader().getResourceAsStream(PROPERTY_FILE_LOC)){
			PROPERTIES.load(is);
		} catch (FileNotFoundException e) {
			LOGGER.error("Properties file not found. Cannot read properties in. Error: ", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("Error reading properties file. Cannot read properties in. Error: ", e);
			throw new RuntimeException(e);
		}
	}
}
