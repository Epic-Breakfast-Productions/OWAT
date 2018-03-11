package com.ebp.owat.app;

import java.io.File;

public class InputValidator {

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
