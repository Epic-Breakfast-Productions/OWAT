package com.ebp.owat.app.runner;

import com.ebp.owat.app.runner.utils.RunResults;
import com.ebp.owat.app.runner.utils.ScrambleRunnerThread;
import com.ebp.owat.lib.datastructure.value.NodeMode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public abstract class OwatRunner {
	private static final Logger LOGGER = LoggerFactory.getLogger(OwatRunner.class);
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	protected static final NodeMode DEFAULT_MODE = NodeMode.BIT;

	protected RunResults lastRunResults;

	protected synchronized void setLastRunResults(RunResults results){
		this.lastRunResults = results;
	}

	public synchronized RunResults getLastRunResults() {
		return this.lastRunResults.clone();
	}
	
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
