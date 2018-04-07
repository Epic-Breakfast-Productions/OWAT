package com.ebp.owat.lib.runner.utils;

import com.ebp.owat.lib.runner.OwatRunner;

import java.io.IOException;

public class ScrambleRunnerThread<T extends OwatRunner> extends Thread {
	private final T runner;
	
	public ScrambleRunnerThread(T runner){
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
	
	public Step curStep(){
		return this.runner.getLastRunResults().getCurStep();
	}
}
