package com.ebp.owat.app.config;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class CommandLineOps {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineOps.class);
	
	private final String[] argsGotten;
	
	@Option(name="-g", aliases = {"--gui-mode"},usage = "Flag that runs the gui interface. No other params needed.")
	private boolean guiMode = false;
	
	// receives other command line parameters than options
	@Argument
	private static List<String> arguments = new ArrayList<>();
	
	public CommandLineOps(String[] args){
		this.argsGotten = Arrays.copyOf(args, args.length);
		
		CmdLineParser parser = new CmdLineParser(this);
		
		try {
			// parse the arguments.
			parser.parseArgument(this.argsGotten);
			
			if( this.argsGotten.length == 0 ) {
				throw new CmdLineException(parser, new IllegalArgumentException("No argument was given"));
			}
		} catch( CmdLineException e ) {
			System.err.println("Error parsing arguments:");
			System.err.println("\t"+e.getMessage());
			System.err.println("");
			// print the list of available options
			System.err.println("Available options:");
			parser.printUsage(System.err);
			System.err.println();
		}
	}
	
	public void printArgs(){
		LOGGER.info("Command line arguments given: {}", Arrays.asList(this.argsGotten));
	}
	
	public boolean runGui(){
		return this.guiMode;
	}
}
