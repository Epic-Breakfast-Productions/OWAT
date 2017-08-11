package com.ebp.owat.lib.dataStructure.matrix.utils.coordinate;

public class DistanceCalc {
	/**
	 * The supported ways to calculate distance between Coordinates.
	 */
	public enum Method {
		MANHATTAN, EUCLIDEAN;
	}
	
	
	
	public static long xDistance(Coordinate coordOne, Coordinate coordTwo){
		this.throwIfNotOnSameMatrix(coordIn);
		return this.xDistanceTo(coordIn.getX());
	}
	
	/**
	 * Gets the euclidian distance from this coordiate to another.
	 *
	 * @return The euclidian distance to the coordinate given.
	 */
	public static double eucDistanceTo(Coordinate coordOne, Coordinate coordTwo){
		//TODO
		long distanceX = 0L;
		long distanceY = 0L;
		//average distances
		return (distanceX + distanceY)/2L;
	}
	
	/**
	 * Gets the manhattan distance from this coordinate to another. (x distance + y distance)
	 * @return The manhattan distance to the coordinate given.
	 */
	public static long manDistanceTo(Coordinate coordOne, Coordinate coordTwo){
		return coordOne.xDistanceTo(coordTwo) + coordOne.yDistanceTo(coordTwo);
	}
	
	public static double calculateDistance(Method method, Coordinate mainCoord, Coordinate ... coordinates){
		double sumOfDistances = 0L;
		for( Coordinate curOther : coordinates){
			switch (method){
				case EUCLIDEAN:
					sumOfDistances += eucDistanceTo(mainCoord, curOther);
					break;
				case MANHATTAN:
					sumOfDistances += manDistanceTo(mainCoord, curOther);
					break;
			}
		}
		return sumOfDistances/coordinates.length;
	}
	
	public static long calculateDistanceFloor(Method method, Coordinate mainCoord, Coordinate ... coordinates){
		return (long)Math.floor(calculateDistance(method, mainCoord, coordinates));
	}
	
	public static long calculateDistanceCeil(Method method, Coordinate mainCoord, Coordinate ... coordinates){
		return (long)Math.ceil(calculateDistance(method, mainCoord, coordinates));
	}
}
