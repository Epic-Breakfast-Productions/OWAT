package com.ebp.owat.lib.dataStructure.matrix.deScrambling;

import com.ebp.owat.lib.dataStructure.io.NodeReader;
import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.node.Node;

/**
 * Describes what a DeScrambling matrix should be/do.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public abstract class DeScramblingMatrix<T extends Node> extends Matrix {
	/**
	 * Constructor to build using a provided node.
	 * <p>
	 * Assumes that the node given is the top right.
	 *
	 * @param nodeIn The node to use to build this object.
	 */
	public DeScramblingMatrix(Node nodeIn) {
		super(nodeIn);
	}
	
	/**
	 * Constructor to set the stream reader and optionally read in the data right away.
	 *
	 * @param readerIn The reader to use to read the data in.
	 * @param readIn   If we are to read the data in immediately or not.
	 */
	public DeScramblingMatrix(NodeReader readerIn, boolean readIn) {
		super(readerIn, readIn);
	}
	
	/**
	 * Constructor to set the stream reader in and read in the data into nodes.
	 *
	 * @param readerIn The reader to get the nodes from.
	 */
	public DeScramblingMatrix(NodeReader readerIn) {
		super(readerIn);
	}
}