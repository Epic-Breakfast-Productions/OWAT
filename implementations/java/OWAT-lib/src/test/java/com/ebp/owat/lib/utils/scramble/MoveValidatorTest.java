package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedScramblingMatrix;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

import static com.ebp.owat.lib.utils.scramble.ScrambleMoves.*;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MoveValidatorTest {
	private static Matrix<Integer> getTestMatrix(long numRowsCols){
		Matrix<Integer> output = new HashedScramblingMatrix<>();
		output.grow(numRowsCols);
		return output;
	}
	
	private static HashMap<ScrambleMoves, ScrambleMove> validMoves = new HashMap<ScrambleMoves, ScrambleMove>() {
		{
			put(SWAP, new ScrambleMove(SWAP, 0,0,1,1));
			put(SWAP_ROW, new ScrambleMove(SWAP_ROW, 0,1));
			put(SWAP_COL, new ScrambleMove(SWAP_COL, 0,1));
			put(SLIDE_ROW, new ScrambleMove(SLIDE_ROW, 0,1));
			put(SLIDE_COL, new ScrambleMove(SLIDE_COL, 0,1));
			put(ROT_BOX, new ScrambleMove(ROT_BOX, 1,0,0,3));
		}
	};
	private static HashMap<Integer, ScrambleMove> invalidMoves = new HashMap<Integer, ScrambleMove>() {
		{
			put(1, new ScrambleMove(SWAP, 0,0,0,0));
			put(2, new ScrambleMove(SWAP, 0,0,0,10));
			put(3, new ScrambleMove(SWAP_ROW, 0,10));
			put(4, new ScrambleMove(SWAP_COL, 0,10));
			put(5, new ScrambleMove(SLIDE_ROW, 10,1));
			put(6, new ScrambleMove(SLIDE_COL, 10,1));
			put(7, new ScrambleMove(ROT_BOX, 1,10,0,3));
			put(8, new ScrambleMove(ROT_BOX, 0,10,0,3));
			put(9, new ScrambleMove(ROT_BOX, -1,10,0,3));
			put(10, new ScrambleMove(ROT_BOX, 4,10,0,3));
		}
	};
	private static HashMap<ScrambleMoves, ScrambleMove> invalidMovesBadType = new HashMap<ScrambleMoves, ScrambleMove>() {
		{
			put(SWAP_ROW, new ScrambleMove(SWAP, 0,0,1,10));
			put(SWAP, new ScrambleMove(SWAP_ROW, 0,10));
			put(SLIDE_ROW, new ScrambleMove(SWAP_COL, 0,10));
			put(SWAP_COL, new ScrambleMove(SLIDE_ROW, 10,1));
			put(ROT_BOX, new ScrambleMove(SLIDE_COL, 10,1));
			put(SLIDE_COL, new ScrambleMove(ROT_BOX, 1,10,0,3));
		}
	};
	
	@Test
	public void testIfMatrixTooSmallForScrambling(){
		Matrix testMatrix = getTestMatrix(1);
		
		assertTrue(MoveValidator.matrixIsTooSmallForScrambling(testMatrix));
		
		try{
			MoveValidator.throwIfMatrixTooSmallForScrambling(testMatrix);
			Assert.fail();
		}catch (IllegalStateException e){
			//nothing to do
		}
		
		testMatrix = getTestMatrix(MoveValidator.MIN_SIZE_FOR_SCRAMBLING);
		
		assertFalse(MoveValidator.matrixIsTooSmallForScrambling(testMatrix));
		
		MoveValidator.throwIfMatrixTooSmallForScrambling(testMatrix);
	}
	
	@Test
	public void testIfInvalidMove(){
		Matrix testMatrix = getTestMatrix(MoveValidator.MIN_SIZE_FOR_SCRAMBLING);
		
		for(Map.Entry<ScrambleMoves, ScrambleMove> curEntry : validMoves.entrySet()){
			MoveValidator.throwIfInvalidMove(testMatrix, curEntry.getValue());
			MoveValidator.throwIfInvalidMove(testMatrix, curEntry.getValue(), curEntry.getKey());
		}
		
		for(Map.Entry<Integer, ScrambleMove> curEntry : invalidMoves.entrySet()){
			try {
				MoveValidator.throwIfInvalidMove(testMatrix, curEntry.getValue());
				Assert.fail();
			}catch (IllegalArgumentException|IndexOutOfBoundsException e){
				//nothing to do
			}
		}
		
		for(Map.Entry<ScrambleMoves, ScrambleMove> curEntry : invalidMovesBadType.entrySet()){
			try {
				MoveValidator.throwIfInvalidMove(testMatrix, curEntry.getValue(), curEntry.getKey());
				Assert.fail();
			}catch (IllegalArgumentException|IndexOutOfBoundsException e){
				//nothing to do
			}
		}
	}
}
