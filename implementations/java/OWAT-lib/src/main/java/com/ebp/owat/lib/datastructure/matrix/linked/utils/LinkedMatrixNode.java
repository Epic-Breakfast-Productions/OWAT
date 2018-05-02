package com.ebp.owat.lib.datastructure.matrix.linked.utils;

import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

import static com.ebp.owat.lib.datastructure.matrix.linked.utils.Direction.*;

/**
 * A single node of the LinkedMatrix.
 *
 * Holds the nodes to the north, south, east and west of this node, and a value.
 *
 * @param <T> The type of value this holds.
 */
public class LinkedMatrixNode<T> {
	private boolean hasValue = false;
	private T value = null;

	private LinkedMatrixNode<T> north = null;
	private LinkedMatrixNode<T> south = null;
	private LinkedMatrixNode<T> east = null;
	private LinkedMatrixNode<T> west = null;

	public LinkedMatrixNode(){

	}

	public LinkedMatrixNode(T value){
		this();
		this.setValue(value);
	}

	/**
	 * Gets the LinkedMatrixNode at the direction given.
	 * @param dir The direction of the node to get.
	 * @return The node.
	 */
	public LinkedMatrixNode<T> getDir(Direction dir){
		switch (dir){
			case NORTH:
				return this.getNorth();
			case SOUTH:
				return this.getSouth();
			case EAST:
				return this.getEast();
			case WEST:
				return this.getWest();
		}
		throw new IllegalArgumentException("Invalid direction given.");
	}

	/**
	 * Sets the node on a certain direction.
	 * @param dir The direction to set.
	 * @param node The new node.
	 * @return The old node at the direction.
	 */
	public LinkedMatrixNode<T> setDir(Direction dir, LinkedMatrixNode<T> node){
		switch (dir) {
			case NORTH:
				return this.setNorth(node);
			case SOUTH:
				return this.setSouth(node);
			case EAST:
				return this.setEast(node);
			case WEST:
				return this.setWest(node);
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Gets the value held by this node.
	 * @return The value held by this node.
	 */
	public T getValue() {
		return value;
	}

	/**
	 * Gets the value held by this node.
	 * @param defaultVal What to return if this node does not have a value.
	 * @return The value held by this node. The default value if not holding anything.
	 */
	public T getValue(T defaultVal){
		return (this.hasValue ? this.getValue() : defaultVal);
	}

	/**
	 * Sets the value held by this node.
	 * @param value The new value.
	 * @return The old value held by this node.
	 */
	public T setValue(T value) {
		T old = this.getValue();
		this.value = value;
		this.hasValue = true;
		return old;
	}

	/**
	 * Clears this node of a value.
	 * @return The old value this held.
	 */
	public T clearValue(){
		T old = this.getValue();
		this.value = null;
		this.hasValue = false;
		return old;
	}

	/**
	 * Determines if this node has a value or not.
	 * @return If this node has a value or not.
	 */
	public boolean hasValue(){
		return this.hasValue;
	}

	/**
	 * Gets the node to the north of this node.
	 * @return The node to the north of this node.
	 */
	public LinkedMatrixNode<T> getNorth() {
		return north;
	}

	/**
	 * Sets the node to the north of this node.
	 *
	 * If the north node was not null, it clears itself from that node's south.
	 *
	 * @param north the new node to the north of this node.
	 * @return The node previously at this node's north.
	 */
	public LinkedMatrixNode<T> setNorth(LinkedMatrixNode<T> north) {
		if(this.isBorder(NORTH)){
			this.north = north;
			this.ensureDoublyLinked(NORTH);
			return null;
		}
		LinkedMatrixNode<T> old = this.getNorth();
		this.north = null;
		old.setDir(NORTH.opposite(), null);
		this.north = north;
		this.ensureDoublyLinked(NORTH);
		return old;
	}

	/**
	 * Gets the node to the south of this node.
	 * @return The node to the south of this node.
	 */
	public LinkedMatrixNode<T> getSouth() {
		return south;
	}

	/**
	 * Sets the node to the south of this node.
	 *
	 * If the south node was not null, it clears itself from that node's north.
	 *
	 * @param south the new node to the south of this node.
	 * @return The node previously at this node's south.
	 */
	public LinkedMatrixNode<T> setSouth(LinkedMatrixNode<T> south) {
		if(this.isBorder(SOUTH)){
			this.south = south;
			this.ensureDoublyLinked(SOUTH);
			return null;
		}
		LinkedMatrixNode<T> old = this.getSouth();
		this.south = null;
		old.setDir(SOUTH.opposite(), null);
		this.south = south;
		this.ensureDoublyLinked(SOUTH);
		return old;
	}

	/**
	 * Gets the node to the east of this node.
	 * @return The node to the east of this node.
	 */
	public LinkedMatrixNode<T> getEast() {
		return east;
	}

	/**
	 * Sets the node to the east of this node.
	 *
	 * If the east node was not null, it clears itself from that node's west.
	 *
	 * @param east The new node to the east of this node.
	 * @return The node previously at this node's east.
	 */
	public LinkedMatrixNode<T> setEast(LinkedMatrixNode<T> east) {
		if(this.isBorder(EAST)){
			this.east = east;
			this.ensureDoublyLinked(EAST);
			return null;
		}
		LinkedMatrixNode<T> old = this.getEast();
		this.east = null;
		old.setDir(EAST.opposite(), null);
		this.east = east;
		this.ensureDoublyLinked(EAST);
		return old;
	}

	/**
	 * Gets the node to the west of this node.
	 * @return The node to the west of this node.
	 */
	public LinkedMatrixNode<T> getWest() {
		return west;
	}

	/**
	 * Sets the node to the west of this node.
	 *
	 * If the west node was not null, it clears itself from that node's east.
	 *
	 * @param west The new node to the west of this node.
	 * @return The node previously at this node's west.
	 */
	public LinkedMatrixNode<T> setWest(LinkedMatrixNode<T> west) {
		if(this.isBorder(WEST)){
			this.west = west;
			this.ensureDoublyLinked(WEST);
			return null;
		}
		LinkedMatrixNode<T> old = this.getWest();
		this.west = null;
		old.setDir(WEST.opposite(), null);
		this.west = west;
		this.ensureDoublyLinked(WEST);
		return old;
	}

	/**
	 * Determines if the node is a border on the direction given.
	 * @param dir The direction to check.
	 * @return If the node is a border on the direction given.
	 */
	public boolean isBorder(Direction dir){
		switch (dir){
			case NORTH:
				return this.getNorth() == null;
			case SOUTH:
				return this.getSouth() == null;
			case EAST:
				return this.getEast() == null;
			case WEST:
				return this.getWest() == null;
		}
		throw new IllegalArgumentException();
	}

	/**
	 * Determines if this node borders the node given on the direction given.
	 * @param node The node to determine if it borders this node.
	 * @param dir The direction to test the border of.
	 * @return If this node borders the node given on the direction given.
	 */
	public boolean borders(LinkedMatrixNode<T> node, Direction dir){
		return this.getDir(dir) == node;
	}

	/**
	 * Determines if this node borders the node given.
	 * @param node The node to determine if it borders this node.
	 * @return The direction the node is in. Null if this doesn't border the node given.
	 */
	public Direction borders(LinkedMatrixNode<T> node){
		for(Direction curDir : Direction.values()){
			if(this.borders(node, curDir)){
				return curDir;
			}
		}
		return null;
	}

	/**
	 * Gets the distance this node has to get to the border.
	 * @param dir The direction of the border.
	 * @return The distance to the border given.
	 */
	public long distanceToBorder(Direction dir){
		long count = 0;

		LinkedMatrixNode<T> curNode = this;
		while (!curNode.isBorder(dir)){
			count++;
			curNode = curNode.getDir(dir);
		}

		return count;
	}

	/**
	 * Gets the coordinate that cooresponds to this node's location on the matrix.
	 * @return The location of this node on the matrix.
	 */
	public Coordinate getCoord(){
		Coordinate output = new Coordinate();

		output.setX(this.distanceToBorder(Direction.WEST));
		output.setY(this.distanceToBorder(Direction.NORTH));

		return output;
	}

	/**
	 * Ensures this node is doubly linked at the direction given.
	 * @param dir The direction to ensure is doubly linked.
	 */
	private void ensureDoublyLinked(Direction dir){
		LinkedMatrixNode<T> node = this.getDir(dir);
		if(node == null){
			return;
		}

		if(node.getDir(dir.opposite()) == this){
			return;
		}

		node.setDir(dir.opposite(), this);
	}

	/**
	 * Ensures this node is doubly linked on all sides.
	 */
	private void ensureDoublyLinked(){
		for(Direction curDir : Direction.values()){
			this.ensureDoublyLinked(curDir);
		}
	}
}
