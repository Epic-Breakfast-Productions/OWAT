package com.ebp.owat.lib.scrambler;

import com.ebp.owat.lib.OwatException;

/**
 * Exception to describe an issue with a Scrambler.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatScramblerException extends OwatException {
	public OwatScramblerException() { super(); }
	
	public OwatScramblerException(String s) {
		super(s);
	}
	
	public OwatScramblerException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatScramblerException(Throwable throwable) {
		super(throwable);
	}
}
