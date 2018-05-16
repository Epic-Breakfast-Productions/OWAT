package com.ebp.owat.lib.runner.utils.results;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.runner.utils.Step;

import static com.ebp.owat.lib.runner.utils.ScrambleMode.SCRAMBLING;

/**
 * Represents the results from scrambling.
 */
public class ScrambleResults extends RunResults {
	/**
	 * Base constructor.
	 */
	public ScrambleResults(){
		super(SCRAMBLING);
	}

	/**
	 * Constructor to set the node mode used.
	 * @param nodeMode The node mode used.
	 */
	public ScrambleResults(NodeMode nodeMode){
		super(SCRAMBLING, nodeMode);
	}

	@Override
	public synchronized ScrambleResults clone(){
		ScrambleResults output = new ScrambleResults(this.getNodeMode());
		output.setMatrixMode(this.getMatrixMode());
		output.setCurStep(this.getCurStep());
		output.setCurStepProg(this.getCurStepProg());
		output.setCurStepProgMax(this.getCurStepProgMax());
		output.setTimingMap(this.getTimingMap());
		output.setMatrixSize(this.getMatrixSize());
		output.setNumBytesIn(this.getNumBytesIn());
		output.setNumBytesOut(this.getNumBytesOut());

		return output;
	}

	/**
	 * Gets the CSV head used for scrambling data results.
	 * @return The CSV head used for scrambling data results.
	 */
	public static String getCsvHead(){
		StringBuilder sb = new StringBuilder(RunResults.getCsvHeadBase());

		for (Step curStep : Step.getStepsIn(SCRAMBLING)) {
			sb.append(",");
			sb.append(curStep.stepName);
		}

		return sb.toString();
	}

	@Override
	public String getCsvLine(boolean header){
		StringBuilder sb = new StringBuilder();

		if(header){
			sb.append(getCsvHead());
			sb.append(System.getProperty("line.separator"));
		}

		sb.append(super.getCsvLineBase());

		sb.append(super.getCsvTiming());

		return sb.toString();
	}

}
