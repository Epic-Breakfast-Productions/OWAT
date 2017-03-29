package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.matrix.set.NodeList;
import com.ebp.owat.lib.dataStructure.node.value.NodeValue;

import java.io.InputStream;
import java.io.OutputStream;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  Abstract class of a matrix to hold all the information and perform scrambling operations on.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public abstract class Matrix<T extends Node<NodeValue>> {
	/**
	 * Enum to describe which node is held in the spot in the hashmap.
	 */
	public enum FixedNodePos{
		TOP_LEFT,
		TOP_RIGHT,
		BOT_LEFT,
		BOT_RIGHT
	}
	
	/**
	 * A set of nodes that we know the positions of.
	 */
	private ConcurrentHashMap<FixedNodePos, T> fixedNodes = new ConcurrentHashMap<>(FixedNodePos.values().length);
	/** The number of rows held by this object. */
	private BigInteger numRows = BigInteger.ZERO;
	/** The number of columns held by this object. */
	private BigInteger numCols = BigInteger.ZERO;
	
	/** The length of the original data set. */
	private BigInteger origDataLength = BigInteger.ZERO;
	/** The width of the original data set. */
	private BigInteger origDataWidth = BigInteger.ZERO;
	
	/**
	 * Basic Constructor.
	 */
	public Matrix(){}
	
	
	/**
	 * Method to set up the original data.
	 * @param streamIn The stream to read data in with.
	 */
	public abstract void readInOriginalData(InputStream streamIn);
	
	/**
	 * Gets an output stream to read out the data.
	 * @return A stream to use to read out the data.
	 */
	public abstract OutputStream getOutputStream();
	
	/**
	 * Gets the number of rows held by this Matrix.
	 * @return The number of rows held by this Matrix.
	 */
	public BigInteger getNumRows(){return this.numRows;}
	/**
	 * Gets the number of columns held by this Matrix.
	 * @return The number of columns held by this Matrix.
	 */
	public BigInteger getNumCols(){return this.numCols;}
	
	/**
	 * Gets the number of rows in the original data set.
	 * @return The number of rows in the original data set.
	 */
	public BigInteger getOrigDataLength(){return this.origDataLength;}
	/**
	 * Gets the number of columns in the original data set.
	 * @return The number of columns in the original data set.
	 */
	public BigInteger getOrigDataWidth(){return this.origDataWidth;}
	
	/**
	 * Checks that the column number given is valid.
	 * @param col The column number given.
	 * @throws OwatMatrixException If the column number given is null or greater than the number of columns held by this object.
	 */
	private void checkValidColNumber(BigInteger col) throws OwatMatrixException{
		if(col == null){
			throw new OwatMatrixException("Bad column number given. It was null.");
		}
		if(this.numCols.compareTo(col) > 0){
			throw new OwatMatrixException("Bad column number given. It was greater than the number of columns in the data set.");
		}
	}
	
	/**
	 * Checks that the row number given is valid.
	 * @param row The row number given.
	 * @throws OwatMatrixException If the row number given is null or greater than the number of rows held by this object.
	 */
	private void checkValidRowNumber(BigInteger row) throws OwatMatrixException{
		if(row == null){
			throw new OwatMatrixException("Bad row number given. It was null.");
		}
		if(this.numRows.compareTo(row) > 0){
			throw new OwatMatrixException("Bad row number given. It was greater than the number of columns in the data set.");
		}
	}
	
	/**
	 * Checks that both the row and column numbers given are valid.
	 * @param row The row number given.
	 * @param col The column number given.
	 * @throws OwatMatrixException  If the row/column number given is null or greater than the number of row/columns held by this object.
	 */
	public void checkValidRowColNumber(BigInteger row, BigInteger col) throws OwatMatrixException{
		OwatMatrixException rowException = null;
		OwatMatrixException colException = null;
		try{
			this.checkValidRowNumber(row);
		}catch (OwatMatrixException e){
			rowException = e;
		}
		try{
			this.checkValidColNumber(col);
		}catch (OwatMatrixException e){
			colException = e;
		}
		if(rowException != null && colException != null){
			throw new OwatMatrixException(
					"Both row and column numbers given are invalid. Details of row: \"" + rowException + "\" Details of column: \"" + colException + "\"" );
		}else if(rowException != null){
			throw rowException;
		}else if(colException != null){
			throw colException;
		}
	}
	
	/**
	 * Gets the node at a fixed position.
	 * @param posIn The fixed position to get the node at.
	 * @return The node at the position given.
	 */
	public T getNode(FixedNodePos posIn){
		return this.fixedNodes.get(posIn);
	}
	
	/**
	 * Gets a particular node from the matrix.
	 * @param row The row of the node we are getting.
	 * @param col The column of the node we are getting.
	 * @return The node at the row and column given.
	 * @throws OwatMatrixException If the row or column was out of bounds of the data the matrix holds.
	 */
	public T getNode(BigInteger row, BigInteger col) throws OwatMatrixException{
		checkValidRowColNumber(row,col);
		BigInteger one = BigInteger.ONE;
		BigInteger curRowCol;
		T curNode = this.getNode(FixedNodePos.TOP_LEFT);
		//go south
		for(curRowCol = BigInteger.ZERO; curRowCol.compareTo(this.numRows) < 1; curRowCol = curRowCol.add(one)){
			curNode = curNode.getSouth();
		}
		//go east
		for(curRowCol = BigInteger.ZERO; curRowCol.compareTo(this.numCols) < 1; curRowCol = curRowCol.add(one)){
			curNode = curNode.getEast();
		}
		return curNode;
	}
	
	public NodeList<T> getRow(BigInteger row) throws OwatMatrixException{
		checkValidRowNumber(row);
		//TODO
	}
	
	public NodeList<T> getCol(BigInteger col) throws OwatMatrixException{
		//TODO
	}
}//Matrix
