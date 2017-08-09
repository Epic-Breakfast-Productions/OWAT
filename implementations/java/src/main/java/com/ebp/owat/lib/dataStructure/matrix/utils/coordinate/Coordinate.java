package com.ebp.owat.lib.dataStructure.matrix.utils.coordinate;

import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;

/**
 * Describes a coordinate on a matrix.
 *
 * Created by Greg Stewart on 4/4/17.
 */
public class Coordinate {
	/** The matrix this coordinate is on. */
	public final Matrix matrix;
	/** The X value (which col) of this coordinate. */
	private long x;
	/** The Y value (which row) of this coordinate. */
	private long y;
	
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
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public Coordinate(Matrix matrix, long xIn, long yIn) throws IllegalArgumentException{
		this(matrix);
		this.setX(xIn).setY(yIn);
	}
	
	/**
	 * Sets the X value (which col) of this coordinate.
	 * @param xIn The X value (which col) of this coordinate.
	 * @return This Coordinate.
	 * @throws OwatMatrixException If the value in is out of bounds.
	 */
	public Coordinate setX(long xIn) throws OwatMatrixException{
		if(!matrix.isValidColIndex(xIn)){
			throw new IllegalArgumentException("Invalid x(column) index given. Index given: " + xIn + " # rows: " + matrix.getNumCols());
		}
		this.x = xIn;
		return this;
	}
	
	/**
	 * Sets the Y value (which row) of this coordinate.
	 * @param yIn The Y value (which row) of this coordinate.
	 * @return This Coordinate.
	 * @throws OwatMatrixException If the value in is out of bounds.
	 */
	public Coordinate setY(long yIn) {
		if(!matrix.isValidRowIndex(yIn)){
			throw new IllegalArgumentException("Invalid y(row) index given. Index given: " + yIn + " # rows: " + matrix.getNumRows());
		}
		this.y = yIn;
		return this;
	}
	
	/**
	 * Gets the X value (which col) of this coordinate.
	 * @return The X value (which col) of this coordinate.
	 */
	public long getX(){
		return this.x;
	}
	
	/**
	 * Gets the y value (which row) of this coordinate.
	 * @return The y value (which row) of this coordinate.
	 */
	public long getY(){
		return this.y;
	}
	
	@Override
	public boolean equals(Object o) {
		if(o == null){
			return false;
		}
		Coordinate c;
		try{
			c = (Coordinate)o;
		}catch(ClassCastException e){
			return false;
		}
		return (
			this.matrix != c.matrix ||
			this.x != c.getX() ||
			this.y != c.getY()
		);
	}
	
	@Override
	protected Coordinate clone(){
		return new Coordinate(this.matrix, this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "Coordinate. Values: X(col)=" + this.x + " Y(row)=" + this.y + " Matrix: " + this.matrix;
	}
	
	@Override
	public int hashCode() {
		int result = matrix.toString().hashCode();
		result = 31 * result + (int) (x ^ (x >>> 32));
		result = 31 * result + (int) (y ^ (y >>> 32));
		return result;
	}
}
