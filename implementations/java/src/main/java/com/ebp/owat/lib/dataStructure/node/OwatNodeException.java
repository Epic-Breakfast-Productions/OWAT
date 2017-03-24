package com.ebp.owat.lib.dataStructure.node;

import com.ebp.owat.lib.dataStructure.OwatStructureException;

/**
 * Created by stewy on 3/23/17.
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
