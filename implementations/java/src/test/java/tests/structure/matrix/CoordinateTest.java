package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Hash.HashedMatrix;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.Collection;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Coordinate object.
 *
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Parameterized.class)
public class CoordinateTest {
	private Logger LOGGER = LoggerFactory.getLogger(CoordinateTest.class);
	
	@Parameterized.Parameters
	public static Collection exceptionsToTest() {
		return Arrays.asList(new Object[][] {
				{ Coordinate.class }
		});
	}
	
	private static Coordinate getCoordInstance(Class<? extends Coordinate> curClass, Matrix m, long x, long y){
		try {
			return curClass.getConstructor(Matrix.class, Long.TYPE, Long.TYPE).newInstance(m, x, y);
		} catch (Throwable e) {
			throw new RuntimeException("Unable to instantiate Coordinate.", e);
		}
	}
	
	private Coordinate testingCoord;
	
	private final Class<? extends Coordinate> curCoordClass;
	
	public CoordinateTest(Class<? extends Coordinate> curCoordClass){
		this.curCoordClass = curCoordClass;
	}
	
	@Test
	public void testCoordinateConstructors() throws Throwable {
		Constructor<? extends Coordinate> constBase = curCoordClass.getConstructor(Matrix.class);
		Constructor<? extends Coordinate> constCoords = curCoordClass.getConstructor(Matrix.class, Long.TYPE, Long.TYPE);
		
		Matrix<Long> matrix = new HashedMatrix<>();
		
		Coordinate coord;
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
		Matrix<Long> matrix = new HashedMatrix<>();
		matrix.addRow();

		Coordinate coord = getCoordInstance(curCoordClass, matrix, 0, 0);
		
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
		Matrix<Long> matrix = new HashedMatrix<>();
		matrix.addRow();
		
		Coordinate coordOne = getCoordInstance(curCoordClass, matrix, 0, 0);
		Coordinate coordTwo = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		assertTrue(coordOne.equals(coordTwo));
		
		matrix.addRow();
		coordOne.setY(1);
		
		assertFalse(coordOne.equals(coordTwo));
		assertFalse(coordOne.equals(null));
		assertFalse(coordOne.equals(new Object()));
	}
	
	@Test
	public void testCoordinateClone() throws Throwable {
		Matrix<Long> matrix = new HashedMatrix<>();
		matrix.addRow();
		
		Coordinate coord = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		Coordinate clone = coord.clone();
		assertTrue(coord.equals(clone));
		assertFalse(coord == clone);
	}
	
	@Test
	public void testCoordinateIsStillOnMatrix() throws Throwable {
		Matrix<Long> matrix = new HashedMatrix<>();
		matrix.grow(3);
		
		Coordinate coord = getCoordInstance(curCoordClass, matrix, 2, 2);
		
		assertTrue(coord.stillOnMatrix());
		
		matrix.addRow();
		
		assertTrue(coord.stillOnMatrix());
		
		matrix.trim(3,3);
		
		assertFalse(coord.stillOnMatrix());
	}
	
	@Test
	public void testCoordinateOther() throws Throwable {
		Matrix<Long> matrix = new HashedMatrix<>();
		Matrix<Long> matrixTwo = new HashedMatrix<>();
		matrix.addRow();
		matrixTwo.addRow();
		
		Coordinate coord = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		LOGGER.debug("toString: \"{}\", Hash: \"{}\"", coord, coord.hashCode());
		
		Coordinate coordTwo = getCoordInstance(curCoordClass, matrix, 0, 0);
		
		assertTrue(coord.isOnSameMatrix(coordTwo));
		
		coordTwo = getCoordInstance(curCoordClass, matrixTwo, 0, 0);
		assertFalse(coord.isOnSameMatrix(coordTwo));
	}
}
