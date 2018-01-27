package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;
import com.ebp.owat.lib.utils.rand.LongGenerator;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import com.ebp.owat.lib.utils.scramble.generator.ScrambleMoveGenerator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class ScrambleMoveGeneratorTest {
	
	private static final int NUM_TEST_ITERATIONS = 3_000_000;
	private static final LongGenerator LONG_GEN = new RandGenerator();
	
	@Test
	public void scrambleMoveGeneratorTest(){
		Matrix<Boolean> testMatrix = new HashedMatrix<>();
		ScrambleMoveGenerator gen = new ScrambleMoveGenerator(LONG_GEN, testMatrix);
		
		try{
			gen.getMove();
			Assert.fail();
		}catch (IllegalStateException e){
			assertEquals("Matrix has to have rows and columns to scramble.", e.getMessage());
		}
		
		testMatrix.grow(100);
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			ScrambleMove curMove = gen.getMove();
			verifyMove(testMatrix, curMove);
		}
		
		testMatrix.addRows(10);
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			ScrambleMove curMove = gen.getMove();
			verifyMove(testMatrix, curMove);
		}
		
		testMatrix.addCols(100);
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			ScrambleMove curMove = gen.getMove();
			verifyMove(testMatrix, curMove);
		}
	}
	
	private static void verifyMove(Matrix matrix, ScrambleMove move){
		switch (move.move){
			case SWAP:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.X1), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.Y1), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.X2), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.Swap.Y2), Plane.Y);
				break;
			case SWAP_ROW:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapRow.ROWCOL1), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapRow.ROWCOL2), Plane.Y);
				break;
			case SWAP_COL:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapCol.ROWCOL1), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SwapCol.ROWCOL2), Plane.X);
				break;
			case SLIDE_ROW:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideRow.ROWCOL), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideRow.NUMTOSLIDE), Plane.X);
				break;
			case SLIDE_COL:
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideCol.ROWCOL), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.SlideCol.NUMTOSLIDE), Plane.Y);
				break;
			case ROT_CLOCK:
			case ROT_CCLOCK:
				//TODO:: ensure valid block. need to ensure their two points are valid to eachother (top right (x/y1) is actually rop right, bottom left (x/y2) is actually bottom left)
				assertTrue(move.getArg(ScrambleConstants.RotateClock.ROTNUM) >= 0 && move.getArg(ScrambleConstants.RotateClock.ROTNUM) <= 3);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateClock.X1), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateClock.Y1), Plane.Y);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateClock.X2), Plane.X);
				MatrixValidator.throwIfBadIndex(matrix, move.getArg(ScrambleConstants.RotateClock.Y2), Plane.Y);
				break;
			default:
				Assert.fail("Unsupported move passed to validator.");
		}
	}
}
