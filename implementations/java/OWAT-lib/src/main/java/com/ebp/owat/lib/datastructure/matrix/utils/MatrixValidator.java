package com.ebp.owat.lib.datastructure.matrix.utils;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

/**
 * Validating methods for the various Matrix related classes.
 */
public class MatrixValidator {
	
	private static boolean listContainsNullVals(Object ... objects){
		for (Object curObj : objects){
			if(curObj == null){
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Determines if the coordinate given is on the matrix given.
	 *
	 * @param matrix The matrix.
	 * @param coordIn The coordinate being tested.
	 * @return If the coordinate given is on the matrix given.
	 */
	public static boolean isOnMatrix(Matrix matrix, MatrixCoordinate coordIn){
		return matrix == coordIn.matrix && coordIn.stillOnMatrix();
	}
	
	/**
	 * Determines if the set of matrixCoordinates given are on the matrix given.
	 * @param matrix The matrix to use.
	 * @param matrixCoordinates The matrixCoordinates to test.
	 * @return If the set of matrixCoordinates given are on the matrix given.
	 */
	public static boolean areOnMatrix(Matrix matrix, MatrixCoordinate... matrixCoordinates){
		if(matrix == null){
			throw new NullPointerException("Null matrix given to MatrixValidator.areOnMatrix(LinkedScramblingMatrix, MatrixCoordinate...).");
		}
		for(MatrixCoordinate curCoord : matrixCoordinates){
			if(curCoord == null || !isOnMatrix(matrix, curCoord)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Deterimines of the matrixCoordinates are all on the same matrix.
	 * @param matrixCoordinates The matrixCoordinates to test.
	 * @return If they are all on the same matrix or not.
	 */
	public static boolean areOnSameMatrix(MatrixCoordinate... matrixCoordinates){
		if(listContainsNullVals((Object[]) matrixCoordinates)){
			throw new NullPointerException("One or more of the matrixCoordinates given was null.");
		}
		if(matrixCoordinates.length <= 1){//if there are no or one matrix, they are implicitly on the same matrix
			return true;
		}
		return areOnMatrix(matrixCoordinates[0].matrix, matrixCoordinates);
	}
	
	public static boolean isOnSameMatrix(MatrixCoordinate coordOne, MatrixCoordinate coordTwo){
		if(coordOne == null || coordTwo == null){
			throw new NullPointerException("One or both coordinates given were null.");
		}
		return coordOne.matrix == coordTwo.matrix;
	}
	
	
	public static void throwIfNotOnMatrix(Matrix matrix, MatrixCoordinate... matrixCoordinates) throws IllegalArgumentException{
		if(!areOnMatrix(matrix, matrixCoordinates)){
			throw new IllegalArgumentException("MatrixCoordinate(s) not on the matrix.");
		}
	}
	
	/**
	 * Throws an exception if the coordinate given is not on the same matrix as this one.
	 * @param matrixCoordinates The matrixCoordinates to test against.
	 * @throws IllegalArgumentException If the coordinate given is not on the same matrix as this one.
	 */
	public static void throwIfNotOnSameMatrix(MatrixCoordinate... matrixCoordinates) throws IllegalArgumentException{
		if(!areOnSameMatrix(matrixCoordinates)){
			throw new IllegalArgumentException("Coordinates are not on the same matrix.");
		}
	}
	
	/**
	 * Throws an IndexOutOfBounds if a bad index is given.
	 *
	 * @param index The index to test.
	 * @param plane The plane the index is a part of.
	 * @throws IndexOutOfBoundsException If the index given is invalid.
	 */
	public static void throwIfBadIndex(Matrix matrix, long index, Plane plane){
		//don't use throwIfNoRowsCols() due to not quite being the right exception.
		switch (plane){
			case X:
				if(!matrix.isValidColIndex(index)){
					throw new IndexOutOfBoundsException("Bad x index given: "+index);
				}
				break;
			case Y:
				if(!matrix.isValidRowIndex(index)){
					throw new IndexOutOfBoundsException("Bad y index given: "+index);
				}
				break;
		}
	}

	/**
	 * Throws an exception if the index given is invalid for both the x or y plane.
	 * @param matrix The matrix we are validating for.
	 * @param index The index given.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds in either the x or y plane.
	 */
	public static void throwIfBadIndex(Matrix matrix, long index){
		throwIfBadIndex(matrix, index, Plane.X);
		throwIfBadIndex(matrix, index, Plane.Y);
	}
	
	/**
	 * Throws an IllegalStateException if the matrix has no rows or columns.
	 * @param matrix The matrix we are validating for.
	 * @throws IllegalStateException if the matrix has no rows or columns.
	 */
	public static void throwIfNoRowsCols(Matrix matrix) throws IllegalStateException{
		if(!matrix.hasRowsCols()){
			throw new IllegalStateException("Matrix has rows and columns.");
		}
	}

	/**
	 * Throws an exception if the matrix has rows or columns
	 * @param matrix The matrix we are validating for.
	 * @throws IllegalStateException If the matrix has rows or columns
	 */
	public static void throwIfHasRowsCols(Matrix matrix) throws IllegalStateException{
		if(matrix.hasRowsCols()){
			throw new IllegalStateException("Matrix rows and columns.");
		}
	}
}
