package com.ebp.owat.lib.datastructure.matrix.utils.coordinate;

import java.util.Objects;

/**
 * Describes a spot on a matrix.
 *
 * TODO:
 *  Test this specifically.
 */
public class Coordinate {
	/** The X value (which col) of this coordinate. */
	protected long x;
	/** The Y value (which row) of this coordinate. */
	protected long y;

	/**
	 * Base constructor. Initializes X/Y to 0.
	 */
	public Coordinate(){
		this.x = 0;
		this.y = 0;
	}

	/**
	 * Constructor to set the x/y values.
	 * @param x The x value of this coordinate.
	 * @param y The y value of this coordinate.
	 */
	public Coordinate(long x, long y){
		this.setX(x);
		this.setY(y);
	}

	/**
	 * Sets the X value (which col) of this coordinate.
	 * @param xIn The X value (which col) of this coordinate.
	 * @return This Coordinate.
	 * @throws IllegalArgumentException If the value in is out of bounds.
	 */
	public Coordinate setX(long xIn) {
		throwIfInvalidIndex(xIn);
		this.x = xIn;
		return this;
	}

	/**
	 * Increments this node's X position.
	 * @return This coordinate.
	 */
	public Coordinate incX(){
		this.setX(this.getX() + 1);
		return this;
	}

	/**
	 * Decrements this node's X position.
	 * @return This coordinate.
	 */
	public Coordinate decX(){
		this.setX(this.getX() - 1);
		return this;
	}

	/**
	 * Sets the Y value (which row) of this coordinate.
	 * @param yIn The Y value (which row) of this coordinate.
	 * @return This MatrixCoordinate.
	 * @throws IllegalArgumentException If the value in is out of bounds.
	 */
	public Coordinate setY(long yIn) {
		throwIfInvalidIndex(yIn);
		this.y = yIn;
		return this;
	}

	/**
	 * Increments this node's y position.
	 * @return This coordinate.
	 */
	public Coordinate incY(){
		this.setY(this.getY() + 1);
		return this;
	}

	/**
	 * Decrements this node's y position.
	 * @return This coordinate.
	 */
	public Coordinate decY(){
		this.setY(this.getY() - 1);
		return this;
	}

	/**
	 * Gets the X value (which col) of this coordinate.
	 * @return The X value (which col) of this coordinate.
	 */
	public long getX(){
		return this.x;
	}

	public long getCol(){
		return this.getX();
	}

	/**
	 * Gets the y value (which row) of this coordinate.
	 * @return The y value (which row) of this coordinate.
	 */
	public long getY(){
		return this.y;
	}
	public long getRow(){
		return this.getY();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Coordinate that = (Coordinate) o;
		return x == that.x &&
			y == that.y;
	}

	@Override
	public int hashCode() {
		return Objects.hash(x, y);
	}

	@Override
	public String toString() {
		return "Coordinate{" +
			"x=" + x +
			", y=" + y +
			'}';
	}

	@Override
	protected Object clone() {
		return new Coordinate(this.getX(), this.getY());
	}

	private void throwIfInvalidIndex(long index){
		if(index < 0){
			throw new IllegalArgumentException("Index given was invalid.");
		}
	}
}
