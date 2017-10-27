package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import org.junit.Assert;
import org.junit.experimental.theories.DataPoints;
import org.junit.experimental.theories.Theories;
import org.junit.experimental.theories.Theory;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import testModels.structure.matrix.TestMatrix;

import java.lang.reflect.Constructor;
import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

/**
 * Tests the Coordinate object.
 *
 * Created by Greg Stewart on 3/30/17.
 */
@RunWith(Theories.class)
public class CoordinateTest {
	private Logger LOGGER = LoggerFactory.getLogger(CoordinateTest.class);
	
	@DataPoints
	public static List<Class<? extends Coordinate>> exceptionsToTest = Arrays.asList(
			Coordinate.class
			//, LinkedNodePos.class // can't test, presumably due to type issues
	);
	
	private Coordinate getCoordInstance(Class<? extends Coordinate> curClass, Matrix m, long x, long y){
		try {
			return curClass.getConstructor(Matrix.class, Long.TYPE, Long.TYPE).newInstance(m, x, y);
		} catch (Throwable e) {
			throw new RuntimeException("Unable to instantiate Coordinate.", e);
		}
	}
	
	@Theory
	public void testCoordinateConstructors(Class<? extends Coordinate> curClass) throws Throwable {
		Constructor<? extends Coordinate> constBase = curClass.getConstructor(Matrix.class);
		Constructor<? extends Coordinate> constCoords = curClass.getConstructor(Matrix.class, Long.TYPE, Long.TYPE);
		
		Matrix<Long> matrix = new TestMatrix();
		
		Coordinate coord;
		
		try {
			coord = constBase.newInstance(matrix);
			Assert.fail();
		} catch (Throwable e) {
			if (!(e.getCause() instanceof IllegalStateException)) {
				Assert.fail();
			}
		}
		
		try {
			coord = constCoords.newInstance(matrix, 0, 0);
			Assert.fail();
		} catch (Throwable e) {
			if (!(e.getCause() instanceof IllegalStateException)) {
				Assert.fail();
			}
		}
		
		matrix.addRow();
		coord = constBase.newInstance(matrix);
		coord = constCoords.newInstance(matrix, 0, 0);
	}
	
	
	@Theory
	public void testCoordinateSettersGetters(Class<? extends Coordinate> curClass) throws Throwable {
		Matrix<Long> matrix = new TestMatrix();
		matrix.addRow();

		Coordinate coord = this.getCoordInstance(curClass, matrix, 0, 0);
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
	
	@Theory
	public void testCoordinateEquals(Class<? extends Coordinate> curClass) throws Throwable {
		Matrix<Long> matrix = new TestMatrix();
		matrix.addRow();
		
		Coordinate coordOne = this.getCoordInstance(curClass, matrix, 0, 0);
		Coordinate coordTwo = this.getCoordInstance(curClass, matrix, 0, 0);
		
		assertTrue(coordOne.equals(coordTwo));
		
		matrix.addRow();
		coordOne.setY(1);
		
		assertFalse(coordOne.equals(coordTwo));
		assertFalse(coordOne.equals(null));
		assertFalse(coordOne.equals(new Object()));
	}
	
	@Theory
	public void testCoordinateClone(Class<? extends Coordinate> curClass) throws Throwable {
		Matrix<Long> matrix = new TestMatrix();
		matrix.addRow();
		
		Coordinate coord = this.getCoordInstance(curClass, matrix, 0, 0);
		
		Coordinate clone = coord.clone();
		assertTrue(coord.equals(clone));
		assertFalse(coord == clone);
	}
	
	@Theory
	public void testCoordinateOther(Class<? extends Coordinate> curClass) throws Throwable {
		Matrix<Long> matrix = new TestMatrix();
		matrix.addRow();
		
		Coordinate coord = this.getCoordInstance(curClass, matrix, 0, 0);
		
		LOGGER.debug("toString: \"{}\", Hash: \"{}\"", coord, coord.hashCode());
		
		Coordinate coordTwo = this.getCoordInstance(curClass, matrix, 0, 0);
		
		
		assertTrue(coord.isOnSameMatrix(coordTwo));
	}
	
}
