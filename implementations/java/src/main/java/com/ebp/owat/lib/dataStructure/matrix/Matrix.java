package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.matrix.utils.MatrixNode;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.FixedNodePos;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.NodePos;
import com.ebp.owat.lib.dataStructure.set.LongLinkedList;

import java.util.Collections;
import java.util.List;

/**
 *  Abstract class of a matrix to hold all the information and perform scrambling operations on.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class Matrix<T> {
	
	/**
	 * A set of nodes that we know the positions of.
	 */
	protected List<NodePos<T>> nodePosList = Collections.emptyList();
	
	/** 0,0 of this matrix. needs to be updated with removals/additions */
	protected MatrixNode<T> headNode;
	
	/** The number of rows held by this object. */
	protected long numRows = 0L;
	/** The number of columns held by this object. */
	protected long numCols = 0L;
	
	/** The number of elements held by this object. I.E., the number of nodes that are not null. */
	protected long numElementsHeld = 0L;
	
	/**
	 * Basic Constructor.
	 */
	private Matrix(){}
	
	public Matrix(LongLinkedList<T> valuesIn){
		this();
		//TODO:: this
	}
	
	/**
	 * Initializes the matrix, adding a single node and setup initial node positions.
	 */
	private void initFirstNode(){
		if(!this.isEmpty()){
			throw new IllegalStateException("Can't initialize the matrix if it already is initialized. Probably shouldn't be able to get to this.");
		}
		
		this.numRows = 1;
		this.numCols = 1;
		this.headNode = new MatrixNode<>();
		
		//Add to the node position list.
		this.nodePosList = new LongLinkedList<>();
		for (NodePos.FixedNodePos curFixedPos : NodePos.FixedNodePos.values()){
			this.nodePosList.add(
				new FixedNodePos<>(this, curFixedPos)
			);
		}
	}
	
	public void addRow(){
		if(this.nodePosList.isEmpty()){
			initFirstNode();
			return;
		}
		//TODO
		resetFixedPoints();
	}
	
	public void addCol(){
		if(this.nodePosList.isEmpty()){
			initFirstNode();
		}
		//TODO
		resetFixedPoints();
	}
	
	public void addRows(List<T> valuesIn){
		//TODO: add rows til we are done adding from values
	}
	
	public void addCols(List<T> valuesIn){
		//TODO: add cols til we are done adding from values
	}
	
	/**
	 * Returns this matrix as a 2d array.
	 * @return
	 */
	public Object[][] to2dArray(){
		Object[][] output = new Object[(int)this.numRows][(int)this.getNumCols()];
		
		
		
		
		return output;
	}
	
	/**
	 * Resets the fixed points in the matrix. Use after any modification to the matrix (add/remove row/cols).
	 */
	private void resetFixedPoints(){
		for (NodePos<T> curEntry : this.nodePosList) {
			curEntry.resetNodePos();
		}
	}
	
	/**
	 * Determines if the number given is a valid row index (Is greater than -1, and is less than or equal to the number of rows - 1).
	 * @param rowNumIn The number to test.
	 * @return If the number given is a valid row index.
	 */
	public boolean isValidRowIndex(long rowNumIn){
		return (rowNumIn > -1) && ((this.numRows -1) >= rowNumIn);
	}
	
	/**
	 * Determines if the number given is a valid column index (Is greater than -1, and is less than or equal to the number of columns - 1).
	 * @param colNumIn The number to test.
	 * @return If the number given is a valid column index.
	 */
	public boolean isValidColIndex(long colNumIn){
		return (colNumIn > -1) && ((this.numCols -1) >= colNumIn);
	}
	
	/**
	 * Determines if this matrix is empty or not. Empty is defined as not having any nodes, as opposed to having nodes, but none of them having a value.
	 * @return If this matrix is empty or not.
	 */
	public boolean isEmpty(){
		return this.numCols == 0 && this.numRows == 0;
	}
	
	/**
	 * Gets the number of rows held by this matrix.
	 * @return The number of rows held by this matrix.
	 */
	public long getNumRows(){
		return numRows;
	}
	
	/**
	 * Gets the number of columns held by this matrix.
	 * @return The number of columns held by this matrix.
	 */
	public long getNumCols(){
		return numCols;
	}
	
	/**
	 * Gets the total number of spots in the matrix.
	 * @return The total number of spots in the matrix.
	 */
	public long size(){
		return this.numRows * this.numCols;
	}
	
	/**
	 * Gets the number of elements held by this matrix.
	 * @return The number of elements held by this matrix.
	 */
	public long numElements(){
		return this.numElementsHeld;
	}
	
	/**
	 * Determines if pos1 is closer to pos2 to the coordinates given.
	 * @param pos1 The node we are testing to see if it is closer.
	 * @param pos2 The node we are comparing to the first one.
	 * @param coordIn The coordinate we are comparing to.
	 * @return If pos1 is closer to the goal coordinates than pos2
	 */
	private boolean nodeIsCloserThan(NodePos<T> pos1, NodePos<T> pos2, Coordinate coordIn){
		if(pos2 == null){
			return true;
		}
		return pos1.isCloserTo(pos2, coordIn);
	}
	
	/**
	 * Gets the closest NodePos we keep track of to the coords given.
	 * @param coordIn The coordinate we are trying to get the closest node to.
	 * @return The closest NodePos we keep track of to the coords given.
	 */
	private NodePos<T> getClosestNodePos(Coordinate coordIn){
		NodePos<T> curClosest = null;
		for ( NodePos<T> curPos : this.nodePosList ) {
			if(curClosest == null || this.nodeIsCloserThan(curPos, curClosest, coordIn)){
				curClosest = curPos;
			}
		}
		return curClosest;
	}
	
	/**
	 * Gets a node at the coordinates given.
	 * @param coordToGet The coordinate of the node to get.
	 * @param usePoints If we are to use the points in the list of points we keep track of.
	 * @return The node at the coords given.
	 */
	public MatrixNode<T> getNode(Coordinate coordToGet, boolean usePoints){
		if(coordToGet.matrix == this){
			throw new IllegalArgumentException("Coordinate given is not on this matrix.");
		}
		MatrixNode<T> output = null;
		if(usePoints && this.nodePosList.size() > 0){
			NodePos<T> closestNodePos = this.getClosestNodePos(coordToGet);
			//TODO goto node from closest pos
		}else{
			//TODO go to node from head node.
		}
		return output;
	}
	
	public T get(long xIn, long yIn){
		return this.getNode(new Coordinate(this, xIn, yIn), true).getValue();
	}
	
	public T get(Coordinate coord){
		return this.getNode(coord, true).getValue();
	}
	
	public LongLinkedList<T> getCol(long xIn){
		//TODO
		return null;
	}
	
	public LongLinkedList<T> getRow(long yIn){
		//TODO
		return null;
	}
	
	//TODO:: iterator(s)
	
}//Matrix
