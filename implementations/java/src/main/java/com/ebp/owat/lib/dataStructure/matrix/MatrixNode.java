package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.node.OwatNodeException;

/**
 * Abstract class for a single node of the matrix.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class MatrixNode<T> {
	private static final String BAD_DIR_GIVEN_ERR_MESSAGE = "Somehow gave a bad direction in. Dir in: ";
	
	/**
	 * Node Direction, use this to tell a node which direction you mean.
	 */
	public enum NodeDir {
		NORTH,SOUTH,EAST,WEST;
		private NodeDir opposite;
		static {
			NORTH.opposite = SOUTH;
			SOUTH.opposite = NORTH;
			EAST.opposite = WEST;
			WEST.opposite = EAST;
		}
		public NodeDir opposite(){
			return this.opposite;
		}
	}
	
	/** The Node that is 'north' or 'above' this node. */
	private MatrixNode<T> north = null;
	/** The node that is 'south' or 'below' this node */
	private MatrixNode<T> south = null;
	/** The node that is 'east' or 'to the right of' this node */
	private MatrixNode<T> east = null;
	/** The node that is 'west' or 'to the left of' this node */
	private MatrixNode<T> west = null;
	
	/** The value this node holds. */
	private T value = null;
	
	/**
	 * Basic constructor. Does not set any values.
	 */
	public MatrixNode(){}
	
	/**
	 * Constructor that sets this node's value.
	 * @param nodeValue The value to set this node to.
	 */
	public MatrixNode(T nodeValue){
		this();
		this.value = nodeValue;
	}
	
	/**
	 * Sets a node to the node of the direction given.
	 *
	 * Automatically sets the NodeIn's opposite direction to this.
	 *
	 * @param dirIn The direction of the node the nodeIn should be set to.
	 * @param nodeIn The node to set to the direction given.
	 * @return This Node.
	 */
	public MatrixNode<T> setNode(NodeDir dirIn, MatrixNode<T> nodeIn){
		switch (dirIn) {
			case NORTH:
				this.north = nodeIn;
				break;
			case SOUTH:
				this.north = nodeIn;
				break;
			case EAST:
				this.north = nodeIn;
				break;
			case WEST:
				this.north = nodeIn;
				break;
			default:
				throw new OwatNodeException(BAD_DIR_GIVEN_ERR_MESSAGE + dirIn);
		}
		return this;
	}
	
	/**
	 * Removes the node in the direction given from this node. Does removes this node from the other node.
	 * @param dirIn The direction to remove this from.
	 * @return The node removed.
	 */
	public MatrixNode<T> removeNeighbor(NodeDir dirIn){
		MatrixNode<T> nodeToRemove = this.getNeighbor(dirIn);
		if(nodeToRemove != null){
			nodeToRemove.setNode(dirIn.opposite(), null);
		}else{
			return null;
		}
		this.setNode(dirIn, null);
		return nodeToRemove;
	}
	
	/**
	 * Removes this node from the rest of the matrix.
	 *
	 * @return This node.
	 */
	public MatrixNode<T> removeSelf(){
		for(NodeDir curDir : NodeDir.values()){
			this.removeNeighbor(curDir);
		}
		return this;
	}
	
	/**
	 * Gets the node at the direction given.
	 *
	 * @param dirIn The direction to get.
	 * @return This node.
	 */
	public MatrixNode<T> getNeighbor(NodeDir dirIn){
		switch (dirIn){
			case NORTH:
				return this.north;
			case SOUTH:
				return this.south;
			case EAST:
				return this.east;
			case WEST:
				return this.west;
		}
		throw new OwatNodeException(BAD_DIR_GIVEN_ERR_MESSAGE + dirIn);
	}
	
	/**
	 * Gets the value held by this node.
	 * @return The value held by this node.
	 */
	public T getValue(){
		return this.value;
	}
	
	/**
	 * Sets the value held by this node.
	 * @param valueIn The value to set this node with.
	 * @return This node object.
	 */
	public MatrixNode<T> setValue(T valueIn){
		this.value = valueIn;
		return this;
	}
	
	/**
	 * Sets the value of this node with the value of another node.
	 * @param nodeIn The node to get the value from to set this node's value to.
	 * @return This node object.
	 */
	public MatrixNode<T> setValue(MatrixNode<T> nodeIn){
		this.value = nodeIn.getValue();
		return this;
	}
	
	/**
	 * Determines if the node is a border to any side.
	 *
	 * @return If the node is a border to any side.
	 */
	public boolean isBorder(){
		return (this.north == null ||
				this.south == null ||
				this.east == null ||
				this.west == null
		);
	}
	
	/**
	 * Determines if this node is a border on a specific side.
	 *
	 * @param sideBorder The side we are testing to see if the node is a border of.
	 * @return If this node is a border to a specific side.
	 */
	public boolean isBorder(NodeDir sideBorder){
		switch (sideBorder){
			case NORTH:
				return (this.north == null);
			case SOUTH:
				return (this.south == null);
			case EAST:
				return (this.east == null);
			case WEST:
				return (this.west == null);
			default:
				throw new OwatNodeException(BAD_DIR_GIVEN_ERR_MESSAGE + sideBorder);
		}
	}
	
	/**
	 * Determines if the given node borders this one.
	 * @param nodeIn The node to see if it borders this node.
	 * @return If the given node borders this one.
	 */
	public boolean borders(MatrixNode<T> nodeIn){
		return this.north == nodeIn ||
				this.south == nodeIn ||
				this.east == nodeIn ||
				this.west == nodeIn;
	}
	
	/**
	 * Determines if the node given borders this node in the direction given.
	 * @param nodeIn The node to test if it borders.
	 * @param dirIn The direction to test on.
	 * @return If the node given borders this node in the direction given.
	 */
	public boolean borders(MatrixNode<T> nodeIn, NodeDir dirIn){
		switch (dirIn){
			case NORTH:
				return this.north == nodeIn;
			case SOUTH:
				return this.south == nodeIn;
			case EAST:
				return this.east == nodeIn;
			case WEST:
				return this.west == nodeIn;
		}
		throw new OwatNodeException(BAD_DIR_GIVEN_ERR_MESSAGE + dirIn);
	}
	
	/**
	 * Determines if this node is a corner node.
	 * @return If this node is a corner node.
	 */
	public boolean isCorner(){
		return (
				(this.north == null && this.east == null) ||
				(this.north == null && this.west == null) ||
				(this.south == null && this.east == null) ||
				(this.south == null && this.west == null)
				);
	}
	
	/**
	 * Trades a value with another node.
	 *
	 * @param nodeIn The node to trade values with.
	 */
	public void tradeValuesWith(MatrixNode<T> nodeIn){
		T temp = nodeIn.getValue();
		nodeIn.setValue(this);
		this.setValue(temp);
	}
}//Node
