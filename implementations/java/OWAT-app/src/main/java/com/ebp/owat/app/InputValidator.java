package com.ebp.owat.app;

import java.io.File;

public class InputValidator {
	public static final String DESC_SCRAMBLED_DATA_OUTPUT = "scrambled data output file";
	public static final String DESC_SCRAMBLED_DATA_INPUT = "scrambled data input file";
	public static final String DESC_SCRAMBLE_DATA_INPUT = "scrambled data input file";
	public static final String DESC_DESCRAMBLED_DATA_OUTPUT = "descrambled data output file";
	public static final String DESC_KEY = "key file";
	public static final String CSV_FILE = "csv stats file";

	//TODO:: rework these to better handle files, using the validation methods in MainGuiApp

	public static void ensureCanWriteToFile(File file, String description) throws IllegalArgumentException {
		if(file == null){
			throw new IllegalArgumentException("No "+ description +" given. Cannot continue.");
		}
		if(file.exists()){
			throw new IllegalArgumentException(description +" ("+file.getPath()+") given already exists.");
		}
	}

	public static void ensureCanReadFromFile(File file, String description) throws IllegalArgumentException {
		if(file == null){
			throw new IllegalArgumentException("No "+ description +" given. Cannot continue.");
		}
		if(!file.exists()){
			throw new IllegalArgumentException(description +" ("+file.getPath()+") given does not exist.");
		}
	}
}
