package com.ebp.owat.lib.dataStructure.matrix.scrambling;

import com.ebp.owat.lib.dataStructure.io.NodeReader;
import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.set.BigLinkedList;
import com.ebp.owat.lib.utils.rand.BigIntegerGenerator;

import java.math.BigInteger;
import java.util.ListIterator;

/**
 * Describes a matrix that will be scrambling nodes.
 *
 * Created by Greg Stewart on 3/28/17.
 */
public abstract class ScramblingMatrix<T extends Node> extends Matrix {
	/** The random number generator to use for scrambling.  */
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
	
	/**
	 * Sets the Random Number Generator.
	 * @param randIn The random number generator to use.
	 * @throws NullPointerException If the random number generator in is null.
	 */
	public void setRandomGenerator(BigIntegerGenerator randIn){
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
		
		BigLinkedList<T> newNodes = this.dataReader.getAllNodes(); //This should always be correct
		
		this.setupOriginalLengthWidth(newNodes.listSize());
		
		BigInteger curRow = BigInteger.ZERO;
		BigInteger curCol = BigInteger.ZERO;
		
		ListIterator<T> listIterator = newNodes.listIterator();
		BigLinkedList<T> curRowToAdd;
		while(listIterator.hasNext()) {
			//TODO:: add nodes
			
			
			
			
			System.out.println(listIterator.next());
		}
		
		
		
		//TODO: put all nodes together into the matrix
		
		this.dataIn = true;
	}
	
	private void setupOriginalLengthWidth(BigInteger numNodes){
		BigInteger sqrtNum = bigIntSqRootCeil(numNodes);
		this.randGenerator.setBounds(sqrtNum.subtract(BigInteger.ONE), BigInteger.ONE);
		BigInteger numToSquishBy = this.randGenerator.next();
		
		this.origDataLength = sqrtNum.add(numToSquishBy);
		this.origDataWidth = sqrtNum.subtract(numToSquishBy);
	}
	
	/**
	 * Function to get the square root of a Bigint
	 * @param x The Bigint to get the square root of.
	 * @return The square root of the Bigint given.
	 * @throws IllegalArgumentException If the number given is less than zero.
	 */
	private static BigInteger bigIntSqRootCeil(BigInteger x) throws IllegalArgumentException {
		if(x == null){
			throw new IllegalArgumentException("BigInteger is null.");
		}
		if (x.compareTo(BigInteger.ZERO) < 0) {
			throw new IllegalArgumentException("Negative argument.");
		}
		// square roots of 0 and 1 are trivial and
		// y == 0 will cause a divide-by-zero exception
		if (x.compareTo(BigInteger.ZERO) == 0 || x.compareTo(BigInteger.ONE) == 0 ){
			return x;
		} // end if
		BigInteger two = BigInteger.valueOf(2L);
		BigInteger y;
		// starting with y = x / 2 avoids magnitude issues with x squared
		for (y = x.divide(two);
		     y.compareTo(x.divide(y)) > 0;
		     y = ((x.divide(y)).add(y)).divide(two));
		if (x.compareTo(y.multiply(y)) == 0) {
			return y;
		} else {
			return y.add(BigInteger.ONE);
		}
	} // end bigIntSqRootCeil
}
