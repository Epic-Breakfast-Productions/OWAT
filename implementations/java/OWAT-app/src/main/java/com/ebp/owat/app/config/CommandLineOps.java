package com.ebp.owat.app.config;

import com.ebp.owat.app.InputValidator;
import org.kohsuke.args4j.Argument;
import org.kohsuke.args4j.CmdLineException;
import org.kohsuke.args4j.CmdLineParser;
import org.kohsuke.args4j.Option;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static com.ebp.owat.app.InputValidator.*;

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

	@Option(name = "-c", aliases = {"--csv-stats"}, usage = "For outputting timing data to a CSV file.")
	private File csvFile = null;

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
				throw new IllegalArgumentException("No arguments given");
			}
			if(this.showHelp){
				System.out.println("Available options:");
				parser.printUsage(System.out);
				System.exit(0);
			}
			this.ensureReadyForRun();
		} catch( CmdLineException|IllegalArgumentException e ) {
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

	public boolean outputCsvStats(){
		return this.csvFile == null;
	}

	private void ensureHaveInputData() throws IllegalArgumentException {
		if(this.inputString == null && this.inputFile == null){
			throw new IllegalArgumentException("No input data given. Cannot continue.");
		}
		if(this.inputString != null && this.inputFile != null){
			throw new IllegalArgumentException("Got both a input file and string. Don't know which to use.");
		}
		if(this.inputFile != null){
			InputValidator.ensureCanReadFromFile(this.inputFile, DESC_SCRAMBLE_DATA_INPUT);
		}
	}

	private void ensureReadyForRun() throws IllegalArgumentException {
		switch (this.runMode){
			case GUI:
				break;
			case SCRAMBLE:
				//ensure we have data to input
				this.ensureHaveInputData();
				//ensure we can write out to
				InputValidator.ensureCanWriteToFile(this.dataOutputFile, DESC_SCRAMBLED_DATA_OUTPUT);
				InputValidator.ensureCanWriteToFile(this.keyFile, DESC_KEY);
				if(this.outputCsvStats()){
					InputValidator.ensureCanWriteToFile(csvFile, CSV_FILE);
				}
				break;
			case DESCRAMBLE:
				//ensure key file exists
				InputValidator.ensureCanReadFromFile(this.keyFile, DESC_KEY);

				//ensure we have data to input
				InputValidator.ensureCanReadFromFile(this.inputFile, DESC_SCRAMBLE_DATA_INPUT);

				//ensure we can write out
				InputValidator.ensureCanWriteToFile(this.dataOutputFile, DESC_DESCRAMBLED_DATA_OUTPUT);
				if(this.outputCsvStats()){
					InputValidator.ensureCanWriteToFile(csvFile, CSV_FILE);
				}
				break;
			default:
				throw new IllegalArgumentException("Must specify a mode to run.");
		}
	}
	
	public void printArgs(){
		LOGGER.info("Command line arguments given: {}", Arrays.asList(this.argsGotten));
	}

	public RunMode getRunMode() {
		return runMode;
	}

	public InputStream getKeyInputStream() throws FileNotFoundException {
		return new FileInputStream(this.keyFile);
	}

	public OutputStream getKeyOutputStream() throws FileNotFoundException {
		return new FileOutputStream(this.keyFile);
	}

	public InputStream getDataInputStream() throws FileNotFoundException {
		if(this.inputFile != null){
			return new FileInputStream(this.inputFile);
		}else{
			return new ByteArrayInputStream(this.inputString.getBytes(StandardCharsets.UTF_8));
		}
	}

	public OutputStream getDataOutputStream() throws FileNotFoundException {
		return new FileOutputStream(this.dataOutputFile);
	}

	public OutputStream getCsvStatsOutputStream() throws FileNotFoundException {
		return new FileOutputStream(this.csvFile);
	}
}
