package com.ebp.owat.lib.dataStructure.set.node;

import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.set.BigLinkedList;

import java.math.BigInteger;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * Defines a list of nodes, typically for holding a whole row/column of a matrix.
 *
 * Created by Greg Stewart on 3/24/17.
 */
public class NodeList<T extends Node> extends BigLinkedList<T> {
	/**
	 * Creates an empty NodeList and sets its type.
	 * @param typeIn The type to set this to.
	 */
	public NodeList(Type typeIn){
		super(typeIn);
	}
	
	/**
	 * Shifts the node values down or up.
	 *
	 * @param numToShiftBy The number of increments to shift the nodes by.
	 */
	public void shiftValues(BigInteger numToShiftBy){
		if(numToShiftBy.signum() == 1){
			//positive movement
			//TODO
			
		}else{
			//negative movement
			//TODO
		}
	}
}
