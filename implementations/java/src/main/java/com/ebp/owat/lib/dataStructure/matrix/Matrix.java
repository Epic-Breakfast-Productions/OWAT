package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.io.NodeReader;
import com.ebp.owat.lib.dataStructure.io.NodeWriter;
import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.set.LongLinkedList;

import java.util.concurrent.ConcurrentHashMap;

/**
 *  Abstract class of a matrix to hold all the information and perform scrambling operations on.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public abstract class Matrix<T extends Node> {
	/**
	 * Enum to describe which node is held in the spot in the hashmap.
	 *
	 * TODO:: add data/method here to determine where these should be?
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
	protected ConcurrentHashMap<FixedNodePos, T> fixedNodes = new ConcurrentHashMap<>(FixedNodePos.values().length);
	
	/** The number of rows held by this object. */
	protected long numRows = 0L;
	/** The number of columns held by this object. */
	protected long numCols = 0L;
	
	/** The length of the original data set. */
	protected long origDataLength = 0L;
	/** The width of the original data set. */
	protected long origDataWidth = 0L;
	
	/** The reader to get in original data. */
	protected NodeReader<T> dataReader;
	
	/** Flag to tell if the data has been read in. */
	protected boolean dataIn = false;
	
	/**
	 * Basic Constructor.
	 */
	private Matrix(){}
	
	/**
	 * Constructor to build using a provided node.
	 *
	 * Assumes that the node given is the top right.
	 *
	 * @param nodeIn The node to use to build this object.
	 */
	public Matrix(T nodeIn){
		this();
		this.setNodes(nodeIn);
		this.dataIn = true;
	}
	
	/**
	 * Constructor to set the stream reader and optionally read in the data right away.
	 * @param readerIn The reader to use to read the data in.
	 * @param readIn If we are to read the data in immediately or not.
	 */
	public Matrix(NodeReader<T> readerIn, boolean readIn){
		this();
		this.setInputStream(readerIn);
		if(readIn){
			this.readInData();
		}
	}
	
	/**
	 * Constructor to set the stream reader in and read in the data into nodes.
	 * @param readerIn The reader to get the nodes from.
	 */
	public Matrix(NodeReader<T> readerIn){
		this(readerIn, true);
	}
	
	/**
	 * Reads in the data from a data stream.
	 * @throws OwatMatrixException If this matrix is not set up to read data in with a stream or it data is already read in.
	 */
	public abstract void readInData() throws OwatMatrixException;
	
	/**
	 * Gets an output stream to read out the data.
	 * @return A stream to use to read out the data.
	 */
	public abstract NodeWriter<T> getOutputStream();
	
	public abstract void setInputStream(NodeReader<T> readerIn);
	
	/**
	 * Sets up the nodes this object holds.
	 *
	 * Also sets up the lengths and widths of the data, and the fixed positions.
	 *
	 * @param nodeIn The node that points to the rest of the matrix.
	 */
	private void setNodes(T nodeIn){
		if(nodeIn == null){
			throw new OwatMatrixException("Unable to set up Matrix with a null node.");
		}
		for(FixedNodePos curPos : FixedNodePos.values()) {
			this.fixedNodes.put(curPos, nodeIn);
		}
		//TODO:: to integrity check on node given.
		updateFixedPosAndLengthWidth();
	}
	
	public void addRow(LongLinkedList<T> listIn){
		if(this.numCols != listIn.listSize()){
			throw new OwatMatrixException("List given is the wrong size to use to add to the matrix.");
		}
		//TODO: add row, ensuring that they all connect to what they need to.
		updateFixedPosAndLengthWidth();
	}
	
	public void addCol(LongLinkedList<T> listIn){
		if(this.numRows != listIn.listSize()){
			throw new OwatMatrixException("List given is the wrong size to use to add to the matrix.");
		}
		//TODO: add column, ensuring that they all connect to what they need to.
		updateFixedPosAndLengthWidth();
	}
	
	/**
	 * Ensures the fixed node positions are at their correct places.
	 */
	public void updateFixedNodes(){
		//top right node
		T curFixedPosNode = this.fixedNodes.get(FixedNodePos.TOP_RIGHT);
		if(!curFixedPosNode.isCorner()){
			while(!curFixedPosNode.isBorder(Node.NodeDir.NORTH)){
				curFixedPosNode = (T)curFixedPosNode.getNorth();
			}
			while(!curFixedPosNode.isBorder(Node.NodeDir.WEST)){
				curFixedPosNode = (T)curFixedPosNode.getWest();
			}
		}
		this.fixedNodes.put(FixedNodePos.TOP_RIGHT,curFixedPosNode);
		//top left node
		curFixedPosNode = this.fixedNodes.get(FixedNodePos.TOP_LEFT);
		if(!curFixedPosNode.isCorner()){
			while(!curFixedPosNode.isBorder(Node.NodeDir.NORTH)){
				curFixedPosNode = (T)curFixedPosNode.getNorth();
			}
			while(!curFixedPosNode.isBorder(Node.NodeDir.EAST)){
				curFixedPosNode = (T)curFixedPosNode.getEast();
			}
		}
		this.fixedNodes.put(FixedNodePos.TOP_RIGHT,curFixedPosNode);
		//bot right node
		curFixedPosNode = this.fixedNodes.get(FixedNodePos.BOT_RIGHT);
		if(!curFixedPosNode.isCorner()){
			while(!curFixedPosNode.isBorder(Node.NodeDir.SOUTH)){
				curFixedPosNode = (T)curFixedPosNode.getSouth();
			}
			while(!curFixedPosNode.isBorder(Node.NodeDir.WEST)){
				curFixedPosNode = (T)curFixedPosNode.getWest();
			}
		}
		this.fixedNodes.put(FixedNodePos.BOT_RIGHT,curFixedPosNode);
		//bot left node
		curFixedPosNode = this.fixedNodes.get(FixedNodePos.BOT_LEFT);
		if(!curFixedPosNode.isCorner()){
			while(!curFixedPosNode.isBorder(Node.NodeDir.SOUTH)){
				curFixedPosNode = (T)curFixedPosNode.getSouth();
			}
			while(!curFixedPosNode.isBorder(Node.NodeDir.EAST)){
				curFixedPosNode = (T)curFixedPosNode.getEast();
			}
		}
		this.fixedNodes.put(FixedNodePos.BOT_LEFT,curFixedPosNode);
		
	}
	
	public void updateLengthWidth(){
		//TODO
	}
	
	/**
	 * Updates the fixed position nodes and the length/width held.
	 */
	public void updateFixedPosAndLengthWidth(){
		this.updateFixedNodes();
		this.updateLengthWidth();
	}
	
	/**
	 * Gets the number of rows held by this Matrix.
	 * @return The number of rows held by this Matrix.
	 */
	public long getNumRows(){return this.numRows;}
	/**
	 * Gets the number of columns held by this Matrix.
	 * @return The number of columns held by this Matrix.
	 */
	public long getNumCols(){return this.numCols;}
	
	/**
	 * Gets the number of rows in the original data set.
	 * @return The number of rows in the original data set.
	 */
	public long getOrigDataLength(){return this.origDataLength;}
	/**
	 * Gets the number of columns in the original data set.
	 * @return The number of columns in the original data set.
	 */
	public long getOrigDataWidth(){return this.origDataWidth;}
	
	/**
	 * Checks that the column number given is valid.
	 * @param col The column number given.
	 * @throws OwatMatrixException If the column number given is null or greater than the number of columns held by this object.
	 */
	public void checkValidColNumber(long col) throws OwatMatrixException{
		if(this.numCols > col){
			throw new OwatMatrixException("Bad column number given. It was greater than the number of columns in the data set.");
		}
	}
	
	/**
	 * Checks that the row number given is valid.
	 * @param row The row number given.
	 * @throws OwatMatrixException If the row number given is null or greater than the number of rows held by this object.
	 */
	public void checkValidRowNumber(long row) throws OwatMatrixException{
		if(this.numRows > row){
			throw new OwatMatrixException("Bad row number given. It was greater than the number of columns in the data set.");
		}
	}
	
	/**
	 * Checks that both the row and column numbers given are valid.
	 * @param row The row number given.
	 * @param col The column number given.
	 * @throws OwatMatrixException  If the row/column number given is null or greater than the number of row/columns held by this object.
	 */
	public void checkValidRowColNumber(long row, long col) throws OwatMatrixException{
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
	 * Tests if the given row number is valid.
	 * @param rowNumIn The row number to test.
	 * @return If the row number given is valid.
	 */
	public boolean isValidRowNum(long rowNumIn){
		try{
			this.checkValidRowNumber(rowNumIn);
		}catch(OwatMatrixException e){
			return false;
		}
		return true;
	}
	
	/**
	 * Tests if the given col number is valid.
	 * @param colNumIn The col number to test.
	 * @return If the col number given is valid.
	 */
	public boolean isValidColNum(long colNumIn){
		try{
			this.checkValidColNumber(colNumIn);
		}catch(OwatMatrixException e){
			return false;
		}
		return true;
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
	public T getNode(long row, long col) throws OwatMatrixException{
		checkValidRowColNumber(row,col);
		long one = 1L;
		long curRowCol;
		T curNode = this.getNode(FixedNodePos.TOP_LEFT);
		//TODO:: work off of the closest fixed node(s)
		//go south
		for(curRowCol = 0L; curRowCol < this.numRows; curRowCol++){
			curNode = (T)curNode.getSouth();// SHOULD always be of type T
		}
		//go east
		for(curRowCol = 0L; curRowCol < this.numCols; curRowCol++){
			curNode = (T)curNode.getEast();// SHOULD always be of type T
		}
		return curNode;
	}
	
	/**
	 * Gets a list of nodes from the structure. The first node is the north/top most node.
	 * @param row The row to get from the structure.
	 * @return A list of nodes that is the whole row.
	 * @throws OwatMatrixException If the row number is greater than the number of rows.
	 */
	public LongLinkedList<T> getRow(long row) throws OwatMatrixException{
		checkValidRowNumber(row);
		T endNode = this.getNode(row, 0L);
		//now have leftmost node in row
		LongLinkedList<T> output = new LongLinkedList<>(LongLinkedList.Type.ROW);
		T curNode = endNode;
		while(curNode != null){
			output.addLast(curNode);
			curNode = (T)curNode.getEast();//SHOULD always be of type T
		}
		return output;
	}
	
	/**
	 * Gets a list of nodes from the structure. The first node is the west/left most node.
	 * @param col The number of the column to get.
	 * @return A list of nodes that is the column in the structure.
	 * @throws OwatMatrixException If the column number given is out of bounds of the structure.
	 */
	public LongLinkedList<T> getCol(long col) throws OwatMatrixException{
		checkValidColNumber(col);
		T topNode = this.getNode(0L, col);
		//now have leftmost node in row
		LongLinkedList<T> output = new LongLinkedList<>(LongLinkedList.Type.ROW);
		T curNode = topNode;
		while(curNode != null){
			output.addLast(curNode);
			curNode = (T)curNode.getSouth();//SHOULD always be of type T
		}
		return output;
	}
	
}//Matrix
