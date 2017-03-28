package com.ebp.owat.lib.dataStructure.node;

import com.ebp.owat.lib.dataStructure.node.value.BitValue;

/**
 *  A Node that holds a BitValue.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class BitNode extends Node<BitValue> {
	/**
	 * Basic constructor. Does not set any values.
	 */
	public BitNode() {
	}
	
	/**
	 * Constructor that sets this node's value.
	 *
	 * @param nodeValue The value to set this node to.
	 */
	public BitNode(BitValue nodeValue) {
		super(nodeValue);
	}
}
