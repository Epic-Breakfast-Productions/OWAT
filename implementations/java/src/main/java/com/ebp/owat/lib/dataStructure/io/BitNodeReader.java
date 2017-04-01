package com.ebp.owat.lib.dataStructure.io;

import com.ebp.owat.lib.dataStructure.matrix.set.NodeList;
import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.node.Node;

import java.io.File;
import java.io.InputStream;

/**
 * A BitNode reader to dead in BitNodes
 *
 * Created by Greg Stewart on 3/30/17.
 */
public class BitNodeReader extends NodeReader<BitNode> {
	
	/**
	 * Creates this node creator.
	 *
	 * @param streamIn The stream to use to get the information.
	 */
	public BitNodeReader(InputStream streamIn) {
		super(streamIn);
	}
	
	/**
	 * Creates this node creator with a file.
	 *
	 * @param fileIn The file to get the input stream from.
	 */
	public BitNodeReader(File fileIn) {
		super(fileIn);
	}
	
	/**
	 * Creates a NodeReader using a string describing the location of a file to read from.
	 *
	 * @param fileLocation The location of the file to read from.
	 */
	public BitNodeReader(String fileLocation) {
		super(fileLocation);
	}
	
	/**
	 * Gets the next node available.
	 *
	 * @return The next node received.
	 */
	@Override
	public Node getNextNode() {
		//TODO
		return null;
	}
	
	/**
	 * Gets all nodes available from the stream.
	 *
	 * @return All of the nodes available from the stream.
	 */
	@Override
	public NodeList<Node> getAllNodes() {
		//TODO
		return null;
	}
}
