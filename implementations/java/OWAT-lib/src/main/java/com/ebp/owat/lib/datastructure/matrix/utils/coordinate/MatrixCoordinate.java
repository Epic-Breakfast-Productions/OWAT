package com.ebp.owat.lib.datastructure.matrix.utils.coordinate;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;

import java.util.Objects;

/**
 * Describes a coordinate on a matrix.
 *
 * Created by Greg Stewart on 4/4/17.
 */
public class MatrixCoordinate extends Coordinate {
	/** The matrix this coordinate is on. */
	public final Matrix matrix;

	/**
	 * Constructs a new coordinate with a matrix. Sets the coordinate to 0,0
	 * @param matrix The matrix to give to this MatrixCoordinate.
	 */
	public MatrixCoordinate(Matrix matrix){
		this.matrix = matrix;
		if(this.matrix == null){
			throw new IllegalArgumentException("Matrix cannot be null.");
		}
		if(!this.matrix.hasRowsCols()){
			throw new IllegalStateException("Cannot have a coordinate on an empty matrix.");
		}
		this.setX(0);
		this.setY(0);
	}
	
	/**
	 * Constructs a new coordinate with all the values set.
	 *
	 * @param matrix The matrix this coordinate is on.
	 * @param xIn The X value (which col) of this coordinate.
	 * @param yIn The Y value (which row) of this coordinate.
	 * @throws IllegalArgumentException If the values in are out of bounds.
	 */
	public MatrixCoordinate(Matrix matrix, long xIn, long yIn){
		this(matrix);
		this.setX(xIn).setY(yIn);
	}

	@Override
	public MatrixCoordinate setX(long xIn) {
		MatrixValidator.throwIfBadIndex(this.matrix, xIn, Plane.X);
		super.setX(xIn);
		return this;
	}
	
	@Override
	public MatrixCoordinate setY(long yIn) {
		MatrixValidator.throwIfBadIndex(this.matrix, yIn, Plane.Y);
		super.setY(yIn);
		return this;
	}

	/**
	 * Determines if the matrix of the coordinate given is the same as the one this coordinate is on.
	 * @param coordIn The coordinate to test against.
	 * @return If this and the coordinate given is on the same matrix or not.
	 */
	public boolean isOnSameMatrix(MatrixCoordinate coordIn){
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
	public MatrixCoordinate clone(){
		return new MatrixCoordinate(this.matrix, this.getX(), this.getY());
	}
	
	@Override
	public String toString() {
		return "MatrixCoordinate. Values: X(col)=" + this.x + " Y(row)=" + this.y + " LinkedMatrix: " + this.matrix;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		MatrixCoordinate that = (MatrixCoordinate) o;
		return Objects.equals(matrix, that.matrix);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), matrix);
	}
}
