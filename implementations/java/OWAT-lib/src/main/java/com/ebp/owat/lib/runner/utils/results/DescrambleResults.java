package com.ebp.owat.lib.runner.utils.results;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.runner.utils.Step;

import static com.ebp.owat.lib.runner.utils.ScrambleMode.DESCRAMBLING;

public class DescrambleResults extends RunResults {
	public DescrambleResults(){
		super(DESCRAMBLING);
	}

	public DescrambleResults(NodeMode nodeMode){
		super(DESCRAMBLING, nodeMode);
	}

	@Override
	public synchronized DescrambleResults clone(){
		DescrambleResults output = new DescrambleResults(this.getNodeMode());
		output.setMatrixMode(this.getMatrixMode());
		output.setCurStep(this.getCurStep());
		output.setCurStepProg(this.getCurStepProg());
		output.setCurStepProgMax(this.getCurStepProgMax());
		output.setTimingMap(this.getTimingMap());
		output.setNumBytesIn(this.getNumBytesIn());
		output.setNumBytesOut(this.getNumBytesOut());

		return output;
	}

	public static String getCsvHead(){
		StringBuilder sb = new StringBuilder(RunResults.getCsvHeadBase());

		for (Step curStep : Step.getStepsIn(DESCRAMBLING)) {
			sb.append(",");
			sb.append(curStep.stepName);
		}

		return sb.toString();
	}

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
