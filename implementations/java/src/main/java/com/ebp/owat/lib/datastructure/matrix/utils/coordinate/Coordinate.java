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
	
	/** The default method of finding distances from one coordinate to another. */
	public static final DistanceCalc.Method DEFAULT_DISTANCE_CALC_METHOD = DistanceCalc.Method.MANHATTAN;
	
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
	public Coordinate(Matrix matrix, long xIn, long yIn){
		this(matrix);
		this.setX(xIn).setY(yIn);
	}
	
	public void checkCoordOnMatrix(){
		if(!this.matrix.isValidColIndex(this.x) || !this.matrix.isValidRowIndex(this.y)){
			throw new IllegalStateException("Bad x index given.");
		}
	}
	
	/**
	 * Sets the X value (which col) of this coordinate.
	 * @param xIn The X value (which col) of this coordinate.
	 * @return This Coordinate.
	 * @throws OwatMatrixException If the value in is out of bounds.
	 */
	public Coordinate setX(long xIn) throws OwatMatrixException{
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
	
	/**
	 * Gets the y value (which row) of this coordinate.
	 * @return The y value (which row) of this coordinate.
	 */
	public long getY(){
		return this.y;
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
	 * Gets the x distance to the index given.
	 * @param xIndex The column index.
	 * @return The distance from this coordinate to the index given.
	 */
	public long xDistanceTo(long xIndex){
		MatrixValidator.throwIfBadIndex(this.matrix, xIndex, Plane.X);
		//TODO
		return 0L;
	}
	
	/**
	 * Gets the x distance to the index given.
	 * @param coordIn The coordinate to get the x-index from.
	 * @return The distance from this coordinate to the coordinate given's x index.
	 */
	public long xDistanceTo(Coordinate coordIn){
		MatrixValidator.throwIfNotOnSameMatrix(this, coordIn);
		return this.xDistanceTo(coordIn.getX());
	}
	
	/**
	 * Gets the y distance to the index given.
	 * @param yIndex The row index.
	 * @return The distance from this coordinate to the index given.
	 */
	public long yDistanceTo(long yIndex){
		MatrixValidator.throwIfBadIndex(this.matrix, yIndex, Plane.Y);
		//TODO
		return 0L;
	}
	
	/**
	 * Gets the y distance to the index given.
	 * @param coordIn The coordinate to get the y-index from.
	 * @return The distance from this coordinate to the coordinate given's y index.
	 */
	public long yDistanceTo(Coordinate coordIn){
		MatrixValidator.throwIfNotOnSameMatrix(this, coordIn);
		return this.yDistanceTo(coordIn.getY());
	}
	
	/**
	 * Gets the distance to another coordinate.
	 * @param coordIn The coordinate to get the distance to.
	 * @param method The method of distance calculation to use.
	 * @return The distance from this coordinate to the one given.
	 */
	public double distanceTo(Coordinate coordIn, DistanceCalc.Method method){
		return DistanceCalc.calculateDistance(method, this, coordIn);
	}
	
	/**
	 * Gets the distance to another coordinate using the {@link Coordinate#DEFAULT_DISTANCE_CALC_METHOD the default method}.
	 * @param coordIn The coordinate to get the distance to.
	 * @return The distance to the other coordinate.
	 */
	public double distanceTo(Coordinate coordIn){
		return this.distanceTo(coordIn, DEFAULT_DISTANCE_CALC_METHOD);
	}
	
	/**
	 * Determines if this coordinate is closer to a point on the matrix than another coordinate.
	 *
	 * @param coordIn The coordinate we are comparing distance to.
	 * @param coordTo The coordinate we are getting the distances to.
	 * @param method The method this should use to determine distance.
	 * @return If this coordinate is closer to a point on the matrix than another given. If the other coordinate given is null, returns true.
	 */
	public boolean isCloserTo(Coordinate coordIn, Coordinate coordTo, DistanceCalc.Method method){
		if(coordIn == null){
			return true;
		}
		MatrixValidator.throwIfNotOnSameMatrix(this, coordIn);
		MatrixValidator.throwIfNotOnSameMatrix(this, coordTo);
		
		
		return this.distanceTo(coordTo, method) < this.distanceTo(coordIn, method);
	}
	
	/**
	 * Determines if this coordinate is closer to a point on the matrix than another coordinate using the {@link Coordinate#DEFAULT_DISTANCE_CALC_METHOD the default method}.
	 * @param coordIn The coordinate we are comparing distance to.
	 * @param coordTo The coordinate we are getting the distances to.
	 * @return If this coordinate is closer to a point on the matrix than another given. If the other coordinate given is null, returns true.
	 */
	public boolean isCloserTo(Coordinate coordIn, Coordinate coordTo){
		return this.isCloserTo(coordIn, coordTo, DEFAULT_DISTANCE_CALC_METHOD);
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
