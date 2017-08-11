package com.ebp.owat.lib.dataStructure.matrix.utils.coordinate;

import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.matrix.utils.MatrixNode;
import com.ebp.owat.lib.dataStructure.matrix.utils.NodeDir;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Describes a node on a matrix, holding the node on the matrix and provides functionality to update that position automatically based on what kind of position this is and how the matrix grows/shrinks.
 *
 * TODO:: organize and ensure x/y coords are set
 *
 * @param <T> The type held by the matrix. Needed to ensure proper typing of the MatrixNode.
 */
public abstract class NodePos<T> extends Coordinate {
	/**
	 * Enum to describe which node is held in the spot in the hashmap.
	 */
	public enum FixedNodePos{
		TOP_LEFT(NodeDir.NORTH, NodeDir.WEST),
		TOP_RIGHT(NodeDir.NORTH, NodeDir.EAST),
		BOT_LEFT(NodeDir.SOUTH, NodeDir.WEST),
		BOT_RIGHT(NodeDir.SOUTH, NodeDir.EAST);
		
		private List<NodeDir> toBorder = new LinkedList<>();
		
		FixedNodePos(NodeDir ... directionsToBorder){
			toBorder.addAll(Arrays.asList(directionsToBorder));
		}
		
		public List<NodeDir> getBorderDirs(){
			return new LinkedList<>(this.toBorder);
		}
	}
	
	/** The node this position holds. Can be updated by {@link NodePos#determinePos() determinePos()}. */
	protected MatrixNode<T> node;
	
	/**
	 * Most basic constructor for a NodePos.
	 * @param matrix The matrix this potision is a part of.
	 */
	private NodePos(Matrix<T> matrix){
		super(matrix);
	}
	
	/**
	 * Constructor that takes in a matrix and a node. Calls {@link NodePos#determinePos() determinePos()} automatically.
	 * @param matrix The matrix this position is a part of.
	 * @param nodeIn The node this position holds.
	 */
	public NodePos(Matrix<T> matrix, MatrixNode<T> nodeIn){
		this(matrix);
		this.setNode(nodeIn);
	}
	
	/**
	 * Constructs a new NodePositon with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param coord The coordinate of the node to get.
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public NodePos(Matrix<T> matrix, Coordinate coord) throws IllegalArgumentException {
		this(matrix, matrix.getNode(coord, false));
	}
	
	/**
	 * Constructs a new NodePositon with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param xIn    The X value (which col) of this coordinate.
	 * @param yIn    The Y value (which row) of this coordinate.
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public NodePos(Matrix<T> matrix, long xIn, long yIn) throws IllegalArgumentException {
		this(matrix, matrix.getNode(new Coordinate(matrix, xIn, yIn), false));
	}
	
	/**
	 * Sets the node held by this position. Calls {@link NodePos#determinePos() determinePos()} automatically.
	 * @param nodeIn The node to set.
	 * @return This Node position.
	 */
	public NodePos<T> setNode(MatrixNode<T> nodeIn){
		//TODO:: check if node is in matrix?
		this.node = nodeIn;
		//TODO:: determine x/y pos
		return this;
	}
	
	/**
	 * Gets the node held at this position.
	 * @return The node at this position.
	 */
	public MatrixNode<T> getNode() {
		return this.node;
	}
	
	/**
	 * Method used by the position to recalculate and update the appropriate position of this node.
	 *
	 * Sets the x&y coordinates
	 */
	protected abstract void determinePos();
	
	/**
	 * Method used to reset this node's position. For use after the matrix is altered (row/col added/ removed). Can change both the node and coordinate held.
	 *
	 * @return If the node's position actually changed or not.
	 */
	public boolean resetNodePos(){
		long origX = this.getX();
		long origY = this.getY();
		MatrixNode<T> origNode = this.node;
		
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
		//set the new node, x&y coord
		this.node = this.node.getNeighbor(dirToGo);
		this.setX(dirToGo.incDec(this.getX()));
		this.setY(dirToGo.incDec(this.getY()));
		
		return true;
	}
}
