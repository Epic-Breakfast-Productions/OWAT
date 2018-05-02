package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedMatrix;
import com.ebp.owat.lib.utils.rand.OwatRandGenerator;
import com.ebp.owat.lib.utils.rand.RandGenerator;
import com.ebp.owat.lib.utils.scramble.generator.ScrambleMoveGenerator;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class ScrambleMoveGeneratorTest {
	
	private static final int NUM_TEST_ITERATIONS = 6_000_000;
	private static final OwatRandGenerator LONG_GEN = new RandGenerator();
	
	@Test
	public void scrambleMoveGeneratorTest(){
		Matrix<Boolean> testMatrix = new HashedMatrix<>();
		ScrambleMoveGenerator gen = new ScrambleMoveGenerator(LONG_GEN, testMatrix);
		
		try{
			gen.getMove();
			Assert.fail();
		}catch (IllegalStateException e){
			assertEquals("Matrix too small for scrambling.", e.getMessage());
		}
		
		testMatrix.grow(100);
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			ScrambleMove curMove = gen.getMove();
			MoveValidator.throwIfInvalidMove(testMatrix, curMove);
		}
		
		testMatrix.addRows(10);
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			ScrambleMove curMove = gen.getMove();
			MoveValidator.throwIfInvalidMove(testMatrix, curMove);
		}
		
		testMatrix.addCols(100);
		
		for(long i = 0; i < NUM_TEST_ITERATIONS; i++){
			ScrambleMove curMove = gen.getMove();
			MoveValidator.throwIfInvalidMove(testMatrix, curMove);
		}
	}
	
}
