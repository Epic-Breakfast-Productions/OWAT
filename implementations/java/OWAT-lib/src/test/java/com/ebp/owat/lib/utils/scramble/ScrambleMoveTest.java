package com.ebp.owat.lib.utils.scramble;

import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScrambleMoveTest extends ScMoTest {
	
	@Test
	public void testScrambleMoveParse(){
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP, 10L, 1L, 100L, 399L), ScrambleMove.parse("sw:10x1,100x399;"));
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP_ROW, 10L, 399L), ScrambleMove.parse("swr:10,399;"));
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP_COL, 10L, 399L), ScrambleMove.parse("swc:10,399;"));
		assertEquals(new ScrambleMove(ScrambleMoves.SLIDE_ROW, 10L, 399L), ScrambleMove.parse("slr:10,399;"));
		assertEquals(new ScrambleMove(ScrambleMoves.SLIDE_COL, 10L, 399L), ScrambleMove.parse("slc:10,399;"));
		assertEquals(new ScrambleMove(ScrambleMoves.ROT_CLOCK, 1, 10L, 1L, 100L, 399L), ScrambleMove.parse("rcl:1,10x1,100x399;"));
		assertEquals(new ScrambleMove(ScrambleMoves.ROT_CCLOCK, 1, 10L, 1L, 100L, 399L), ScrambleMove.parse("rcc:1,10x1,100x399;"));
		
	}
	
	@Test
	public void testScrambleMoveMultiParse(){
		//TODO
	}
	
	
	@Test
	public void testScrambleMoveToKeyString(){
		//TODO
	}
}

