package com.ebp.owat.lib.utils.rand;

import com.ebp.owat.lib.utils.OwatUtilException;

/**
 * Exception to describe an issue with a Random generator.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatRandException extends OwatUtilException {
	public OwatRandException() {super();}
	
	public OwatRandException(String s) {
		super(s);
	}
	
	public OwatRandException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatRandException(Throwable throwable) {
		super(throwable);
	}
}
