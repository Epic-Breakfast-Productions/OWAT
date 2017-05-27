package com.ebp.owat.lib.dataStructure.io;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.dataStructure.node.Node;

import java.io.*;

/**
 * Abstract class to define the behaviors of a node reader.
 *
 * Created by Greg Stewart on 3/30/17.
 */
public abstract class NodeReader<T extends Node> {
	/** The input stream to get the information from. */
	private InputStream inStream;
	
	/**
	 * Creates this node creator.
	 * @param streamIn The stream to use to get the information.
	 */
	public NodeReader(InputStream streamIn){
		this.setInputStream(streamIn);
	}
	
	/**
	 * Creates this node creator with a file.
	 * @param fileIn The file to get the input stream from.
	 */
	public NodeReader(File fileIn){
		if(!fileIn.canRead()){
			throw new OwatNodeIOException("File given cannot be read from.");
		}
		try{
			this.setInputStream(new FileInputStream(fileIn));
		}catch (FileNotFoundException e) {
			throw new OwatNodeIOException("Unable to find file for reading. ", e);
		}
	}
	
	/**
	 * Creates a NodeReader using a string describing the location of a file to read from.
	 * @param fileLocation The location of the file to read from.
	 */
	public NodeReader(String fileLocation){
		try {
			this.setInputStream(new FileInputStream(fileLocation));
		}catch (FileNotFoundException e){
			throw new OwatNodeIOException("Unable to find file for reading. ", e);
		}
	}
	
	/**
	 * Sets this reader's input stream.
	 * @param streamIn The stream to set.
	 */
	private void setInputStream(InputStream streamIn) {
		this.inStream = streamIn;
	}
	
	/**
	 * Determines if all the nodes are read in from the stream.
	 * @return If all the nodes are read in from the stream.
	 * @throws OwatNodeIOException IF an I/O exception occurs.
	 */
	public boolean allReadIn() throws OwatNodeIOException{
		try {
			return this.inStream.available() == 0;
		}catch (IOException e){
			throw new OwatNodeIOException("Unable to determine of we hav read in all information from stream; I/O exception. ", e);
		}
	}
	
	/**
	 * Gets the next node available.
	 * @return The next node received.
	 */
	public abstract Node getNextNode();
	
	/**
	 * Gets all nodes available from the stream.
	 * @return All of the nodes available from the stream.
	 */
	public abstract LongLinkedList getAllNodes();
}
