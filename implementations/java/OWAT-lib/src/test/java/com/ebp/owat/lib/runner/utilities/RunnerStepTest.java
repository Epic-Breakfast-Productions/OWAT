package com.ebp.owat.lib.runner.utilities;

import com.ebp.owat.lib.runner.utils.ScrambleMode;
import com.ebp.owat.lib.runner.utils.Step;
import org.junit.Test;

import java.util.Collection;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class RunnerStepTest {

	@Test
	public void testGetStepsIn(){
		Collection<Step> stepsIn = Step.getStepsIn(ScrambleMode.DESCRAMBLING);
		assertNotNull(stepsIn);
		assertFalse(stepsIn.isEmpty());

		stepsIn = Step.getStepsIn(ScrambleMode.SCRAMBLING);
		assertNotNull(stepsIn);
		assertFalse(stepsIn.isEmpty());
	}
}
