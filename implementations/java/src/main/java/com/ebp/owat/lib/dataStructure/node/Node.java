package com.ebp.owat.lib.dataStructure.node;

import com.ebp.owat.lib.dataStructure.node.value.NodeValue;

/**
 * Created by stewy on 3/23/17.
 */
public abstract class Node<T extends NodeValue> {
	/** The Node that is 'north' or 'above' this node. */
	private final Node<T> north;
	/** The node that is 'south' or 'below' this node */
	private final Node<T> south;
	/** The node that is 'east' or 'to the right of' this node */
	private final Node<T> east;
	/** The node that is 'west' or 'to the left of' this node */
	private final Node<T> west;
	
	/** The value this node holds. */
	private T value;
	
	public Node(Node<T> north, Node<T> south, Node<T> east, Node<T> west){
		this.north = north;
		this.south = south;
		this.east = east;
		this.west = west;
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
