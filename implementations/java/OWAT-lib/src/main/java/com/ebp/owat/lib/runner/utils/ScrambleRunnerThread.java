package com.ebp.owat.lib.runner.utils;

import com.ebp.owat.lib.runner.OwatRunner;

import java.io.IOException;

/**
 * A thread that a scrambler/descrambler can run in. Used for multithreaded applications.
 * @param <T> The type of runner this is running
 */
public class ScrambleRunnerThread<T extends OwatRunner> extends Thread {
	private final T runner;

	/**
	 * Constructor to set the runner.
	 * @param runner The runner.
	 */
	public ScrambleRunnerThread(T runner){
		if(runner == null){
			throw new IllegalArgumentException("Runner cannot be null");
		}
		this.runner = runner;
	}
	
	@Override
	public void run() {
		try {
			runner.doSteps();
		} catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
