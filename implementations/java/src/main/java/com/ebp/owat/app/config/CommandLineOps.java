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

import static org.kohsuke.args4j.OptionHandlerFilter.ALL;

/**
 *
 */
public class CommandLineOps {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineOps.class);
	
	private final String[] argsGotten;
	
	@Option(name="-t", aliases = {"--test"},usage = "test thing")
	private boolean test = true;
	
	@Option(name="-g", aliases = {"--gui-mode"},usage = "Runs the gui interface.")
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
			
			// you can parse additional arguments if you want.
			// parser.parseArgument("more","args");
			
			// after parsing arguments, you should check
			// if enough arguments are given.
			if( arguments.isEmpty() )
				throw new CmdLineException(parser,"No argument is given");
			
		} catch( CmdLineException e ) {
			// if there's a problem in the command line,
			// you'll get this exception. this will report
			// an error message.
			System.err.println(e.getMessage());
			System.err.println("java SampleMain [options...] arguments...");
			// print the list of available options
			parser.printUsage(System.err);
			System.err.println();
			
			// print option sample. This is useful some time
			System.err.println("  Example: java SampleMain"+parser.printExample(ALL));
			
			return;
		}
	}
	
	public void printArgs(){
		LOGGER.info("Command line arguments given: {}", Arrays.asList(this.argsGotten));
	}
	
	public boolean runGui(){
		return this.guiMode;
	}
}
