package com.ebp.owat.lib.dataStructure.set;

import com.ebp.owat.lib.dataStructure.node.Node;

/**
 * Created by stewy on 5/28/17.
 * TODO:: docs and flesh out
 */
public class NodeList<Node> extends LongLinkedList<Node> {
	/**
	 * The types of lists a NodeList can be.
	 */
	public enum Type {
		ROW,COL,NA
	}
	
	/**
	 * The type of list this is.
	 */
	private final Type type;
	
	public NodeList(){
		this.type= Type.NA;
	}
	
	public NodeList(Type t){
		this.type = t;
	}
}
