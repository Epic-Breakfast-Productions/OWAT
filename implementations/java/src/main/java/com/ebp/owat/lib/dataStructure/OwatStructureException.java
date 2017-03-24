package com.ebp.owat.lib.dataStructure;

import com.ebp.owat.lib.OwatException;

/**
 * Created by stewy on 3/23/17.
 */
public class OwatStructureException extends OwatException {
	public OwatStructureException() {super();}
	
	public OwatStructureException(String s) {
		super(s);
	}
	
	public OwatStructureException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatStructureException(Throwable throwable) {
		super(throwable);
	}
}
