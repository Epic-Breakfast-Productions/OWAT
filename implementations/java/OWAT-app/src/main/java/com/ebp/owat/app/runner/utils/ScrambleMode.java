package com.ebp.owat.app.runner.utils;

public enum ScrambleMode {
	SCRAMBLING("scrambling"),
	DESCRAMBLING("scrambling");

	public final String name;

	ScrambleMode(String name){
		this.name = name;
	}
}
