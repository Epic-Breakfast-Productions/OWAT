package com.ebp.owat.app.runner;

import com.fasterxml.jackson.databind.ObjectMapper;

public abstract class OwatRunner {
	protected static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();
	
	private Step curStep = Step.NOT_STARTED;
	
	public synchronized Step getCurStep(){
		return this.curStep;
	}
	
	protected synchronized void setCurStep(Step curStep){
		this.curStep = curStep;
	}

}
