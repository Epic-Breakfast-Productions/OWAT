package com.ebp.owat.lib.dataStructure.matrix.utils;

/**
 * Node Direction, use this to tell a node which direction you mean.
 */
public enum NodeDir {
	NORTH(Plane.X, IncDec.INC),
	SOUTH(Plane.X, IncDec.DEC),
	EAST(Plane.Y, IncDec.INC),
	WEST(Plane.Y, IncDec.DEC);
	
	public enum Plane {
		X,Y
	}
	
	public enum IncDec {
		INC,DEC,NA;
		
		/**
		 * Increments or decrements the value given based on the enum value given.
		 * @param incDecIn The incDec in.
		 * @param valIn The value to inc/dec.
		 * @return The inc/dec value.
		 */
		public static long incDecValue(IncDec incDecIn, long valIn){
			switch (incDecIn){
				case INC:
					return valIn++;
				case DEC:
					return valIn--;
			}
			throw new IllegalStateException("Somehow got bad IncDec. Should not get this.");
		}
	}
	
	/** Used for exceptions relating to a bad direction given. */
	public static final String BAD_DIR_GIVEN_ERR_MESSAGE = "Somehow gave a bad direction in. Dir in: ";
	
	/** The opposite direction of the direction this direction is. Initialized in the static initializer field. */
	private NodeDir opposite;
	
	private final IncDec incDecX;
	private final IncDec incDecY;
	
	static {
		NORTH.opposite = SOUTH;
		SOUTH.opposite = NORTH;
		EAST.opposite = WEST;
		WEST.opposite = EAST;
	}
	
	private NodeDir(Plane plane, IncDec incDec){
		if(plane == Plane.X){
			incDecX = incDec;
			incDecY = IncDec.NA;
		}else if(plane == Plane.Y){
			incDecY = incDec;
			incDecX = IncDec.NA;
		}else{
			throw new IllegalStateException("Somehow got bad plane. Should not get this.");
		}
	}
	
	/**
	 * Increments or decrements a value based on a plane given and what this dir is setup for. Is possible for the value to not change.
	 * @param plane The plane to inc/dec on.
	 * @param valToDec The value to increment or decrement.
	 * @return The incremented/decremented value.
	 */
	public long incDec(Plane plane, long valToDec){
		switch (plane){
			case X:
				return IncDec.incDecValue(this.incDecX, valToDec);
			case Y:
				return IncDec.incDecValue(this.incDecY, valToDec);
		}
		throw new IllegalStateException("Somehow got bad plane. Should not get this.");
	}
	
	/**
	 * Gets the opposite direction of this direction.
	 * @return The opposite direction of this direction.
	 */
	public NodeDir opposite(){
		return this.opposite;
	}
}
