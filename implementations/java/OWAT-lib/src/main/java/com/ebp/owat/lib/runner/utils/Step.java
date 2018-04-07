package com.ebp.owat.lib.runner.utils;

import java.util.LinkedList;
import java.util.List;

public enum Step {
	NOT_STARTED_SCRAMBLE(0, "Not Started", ScrambleMode.SCRAMBLING),
	NOT_STARTED_DESCRAMBLE(0, "Not Started", ScrambleMode.DESCRAMBLING),

	LOAD_DATA(1, "Loading Data", ScrambleMode.SCRAMBLING),
	PAD_DATA(2, "Padding Data", ScrambleMode.SCRAMBLING),
	SCRAMBLING(3, "Scrambling", ScrambleMode.SCRAMBLING),
	OUT_SCRAMBLED_DATA(4, "Outputting Scrambled Data", ScrambleMode.SCRAMBLING),
	OUT_KEY(5, "Outputting key", ScrambleMode.SCRAMBLING),
	DONE_SCRAMBLING(6, "Done", ScrambleMode.SCRAMBLING),
	
	LOAD_KEY(1, "Loading Key", ScrambleMode.DESCRAMBLING),
	LOAD_SCRAMBLED_DATA(2, "Loading Scrambled Data", ScrambleMode.DESCRAMBLING),
	DESCRAMBLING(3, "Descrambling", ScrambleMode.DESCRAMBLING),
	OUT_DESCRAMBLED_DATA(4, "Outputting Descrambled Data", ScrambleMode.DESCRAMBLING),
	DONE_DESCRAMBLING(5, "Done", ScrambleMode.DESCRAMBLING);
	
	public final int stepNo;
	public final String stepName;
	public final ScrambleMode mode;
	
	Step(int stepNo, String name, ScrambleMode mode){
		this.stepNo = stepNo;
		this.stepName = name;
		this.mode = mode;
	}

	public static List<Step> getStepsIn(ScrambleMode mode){
		List<Step> steps = new LinkedList<>();

		for(Step curStep : steps){
			if(curStep.mode == mode){
				steps.add(curStep);
			}
		}
		return steps;
	}
}
