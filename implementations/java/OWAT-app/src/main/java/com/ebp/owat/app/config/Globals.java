package com.ebp.owat.app.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Enumeration;
import java.util.Properties;

public class Globals {
	private static final Logger LOGGER = LoggerFactory.getLogger(Globals.class);

	/** The location of the properties file in the resources folder. */
	public static final String PROPERTY_FILE_LOC = "properties.properties";
	
	/** The properties read from the properties file. To populate, call {@link Globals the constructor} */
	private static final Properties PROPERTIES = new Properties();
	
	static {
		try(InputStream is = Globals.class.getClassLoader().getResourceAsStream(PROPERTY_FILE_LOC)){
			PROPERTIES.load(is);
		} catch (FileNotFoundException e) {
			LOGGER.error("Properties file not found. Cannot read properties in. Error: ", e);
			throw new RuntimeException(e);
		} catch (IOException e) {
			LOGGER.error("Error reading properties file. Cannot read properties in. Error: ", e);
			throw new RuntimeException(e);
		}
	}
	
	public static String getProp(PropertyKey property){
		return PROPERTIES.getProperty(property.propKey);
	}
	
	public static Enumeration getPropNames(){
		return PROPERTIES.propertyNames();
	}
	
	public enum PropertyKey {
		APP_NAME_PROP_KEY("app.name"),
		APP_VERSION_PROP_KEY("app.version"),
		APP_VERSION_NAME_PROP_KEY("app.version.nickname"),
		LIB_VERSION_PROP_KEY("lib.version");
		
		public final String propKey;
		
		PropertyKey(String propKey){
			this.propKey = propKey;
		}
	}
}
