package com.ebp.owat.lib.datastructure.matrix.utils.coordinate;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;

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
		if(!matrix.hasRowsCols()){
			throw new IllegalStateException("Cannot have a coordinate on an empty matrix.");
		}
		this.matrix = matrix;
		this.setY(0).setX(0);
	}
	
	/**
	 * Constructs a new coordinate with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param xIn The X value (which col) of this coordinate.
	 * @param yIn The Y value (which row) of this coordinate.
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public Coordinate(Matrix matrix, long xIn, long yIn){
		this(matrix);
		this.setX(xIn).setY(yIn);
	}
	
	/**
	 * Sets the X value (which col) of this coordinate.
	 * @param xIn The X value (which col) of this coordinate.
	 * @return This Coordinate.
	 * @throws IllegalArgumentException If the value in is out of bounds.
	 */
	public Coordinate setX(long xIn) {
		MatrixValidator.throwIfBadIndex(this.matrix, xIn, Plane.X);
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
		MatrixValidator.throwIfBadIndex(this.matrix, yIn, Plane.Y);
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
	
	/**
	 * Determines if the matrix of the coordinate given is the same as the one this coordinate is on.
	 * @param coordIn The coordinate to test against.
	 * @return If this and the coordinate given is on the same matrix or not.
	 */
	public boolean isOnSameMatrix(Coordinate coordIn){
		return MatrixValidator.isOnSameMatrix(this, coordIn);
	}
	
	/**
	 * Determines if this coordinate is still on the matrix. It is possible to be not on the matrix if the matrix is shrunk after the coordinate has been created.
	 * @return If this coordinate is still on the matrix.
	 */
	public boolean stillOnMatrix(){
		return (
			this.matrix.getNumRows() > this.getY() &&
			this.matrix.getNumCols() > this.getX()
		);
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
			this.matrix == c.matrix &&
			this.x == c.getX() &&
			this.y == c.getY()
		);
	}
	
	@Override
	public Coordinate clone(){
		return new Coordinate(this.matrix, this.x, this.y);
	}
	
	@Override
	public String toString() {
		return "Coordinate. Values: X(col)=" + this.x + " Y(row)=" + this.y + " LinkedMatrix: " + this.matrix;
	}
	
	@Override
	public int hashCode() {
		int result = matrix.toString().hashCode();
		result = 31 * result + (int) (x ^ (x >>> 32));
		result = 31 * result + (int) (y ^ (y >>> 32));
		return result;
	}
}
