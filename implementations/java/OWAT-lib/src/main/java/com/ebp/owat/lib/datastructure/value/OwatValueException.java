package com.ebp.owat.lib.datastructure.value;

import com.ebp.owat.lib.datastructure.OwatStructureException;

/**
 * Exception to describe an issue with a Node.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatValueException extends OwatStructureException{
	public OwatValueException() {super();}
	
	public OwatValueException(String s) {
		super(s);
	}
	
	public OwatValueException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatValueException(Throwable throwable) {
		super(throwable);
	}
}
