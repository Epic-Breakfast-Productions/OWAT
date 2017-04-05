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
	 * Basic constructor. Sets the type to NA.
	 */
	public NodeList() {
		super();
	}
}
