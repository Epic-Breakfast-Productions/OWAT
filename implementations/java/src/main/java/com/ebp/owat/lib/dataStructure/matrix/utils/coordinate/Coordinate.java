package com.ebp.owat.lib.dataStructure.matrix.utils.coordinate;

import com.ebp.owat.lib.dataStructure.matrix.Matrix;
import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;

/**
 * Describes a coordinate on a matrix.
 *
 * Created by Greg Stewart on 4/4/17.
 */
public class Coordinate {
	
	public enum DistanceCalcMethod{
		MANHATTAN,EUCLIDIAN;
	}
	
	/** The default method of finding distances from one coordinate to another. */
	public static final DistanceCalcMethod DEFAULT_DISTANCE_CALC_METHOD = DistanceCalcMethod.MANHATTAN;
	
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
	
	/**
	 * Determines if the matrix of the coordinate given is the same as the one this coordinate is on.
	 * @param coordIn The coordinate to test against.
	 * @return If this and the coordinate given is on the same matrix or not.
	 */
	public boolean isOnSameMatrix(Coordinate coordIn){
		return coordIn != null && this.matrix == coordIn.matrix;
	}
	
	/**
	 * Throws an exception if the coordinate given is not on the same matrix as this one.
	 * @param coordIn The coordinate to test against.
	 * @throws IllegalArgumentException If the coordinate given is not on the same matrix as this one.
	 */
	private void throwIfNotOnSameMatrix(Coordinate coordIn){
		if(!this.isOnSameMatrix(coordIn)){
			throw new IllegalArgumentException("Coordinate given is not on the same matrix as this coordinate.");
		}
	}
	
	/**
	 * Gets the euclidian distance from this coordiate to another.
	 *
	 * @param coordIn The coordinate to get the distance to.
	 * @return The euclidian distance to the coordinate given.
	 */
	public long eucDistTo(Coordinate coordIn){
		this.throwIfNotOnSameMatrix(coordIn);
		//TODO
		long distanceX = 0L;
		long distanceY = 0L;
		//average distances
		return (distanceX + distanceY)/2L;
	}
	
	/**
	 * Gets the manhattan distance from this coordinate to another.
	 * @param coordIn
	 * @return
	 */
	public long manDistanceTo(Coordinate coordIn){
		this.throwIfNotOnSameMatrix(coordIn);
		//TODO:: this
		return 0L;
	}
	
	/**
	 * Gets the distance to another coordinate.
	 * @param coordIn The coordinate to get the distance to.
	 * @param method The method of distance calculation to use.
	 * @return The distance from this coordinate to the one given.
	 */
	public long distanceTo(Coordinate coordIn, DistanceCalcMethod method){
		switch (method){
			case EUCLIDIAN:
				return this.eucDistTo(coordIn);
			case MANHATTAN:
				return this.manDistanceTo(coordIn);
		}
		throw new IllegalArgumentException("Somehow got a bad method. This should not happen.");
	}
	
	/**
	 * Gets the distance to another coordinate using the {@link Coordinate#DEFAULT_DISTANCE_CALC_METHOD the default method}.
	 * @param coordIn The coordinate to get the distance to.
	 * @return The distance to the other coordinate.
	 */
	public long distanceTo(Coordinate coordIn){
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
	public boolean isCloserTo(Coordinate coordIn, Coordinate coordTo, DistanceCalcMethod method){
		if(coordIn == null){
			return true;
		}
		this.throwIfNotOnSameMatrix(coordIn);
		this.throwIfNotOnSameMatrix(coordTo);
		
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
