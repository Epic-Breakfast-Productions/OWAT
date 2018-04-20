package com.ebp.owat.lib.runner.utils;

import java.util.LinkedList;
import java.util.List;

/**
 * Enum to describe the steps in the processes.
 */
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

	/** The step number of the step. */
	public final int stepNo;
	/** The name of the step. */
	public final String stepName;
	/** The mode the step is part of. */
	public final ScrambleMode mode;
	
	Step(int stepNo, String name, ScrambleMode mode){
		this.stepNo = stepNo;
		this.stepName = name;
		this.mode = mode;
	}

	/**
	 * Gets the steps that are part of a mode.
	 * @param mode The mode to get the steps of
	 * @return The list of steps in the mode.
	 */
	public static List<Step> getStepsIn(ScrambleMode mode){
		List<Step> steps = new LinkedList<>();

		for(Step curStep : Step.values()){
			if(
				curStep == NOT_STARTED_SCRAMBLE ||
				curStep == NOT_STARTED_DESCRAMBLE ||
				curStep == DONE_DESCRAMBLING ||
				curStep == DONE_SCRAMBLING
			){
				continue;
			}
			if(curStep.mode == mode){
				steps.add(curStep);
			}
		}
		return steps;
	}
}
