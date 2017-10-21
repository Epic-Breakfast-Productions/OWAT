package com.ebp.owat.lib.datastructure.set;

import com.ebp.owat.lib.datastructure.node.OwatNodeException;

/**
 * Exception to describe an issue with a Node Set.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class OwatNodeSetException extends OwatNodeException {
	public OwatNodeSetException() {super();}
	
	public OwatNodeSetException(String s) {
		super(s);
	}
	
	public OwatNodeSetException(String s, Throwable throwable) {
		super(s, throwable);
	}
	
	public OwatNodeSetException(Throwable throwable) {
		super(throwable);
	}
}
