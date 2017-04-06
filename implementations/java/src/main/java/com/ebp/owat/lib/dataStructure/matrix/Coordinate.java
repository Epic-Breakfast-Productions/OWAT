package com.ebp.owat.lib.dataStructure.matrix;

import java.math.BigInteger;

/**
 * Describes a coordinate on a matrix.
 *
 * Created by Greg Stewart on 4/4/17.
 */
public class Coordinate {
	/** The matrix this coordinate is on. */
	private final Matrix matrix;
	/** The X value (which col) of this coordinate. */
	private BigInteger x;
	/** The Y value (which row) of this coordinate. */
	private BigInteger y;
	
	/**
	 * Constructs a new coordinate with a matrix.
	 * @param matrix The matrix to give to this Coordinate.
	 */
	public Coordinate(Matrix matrix){
		this.matrix = matrix;
	}
	
	/**
	 * Constructs a new coordinate with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param xIn The X value (which col) of this coordinate.
	 * @param yIn The Y value (which row) of this coordinate.
	 */
	public Coordinate(Matrix matrix, BigInteger xIn, BigInteger yIn){
		this(matrix);
		this.setX(xIn).setY(yIn);
	}
	
	/**
	 * Sets the X value (which col) of this coordinate.
	 * @param xIn The X value (which col) of this coordinate.
	 * @return This Coordinate.
	 */
	public Coordinate setX(BigInteger xIn){
		matrix.checkValidColNumber(xIn);
		return this;
	}
	
	/**
	 * Sets the Y value (which row) of this coordinate.
	 * @param yIn The Y value (which row) of this coordinate.
	 * @return This Coordinate.
	 */
	public Coordinate setY(BigInteger yIn){
		matrix.checkValidColNumber(yIn);
		return this;
	}
	
	/**
	 * Gets the X value (which col) of this coordinate.
	 * @return The X value (which col) of this coordinate.
	 */
	public BigInteger getX(){
		return this.x;
	}
	
	/**
	 * Gets the y value (which row) of this coordinate.
	 * @return The y value (which row) of this coordinate.
	 */
	public BigInteger getY(){
		return this.y;
	}
}
