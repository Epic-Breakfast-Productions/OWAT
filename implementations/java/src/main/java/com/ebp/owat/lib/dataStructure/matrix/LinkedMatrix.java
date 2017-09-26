package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.matrix.utils.MatrixNode;
import com.ebp.owat.lib.dataStructure.matrix.utils.NodeDir;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.FixedNodePos;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.NodePos;
import com.ebp.owat.lib.dataStructure.set.LongLinkedList;

import java.util.Collection;
import java.util.Collections;
import java.util.List;

/**
 *  Abstract class of a matrix to hold all the information and perform scrambling operations on.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class LinkedMatrix<T> extends ScramblingMatrix<T>{
	/**
	 * A set of nodes that we know the positions of.
	 */
	protected List<NodePos<T>> nodePosList = Collections.emptyList();
	
	/** 0,0 of this matrix. needs to be updated with removals/additions */
	protected MatrixNode<T> headNode;
	
	/**
	 * Basic Constructor.
	 */
	private LinkedMatrix(Type type){
		super(type);
	}
	
	
	public LinkedMatrix(Type type, LongLinkedList<T> valuesIn){
		this(type);
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
	
	/**
	 * Resets the fixed points in the matrix. Use after any modification to the matrix (add/remove row/cols).
	 */
	private void resetFixedPoints(){
		for (NodePos<T> curEntry : this.nodePosList) {
			curEntry.resetNodePos();
		}
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
	 * Given a node position, adjusts that position to be at the coordinates given.
	 * @param coordToGet The coordinates we want to go to.
	 * @param curPoint The point we are starting at.
	 * @return The Node at the coordinates given.
	 */
	private MatrixNode<T> getNodeFromPos(Coordinate coordToGet, NodePos<T> curPoint){
		if(coordToGet == null || curPoint == null){
			throw new IllegalArgumentException("Coord to get or start point given was null.");
		}
		if(coordToGet.matrix != this || curPoint.matrix != this){
			throw new IllegalArgumentException("Coord or start point given not on this matrix.");
		}
		
		while(!curPoint.equals(coordToGet)){
			if(coordToGet.getX() > curPoint.getX()){
				curPoint.go(NodeDir.EAST);
			}else if(coordToGet.getX() < curPoint.getX()){
				curPoint.go(NodeDir.WEST);
			}
			
			if(coordToGet.getY() > curPoint.getY()){
				curPoint.go(NodeDir.NORTH);
			}else if(coordToGet.getY() < curPoint.getY()){
				curPoint.go(NodeDir.SOUTH);
			}
		}
		return curPoint.getNode();
	}
	
	
	/**
	 * Adds a row to the matrix. Will be added to the right of the existing matrix.
	 */
	@Override
	public void addRow() {
		//TODO
	}
	
	/**
	 * Adds rows to the matrix until capacity is added for all the values given to be added, and the values are added. Values are added top-down on the new rows.
	 *
	 * @param valuesIn The values to add to the rows.
	 * @return If all the rows added were added completely by the values given.
	 */
	@Override
	public boolean addRows(Collection<T> valuesIn) {
		//TODO
		return false;
	}
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 *
	 * @param numRows The number of rows to add.
	 */
	@Override
	public void addRows(long numRows) {
		//TODO
	}
	
	/**
	 * Adds a column to the matrix. Will be added to the bottom of the existing matrix.
	 */
	@Override
	public void addCol() {
		//TODO
	}
	
	/**
	 * Adds columns to the matrix until capacity is added for all the values given to be added, and the values are added to those rows. Values are added left to right to the new columns.
	 *
	 * @param valuesIn The values to add to the rows.
	 * @return If all the columns added were added completely by the values given.
	 */
	@Override
	public void addCols(Collection<T> valuesIn) {
		//TODO
	}
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 *
	 * @param numCols The number of columns to add.
	 */
	@Override
	public void addCols(long numCols) {
		//TODO
	}
	
	/**
	 * Adds rows and columns until all the items in the collection are added.
	 * <p>
	 * Starts by adding a column, then a row, and keeps switching between adding cols and rows.
	 *
	 * @param valuesIn The values to add.
	 */
	@Override
	public void addRowsCols(Collection<T> valuesIn) {
		//TODO
	}
	
	/**
	 * Removes a row from this matrix.
	 *
	 * @param rowIndex
	 * @return The elements that comprised the row.
	 * @throws IndexOutOfBoundsException If the row index given is out of bounds.
	 */
	@Override
	public List<T> removeRow(long rowIndex) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Removes a column from this matrix.
	 *
	 * @param colIndex
	 * @return The elements that comprised the column.
	 * @throws IndexOutOfBoundsException If the column index given is out of bounds.
	 */
	@Override
	public List<T> removeCol(long colIndex) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Replaces a particular node's value.
	 *
	 * @param nodeToReplace The coordinate of the node to replace.
	 * @param newValue      The value to replace.
	 * @return The previously held value.
	 */
	@Override
	public T replaceNode(Coordinate nodeToReplace, T newValue) {
		//TODO
		return null;
	}
	
	/**
	 * Replaces a row of values.
	 *
	 * @param rowIndex  The index of the row to replace.
	 * @param newValues The values to use to replace the row.
	 * @return The values this method replaced. Ordered top to bottom.
	 */
	@Override
	public List<T> replaceRow(long rowIndex, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Replaces a column of values.
	 *
	 * @param colIndex  The index of the column to replace.
	 * @param newValues The values to use to replace the column.
	 * @return The values this method replaced. Ordered left to right.
	 */
	@Override
	public List<T> replaceCol(long colIndex, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets a value from this matrix.
	 *
	 * @param xIn The x index of the node to get
	 * @param yIn The y index of the node to get
	 * @return The value at the point given.
	 * @throws IndexOutOfBoundsException If either of the indexes are out of bounds.
	 */
	@Override
	public T get(long xIn, long yIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets a value from this matrix.
	 *
	 * @param coordIn The coordinate to use. Coordinate must be on this matrix.
	 * @return The value at the coordinate given.
	 */
	@Override
	public T get(Coordinate coordIn) {
		//TODO
		return null;
	}
	
	/**
	 * Gets a column of this matrix.
	 *
	 * @param xIn The index of the column to get.
	 * @return The list of elements in the column.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	@Override
	public List<T> getCol(long xIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the column from.
	 * @return The list of elements in the column specified.
	 */
	@Override
	public List<T> getCol(Coordinate coordIn) {
		//TODO
		return null;
	}
	
	/**
	 * Gets a row of this matrix.
	 *
	 * @param yIn The index of the column to get.
	 * @return The list of elements in the column.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	@Override
	public List<T> getRow(long yIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the row from.
	 * @return The list of elements in the row specified.
	 */
	@Override
	public List<T> getRow(Coordinate coordIn) {
		//TODO
		return null;
	}
	
	/**
	 * Gets this matrix represented as a two dimensional matrix.
	 *
	 * @return This matrix as a 2d array.
	 */
	@Override
	public Object[][] to2dArray() {
		//TODO
		return new Object[0][];
	}
	
	/**
	 * Determines if pos1 is closer to pos2 to the coordinates given.
	 *
	 * @param pos1    The node we are testing to see if it is closer.
	 * @param pos2    The node we are comparing to the first one.
	 * @param coordIn The coordinate we are comparing to.
	 * @return If pos1 is closer to the goal coordinates than pos2
	 */
	@Override
	public boolean nodeIsCloserThan(NodePos<T> pos1, NodePos<T> pos2, Coordinate coordIn) {
		//TODO
		return false;
	}
}//LinkedMatrix
