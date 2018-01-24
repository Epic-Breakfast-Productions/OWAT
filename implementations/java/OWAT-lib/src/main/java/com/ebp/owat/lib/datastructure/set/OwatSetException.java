package com.ebp.owat.lib.datastructure.set;

import com.ebp.owat.lib.datastructure.value.OwatValueException;

/**
 * Exception to describe an issue with a Node Set.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatSetException extends OwatValueException {
	public OwatSetException() {super();}
	
	public OwatSetException(String s) {
		super(s);
	}
	
	public OwatSetException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatSetException(Throwable throwable) {
		super(throwable);
	}
}
