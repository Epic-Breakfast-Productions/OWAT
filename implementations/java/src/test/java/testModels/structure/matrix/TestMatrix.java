package testModels.structure.matrix;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

import java.util.Collection;
import java.util.List;

public class TestMatrix extends Matrix<Long> {
	/**
	 * Adds a row to the matrix. Will be added to the right of the existing matrix.
	 */
	@Override
	public void addRow() {
		if(this.numCols == 0){
			this.numCols++;
		}
		this.numRows++;
	}
	
	/**
	 * Adds rows to the matrix until capacity is added for all the values given to be added, and the values are added. Values are added top-down on the new rows.
	 *
	 * @param valuesIn The values to add to the rows.
	 * @return If all the rows added were added completely by the values given.
	 */
	@Override
	public boolean addRows(Collection<Long> valuesIn) {
		return false;
	}
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 *
	 * @param numRows The number of rows to add.
	 */
	@Override
	public void addRows(long numRows) {
	
	}
	
	/**
	 * Adds a column to the matrix. Will be added to the bottom of the existing matrix.
	 */
	@Override
	public void addCol() {
		if(numRows == 0){
			this.numRows++;
		}
		this.numCols++;
	}
	
	/**
	 * Adds columns to the matrix until capacity is added for all the values given to be added, and the values are added to those rows. Values are added left to right to the new columns.
	 *
	 * @param valuesIn The values to add to the rows.
	 * @return If all the columns added were added completely by the values given.
	 */
	@Override
	public void addCols(Collection<Long> valuesIn) {
	
	}
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 *
	 * @param numCols The number of columns to add.
	 */
	@Override
	public void addCols(long numCols) {
	
	}
	
	/**
	 * Adds rows and columns until all the items in the collection are added.
	 * <p>
	 * Starts by adding a column, then a row, and keeps switching between adding cols and rows.
	 *
	 * @param valuesIn The values to add.
	 */
	@Override
	public void addRowsCols(Collection<Long> valuesIn) {
	
	}
	
	/**
	 * Removes a row from this matrix.
	 *
	 * @param rowIndex
	 * @return The elements that comprised the row.
	 * @throws IndexOutOfBoundsException If the row index given is out of bounds.
	 */
	@Override
	public List<Long> removeRow(long rowIndex) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Removes a column from this matrix.
	 *
	 * @param colIndex
	 * @return The elements that comprised the column.
	 * @throws IndexOutOfBoundsException If the column index given is out of bounds.
	 */
	@Override
	public List<Long> removeCol(long colIndex) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Replaces a particular node's value.
	 *
	 * @param nodeToReplace The coordinate of the node to replace.
	 * @param newValue      The value to replace.
	 * @return The previously held value.
	 */
	@Override
	public Long setValue(Coordinate nodeToReplace, Long newValue) {
		return null;
	}
	
	/**
	 * Replaces a row of values.
	 *
	 * @param rowIndex  The index of the row to replace.
	 * @param newValues The values to use to replace the row.
	 * @return The values this method replaced. Ordered top to bottom.
	 */
	@Override
	public List<Long> replaceRow(long rowIndex, Collection<Long> newValues) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Replaces a column of values.
	 *
	 * @param colIndex  The index of the column to replace.
	 * @param newValues The values to use to replace the column.
	 * @return The values this method replaced. Ordered left to right.
	 */
	@Override
	public List<Long> replaceCol(long colIndex, Collection<Long> newValues) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Gets a value from this matrix.
	 *
	 * @param xIn The x index of the node to get
	 * @param yIn The y index of the node to get
	 * @return The value at the point given.
	 * @throws IndexOutOfBoundsException If either of the indexes are out of bounds.
	 */
	@Override
	public Long get(long xIn, long yIn) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Gets a value from this matrix.
	 *
	 * @param coordIn The coordinate to use. Coordinate must be on this matrix.
	 * @return The value at the coordinate given.
	 */
	@Override
	public Long get(Coordinate coordIn) {
		return null;
	}
	
	/**
	 * Gets a column of this matrix.
	 *
	 * @param xIn The index of the column to get.
	 * @return The list of elements in the column.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	@Override
	public List<Long> getCol(long xIn) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the column from.
	 * @return The list of elements in the column specified.
	 */
	@Override
	public List<Long> getCol(Coordinate coordIn) {
		return null;
	}
	
	/**
	 * Gets a row of this matrix.
	 *
	 * @param yIn The index of the column to get.
	 * @return The list of elements in the column.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	@Override
	public List<Long> getRow(long yIn) throws IndexOutOfBoundsException {
		return null;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the row from.
	 * @return The list of elements in the row specified.
	 */
	@Override
	public List<Long> getRow(Coordinate coordIn) {
		return null;
	}
	
	/**
	 * Gets this matrix represented as a two dimensional matrix.
	 *
	 * @return This matrix as a 2d array.
	 */
	@Override
	public Object[][] to2dArray() {
		return new Object[0][];
	}
}
