package com.ebp.owat.lib.runner.utils;

import java.util.Random;

/**
 * Enum to describe the types of matrices
 */
public enum MatrixMode {
	HASHED("hashed"),
	//LINKED("linked"),
	ARRAY("array");

	public final String name;

	MatrixMode(String name){
		this.name = name;
	}

	/**
	 * Determines which matrix type to use based on the number of elements in the set to hold.
	 * @param n The number of elements to hold
	 * @return The matrix mode best to use.
	 */
	public static MatrixMode determineModeToUse(long n){
		//TODO:: use statistics to determine how to do this intelligently
		if(new Random().nextBoolean()){
			return ARRAY;
		}
		if(new Random().nextBoolean()){
			//return LINKED;
		}
		return HASHED;
	}
}
