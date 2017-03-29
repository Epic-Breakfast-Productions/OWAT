package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.OwatException;
import com.ebp.owat.lib.dataStructure.OwatStructureException;

/**
 * Exception to describe an issue with a Data Structure.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatMatrixException extends OwatStructureException {
	public OwatMatrixException() {super();}
	
	public OwatMatrixException(String s) {
		super(s);
	}
	
	public OwatMatrixException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatMatrixException(Throwable throwable) {
		super(throwable);
	}
}
