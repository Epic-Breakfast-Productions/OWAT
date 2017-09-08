package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.matrix.utils.MatrixNode;
import com.ebp.owat.lib.dataStructure.matrix.utils.coordinate.Coordinate;

import java.util.Collection;
import java.util.List;

/**
 * General Matrix class.
 *
 * For reference:
 *
 * <pre>
 *            Top
 *          (north)
 *         0 1 2 3 ...
 *       0 ---------
 *       1 |+++++++|
 * Left  2 |+++++++|Right
 * (west)3 |+++++++|(east)
 *       . |+++++++|
 *       . ---------
 *       .   Bottom
 *           (south)
 *</pre>
 * {@code}
 *
 *
 *
 * @param <T> The type of value this matrix holds.
 */
public abstract class Matrix<T> implements Iterable<T> {
	/**
	 * The default value to set new elements where values are not specified.
	 */
	protected T defaultValue = null;
	
	/**
	 * Sets the {@link #defaultValue default value}.
	 * @param valIn The value to set.
	 */
	protected void setDefaultValue(T valIn){
		this.defaultValue = valIn;
	}
	
	/**
	 * Adds a row to the matrix. Will be added to the right of the existing matrix.
	 */
	public abstract void addRow();
	
	/**
	 * Adds rows to the matrix until capacity is added for all the values given to be added, and the values are added. Values are added top-down on the new rows.
	 * @param valuesIn The values to add to the rows.
	 * @return If all the rows added were added completely by the values given.
	 */
	public abstract boolean addRows(Collection<T> valuesIn);
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 * @param numRows The number of rows to add.
	 */
	public abstract void addRows(long numRows);
	
	/**
	 * Adds a column to the matrix. Will be added to the bottom of the existing matrix.
	 */
	public abstract void addCol();
	
	/**
	 * Adds columns to the matrix until capacity is added for all the values given to be added, and the values are added to those rows. Values are added left to right to the new columns.
	 * @param valuesIn The values to add to the rows.
	 * @return If all the columns added were added completely by the values given.
	 */
	public abstract void addCols(Collection<T> valuesIn);
	
	/**
	 * Adds the number of rows specified. Adds rows to the right of the existing matrix.
	 * @param numCols The number of columns to add.
	 */
	public abstract void addCols(long numCols);
	
	/**
	 * Adds rows and columns until all the items in the collection are added.
	 *
	 * Starts by adding a column, then a row, and keeps switching between adding cols and rows.
	 *
	 * @param valuesIn The values to add.
	 */
	public abstract void addRowsCols(Collection<T> valuesIn);
	
	//TODO:: row/col removal methods
	
	/**
	 * Determines if the column index given is valid or not.
	 * @param colNumIn The column number to determine if it is valid.
	 * @return If the number given if a valid column number or not.
	 */
	public abstract boolean isValidColIndex(long colNumIn);
	
	/**
	 * Determines if the row index given is valid or not.
	 * @param rowNumIn The row number to determine if it is valid.
	 * @return If the number given if a valid row number or not.
	 */
	public abstract boolean isValidRowIndex(long rowNumIn);
	
	/**
	 * Determines if this matrix holds any elements.
	 *
	 * Equivalent to calling {@link Matrix#numElements() numElements()} > 0
	 *
	 * @return If this matrix holds any elements.
	 */
	public boolean isEmpty(){
		return this.numElements() > 0;
	}
	
	/**
	 * Determines if the matrix is full.
	 *
	 * Definition of full: All spaces in the matrix hold a value.
	 *
	 * @return If the matrix is full.
	 */
	public boolean isFull(){
		return this.size() == numElements();
	}
	
	/**
	 * Gets the number of columns held by this matrix.
	 * @return The number of columns held by this matrix.
	 */
	public abstract long getNumCols();
	
	/**
	 * Gets the number of rows held by this matrix.
	 * @return The number of rows held by this matrix.
	 */
	public abstract long getNumRows();
	
	/**
	 * Gets the number of spots in this matrix to hold elements.
	 *
	 * @return The number of spots in this matrix to hold elements.
	 */
	public long size(){
		return this.getNumCols() * this.getNumRows();
	}
	
	/**
	 * Gets the number of elements actually held in the matrix.
	 * @return The number of elements in the matrix.
	 */
	public abstract long numElements();
	
	/**
	 * Gets a value from this matrix.
	 * @param xIn The x index of the node to get
	 * @param yIn The y index of the node to get
	 * @return The value at the point given.
	 * @throws IndexOutOfBoundsException If either of the indexes are out of bounds.
	 */
	public abstract T get(long xIn, long yIn) throws IndexOutOfBoundsException;
	
	/**
	 * Gets a value from this matrix.
	 * @param coordIn The coordinate to use. Coordinate must be on this matrix.
	 * @return The value at the coordinate given.
	 */
	public abstract T get(Coordinate coordIn);
	
	/**
	 * Gets a column of this matrix.
	 * @param xIn The index of the column to get.
	 * @return The list of elements in the column.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	public abstract List<T> getCol(long xIn) throws IndexOutOfBoundsException;
	
	/**
	 * Gets the row the coordinate is on.
	 * @param coordIn The coordinate to get the column from.
	 * @return The list of elements in the column specified.
	 */
	public abstract List<T> getCol(Coordinate coordIn);
	
	/**
	 * Gets a row of this matrix.
	 * @param yIn The index of the column to get.
	 * @return The list of elements in the column.
	 * @throws IndexOutOfBoundsException If the index given is out of bounds.
	 */
	public abstract List<T> getRow(long yIn) throws IndexOutOfBoundsException;
	
	/**
	 * Gets the row the coordinate is on.
	 * @param coordIn The coordinate to get the row from.
	 * @return The list of elements in the row specified.
	 */
	public abstract List<T> getRow(Coordinate coordIn);
	
	/**
	 * Gets this matrix represented as a two dimensional matrix.
	 * @return This matrix as a 2d array.
	 */
	public abstract Object[][] to2dArray();
	
	//TODO:: basic iterators
}
