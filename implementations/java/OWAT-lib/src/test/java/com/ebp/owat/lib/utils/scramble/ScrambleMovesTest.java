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
public class ScrambleMovesTest {
	private static final Collection<String> SWAP = Arrays.asList("sw:10x1,100x399;", " sw : 10 x 1 , 100 x 399 ; ");
	private static final Collection<String> SWAP_ROW = Arrays.asList("swr:10,100;", " swr : 10 , 100 ; ");
	private static final Collection<String> SWAP_COL = Arrays.asList("swc:100,399;", " swc : 100 , 399 ; ");
	private static final Collection<String> SLIDE_ROW = Arrays.asList("slr:100,399;"," slr : 100 , 399 ; ");
	private static final Collection<String> SLIDE_COL = Arrays.asList("slc:100,399;"," slc : 100 , 399 ; ");
	private static final Collection<String> ROT_CLOCK = Arrays.asList("rcl:1,1x2,3x4;"," rcl : 1 , 1 x 2 , 3 x 4 ; ");
	private static final Collection<String> ROT_CCLOCK = Arrays.asList("rcc:1,1x2,3x4;"," rcc : 1 , 1 x 2 , 3 x 4 ; ");
	private static final Collection<String> INVALID = Arrays.asList("", "sw:10x1,100x399");
	
	private final ScrambleMoves sm;
	private final Collection<String> validMatches;
	private final Iterable<String> otherValid;
	private final Collection<String> inValid;
	
	
	
	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ ScrambleMoves.SWAP, SWAP, Iterables.concat(SWAP_ROW, SWAP_COL, SLIDE_COL, SLIDE_ROW, ROT_CLOCK, ROT_CCLOCK), INVALID },
			{ ScrambleMoves.SWAP_ROW, SWAP_ROW, Iterables.concat(SWAP, SWAP_COL, SLIDE_COL, SLIDE_ROW, ROT_CLOCK, ROT_CCLOCK), INVALID },
			{ ScrambleMoves.SWAP_COL, SWAP_COL, Iterables.concat(SWAP, SWAP_ROW, SLIDE_COL, SLIDE_ROW, ROT_CLOCK, ROT_CCLOCK), INVALID },
			{ ScrambleMoves.SLIDE_ROW, SLIDE_ROW, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_COL, ROT_CLOCK, ROT_CCLOCK), INVALID },
			{ ScrambleMoves.SLIDE_COL, SLIDE_COL, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_ROW, ROT_CLOCK, ROT_CCLOCK), INVALID },
			{ ScrambleMoves.ROT_CLOCK, ROT_CLOCK, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_ROW, SLIDE_COL, ROT_CCLOCK), INVALID },
			{ ScrambleMoves.ROT_CCLOCK, ROT_CCLOCK, Iterables.concat(SWAP, SWAP_ROW, SWAP_COL, SLIDE_ROW, SLIDE_COL, ROT_CLOCK), INVALID }
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
