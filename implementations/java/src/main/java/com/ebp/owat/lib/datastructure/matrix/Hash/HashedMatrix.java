package com.ebp.owat.lib.datastructure.matrix.Hash;

import com.ebp.owat.lib.datastructure.matrix.ScramblingMatrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;

/**
 * Matrix created by inserting elements into a {@link java.util.HashMap}, with a {@link com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate Coordinate} as the key for the value.
 *
 * Best used for smaller matrices.
 *
 * Created by Greg Stewart on 10/15/17.
 *
 * @param <T> The type of object this matrix holds.
 */
public class HashedMatrix<T>  extends ScramblingMatrix<T> {
	
	/**
	 * The map of values the matrix holds.
	 */
	private HashMap<Coordinate, T> valueMap = new HashMap<>();
	
	/**
	 * Constructor to set if this matrix is a scrambling one.
	 *
	 * @param type If this is a scrambling matrix or a de-scrambling one
	 */
	public HashedMatrix(Type type) {
		super(type);
	}
	
	/**
	 * Adds a row to the matrix. Will be added to the right of the existing matrix.
	 */
	@Override
	public void addRow() {
		this.numRows++;
	}
	
	/**
	 * Adds rows to the matrix until capacity is added for all the values given to be added, and the values are added. Values are added top-down on the new rows.
	 *
	 * @param valuesIn The values to add to the rows.
	 * @return If all the rows added were added completely by the values given.
	 */
	@Override
	public boolean addRows(Collection<T> valuesIn) {
		//TODO
		return false;
	}
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 *
	 * @param numRows The number of rows to add.
	 */
	@Override
	public void addRows(long numRows) {
		if(numRows < 0){
			throw new IllegalArgumentException("Cannot add a negative number of rows.");
		}
		this.numRows += numRows;
	}
	
	/**
	 * Adds a column to the matrix. Will be added to the bottom of the existing matrix.
	 */
	@Override
	public void addCol() {
		this.numCols++;
	}
	
	/**
	 * Adds columns to the matrix until capacity is added for all the values given to be added, and the values are added to those rows. Values are added left to right to the new columns.
	 *
	 * @param valuesIn The values to add to the rows.
	 * @return If all the columns added were added completely by the values given.
	 */
	@Override
	public void addCols(Collection<T> valuesIn) {
		//TODO
	}
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 *
	 * @param numCols The number of columns to add.
	 */
	@Override
	public void addCols(long numCols) {
		if(numCols < 0){
			throw new IllegalArgumentException("Cannot add a negative number of rows.");
		}
		this.numCols += numCols;
	}
	
	/**
	 * Adds rows and columns until all the items in the collection are added.
	 * <p>
	 * Starts by adding a column, then a row, and keeps switching between adding cols and rows.
	 *
	 * @param valuesIn The values to add.
	 */
	@Override
	public void addRowsCols(Collection<T> valuesIn) {
		//TODO
	}
	
	/**
	 * Removes a row from this matrix.
	 *
	 * @param rowIndex
	 * @return The elements that comprised the row.
	 * @throws IndexOutOfBoundsException If the row index given is out of bounds.
	 */
	@Override
	public List<T> removeRow(long rowIndex) throws IndexOutOfBoundsException {
		//TODO
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
	public List<T> removeCol(long colIndex) throws IndexOutOfBoundsException {
		//TODO
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
	public T replaceNode(Coordinate nodeToReplace, T newValue) {
		//TODO
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
	public List<T> replaceRow(long rowIndex, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
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
	public List<T> replaceCol(long colIndex, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
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
	public T get(long xIn, long yIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets a value from this matrix.
	 *
	 * @param coordIn The coordinate to use. Coordinate must be on this matrix.
	 * @return The value at the coordinate given.
	 */
	@Override
	public T get(Coordinate coordIn) {
		//TODO
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
	public List<T> getCol(long xIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the column from.
	 * @return The list of elements in the column specified.
	 */
	@Override
	public List<T> getCol(Coordinate coordIn) {
		//TODO
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
	public List<T> getRow(long yIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the row from.
	 * @return The list of elements in the row specified.
	 */
	@Override
	public List<T> getRow(Coordinate coordIn) {
		//TODO
		return null;
	}
	
	/**
	 * Gets this matrix represented as a two dimensional matrix.
	 *
	 * @return This matrix as a 2d array.
	 */
	@Override
	public Object[][] to2dArray() {
		//TODO
		return new Object[0][];
	}
}
