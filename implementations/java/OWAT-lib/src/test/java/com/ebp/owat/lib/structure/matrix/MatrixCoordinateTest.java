package com.ebp.owat.lib.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.hash.HashedScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.*;

/**
 * Tests the MatrixCoordinate object.
 *
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Parameterized.class)
public class MatrixCoordinateTest {
	private Logger LOGGER = LoggerFactory.getLogger(MatrixCoordinateTest.class);
	
	@Parameterized.Parameters
	public static Collection exceptionsToTest() {
		return Arrays.asList(new Object[][] {
				{ MatrixCoordinate.class }
		});
	}
	
	private static MatrixCoordinate getCoordInstance(Class<? extends MatrixCoordinate> curClass, Matrix m, long x, long y){
		try {
			return curClass.getConstructor(Matrix.class, Long.TYPE, Long.TYPE).newInstance(m, x, y);
		} catch (Throwable e) {
			throw new RuntimeException("Unable to instantiate MatrixCoordinate.", e);
		}
	}
	
	private MatrixCoordinate testingCoord;
	
	private final Class<? extends MatrixCoordinate> curCoordClass;
	
	public MatrixCoordinateTest(Class<? extends MatrixCoordinate> curCoordClass){
		this.curCoordClass = curCoordClass;
	}
	
	@Test
	public void testCoordinateConstructors() throws Throwable {
		Constructor<? extends MatrixCoordinate> constBase = curCoordClass.getConstructor(Matrix.class);
		Constructor<? extends MatrixCoordinate> constCoords = curCoordClass.getConstructor(Matrix.class, Long.TYPE, Long.TYPE);
		
		Matrix<Long> matrix = new HashedScramblingMatrix<>();
		
		MatrixCoordinate coord;
		//test that you can't make a coord on an empty matrix
		try {
			coord = constBase.newInstance(matrix);
			Assert.fail();
		} catch (Throwable e) {
			if (!(e.getCause() instanceof IllegalStateException)) {
				Assert.fail();
			}
		}
		
		matrix.addRow();
		
		coord = constCoords.newInstance(matrix, 0, 0);
	}
	
	@Test
	public void testCoordinateSettersGetters() throws Throwable {
		Matrix<Long> matrix = new HashedScramblingMatrix<>();
		matrix.addRow();

		MatrixCoordinate coord = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		assertEquals("", 0L, coord.getX());
		assertEquals("", 0L, coord.getY());
		
		matrix.addRow();
		
		coord.setY(1);
		
		assertEquals("", 0L, coord.getX());
		assertEquals("", 1L, coord.getY());
		
		matrix.addCol();
		
		coord.setX(1);
		
		assertEquals("", 1L, coord.getX());
		assertEquals("", 1L, coord.getY());
		
		try{
			coord.setX(2);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){
			//nothing to do
		}
		
		try{
			coord.setY(2);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){
			//nothing to do
		}
		
	}
	
	@Test
	public void testCoordinateEquals() throws Throwable {
		Matrix<Long> matrix = new HashedScramblingMatrix<>();
		matrix.addRow();
		
		MatrixCoordinate coordOne = getCoordInstance(curCoordClass, matrix, 0, 0);
		MatrixCoordinate coordTwo = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		assertTrue(coordOne.equals(coordTwo));
		
		matrix.addRow();
		coordOne.setY(1);
		
		assertFalse(coordOne.equals(coordTwo));
		assertFalse(coordOne.equals(null));
		assertFalse(coordOne.equals(new Object()));
	}
	
	@Test
	public void testCoordinateClone() throws Throwable {
		Matrix<Long> matrix = new HashedScramblingMatrix<>();
		matrix.addRow();
		
		MatrixCoordinate coord = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		MatrixCoordinate clone = coord.clone();
		assertTrue(coord.equals(clone));
		assertFalse(coord == clone);
	}
	
	@Test
	public void testCoordinateIsStillOnMatrix() throws Throwable {
		Matrix<Long> matrix = new HashedScramblingMatrix<>();
		matrix.grow(3);
		
		MatrixCoordinate coord = getCoordInstance(curCoordClass, matrix, 2, 2);
		
		assertTrue(coord.stillOnMatrix());
		
		matrix.addRow();
		
		assertTrue(coord.stillOnMatrix());
		
		matrix.trimTo(0,0);
		
		assertFalse(coord.stillOnMatrix());
	}
	
	@Test
	public void testCoordinateOther() throws Throwable {
		Matrix<Long> matrix = new HashedScramblingMatrix<>();
		Matrix<Long> matrixTwo = new HashedScramblingMatrix<>();
		matrix.addRow();
		matrixTwo.addRow();
		
		MatrixCoordinate coord = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		LOGGER.debug("toString: \"{}\", hash: \"{}\"", coord, coord.hashCode());
		
		MatrixCoordinate coordTwo = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		assertTrue(coord.isOnSameMatrix(coordTwo));
		
		coordTwo = getCoordInstance(curCoordClass, matrixTwo, 0, 0);
		assertFalse(coord.isOnSameMatrix(coordTwo));
	}
}
