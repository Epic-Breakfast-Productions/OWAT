package com.ebp.owat.lib.structure.matrix.scrambler;

import com.ebp.owat.lib.datastructure.matrix.ScrambleMatrix;
import com.ebp.owat.lib.datastructure.matrix.array.ArrayScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedScramblingMatrix;
import com.ebp.owat.lib.testUtils.TestUtils;
import com.ebp.owat.lib.utils.scramble.ScrambleMove;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.Collection;

import static com.ebp.owat.lib.utils.scramble.ScrambleMoves.*;
import static org.junit.Assert.assertEquals;

/**
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Parameterized.class)
public class ScrambleMatrixTest <E extends ScrambleMatrix<Integer>> {
	private static final Logger LOGGER = LoggerFactory.getLogger(ScrambleMatrixTest.class);
	
	protected final Class<E> curMatrixClass;
	
	private final Integer N;
	
	private long origNumInMatrix;
	
	public ScrambleMatrixTest(Class<E> curMatrixClass) throws Exception {
		this.curMatrixClass = curMatrixClass;
		N = this.getTestingInstance().getDefaultValue();
	}
	
	@Parameterized.Parameters
	public static Collection getMatrixClassesToTest(){
		return Arrays.asList(new Object[][] {
			{ HashedScramblingMatrix.class },
			{ ArrayScramblingMatrix.class },
			//{ LinkedScramblingMatrix.class }//infinite loop somewhere
		});
	}
	
	@Before
	public void setUp() throws Exception {
		LOGGER.info("Testing the scrambling matrix {}", this.curMatrixClass);
	}
	
	@After
	public void tearDown() throws Exception {
		LOGGER.info("Done testing the scrambling matrix {}", this.curMatrixClass);
	}
	
	@SuppressWarnings("unchecked")
	protected E getTestingInstance() throws Exception {
		LOGGER.debug("Getting instance of {} matrix.", this.curMatrixClass.getName());
		return (E) this.curMatrixClass.getConstructor().newInstance();
	}
	
	private E getPopulatedTestingInstance() throws Exception{
		E testMatrix = this.getTestingInstance();
		
		TestUtils.setupMatrix(testMatrix);
		
		this.origNumInMatrix = testMatrix.numElements();
		return testMatrix;
	}
	
	@Test
	public void testSwap() throws Exception {
		E testMatrix = this.getPopulatedTestingInstance();
		
		try{
			testMatrix.swap(new ScrambleMove(SWAP_ROW, 1,0));
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}

		ScrambleMove testMove = new ScrambleMove(SWAP, 0,0,4,4);
		testMatrix.swap(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{24, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N, 0}
			},
			testMatrix
		);

		testMove.toReverse();
		testMatrix.swap(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);

		testMove = new ScrambleMove(SWAP, 1,0,1,1);

		testMatrix.swap(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, 6, 2, N, 4},
				{ N, N, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);

		testMove.toReverse();
		testMatrix.swap(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);
	}
	
	@Test
	public void testSwapRows() throws Exception {
		E testMatrix = this.getPopulatedTestingInstance();
		
		try{
			testMatrix.swapRows(new ScrambleMove(SWAP, 1,0,3,4));
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}

		ScrambleMove testMove = new ScrambleMove(SWAP_ROW, 1,0);

		testMatrix.swapRows(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ N, 6, N, 8, N},
				{ 0, N, 2, N, 4},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N, 24}
			},
			testMatrix
		);

		testMove.toReverse();
		testMatrix.swapRows(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);
	}
	
	@Test
	public void testSwapCols() throws Exception {
		E testMatrix = this.getPopulatedTestingInstance();
		
		try{
			testMatrix.swapCols(new ScrambleMove(SWAP, 1,0,3,4));
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}

		ScrambleMove testMove = new ScrambleMove(SWAP_COL, 1,0);
		testMatrix.swapCols(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ N, 0, 2, N, 4},
				{ 6, N, N, 8, N},
				{ N,10,12,13,14},
				{16,15, N,18,19},
				{ N,20,22, N,24}
			},
			testMatrix
		);

		testMove.toReverse();
		testMatrix.swapCols(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);
	}
	
	@Test
	public void testSlideRow() throws Exception {
		E testMatrix = this.getPopulatedTestingInstance();
		
		try{
			testMatrix.slideRow(new ScrambleMove(SWAP, 1,0,3,4));
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}

		ScrambleMove testMove = new ScrambleMove(SLIDE_ROW, 0, 3);

		testMatrix.slideRow(testMove);
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 2, N, 4, 0, N},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);

		testMove.toReverse();
		testMatrix.slideRow(testMove);
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);
	}
	
	@Test
	public void testSlideCol() throws Exception {
		E testMatrix = this.getPopulatedTestingInstance();
		
		try{
			testMatrix.slideCol(new ScrambleMove(SWAP, 1,0,3,4));
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}

		ScrambleMove testMove = new ScrambleMove(SLIDE_COL, 0, 3);
		testMatrix.slideCol(testMove);
		
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{10, N, 2, N, 4},
				{15, 6, N, 8, N},
				{20, N,12,13,14},
				{ 0,16, N,18,19},
				{ N, N,22, N,24}
			},
			testMatrix
		);

		testMove.toReverse();
		testMatrix.slideCol(testMove);
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);

	}
	
	@Test
	public void testRotBox() throws Exception {
		E testMatrix = this.getPopulatedTestingInstance();
		
		try{
			testMatrix.rotBox(new ScrambleMove(SWAP, 1,0,3,4));
			Assert.fail();
		}catch (IllegalArgumentException e){
			//nothing to do
		}
		
		testMatrix.rotBox(new ScrambleMove(ROT_BOX, 1, 0,0,5));
		
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{20,15,10, N, 0},
				{ N,16, N, 6, N},
				{22, N,12, N, 2},
				{ N,18,13, 8, N},
				{24,19,14, N, 4}
			},
			testMatrix
		);
		
		testMatrix.rotBox(new ScrambleMove(ROT_BOX, 2, 0,0,5));
		
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 4, N,14,19,24},
				{ N, 8,13,18, N},
				{ 2, N,12, N,22},
				{ N, 6, N,16, N},
				{ 0, N,10,15,20}
			},
			testMatrix
		);
		
		testMatrix.rotBox(new ScrambleMove(ROT_BOX, 1, 0,0,5));
		
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);
		
		testMatrix.rotBox(new ScrambleMove(ROT_BOX, -1, 0,0,5));
		
		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 4, N,14,19,24},
				{ N, 8,13,18, N},
				{ 2, N,12, N,22},
				{ N, 6, N,16, N},
				{ 0, N,10,15,20}
			},
			testMatrix
		);

		testMatrix = this.getPopulatedTestingInstance();

		ScrambleMove testMove = new ScrambleMove(ROT_BOX, 1, 0,0,5);
		testMatrix.rotBox(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{20,15,10, N, 0},
				{ N,16, N, 6, N},
				{22, N,12, N, 2},
				{ N,18,13, 8, N},
				{24,19,14, N, 4}
			},
			testMatrix
		);

		testMove.toReverse();

		testMatrix.rotBox(testMove);

		assertEquals(this.origNumInMatrix, testMatrix.numElements());
		TestUtils.assertMatrix(
			new Object[][]{
				{ 0, N, 2, N, 4},
				{ N, 6, N, 8, N},
				{10, N,12,13,14},
				{15,16, N,18,19},
				{20, N,22, N,24}
			},
			testMatrix
		);
	}
}
