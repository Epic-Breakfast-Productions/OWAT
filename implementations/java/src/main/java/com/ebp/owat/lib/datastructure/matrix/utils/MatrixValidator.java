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
		return matrix == coordIn.matrix;
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
		if(coordinates.length <= 1){//if there are no or one matrix, they are inmplicitly on the same metrix
			return true;
		}
		if(listContainsNullVals((Object[]) coordinates)){
			//TODO:: throw?
		}
		return areOnMatrix(coordinates[0].matrix, coordinates);
	}
	
	/**
	 * Determines if the matrix of the coordinate given is the same as the one this coordinate is on. If either coordinate is null, returns false.
	 * @param coordIn The coordinate to test against.
	 * @return If this and the coordinate given is on the same matrix or not.
	 */
	public static boolean isOnSameMatrix(Coordinate coordOne, Coordinate coordTwo){
		if(coordOne == null || coordTwo == null){
			return false;
		}
		return coordOne.matrix == coordTwo.matrix;
	}
	
	/**
	 * Throws an exception if the coordinate given is not on the same matrix as this one.
	 * @param coordinates The coordinates to test against.
	 * @throws IllegalArgumentException If the coordinate given is not on the same matrix as this one.
	 */
	public static void throwIfNotOnSameMatrix(Coordinate ... coordinates){
		if(!areOnSameMatrix(coordinates)){
			throw new IllegalArgumentException("Coordinates are not on the same matrix.");
		}
	}
	
	/**
	 * Throws an IlegalArgumentException if a bad index is given.
	 *
	 * @param index The index to test.
	 * @param plane The plane the index is a part of.
	 */
	public static void throwIfBadIndex(Matrix matrix, long index, Plane plane){
		switch (plane){
			case X:
				if(!matrix.isValidColIndex(index)){
					throw new IllegalArgumentException("Bad x index given.");
				}
				break;
			case Y:
				if(!matrix.isValidRowIndex(index)){
					throw new IllegalArgumentException("Bad y index given.");
				}
				break;
		}
	}
}
