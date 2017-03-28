package com.ebp.owat.lib.dataStructure.node;

import com.ebp.owat.lib.dataStructure.node.value.NodeValue;

/**
 * Abstract class for a single node of the matrix.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public abstract class Node<T extends NodeValue> {
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
	 * @param dirIn The direction of the node the nodeIn should be set to.
	 * @param nodeIn The node to set to the direction given.
	 * @return This Node.
	 */
	public Node<T> setNode(NodeDir dirIn, Node<T> nodeIn){
		switch (dirIn){
			case NORTH:
				this.north = nodeIn;
				break;
			case SOUTH:
				this.south = nodeIn;
				break;
			case EAST:
				this.east = nodeIn;
				break;
			case WEST:
				this.west = nodeIn;
				break;
		}
		return this;
	}
	
	/**
	 * Sets the northern node.
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setNorthNode(Node<T> nodeIn){
		this.setNode(NodeDir.NORTH, nodeIn);
		return this;
	}
	
	/**
	 * Sets the southern node.
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setSouthNode(Node<T> nodeIn){
		this.setNode(NodeDir.SOUTH, nodeIn);
		return this;
	}
	
	/**
	 * Sets the eastern node.
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setEastNode(Node<T> nodeIn){
		this.setNode(NodeDir.EAST, nodeIn);
		return this;
	}
	
	/**
	 * Sets the western node.
	 * @param nodeIn The node to set.
	 * @return This Node.
	 */
	public Node<T> setWestNode(Node<T> nodeIn){
		this.setNode(NodeDir.WEST, nodeIn);
		return this;
	}
	
	/**
	 * Node Direction, use this to tell a node which direction you mean.
	 */
	public enum NodeDir {NORTH,SOUTH,EAST,WEST}
	
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
	
	public T getValue(){
		return this.value;
	}
	
	public Node<T> setValue(T valueIn){
		this.value = valueIn;
		return this;
	}
	
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
