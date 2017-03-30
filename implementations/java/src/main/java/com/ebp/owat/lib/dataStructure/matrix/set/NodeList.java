package com.ebp.owat.lib.dataStructure.matrix.set;

import com.ebp.owat.lib.dataStructure.node.Node;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.LinkedList;

/**
 * Defines a list of nodes, typically for holding a whole row/column of a matrix.
 *
 * Created by Greg Stewart on 3/24/17.
 */
public class NodeList<T extends Node> extends LinkedList<T> {
	/**
	 * The type of list this is.
	 */
	private final Type type;
	
	/**
	 * The types of lists a NodeList can be.
	 */
	public enum Type {
		ROW,COL,NA
	}
	
	/**
	 * Creates an empty NodeList and sets its type.
	 * @param typeIn The type to set this to.
	 */
	public NodeList(Type typeIn){
		this.type = typeIn;
	}
	
	/**
	 * Shifts the node values down or up.
	 *
	 * @param numToShiftBy The number of increments to shift the nodes by.
	 * @return This NodeList object
	 */
	public NodeList shift(BigInteger numToShiftBy){
		if(numToShiftBy.signum() == 1){
			//positive movement
			//TODO
			
		}else{
			//negative movement
			//TODO
		}
		return this;
	}
	
	/**
	 * Gets the type of list this is.
	 *
	 * @return The type of list this is.
	 */
	public Type getType(){
		return this.type;
	}
}
