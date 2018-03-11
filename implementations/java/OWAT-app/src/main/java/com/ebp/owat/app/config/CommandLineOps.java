package com.ebp.owat.app.config;

import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 *
 */
public class CommandLineOps {
	private static final Logger LOGGER = LoggerFactory.getLogger(CommandLineOps.class);
	
	private final String[] argsGotten;

	@Option(name="-m", aliases={"--mode"}, usage="The mode that this will run with. Required. ")
	private RunMode runMode = null;

	@Option(name="-i", aliases={"--input"}, usage="Input data straight from the command line.")
	private String inputString = null;

	@Option(name="-f", aliases={"--input-file"}, usage="Input data from a file.")
	private File inputFile = null;

	@Option(name = "-o", aliases = {"--output-file"}, usage = "Where to output the data.")
	private File dataOutputFile = null;

	@Option(name = "-k", aliases = {"--key-file"}, usage = "The key file.")
	private File keyFile = null;

	@Option(name = "-h", aliases = {"--help"}, usage = "Show this help dialogue.")
	private boolean showHelp = false;

	public enum RunMode {
		SCRAMBLE,
		DESCRAMBLE,
		GUI;
	}

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
			if(this.showHelp){
				System.out.println("Available options:");
				parser.printUsage(System.out);
				System.exit(0);
			}
			this.ensureReadyForRun(parser);
		} catch( CmdLineException e ) {
			System.err.println("Error parsing arguments:");
			System.err.println("\t"+e.getMessage());
			System.err.println("");
			// print the list of available options
			System.err.println("Available options:");
			parser.printUsage(System.err);
			System.err.println();
			System.exit(1);
		}

	}

	private void ensureHaveInputData(CmdLineParser parser) throws CmdLineException {
		if(this.inputString == null && this.inputFile == null){
			throw new CmdLineException(parser, new IllegalArgumentException("No input data given. Cannot continue."));
		}
		if(this.inputString != null && this.inputFile != null){
			throw new CmdLineException(parser, new IllegalArgumentException("Got both a input file and string. Don't know which to use."));
		}
		if(this.inputFile != null){
			this.ensureCanReadFromFile(this.inputFile, "data input file", parser);
		}
	}

	private void ensureCanWriteToFile(File file, String description, CmdLineParser parser) throws CmdLineException {
		if(file == null){
			throw new CmdLineException(parser, new IllegalArgumentException("No "+ description +" given. Cannot continue."));
		}
		if(this.inputFile.exists()){
			throw new CmdLineException(parser, new IllegalArgumentException(description +" file given already exists."));
		}
		if(!this.inputFile.canWrite()){
			throw new CmdLineException(parser, new IllegalArgumentException("Cannot write to "+description+" given."));
		}
	}

	private void ensureCanReadFromFile(File file, String description, CmdLineParser parser) throws CmdLineException {
		if(file == null){
			throw new CmdLineException(parser, new IllegalArgumentException("No "+ description +" given. Cannot continue."));
		}
		if(!this.inputFile.exists()){
			throw new CmdLineException(parser, new IllegalArgumentException(description +" file given already exists."));
		}
		if(!this.inputFile.canRead()){
			throw new CmdLineException(parser, new IllegalArgumentException("Cannot write to "+description+" given."));
		}
	}

	private void ensureReadyForRun(CmdLineParser parser) throws CmdLineException {
		switch (this.runMode){
			case GUI:
				break;
			case SCRAMBLE:
				//ensure we have data to input
				this.ensureHaveInputData(parser);
				//ensure we can write out to
				this.ensureCanWriteToFile(this.dataOutputFile, "output file", parser);
				this.ensureCanWriteToFile(this.keyFile, "key file", parser);
				break;
			case DESCRAMBLE:
				//ensure key file exists
				this.ensureCanReadFromFile(this.keyFile, "key file", parser);

				//ensure we have data to input
				this.ensureCanReadFromFile(this.inputFile, "data input file", parser);

				//ensure we can write out
				this.ensureCanWriteToFile(this.inputFile, "data output file", parser);
				break;
			default:
				throw new CmdLineException(parser, new IllegalArgumentException("Must specify a mode to run."));
		}
	}
	
	public void printArgs(){
		LOGGER.info("Command line arguments given: {}", Arrays.asList(this.argsGotten));
	}
	
	public boolean runGui(){
		return this.runMode == RunMode.GUI;
	}
}
