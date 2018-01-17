package com.ebp.owat.lib.datastructure.matrix.utils;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

/**
 * Validating methods for the various LinkedMatrix related classes.
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
	public static boolean isOnMatrix(Matrix matrix, Coordinate coordIn){
		return matrix == coordIn.matrix && coordIn.stillOnMatrix();
	}
	
	/**
	 * Determines if the set of coordinates given are on the matrix given.
	 * @param matrix The matrix to use.
	 * @param coordinates The coordinates to test.
	 * @return If the set of coordinates given are on the matrix given.
	 */
	public static boolean areOnMatrix(Matrix matrix, Coordinate ... coordinates){
		if(matrix == null){
			throw new NullPointerException("Null matrix given to MatrixValidator.areOnMatrix(LinkedMatrix, Coordinate...).");
		}
		for(Coordinate curCoord : coordinates){
			if(curCoord == null || !isOnMatrix(matrix, curCoord)){
				return false;
			}
		}
		return true;
	}
	
	/**
	 * Deterimines of the coordinates are all on the same matrix.
	 * @param coordinates The coordinates to test.
	 * @return If they are all on the same matrix or not.
	 */
	public static boolean areOnSameMatrix(Coordinate ... coordinates){
		if(listContainsNullVals((Object[]) coordinates)){
			throw new NullPointerException("One or more of the coordinates given was null.");
		}
		if(coordinates.length <= 1){//if there are no or one matrix, they are implicitly on the same matrix
			return true;
		}
		return areOnMatrix(coordinates[0].matrix, coordinates);
	}
	
	public static boolean isOnSameMatrix(Coordinate coordOne, Coordinate coordTwo){
		if(coordOne == null || coordTwo == null){
			throw new NullPointerException("One or both coordinates given were null.");
		}
		return coordOne.matrix == coordTwo.matrix;
	}
	
	
	public static void throwIfNotOnMatrix(Matrix matrix, Coordinate ... coordinates) throws IllegalArgumentException{
		if(!areOnMatrix(matrix, coordinates)){
			throw new IllegalArgumentException("Coordinate(s) not on the matrix.");
		}
	}
	
	/**
	 * Throws an exception if the coordinate given is not on the same matrix as this one.
	 * @param coordinates The coordinates to test against.
	 * @throws IllegalArgumentException If the coordinate given is not on the same matrix as this one.
	 */
	public static void throwIfNotOnSameMatrix(Coordinate ... coordinates) throws IllegalArgumentException{
		if(!areOnSameMatrix(coordinates)){
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
					throw new IndexOutOfBoundsException("Bad x index given.");
				}
				break;
			case Y:
				if(!matrix.isValidRowIndex(index)){
					throw new IndexOutOfBoundsException("Bad y index given.");
				}
				break;
		}
	}
	
	/**
	 * Throws an IllegalStateException if the matrix has no rows or columns.
	 * @param matrix
	 * @throws IllegalStateException
	 */
	public static void throwIfNoRowsCols(Matrix matrix) throws IllegalStateException{
		if(!matrix.hasRowsCols()){
			throw new IllegalStateException("Matrix has rows and columns.");
		}
	}
	
	public static void throwIfHasRowsCols(Matrix matrix) throws IllegalStateException{
		if(matrix.hasRowsCols()){
			throw new IllegalStateException("Matrix rows and columns.");
		}
	}
}
