package com.ebp.owat.app.runner;

public enum Step {
	NOT_STARTED(0, "Not Started"),
	
	LOAD_DATA(1, "Loading Data"),
	PAD_DATA(2, "Padding Data"),
	SCRAMBLING(3, "Scrambling"),
	OUT_SCRAMBLED_DATA(4, "Outputting Scrambled Data"),
	OUT_KEY(5, "Outputting key"),
	DONE_SCRAMBLING(6, "Done"),
	
	LOAD_KEY(1, "Loading Key"),
	LOAD_SCRAMBLED_DATA(2, "Loading Scrambled Data"),
	DESCRAMBLING(3, "Descrambling"),
	OUT_DESCRAMBLED_DATA(4, "Outputting Descrambled Data"),
	DONE_DESCRAMBLING(5, "Done");
	
	public static final int NUM_STEPS_SCRAMBLING = 5;
	public static final int NUM_STEPS_DE_SCRAMBLING = 4;
	
	public final int stepNo;
	public final String stepName;
	
	Step(int stepNo, String name){
		this.stepNo = stepNo;
		this.stepName = name;
	}
}
