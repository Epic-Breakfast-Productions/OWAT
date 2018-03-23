package com.ebp.owat.app.runner.utils;

public enum ScrambleMode {
	SCRAMBLING("scrambling", 5),
	DESCRAMBLING("scrambling", 4);

	public final String name;
	public final long numSteps;

	ScrambleMode(String name, long numSteps){
		this.name = name;
		this.numSteps = numSteps;
	}
}
