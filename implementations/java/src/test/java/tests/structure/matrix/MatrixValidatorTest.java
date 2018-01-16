package tests.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class MatrixValidatorTest extends MatrixTest {
	
	public MatrixValidatorTest(Class<? extends Matrix> curMatrixClass) {
		super(curMatrixClass);
	}
	
	@Test
	public void testIsOnMatrix() throws Exception {
		Matrix m = this.getTestingInstance();
		Matrix n = this.getTestingInstance();
		m.grow(1);
		
		assertTrue(MatrixValidator.isOnMatrix(m, new Coordinate(m )));
		assertFalse(MatrixValidator.isOnMatrix(n, new Coordinate(m )));
	}
	
	@Test
	public void testIsOnSameMatrix() throws Exception {
		Matrix m = this.getTestingInstance();
		Matrix n = this.getTestingInstance();
		m.grow(1);
		n.grow(1);
		
		assertTrue(MatrixValidator.isOnSameMatrix(new Coordinate(m), new Coordinate(m)));
		assertFalse(MatrixValidator.isOnSameMatrix(new Coordinate(m), new Coordinate(n)));
	}
	
	@Test
	public void testAreOnMatrix() throws Exception {
		Matrix m = this.getTestingInstance();
		Matrix n = this.getTestingInstance();
		m.grow(1);
		n.grow(1);
		
		assertTrue(MatrixValidator.areOnMatrix(m, new Coordinate(m), new Coordinate(m)));
		assertFalse(MatrixValidator.areOnMatrix(m, new Coordinate(m), new Coordinate(n)));
		
		try{
			MatrixValidator.areOnMatrix(null, new Coordinate(m), new Coordinate(m));
			Assert.fail();
		}catch (NullPointerException e){
			//nothing to do
		}
	}
	
	@Test
	public void testAreOnSameMatrix() throws Exception {
		Matrix m = this.getTestingInstance();
		Matrix n = this.getTestingInstance();
		m.grow(1);
		n.grow(1);
		
		assertTrue(MatrixValidator.areOnSameMatrix(new Coordinate(m)));
		assertTrue(MatrixValidator.areOnSameMatrix(new Coordinate(m), new Coordinate(m)));
		assertFalse(MatrixValidator.areOnSameMatrix(new Coordinate(m), new Coordinate(n)));
		
		try{
			MatrixValidator.areOnSameMatrix(null, new Coordinate(m), new Coordinate(m));
			Assert.fail();
		}catch (NullPointerException e){
			//nothing to do
		}
	}
	
	@Test
	public void testThrowIfNotOnMatrix() throws Exception {
		Matrix m = this.getTestingInstance();
		Matrix n = this.getTestingInstance();
		m.grow(1);
		n.grow(1);
		
		//TODO:: finish MatrixValidator.throwIfNotOnMatrix();
		
	}
	
	@Test
	public void testThrowIfNotOnSameMatrix() throws Exception {
		//TODO:: do
	}
	
	@Test
	public void testThrowIfBadIndex() throws Exception {
		Matrix m = this.getTestingInstance();
		
		try {
			MatrixValidator.throwIfBadIndex(m, 0, Plane.X);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){}
		
		try {
			MatrixValidator.throwIfBadIndex(m, 0, Plane.Y);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){}
		
		m.grow(1);
		
		MatrixValidator.throwIfBadIndex(m, 0, Plane.X);
		MatrixValidator.throwIfBadIndex(m, 0, Plane.Y);
		try {
			MatrixValidator.throwIfBadIndex(m, 1, Plane.X);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){}
		
		try {
			MatrixValidator.throwIfBadIndex(m, 1, Plane.Y);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){}
		try {
			MatrixValidator.throwIfBadIndex(m, -1, Plane.X);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){}
		
		try {
			MatrixValidator.throwIfBadIndex(m, -1, Plane.Y);
			Assert.fail();
		}catch (IndexOutOfBoundsException e){}
	}
	
	@Test
	public void testThrowIfNoRowsCols() throws Exception {
		Matrix m = this.getTestingInstance();
		
		try{
			MatrixValidator.throwIfNoRowsCols(m);
			Assert.fail();
		}catch (IllegalStateException e){}
		
		m.grow(1);
		MatrixValidator.throwIfNoRowsCols(m);
	}
	
	@Test
	public void testThrowIfHasRowsCols() throws Exception {
		Matrix m = this.getTestingInstance();
		
		MatrixValidator.throwIfHasRowsCols(m);
		m.grow(1);
		try{
			MatrixValidator.throwIfHasRowsCols(m);
			Assert.fail();
		}catch (IllegalStateException e){}
		
	}
}
