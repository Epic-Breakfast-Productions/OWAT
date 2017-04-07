package com.ebp.owat.lib.dataStructure.matrix.scrambling;

import com.ebp.owat.lib.dataStructure.io.NodeReader;
import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.set.BigLinkedList;
import com.ebp.owat.lib.utils.rand.BigIntegerGenerator;

import java.math.BigInteger;

/**
 * Describes a matrix that will be scrambling nodes.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public abstract class ScramblingMatrix<T extends Node> extends Matrix {
	/** The random number  */
	private BigIntegerGenerator randGenerator;
	
	/**
	 * Constructor to build using a provided node.
	 * <p>
	 * Assumes that the node given is the top right.
	 *
	 * @param nodeIn The node to use to build this object.
	 */
	public ScramblingMatrix(Node nodeIn, BigIntegerGenerator randGenerator) {
		super(nodeIn);
		this.setRandomGenerator(randGenerator);
	}
	
	/**
	 * Constructor to set the stream reader and optionally read in the data right away.
	 *
	 * @param readerIn The reader to use to read the data in.
	 * @param readIn   If we are to read the data in immediately or not.
	 */
	public ScramblingMatrix(NodeReader readerIn, boolean readIn, BigIntegerGenerator randGenerator) {
		super(readerIn, readIn);
		this.setRandomGenerator(randGenerator);
	}
	
	/**
	 * Constructor to set the stream reader in and read in the data into nodes.
	 *
	 * @param readerIn The reader to get the nodes from.
	 */
	public ScramblingMatrix(NodeReader readerIn, BigIntegerGenerator randGenerator) {
		super(readerIn);
		this.setRandomGenerator(randGenerator);
	}
	
	public void setRandomGenerator(BigIntegerGenerator randIn){
		this.randGenerator = randIn;
	}
	
	public void readInData() throws OwatMatrixException {
		if(this.dataReader == null){
			throw new OwatMatrixException("This matrix is not setup to read in nodes from a stream.");
		}
		if(this.dataIn){
			throw new OwatMatrixException("Cannot read data in. Data already read in.");
		}
		
		BigLinkedList<T> newNodes = this.dataReader.getAllNodes(); //This should always be correct
		
		//determine length and width with the help of the rand.
		BigInteger listLength = newNodes.listSize();
		
		boolean found = false;
		while(!found){
		
		}
		
		//TODO: put all nodes together into the matrix
		
		this.dataIn = true;
	}
}
