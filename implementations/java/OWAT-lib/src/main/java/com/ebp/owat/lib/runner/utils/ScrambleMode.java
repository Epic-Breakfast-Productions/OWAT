package com.ebp.owat.lib.runner.utils;

/**
 * The mode of scrambling.
 */
public enum ScrambleMode {
	SCRAMBLING("scrambling", 5),
	DESCRAMBLING("scrambling", 4);

	/** The name of the mode */
	public final String name;
	/** The number of steps in the mode. */
	public final long numSteps;

	ScrambleMode(String name, long numSteps){
		this.name = name;
		this.numSteps = numSteps;
	}
}
