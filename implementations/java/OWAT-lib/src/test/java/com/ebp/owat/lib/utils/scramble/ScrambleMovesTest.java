package com.ebp.owat.lib.utils.scramble;

import com.google.common.collect.Iterables;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

@RunWith(Parameterized.class)
public class ScrambleMovesTest extends ScMoTest {
	private final ScrambleMoves sm;
	private final Collection<String> validMatches;
	private final Iterable<String> otherValid;
	private final Collection<String> inValid;
	
	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ ScrambleMoves.SWAP, SWAP, Iterables.concat(SWAP_ROW, SWAP_COL, SLIDE_COL, SLIDE_ROW, ROT_BOX), INVALID },
			{ ScrambleMoves.SWAP_ROW, SWAP_ROW, Iterables.concat(SWAP, SWAP_COL, SLIDE_COL, SLIDE_ROW, ROT_BOX), INVALID },
			{ ScrambleMoves.SWAP_COL, SWAP_COL, Iterables.concat(SWAP, SWAP_ROW, SLIDE_COL, SLIDE_ROW, ROT_BOX), INVALID },
			{ ScrambleMoves.SLIDE_ROW, SLIDE_ROW, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_COL, ROT_BOX), INVALID },
			{ ScrambleMoves.SLIDE_COL, SLIDE_COL, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_ROW, ROT_BOX), INVALID },
			{ ScrambleMoves.ROT_BOX, ROT_BOX, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_ROW, SLIDE_COL), INVALID }
		});
	}
	
	public ScrambleMovesTest(ScrambleMoves sm, Collection<String> validMatches, Iterable<String> otherValid, Collection<String> inValid){
		this.sm = sm;
		this.validMatches = validMatches;
		this.otherValid = otherValid;
		this.inValid = inValid;
	}
	
	@Test
	public void testScrambleMovesIsValid(){
		for(String curValidMatch : this.validMatches){
			assertTrue(this.sm.isValid(curValidMatch));
		}
		for(String curOther : this.otherValid){
			assertFalse(this.sm.isValid(curOther));
		}
		for(String curInvalid : this.inValid){
			assertFalse(this.sm.isValid(curInvalid));
		}
	}
	
	@Test
	public void testScrambleMovesDetermine(){
		for(String curValidMatch : this.validMatches) {
			assertEquals(this.sm, ScrambleMoves.determineMove(curValidMatch));
		}
		for(String curOther : this.otherValid){
			assertNotEquals(this.sm, ScrambleMoves.determineMove(curOther));
		}
		for(String curInvalid : this.inValid){
			try{
				ScrambleMoves.determineMove(curInvalid);
				Assert.fail();
			}catch (IllegalArgumentException e){
				//nothing to do
			}
		}
	}
}
