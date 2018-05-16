package com.ebp.owat.lib.runner.utils.results;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.ebp.owat.lib.runner.utils.MatrixMode;
import com.ebp.owat.lib.runner.utils.ScrambleMode;
import com.ebp.owat.lib.runner.utils.Step;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Results of a run, keeps information about it for reporting.
 *
 * This is the abstract class to genericize data kept by both types of run (scrambling/descrambling)
 */
public abstract class RunResults {
	private static final Logger LOGGER = LoggerFactory.getLogger(RunResults.class);

	/**
	 * Constructor to set the scrambleMode of this scramble scrambleMode
	 * @param scrambleMode The scrambleMode of this run.
	 */
	public RunResults(ScrambleMode scrambleMode){
		this.scrambleMode = scrambleMode;
		if(this.scrambleMode == ScrambleMode.SCRAMBLING){
			this.curStep = Step.NOT_STARTED_SCRAMBLE;
		}
		if(this.scrambleMode == ScrambleMode.DESCRAMBLING){
			this.curStep = Step.NOT_STARTED_DESCRAMBLE;
		}
	}

	/**
	 * Constructor to setup the scramble and node modes.
	 * @param scrambleMode The scramble mode of this run.
	 * @param nodeMode The node mode of this run.
	 */
	public RunResults(ScrambleMode scrambleMode, NodeMode nodeMode) {
		this(scrambleMode);
		this.setNodeMode(nodeMode);
	}

	/** The scrambleMode of this run. */
	private final ScrambleMode scrambleMode;
	/** The number of bytes read in for the data. */
	private long numBytesIn = -1;
	/** The number of bytes in the data written out. */
	private long numBytesOut = -1;
	/** The size of the matrix used. */
	private long matrixSize = -1;

	/** The node type that was used. */
	private NodeMode nodeMode;
	/** The type of matrix used. */
	private MatrixMode matrixMode;

	/** The map of timing data. */
	private LinkedHashMap<Step, Long> timingMap = new LinkedHashMap<>();

	/** The step we are currently on in the run. */
	private Step curStep;

	/** The progress of the current step. */
	private long curStepProg = 0;
	/** The max progress value for the step. */
	private long curStepProgMax = 0;

	/**
	 * Gets the node mode of this run.
	 * @return The node mode of this run.
	 */
	public synchronized NodeMode getNodeMode(){
		return this.nodeMode;
	}

	/**
	 * Sets the node mode of this run
	 *
	 * @param nodeMode The node mode to set.
	 */
	public synchronized void setNodeMode(NodeMode nodeMode){
		if(this.getNodeMode() != null){
			throw new IllegalStateException("Node mode was already set.");
		}
		this.nodeMode = nodeMode;
	}

	/**
	 * Gets the scramble mode of the run.
	 * @return The scramble mode of the run.
	 */
	public ScrambleMode getScrambleMode(){
		return this.scrambleMode;
	}

	/**
	 * Gets the type of matrix used on this run.
	 * @return The type of matrix used on this run.
	 */
	public synchronized MatrixMode getMatrixMode(){
		return this.matrixMode;
	}

	/**
	 * Sets the type matrix used in this run
	 *
	 * @param matrixMode The type of matrix used in the run.
	 */
	public synchronized void setMatrixMode(MatrixMode matrixMode){
		if(this.getMatrixMode() != null){
			throw new IllegalStateException("Matrix mode already set.");
		}
		this.matrixMode = matrixMode;
	}

	/**
	 * Gets the current step that the run is currently on.
	 * @return The current step that the run is currently on.
	 */
	public synchronized Step getCurStep(){
		return this.curStep;
	}

	/**
	 * Sets the current step that the run is currently on.
	 *
	 * @param curStep The current step that the run is currently on.
	 */
	public synchronized void setCurStep(Step curStep){
		if(this.getTimingMap().containsKey(curStep)){
			throw new IllegalStateException("Step already occurred, cannot repeat steps.");
		}
		this.curStep = curStep;
		this.resetStepProg();
	}

	/**
	 * Sets the {@link #timingMap timing map}.
	 * @param timingMap The new timing map to use.
	 */
	protected synchronized void setTimingMap(LinkedHashMap<Step,Long> timingMap){
		this.timingMap = timingMap;
	}

	/**
	 * Gets {@link #curStepProg current step progress}.
	 * @return The current step progress.
	 */
	public synchronized long getCurStepProg(){
		return curStepProg;
	}

	/**
	 * Sets the {@link #curStepProg current step progress}.
	 * @param curStepProg The current step progress to use.
	 */
	public synchronized void setCurStepProg(long curStepProg){
		this.curStepProg = curStepProg;
	}

	/**
	 * Gets the {@link #curStepProgMax current step progress max value}.
	 * @return The current max step progress.
	 */
	public synchronized long getCurStepProgMax(){
		return curStepProgMax;
	}

	/**
	 * Sets the {@link #curStepProgMax current step progress max value}. Also resets {@link #curStepProg current step progress}.
	 * @param curStepProgMax The new max value to use.
	 */
	public synchronized void setCurStepProgMax(long curStepProgMax){
		this.setCurStepProg(0);
		this.curStepProgMax = curStepProgMax;
	}

	/**
	 * Resets the current step progress, setting {@link #curStepProg current step progress}. and {@link #curStepProgMax current step progress max} to 0.
	 */
	private synchronized void resetStepProg(){
		this.setCurStepProgMax(0);
	}

	/**
	 * Gets the percentage complete of the current step.
	 * @return The percentage of the step completed.
	 */
	public synchronized byte getStepPercentDone(){
		if(this.getCurStepProgMax() < 1){
			return 0;
		}
		return (byte) (((double)this.getCurStepProg() / (double) this.getCurStepProgMax()) * 100.0);
	}

	/**
	 * Sets the time it took to complete a specific step.
	 * @param step The step.
	 * @param timeTook How long it took to complete the step.
	 * @throws IllegalArgumentException If the step given is not in the set of steps for this run.
	 * @throws IllegalStateException If the step is already in the timing data.
	 */
	public synchronized void setElapsedTime(Step step, long timeTook){
		if(step.mode != this.scrambleMode){
			throw new IllegalArgumentException("Step given was not part of the mode setup.");
		}
		if(this.timingMap.containsKey(step)){
			throw new IllegalStateException("Cannot overwrite step timing.");
		}
		this.timingMap.put(step, timeTook);
	}

	/**
	 * Sets the time taken to run a step.
	 * @param step The step that took place.
	 * @param startTime Time in milliseconds when the step started.
	 * @param endTime Time in milliseconds when the step ended.
	 * @throws IllegalArgumentException If the step given is not in the set of steps for this run.
	 * @throws IllegalStateException If the step is already in the timing data.
	 */
	public synchronized void setElapsedTime(Step step, long startTime, long endTime){
		this.setElapsedTime(step, endTime - startTime);
	}

	/**
	 * Gets the number of milliseconds it took for the step to be completed.
	 * @param step The step to get the data for.
	 * @return The number of milliseconds it took to complete the step.
	 */
	public synchronized long getStepTiming(Step step){
		return this.timingMap.get(step);
	}

	/**
	 * Gets the timing data from the runner.
	 * @return The timing data from the runner. Null if not run.
	 */
	public synchronized LinkedHashMap<Step, Long> getTimingMap(){
		if(this.timingMap == null){
			return null;
		}
		return (LinkedHashMap<Step, Long>) this.timingMap.clone();
	}

	/**
	 * Sets the number of bytes in the data read in. Note this is not meant to include key data.
	 * @param numBytesIn The number of bytes in the data read in.
	 */
	public synchronized void setNumBytesIn(long numBytesIn){
		if(this.getNumBytesIn() != -1){
			throw new IllegalStateException("Already set the number of bytes in.");
		}
		this.numBytesIn = numBytesIn;
	}

	/**
	 * Gets the number of bytes in the data read in. Note this is not meant to include key data.
	 * @return The number of bytes of the data read in.
	 */
	public synchronized long getNumBytesIn(){
		return this.numBytesIn;
	}

	/**
	 * Sets the number of bytes written out. Note this is not meant to include key data.
	 *
	 * @param numBytesOut The number of bytes in the data written out.
	 */
	public synchronized void setNumBytesOut(long numBytesOut){
		if(this.getNumBytesOut() != -1){
			throw new IllegalStateException("Already set the number of bytes out.");
		}
		this.numBytesOut = numBytesOut;
	}

	/**
	 * Gets the size of the matrix used.
	 * @return The size of the matrix used.
	 */
	public synchronized long getMatrixSize(){
		return this.matrixSize;
	}

	/**
	 * Sets the size of the matrix used.
	 * @param matrixSize The size of the matrix used.
	 */
	public synchronized void setMatrixSize(long matrixSize){
		if(this.getMatrixSize() != -1){
			throw new IllegalStateException("Already set the number of bytes out.");
		}
		this.matrixSize = matrixSize;
	}

	/**
	 * Gets the number of bytes written out. Note this is not meant to include key data.
	 * @return The number of bytes written out.
	 */
	public synchronized long getNumBytesOut(){
		return this.numBytesOut;
	}

	/**
	 * Logs out the timing data to the {@link #LOGGER}.
	 */
	public void logOutTimingData(){
		HashMap<Step, Long> timingMap = this.getTimingMap();

		LOGGER.info("Step Timing data: (how log it took to do each step)");
		for(Map.Entry<Step, Long> curStep : timingMap.entrySet()){
			LOGGER.info("\t{}: {}s", curStep.getKey(), (double)curStep.getValue()/1000.0);
		}
	}

	/**
	 * Gets the base CSV header applicable to all run modes.
	 * @return The base CSV header applicable to all run modes.
	 */
	public static String getCsvHeadBase(){
		return "scrambleMode,nodeMode,matrixMode,lastStep,matrixSize,numBytesIn,numBytesOut";
	}

	/**
	 * Gets the base CSV line applicable to all run modes.
	 * @return The base CSV line applicable to all run modes.
	 */
	public String getCsvLineBase(){
		StringBuilder sb = new StringBuilder();

		sb.append(this.scrambleMode.name);
		sb.append(",");
		sb.append(this.nodeMode.typeStr);
		sb.append(",");
		sb.append(this.matrixMode.name);
		sb.append(",");
		sb.append(this.getCurStep().stepName);
		sb.append(",");
		sb.append(this.getMatrixSize());
		sb.append(",");
		sb.append(this.getNumBytesIn());
		sb.append(",");
		sb.append(this.getNumBytesOut());

		return sb.toString();
	}

	/**
	 * Gets the csv values for the timing data held.
	 *
	 * Note that this is run type dependant.
	 *
	 * @return The csv values for the timing data held.
	 */
	public String getCsvTiming(){
		StringBuilder sb = new StringBuilder();
		LinkedHashMap<Step, Long> steps = this.getTimingMap();
		for (Step curStep : Step.getStepsIn(this.scrambleMode)) {
			Long val = steps.get(curStep);
			sb.append(",");
			sb.append(
				(val == null ? -1 : val)
			);
		}
		return sb.toString();
	}

	@Override
	public abstract RunResults clone();

	/**
	 * Gets a csv line for this result.
	 * @return a csv line representing this result.
	 */
	public abstract String getCsvLine(boolean header);

	/**
	 * Gets the csv line for this result. Does not include the header.
	 * @return The csv line for this result.
	 */
	public String getCsvLine(){
		return this.getCsvLine(false);
	}

	/**
	 * Gets all the csv lines of the results in the collection.
	 * @param results The run results to use
	 * @return The string of csv's from the results.
	 * @throws IllegalArgumentException If the results are of mixed scrambling modes.
	 */
	public static String getCsvLines(Collection<RunResults> results) throws IllegalArgumentException{
		StringBuilder sb = new StringBuilder();
		ScrambleMode mode = null;

		for(RunResults curResults : results){
			if(mode == null){
				mode = curResults.getScrambleMode();
				if(curResults instanceof ScrambleResults){
					sb.append(ScrambleResults.getCsvHead());
				}else{
					sb.append(DescrambleResults.getCsvHead());
				}
			}

			if(curResults.getScrambleMode() != mode){
				throw new IllegalArgumentException("The list of run results were not all the same scrambling mode.");
			}
			sb.append(curResults.getCsvLine());
		}
		return sb.toString();
	}
}
