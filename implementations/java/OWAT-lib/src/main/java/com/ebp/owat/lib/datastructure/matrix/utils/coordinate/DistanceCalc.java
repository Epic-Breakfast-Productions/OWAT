package com.ebp.owat.lib.datastructure.matrix.utils.coordinate;

public class DistanceCalc {
	/**
	 * The supported ways to calculate distance between Coordinates.
	 */
	public enum Method {
		MANHATTAN, EUCLIDEAN;
	}

	/** The default calculation method to use. */
	public static final Method DEFAULT_CALC_METHOD = Method.MANHATTAN;

	/**
	 * Gets the distance between two long values.
	 * @param valOne The first value.
	 * @param valTwo The second value.
	 * @return The distance between them.
	 */
	private static long getDistance(long valOne, long valTwo){
		if(valOne > valTwo) {
			return valOne - valTwo;
		}else if(valTwo > valOne){
			return valTwo - valOne;
		}else{
			return 0;
		}
	}

	/**
	 * Gets the distance from one value to the other on the x axis
	 * @param coordOne The coord we are dealing with.
	 * @param coordTwo The coord we are getting the distance to.
	 * @return The distance from one value to the other on the x axis
	 */
	public static long xDistance(Coordinate coordOne, Coordinate coordTwo) {
		return getDistance(coordOne.getX(), coordTwo.getX());
	}
	
	/**
	 * Gets the distance from one value to the other on the y axis
	 * @param coordOne The coord we are dealing with.
	 * @param coordTwo The coord we are getting the distance to.
	 * @return The distance from one value to the other on the y axis
	 */
	public static long yDistance(Coordinate coordOne, Coordinate coordTwo) {
		return getDistance(coordOne.getY(), coordTwo.getY());
	}
	
	/**
	 * Gets the euclidian distance from this coordiate to another.
	 *
	 * @return The euclidian distance to the coordinate given.
	 */
	public static double eucDistanceTo(Coordinate coordOne, Coordinate coordTwo) {
		long distanceX = xDistance(coordOne, coordTwo);
		long distanceY = yDistance(coordOne, coordTwo);
		//average distances
		return (distanceX + distanceY) / 2L;
	}
	
	/**
	 * Gets the manhattan distance from this coordinate to another. (x distance + y distance)
	 *
	 * @return The manhattan distance to the coordinate given.
	 */
	public static long manDistanceTo(Coordinate coordOne, Coordinate coordTwo) {
		return xDistance(coordOne, coordTwo) + yDistance(coordOne, coordTwo);
	}
	
	/**
	 * Calculates the distance from one coord to another.
	 * @param method The method to use.
	 * @param mainCoord The coord we are working with
	 * @param otherCoord The coord we are getting distance to.
	 * @return The distance from one coord to another.
	 */
	public static double calculateDistance(Method method, Coordinate mainCoord, Coordinate otherCoord) {
		switch (method) {
			case EUCLIDEAN:
				return eucDistanceTo(mainCoord, otherCoord);
			case MANHATTAN:
				return manDistanceTo(mainCoord, otherCoord);
		}
		throw new IllegalStateException("Invalid method given.");
	}
	
	/**
	 * Calculates the distance from one coord to another using the default method.
	 * @param mainCoord The coord we are working with
	 * @param otherCoord The coord we are getting distance to.
	 * @return The distance from one coord to another.
	 */
	public static double calculateDistance(Coordinate mainCoord, Coordinate otherCoord) {
		return calculateDistance(DEFAULT_CALC_METHOD, mainCoord, otherCoord);
	}
	
	/**
	 * Calculates the distance from one coord to another, flooring the result.
	 * @param method The method to use.
	 * @param mainCoord The coord we are working with
	 * @param otherCoord The coord we are getting distance to.
	 * @return The distance from one coord to another floored.
	 */
	public static long calculateDistanceFloor(Method method, Coordinate mainCoord, Coordinate otherCoord) {
		return (long) Math.floor(calculateDistance(method, mainCoord, otherCoord));
	}
	
	/**
	 * Calculates the distance from one coord to another ceilinging the result.
	 * @param method The method to use.
	 * @param mainCoord The coord we are working with
	 * @param otherCoord The coord we are getting distance to.
	 * @return The distance from one coord to another ceilinged.
	 */
	public static long calculateDistanceCeil(Method method, Coordinate mainCoord, Coordinate otherCoord) {
		return (long) Math.ceil(calculateDistance(method, mainCoord, otherCoord));
	}
	
	/**
	 * Determines if pos1 is closer to pos2 to the coordinates given.
	 * @param method The method of distance calculation to use.
	 * @param base The coordinate we are comparing to.
	 * @param pos1    The value we are testing to see if it is closer.
	 * @param pos2    The value we are comparing to the first one.
	 * @return True if pos1 is closer to the goal coordinates than pos2
	 */
	public static boolean nodeIsCloserThan(Method method, Coordinate base, Coordinate pos1, Coordinate pos2) {
		return (calculateDistance(method, base, pos1) < calculateDistance(method, base, pos2));
	}
	
	/**
	 * Determines if pos1 is closer to pos2 to the coordinates given, using the default calculation method.
	 * @param base The coordinate we are comparing to.
	 * @param pos1 The value we are testing to see if it is closer.
	 * @param pos2 The value we are comparing to the first one.
	 * @return True if pos1 is closer to the goal coordinates than pos2
	 */
	public static boolean nodeIsCloserThan(Coordinate base, Coordinate pos1, Coordinate pos2) {
		return nodeIsCloserThan(DEFAULT_CALC_METHOD, base, pos1, pos2);
	}
}