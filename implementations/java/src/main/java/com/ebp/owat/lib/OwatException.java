package com.ebp.owat.lib;

/**
 * Created by stewy on 3/23/17.
 */
public class OwatException extends RuntimeException {
	public OwatException() { super(); }
	
	public OwatException(String s) {
		super(s);
	}
	
	public OwatException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatException(Throwable throwable) {
		super(throwable);
	}
}
