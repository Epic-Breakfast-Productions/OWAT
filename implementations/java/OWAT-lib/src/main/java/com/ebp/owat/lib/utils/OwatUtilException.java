package com.ebp.owat.lib.utils;

import com.ebp.owat.lib.OwatException;

/**
 * Exception to describe an issue with a Util.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatUtilException extends OwatException {
	public OwatUtilException() {super();}
	
	public OwatUtilException(String s) {
		super(s);
	}
	
	public OwatUtilException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatUtilException(Throwable throwable) {
		super(throwable);
	}
}
