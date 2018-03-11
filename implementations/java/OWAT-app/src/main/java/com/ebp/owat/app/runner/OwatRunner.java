package com.ebp.owat.app.runner;

import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

public abstract class OwatRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(OwatRunner.class);
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	protected static final NodeMode DEFAULT_MODE = NodeMode.BIT;
	
	private Step curStep = Step.NOT_STARTED;
	
	public synchronized Step getCurStep(){
		return this.curStep;
	}
	
	protected synchronized void setCurStep(Step curStep){
		this.curStep = curStep;
	}
	
	public abstract void doSteps() throws IOException;

	/** The map of timing data. */
	private HashMap<Step, Long> timingMap = null;

	/**
	 * Resets the timing data.
	 */
	protected synchronized void resetTiming(){
		this.timingMap = new LinkedHashMap<>();
	}

	/**
	 * Sets the time it took to complete a specific step.
	 * @param step The step.
	 * @param timeTook How long it took to complete the step.
	 */
	protected synchronized void setElapsedTime(Step step, long timeTook){
		if(this.timingMap.containsKey(step)){
			throw new IllegalStateException("Cannot overwrite step timing.");
		}
		this.timingMap.put(step, timeTook);
	}

	/**
	 *
	 * @param step The step that took place.
	 * @param startTime Time in milliseconds when the step started.
	 * @param endTime Time in milliseconds when the step ended.
	 */
	protected synchronized void setElapsedTime(Step step, long startTime, long endTime){
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
	public synchronized HashMap<Step, Long> getTimingMap(){
		if(this.timingMap == null){
			return null;
		}
		return (HashMap<Step, Long>) this.timingMap.clone();
	}
	
	/**
	 * Gets a runner thread to use to easily make the process of running the scrambling/descrambling threaded.
	 * @param runnerObj The runner object to run.
	 * @param start If the method is to start the run right away.
	 * @return The thread to run the scrambling/descrambling.
	 */
	public static ScrambleRunnerThread<OwatRunner> getThread(OwatRunner runnerObj, boolean start){
		ScrambleRunnerThread<OwatRunner> runningThread = new ScrambleRunnerThread<>(runnerObj);
		
		if(start){
			runningThread.run();
		}
		
		return runningThread;
	}

	public void logOutTimingData(){
		HashMap<Step, Long> timingMap = this.getTimingMap();

		LOGGER.info("Step Timing data: (how log it took to do each step)");
		for(Map.Entry<Step, Long> curStep : timingMap.entrySet()){
			LOGGER.info("\t{}: {}s", curStep.getKey(), (double)curStep.getValue()/1000.0);
		}
	}
}
