package com.ebp.owat.lib.dataStructure.matrix.scrambling;

import com.ebp.owat.lib.dataStructure.io.NodeReader;
import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.set.LongLinkedList;
import com.ebp.owat.lib.utils.rand.LongGenerator;

import java.util.ListIterator;

/**
 * Describes a matrix that will be scrambling nodes.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public abstract class ScramblingMatrix<T extends Node> extends Matrix {
	/** The random number generator to use for scrambling.  */
	private LongGenerator randGenerator;
	
	/**
	 * Constructor to build using a provided node.
	 * <p>
	 * Assumes that the node given is the top right.
	 *
	 * @param nodeIn The node to use to build this object.
	 */
	public ScramblingMatrix(Node nodeIn, LongGenerator randGenerator) {
		super(nodeIn);
		this.setRandomGenerator(randGenerator);
	}
	
	/**
	 * Constructor to set the stream reader and optionally read in the data right away.
	 *
	 * @param readerIn The reader to use to read the data in.
	 * @param readIn   If we are to read the data in immediately or not.
	 */
	public ScramblingMatrix(NodeReader readerIn, boolean readIn, LongGenerator randGenerator) {
		super(readerIn, readIn);
		this.setRandomGenerator(randGenerator);
	}
	
	/**
	 * Constructor to set the stream reader in and read in the data into nodes.
	 *
	 * @param readerIn The reader to get the nodes from.
	 */
	public ScramblingMatrix(NodeReader readerIn, LongGenerator randGenerator) {
		super(readerIn);
		this.setRandomGenerator(randGenerator);
	}
	
	/**
	 * Sets the Random Number Generator.
	 * @param randIn The random number generator to use.
	 * @throws NullPointerException If the random number generator in is null.
	 */
	public void setRandomGenerator(LongGenerator randIn){
		if(randIn == null){
			throw new NullPointerException("The random number generator in cannot be null.");
		}
		this.randGenerator = randIn;
	}
	
	/**
	 *
	 * @throws OwatMatrixException
	 */
	public void readInData() throws OwatMatrixException {
		if(this.dataReader == null){
			throw new OwatMatrixException("This matrix is not setup to read in nodes from a stream.");
		}
		if(this.dataIn){
			throw new OwatMatrixException("Cannot read data in. Data already read in.");
		}
		
		LongLinkedList<T> newNodes = this.dataReader.getAllNodes(); //This should always be correct
		
		this.setupOriginalLengthWidth(newNodes.listSize());
		
		long curRow = 0L;
		long curCol = 0L;
		
		ListIterator<T> listIterator = newNodes.listIterator();
		LongLinkedList<T> curRowToAdd;
		while(listIterator.hasNext()) {
			//TODO:: add nodes
			
			
			
			
			System.out.println(listIterator.next());//TODO:: use logger
		}
		
		
		
		//TODO: put all nodes together into the matrix
		
		this.dataIn = true;
	}
	
	private void setupOriginalLengthWidth(long numNodes){
		long sqrtNum = (long)Math.ceil(Math.sqrt(numNodes));
		this.randGenerator.setBounds(sqrtNum - 1L, 1);
		long numToSquishBy = this.randGenerator.next();
		
		this.origDataLength = sqrtNum + numToSquishBy;
		this.origDataWidth = sqrtNum - numToSquishBy;
	}
	
}
