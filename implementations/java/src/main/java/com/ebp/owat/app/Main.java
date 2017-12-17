package com.ebp.owat.app;

import com.ebp.owat.app.config.CommandLineOps;
import com.ebp.owat.app.config.Globals;
import com.ebp.owat.app.gui.MainGuiApp;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;

import static com.ebp.owat.app.config.Globals.*;

/**
 * Created by Greg Stewart on 5/27/17.
 */
public class Main {
	private static final Logger LOGGER = LoggerFactory.getLogger(Main.class);
	
	private static CommandLineOps COMMAND_LINE_OPS;
	
	public static void main(String[] args){
		new Globals();//init globals, no need to keep object.
		
		LOGGER.debug("Properties read in:");
		for(PropertyKey curKey : Globals.PropertyKey.values()){
			LOGGER.debug("Key : \"{}\", Value : \"{}\"", curKey, Globals.getProp(curKey));
		}
		
		LOGGER.info(
			"{} - App v{} ({}), Lib v{}",
			Globals.getProp(PropertyKey.APP_NAME_PROP_KEY),
			Globals.getProp(PropertyKey.APP_VERSION_PROP_KEY),
			Globals.getProp(PropertyKey.APP_VERSION_NAME_PROP_KEY),
			Globals.getProp(PropertyKey.LIB_VERSION_PROP_KEY)
		);
		
		COMMAND_LINE_OPS = new CommandLineOps(args);
		
		COMMAND_LINE_OPS.printArgs();
		
		if(COMMAND_LINE_OPS.runGui()) {
			LOGGER.info("Starting GUI.");
			MainGuiApp.main(args);
		}
	}
}
