package com.ebp.owat.lib.datastructure.matrix.Linked;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

/**
 * Describes a value on a matrix, holding the value on the matrix and provides functionality to update that position automatically based on what kind of position this is and how the matrix grows/shrinks.
 *
 * TODO:: organize and ensure x/y coords are set
 *
 * @param <T> The type held by the matrix. Needed to ensure proper typing of the LinkedMatrixNode.
 */
public abstract class LinkedNodePos<T> extends Coordinate {
	
	/** The value this position holds. Can be updated by {@link LinkedNodePos#determinePos() determinePos()}. */
	protected LinkedMatrixNode<T> node;
	
	/**
	 * Most basic constructor for a LinkedNodePos.
	 * @param matrix The matrix this potision is a part of.
	 */
	public LinkedNodePos(Matrix<T> matrix){
		super(matrix);
	}
	
	/**
	 * Constructor that takes in a matrix and a value. Calls {@link LinkedNodePos#determinePos() determinePos()} automatically.
	 * @param matrix The matrix this position is a part of.
	 * @param nodeIn The value this position holds.
	 */
	public LinkedNodePos(LinkedMatrix<T> matrix, LinkedMatrixNode<T> nodeIn){
		this(matrix);
		this.setNode(nodeIn);
	}
	
	/**
	 * Constructs a new NodePositon with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param coord The coordinate of the value to get.
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public LinkedNodePos(LinkedMatrix<T> matrix, Coordinate coord) throws IllegalArgumentException {
		this(matrix, matrix.getNode(coord, true));
	}
	
	/**
	 * Constructs a new NodePositon with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param xIn    The X value (which col) of this coordinate.
	 * @param yIn    The Y value (which row) of this coordinate.
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public LinkedNodePos(LinkedMatrix<T> matrix, long xIn, long yIn) throws IllegalArgumentException {
		this(matrix, matrix.getNode(new Coordinate(matrix, xIn, yIn), true));
	}
	
	/**
	 * Sets the value held by this position. Calls {@link LinkedNodePos#determinePos() determinePos()} automatically.
	 * @param nodeIn The value to set.
	 * @return This Node position.
	 */
	public LinkedNodePos<T> setNode(LinkedMatrixNode<T> nodeIn){
		//TODO:: check if value is in matrix?
		this.node = nodeIn;
		this.determinePos();
		return this;
	}
	
	/**
	 * Gets the value held at this position.
	 * @return The value at this position.
	 */
	public LinkedMatrixNode<T> getNode() {
		return this.node;
	}
	
	/**
	 * Method used by the position to recalculate and update the appropriate position (x/y coordinates) of this value.
	 *
	 * Sets the x&y coordinates
	 */
	protected abstract void determinePos();
	
	/**
	 * Method used to reset this value's position. For use after the matrix is altered (row/col added/ removed). Can change both the value and coordinate held.
	 *
	 * @return If the value's position actually changed or not.
	 */
	public boolean resetNodePos(){
		long origX = this.getX();
		long origY = this.getY();
		LinkedMatrixNode<T> origNode = this.node;
		
		this.determinePos();
		
		return (
			origX == this.getX() &&
			origY == this.getY() &&
			origNode == this.node
		);
	}
	
	/**
	 * Gets the coordinate held by this position. Does a defensive copy.
	 * @return A copy of the coordinate this position is at.
	 */
	public Coordinate getCoordinate(){
		return (Coordinate)super.clone();
	}
	
	/**
	 * Method to move this position one increment in one direction.
	 * @param dirToGo The direction this position is to go.
	 * @return If the position was able to move or not. Would return false if this position is a border on the side given.
	 */
	public boolean go(NodeDir dirToGo){
		if(this.node.isBorder(dirToGo)){
			return false;
		}
		//set the new value, x&y coord
		this.node = this.node.getNeighbor(dirToGo);
		this.setX(dirToGo.incDec(this.getX()));
		this.setY(dirToGo.incDec(this.getY()));
		
		return true;
	}
}
