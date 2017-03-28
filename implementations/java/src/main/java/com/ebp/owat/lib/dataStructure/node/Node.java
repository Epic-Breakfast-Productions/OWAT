package com.ebp.owat.lib.dataStructure.node;

import com.ebp.owat.lib.dataStructure.node.value.NodeValue;

/**
 * Abstract class for a single node of the matrix.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public abstract class Node<T extends NodeValue> {
	/**
	 * Node Direction, use this to tell a node which direction you mean.
	 */
	public enum NodeDir {NORTH,SOUTH,EAST,WEST}
	
	/** The Node that is 'north' or 'above' this node. */
	private Node<T> north = null;
	/** The node that is 'south' or 'below' this node */
	private Node<T> south = null;
	/** The node that is 'east' or 'to the right of' this node */
	private Node<T> east = null;
	/** The node that is 'west' or 'to the left of' this node */
	private Node<T> west = null;
	
	/** The value this node holds. */
	private T value = null;
	
	/**
	 * Basic constructor. Does not set any values.
	 */
	public Node(){}
	
	/**
	 * Constructor that sets this node's value.
	 * @param nodeValue The value to set this node to.
	 */
	public Node(T nodeValue){
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
	private Node<T> setNode(NodeDir dirIn, Node<T> nodeIn){
		switch (dirIn){
			case NORTH:
				this.north = nodeIn;
				if(nodeIn.getSouth() != this) {
					nodeIn.setSouthNode(this);
				}
				break;
			case SOUTH:
				this.south = nodeIn;
				if(nodeIn.getNorth() != this) {
					nodeIn.setNorthNode(this);
				}
				break;
			case EAST:
				this.east = nodeIn;
				if(nodeIn.getWest() != this) {
					nodeIn.setWestNode(this);
				}
				break;
			case WEST:
				this.west = nodeIn;
				if(nodeIn.getEast() != this) {
					nodeIn.setEastNode(this);
				}
				break;
		}
		return this;
	}
	
	/**
	 * Sets the northern node.
	 *
	 * Automatically sets the NodeIn's south direction to this.
	 *
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setNorthNode(Node<T> nodeIn){
		this.setNode(NodeDir.NORTH, nodeIn);
		return this;
	}
	
	/**
	 * Sets the southern node.
	 *
	 * Automatically sets the NodeIn's north direction to this.
	 *
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setSouthNode(Node<T> nodeIn){
		this.setNode(NodeDir.SOUTH, nodeIn);
		return this;
	}
	
	/**
	 * Sets the eastern node.
	 *
	 * Automatically sets the NodeIn's west direction to this.
	 *
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setEastNode(Node<T> nodeIn){
		this.setNode(NodeDir.EAST, nodeIn);
		return this;
	}
	
	/**
	 * Sets the western node.
	 *
	 * Automatically sets the NodeIn's east direction to this.
	 *
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setWestNode(Node<T> nodeIn){
		this.setNode(NodeDir.WEST, nodeIn);
		return this;
	}
	
	/**
	 * Gets the node to the north of this node.
	 * @return The node to the north of this node.
	 */
	public Node<T> getNorth(){return north;}
	
	/**
	 * Gets the node to the south of this node.
	 * @return The node to the south of this node.
	 */
	public Node<T> getSouth(){return west;}
	
	/**
	 * Gets the node to the east of this node.
	 * @return The node to the east of this node.
	 */
	public Node<T> getEast(){return east;}
	
	/**
	 * Gets the node to the south of this node.
	 * @return The node to the south of this node.
	 */
	public Node<T> getWest(){return south;}
	
	/**
	 * Trades a value with another node.
	 *
	 * @param nodeIn The node to trade values with.
	 */
	public void trade(Node<T> nodeIn){
		T temp = nodeIn.getValue();
		nodeIn.setValue(this);
		this.setValue(temp);
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
	public Node<T> setValue(T valueIn){
		this.value = valueIn;
		return this;
	}
	
	/**
	 * Sets the value of this node with the value of another node.
	 * @param nodeIn The node to get the value from to set this node's value to.
	 * @return This node object.
	 */
	public Node<T> setValue(Node<T> nodeIn){
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
				throw new OwatNodeException("Bad site border given. You should not be able to get this.");
		}
	}
	
	/**
	 * Determines if the given node borders this one.
	 * @param nodeIn The node to see if it borders this node.
	 * @return If the given node borders this one.
	 */
	public boolean borders(Node<T> nodeIn){
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
	public boolean borders(Node<T> nodeIn, NodeDir dirIn){
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
		throw new OwatNodeException("Didn't catch in dir switch. This should not happen.");
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
}//Node
