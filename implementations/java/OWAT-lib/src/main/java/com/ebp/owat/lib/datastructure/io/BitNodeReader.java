package com.ebp.owat.lib.datastructure.io;

import com.ebp.owat.lib.datastructure.set.LongLinkedList;
import com.ebp.owat.lib.datastructure.value.BitValue;

import java.io.File;
import java.io.InputStream;

/**
 * A BitValue reader to dead in BitNodes
 *
 * Created by Greg Stewart on 3/30/17.
 */
public class BitNodeReader extends NodeReader<BitValue> {
	
	/**
	 * Creates this value creator.
	 *
	 * @param streamIn The stream to use to get the information.
	 */
	public BitNodeReader(InputStream streamIn) {
		super(streamIn);
	}
	
	/**
	 * Creates this value creator with a file.
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
	 * Gets the nextLong value available.
	 *
	 * @return The nextLong value received.
	 */
	@Override
	public BitValue getNextNode() {
		//TODO
		return null;
	}
	
	/**
	 * Gets all nodes available from the stream.
	 *
	 * @return All of the nodes available from the stream.
	 */
	@Override
	public LongLinkedList<BitValue> getAllNodes() {
		//TODO
		return null;
	}
}
