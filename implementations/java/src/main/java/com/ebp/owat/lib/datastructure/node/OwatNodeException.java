package com.ebp.owat.lib.datastructure.node;

import com.ebp.owat.lib.datastructure.OwatStructureException;

/**
 * Exception to describe an issue with a Node.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatNodeException extends OwatStructureException{
	public OwatNodeException() {super();}
	
	public OwatNodeException(String s) {
		super(s);
	}
	
	public OwatNodeException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatNodeException(Throwable throwable) {
		super(throwable);
	}
}
