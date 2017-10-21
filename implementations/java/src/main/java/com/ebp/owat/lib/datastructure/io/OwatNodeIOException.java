package com.ebp.owat.lib.datastructure.io;

import com.ebp.owat.lib.datastructure.OwatStructureException;

/**
 * Exception to describe an issue with a Node.
 *
 * Created by Greg Stewart on 3/30/17.
 */
public class OwatNodeIOException extends OwatStructureException{
	public OwatNodeIOException() {super();}
	
	public OwatNodeIOException(String s) {
		super(s);
	}
	
	public OwatNodeIOException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatNodeIOException(Throwable throwable) {
		super(throwable);
	}
}
