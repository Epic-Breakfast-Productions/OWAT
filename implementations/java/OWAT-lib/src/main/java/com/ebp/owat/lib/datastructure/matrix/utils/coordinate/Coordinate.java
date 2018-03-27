package com.ebp.owat.lib.datastructure.matrix.utils.coordinate;

import com.ebp.owat.lib.datastructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;

import java.util.Objects;

public class Coordinate {
	/** The X value (which col) of this coordinate. */
	protected long x;
	/** The Y value (which row) of this coordinate. */
	protected long y;

	public Coordinate(){
		this(0,0);
	}

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
	protected Object clone() throws CloneNotSupportedException {
		return new Coordinate(this.getX(), this.getY());
	}

	private void throwIfInvalidIndex(long index){
		if(index < 0){
			throw new IllegalArgumentException("Index given was invalid.");
		}
	}
}
