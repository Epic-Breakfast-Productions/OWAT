package com.ebp.owat.lib.runner;

import com.ebp.owat.lib.datastructure.matrix.ScrambleMatrix;
import com.ebp.owat.lib.datastructure.value.Value;
import com.ebp.owat.lib.runner.utils.RunnerUtilities;
import com.ebp.owat.lib.runner.utils.ScrambleRunnerThread;
import com.ebp.owat.lib.runner.utils.Step;
import com.ebp.owat.lib.runner.utils.results.RunResults;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

/**
 * Describes a runner for scrambling/descrambling.
 * @param <N> The type of value to use
 * @param <M> The type of matrix to use
 * @param <R> The random number generator to use
 */
public abstract class OwatRunner<N extends Value, M extends ScrambleMatrix<N>, R extends OwatRandGenerator> {
	private static final Logger LOGGER = LoggerFactory.getLogger(OwatRunner.class);

	/**
	 * Object mapper for serializing/deserializing a key.
	 */
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

	/**
	 * The utilities to use to facilitate descrambling
	 */
	protected RunnerUtilities<N, M, R> utils = new RunnerUtilities<>();

	/**
	 * The results of the last run.
	 */
	protected RunResults lastRunResults;

	/**
	 * Sets the results of the last run.
	 * @param results The results to set.
	 */
	protected synchronized void setLastRunResults(RunResults results){
		this.lastRunResults = results;
	}

	/**
	 * Gets a clone of the last run results.
	 * @return A clone of the last run results.
	 */
	public abstract RunResults getLastRunResults();

	/**
	 * Gets the percentage done on this current step.
	 * @return The percentage of the process on the current step.
	 */
	public synchronized float getLastRunCurStepPercent(){
		return this.lastRunResults.getStepPercentDone();
	}

	/**
	 * Gets the step the process is currently on.
	 * @return The step the process is currently on.
	 */
	public synchronized Step getCurStep() {
		if(this.lastRunResults == null){
			return Step.NOT_STARTED_SCRAMBLE;
		}
		return this.lastRunResults.getCurStep();
	}

	/**
	 * Runs the process.
	 * @throws IOException If something went wrong in the input or output of data.
	 */
	public abstract void doSteps() throws IOException;
	
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
}
