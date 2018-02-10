package com.ebp.owat.app.runner;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

public abstract class OwatRunner {
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private Step curStep = Step.NOT_STARTED;
	
	public synchronized Step getCurStep(){
		return this.curStep;
	}
	
	protected synchronized void setCurStep(Step curStep){
		this.curStep = curStep;
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
