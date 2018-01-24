package com.ebp.owat.lib;

/**
 * Exception to describe an issue with an element of OWAT.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatException extends RuntimeException {
	public OwatException() { super(); }
	
	public OwatException(String s) {
		super(s);
	}
	
	public OwatException(Throwable throwable) {
		super(throwable);
	}
	
	public OwatException(String s, Throwable throwable) {
		super(s, throwable);
	}
}
