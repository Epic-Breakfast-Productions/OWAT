package com.ebp.owat.lib.datastructure.matrix.Linked;

/**
 * Abstract class for a single value of the matrix.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class LinkedMatrixNode<T> {
	/** The Node that is 'north' or 'above' this value. */
	private LinkedMatrixNode<T> north = null;
	/** The value that is 'south' or 'below' this value */
	private LinkedMatrixNode<T> south = null;
	/** The value that is 'east' or 'to the right of' this value */
	private LinkedMatrixNode<T> east = null;
	/** The value that is 'west' or 'to the left of' this value */
	private LinkedMatrixNode<T> west = null;
	
	/** The value this value holds. */
	private T value = null;
	
	/**
	 * Basic constructor. Does not set any values.
	 */
	public LinkedMatrixNode(){}
	
	/**
	 * Constructor that sets this value's value.
	 * @param nodeValue The value to set this value to.
	 */
	public LinkedMatrixNode(T nodeValue){
		this();
		this.value = nodeValue;
	}
	
	/**
	 * Sets a value to the value of the direction given.
	 *
	 * Automatically sets the NodeIn's opposite direction to this.
	 *
	 * @param dirIn The direction of the value the nodeIn should be set to.
	 * @param nodeIn The value to set to the direction given.
	 * @return This Node.
	 */
	public LinkedMatrixNode<T> setNeighbor(NodeDir dirIn, LinkedMatrixNode<T> nodeIn){
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
				throw new IllegalArgumentException(NodeDir.BAD_DIR_GIVEN_ERR_MESSAGE + dirIn);
		}
		if(nodeIn != null && !nodeIn.borders(this, dirIn.opposite())){
			nodeIn.setNeighbor(dirIn.opposite(), this);
		}
		return this;
	}
	
	/**
	 * Removes the value in the direction given from this value. Does removes this value from the other value.
	 * @param dirIn The direction to remove this from.
	 * @return The value removed.
	 */
	public LinkedMatrixNode<T> removeNeighbor(NodeDir dirIn){
		LinkedMatrixNode<T> nodeToRemove = this.getNeighbor(dirIn);
		if(nodeToRemove != null){
			nodeToRemove.setNeighbor(dirIn.opposite(), null);
		}else{
			return null;
		}
		this.setNeighbor(dirIn, null);
		return nodeToRemove;
	}
	
	/**
	 * Removes this value from the rest of the matrix.
	 *
	 * @return This value.
	 */
	public LinkedMatrixNode<T> removeSelf(){
		for(NodeDir curDir : NodeDir.values()){
			this.removeNeighbor(curDir);
		}
		return this;
	}
	
	/**
	 * Gets the value at the direction given.
	 *
	 * @param dirIn The direction to get.
	 * @return This value.
	 */
	public LinkedMatrixNode<T> getNeighbor(NodeDir dirIn){
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
		throw new IllegalArgumentException(NodeDir.BAD_DIR_GIVEN_ERR_MESSAGE + dirIn);
	}
	
	/**
	 * Gets the value held by this value.
	 * @return The value held by this value.
	 */
	public T getValue(){
		return this.value;
	}
	
	/**
	 * Sets the value held by this value.
	 * @param valueIn The value to set this value with.
	 * @return This value object.
	 */
	public LinkedMatrixNode<T> setValue(T valueIn){
		this.value = valueIn;
		return this;
	}
	
	/**
	 * Sets the value of this value with the value of another value.
	 * @param nodeIn The value to get the value from to set this value's value to.
	 * @return This value object.
	 */
	public LinkedMatrixNode<T> setValue(LinkedMatrixNode<T> nodeIn){
		this.value = nodeIn.getValue();
		return this;
	}
	
	/**
	 * Determines if the value is a border to any side.
	 *
	 * @return If the value is a border to any side.
	 */
	public boolean isBorder(){
		return (this.north == null ||
				this.south == null ||
				this.east == null ||
				this.west == null
		);
	}
	
	/**
	 * Determines if this value is a border on a specific side.
	 *
	 * @param sideBorder The side we are testing to see if the value is a border of.
	 * @return If this value is a border to a specific side.
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
				throw new IllegalArgumentException(NodeDir.BAD_DIR_GIVEN_ERR_MESSAGE + sideBorder);
		}
	}
	
	/**
	 * Determines if the given value borders this one.
	 * @param nodeIn The value to see if it borders this value.
	 * @return If the given value borders this one.
	 */
	public boolean borders(LinkedMatrixNode<T> nodeIn){
		return this.north == nodeIn ||
				this.south == nodeIn ||
				this.east == nodeIn ||
				this.west == nodeIn;
	}
	
	/**
	 * Determines if the value given borders this value in the direction given.
	 * @param nodeIn The value to test if it borders.
	 * @param dirIn The direction to test on.
	 * @return If the value given borders this value in the direction given.
	 */
	public boolean borders(LinkedMatrixNode<T> nodeIn, NodeDir dirIn){
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
		throw new IllegalArgumentException(NodeDir.BAD_DIR_GIVEN_ERR_MESSAGE + dirIn);
	}
	
	/**
	 * Determines if this value is a corner value.
	 * @return If this value is a corner value.
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
	 * Trades a value with another value.
	 *
	 * @param nodeIn The value to trade values with.
	 */
	public void tradeValuesWith(LinkedMatrixNode<T> nodeIn){
		T temp = nodeIn.getValue();
		nodeIn.setValue(this);
		this.setValue(temp);
	}
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		
		LinkedMatrixNode<?> that = (LinkedMatrixNode<?>) o;
		
		if (north != null ? !north.equals(that.north) : that.north != null) return false;
		if (south != null ? !south.equals(that.south) : that.south != null) return false;
		if (east != null ? !east.equals(that.east) : that.east != null) return false;
		if (west != null ? !west.equals(that.west) : that.west != null) return false;
		return getValue() != null ? getValue().equals(that.getValue()) : that.getValue() == null;
	}
	
	@Override
	public int hashCode() {
		int result = north != null ? north.hashCode() : 0;
		result = 31 * result + (south != null ? south.hashCode() : 0);
		result = 31 * result + (east != null ? east.hashCode() : 0);
		result = 31 * result + (west != null ? west.hashCode() : 0);
		result = 31 * result + (getValue() != null ? getValue().hashCode() : 0);
		return result;
	}
}//Node
