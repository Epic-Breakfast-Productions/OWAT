package com.ebp.owat.lib.dataStructure.matrix.utils;

import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;

/**
 * Exception to describe an issue with a Data Structure.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatMatrixUtilException extends OwatMatrixException {
	public OwatMatrixUtilException() {super();}
	
	public OwatMatrixUtilException(String s) {
		super(s);
	}
	
	public OwatMatrixUtilException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatMatrixUtilException(Throwable throwable) {
		super(throwable);
	}
}
