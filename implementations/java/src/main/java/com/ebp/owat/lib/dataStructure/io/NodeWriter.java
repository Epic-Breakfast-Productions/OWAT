package com.ebp.owat.lib.dataStructure.io;

import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.node.value.NodeValue;

/**
 * Abstract class to define the behaviors of a NodeWriter.
 *
 * Created by Greg Stewart on 3/30/17.
 */
public abstract class NodeWriter<T extends Node<NodeValue>> {
	//TODO:: abstract methods to write out nodes using an OutputStream.
	//TODO:: possibly extend OutputStream?
}
