package com.ebp.owat.lib.datastructure.node.value;

import com.ebp.owat.lib.datastructure.node.OwatNodeException;

/**
 * Exception to describe an issue with a Node Value.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatNodeValueException extends OwatNodeException {
	public OwatNodeValueException() {super();}
	
	public OwatNodeValueException(String s) {
		super(s);
	}
	
	public OwatNodeValueException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatNodeValueException(Throwable throwable) {
		super(throwable);
	}
}
