package com.ebp.owat.app.runner;

public enum Step {
	NOT_STARTED(0),
	
	LOAD_DATA(1),
	PAD_DATA(2),
	SCRAMBLING(3),
	OUT_SCRAMBLED_DATA(4),
	OUT_KEY(5),
	DONE_SCRAMBLING(6),
	
	LOAD_KEY(1),
	LOAD_SCRAMBLED_DATA(2),
	DESCRAMBLING(3),
	OUT_DESCRAMBLED_DATA(4),
	DONE_DESCRAMBLING(5);
	
	public static final int NUM_STEPS_SCRAMBLING = 5;
	public static final int NUM_STEPS_DE_SCRAMBLING = 4;
	
	public final int stepNo;
	
	Step(int stepNo){
		this.stepNo = stepNo;
	}
}
