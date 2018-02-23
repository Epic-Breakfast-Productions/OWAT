package com.ebp.owat.lib.utils.scramble;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

@RunWith(Parameterized.class)
public class ScrambleMoveTest extends ScMoTest {

	private final ScrambleMove testMove;
	private final boolean usingOpcode;
	private final String expectedString;
	private final String expectedReverseString;

	public ScrambleMoveTest(ScrambleMove testMove, boolean usingOpcode, String expectedString, String expectedReverseString){
		this.testMove = testMove;
		ScrambleMove.useOpCode(usingOpcode);
		this.usingOpcode = usingOpcode;
		this.expectedString = expectedString;
		this.expectedReverseString = expectedReverseString;
	}

	@Test
	public void testCloneEquals(){
		ScrambleMove test = this.testMove.clone();
		assertTrue(this.testMove.equals(test));
		//TODO:: test this more?
	}

	@Test
	public void testToString(){
		ScrambleMove testMove = this.testMove.clone();

		assertEquals(this.expectedString, testMove.toKeyString(null, false));
		assertEquals(this.expectedReverseString, testMove.toKeyString(null, true));
	}

	@Test
	public void testParse(){
		assertEquals(this.testMove, ScrambleMove.parse(this.expectedString));
	}

	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][]{
			{
				new ScrambleMove(ScrambleMoves.SWAP, 10L, 1L, 100L, 399L),
				true,
				"1:10x1,100x399;",
				"1:10x1,100x399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SWAP, 10L, 1L, 100L, 399L),
				false,
				"sw:10x1,100x399;",
				"sw:10x1,100x399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SWAP_ROW, 10L, 399L),
				true,
				"2:10,399;",
				"2:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SWAP_ROW, 10L, 399L),
				false,
				"swr:10,399;",
				"swr:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SWAP_COL, 10L, 399L),
				true,
				"3:10,399;",
				"3:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SWAP_COL, 10L, 399L),
				false,
				"swc:10,399;",
				"swc:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_ROW, 10L, 399L),
				true,
				"4:10,399;",
				"4:10,-399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_ROW, 10L, 399L),
				false,
				"slr:10,399;",
				"slr:10,-399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_ROW, 10L, -399L),
				true,
				"4:10,-399;",
				"4:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_ROW, 10L, -399L),
				false,
				"slr:10,-399;",
				"slr:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_COL, 10L, 399L),
				true,
				"5:10,399;",
				"5:10,-399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_COL, 10L, 399L),
				false,
				"slc:10,399;",
				"slc:10,-399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_COL, 10L, -399L),
				true,
				"5:10,-399;",
				"5:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.SLIDE_COL, 10L, -399L),
				false,
				"slc:10,-399;",
				"slc:10,399;"
			},
			{
				new ScrambleMove(ScrambleMoves.ROT_BOX, 1, 10L, 1L, 50L),
				true,
				"6:1,10x1,50;",
				"6:-1,10x1,50;"
			},
			{
				new ScrambleMove(ScrambleMoves.ROT_BOX, 1, 10L, 1L, 50L),
				false,
				"rot:1,10x1,50;",
				"rot:-1,10x1,50;"
			},
			{
				new ScrambleMove(ScrambleMoves.ROT_BOX, -1, 10L, 1L, 50L),
				true,
				"6:-1,10x1,50;",
				"6:1,10x1,50;"
			},
			{
				new ScrambleMove(ScrambleMoves.ROT_BOX, -1, 10L, 1L, 50L),
				false,
				"rot:-1,10x1,50;",
				"rot:1,10x1,50;"
			}
		});
	}
}

