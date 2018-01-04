package com.ebp.owat.lib.datastructure.matrix;

import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.NoSuchElementException;

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
 * @param <T> The type of value this matrix holds.
 */
public abstract class Matrix<T> implements Iterable<T> {
	/** The number of rows held by this object. */
	protected long numRows = 0L;
	/** The number of columns held by this object. */
	protected long numCols = 0L;
	
	/** The number of elements held by this object. I.E., the number of nodes that are not null. */
	protected long numElementsHeld = 0L;
	
	/**
	 * The default value to set new elements where values are not specified.
	 */
	protected T defaultValue = null;
	
	/**
	 * Sets the {@link #defaultValue default value}.
	 * @param valIn The value to set.
	 */
	public void setDefaultValue(T valIn){
		this.defaultValue = valIn;
	}
	
	/**
	 * Gets the {@link #defaultValue default value}. Does NOT do a defensive copy.
	 * @return {@link #defaultValue default value}
	 */
	public T getDefaultValue(){
		return this.defaultValue;
	}
	
	/**
	 * Adds a row to the matrix. Will be added below the existing matrix.
	 */
	public abstract void addRow();
	
	/**
	 * Adds rows to the matrix until capacity is added for all the values given to be added, and the values are added. Values are added top-down on the new rows.
	 * @param valuesIn The values to add to the rows.
	 * @return If all the new rows added were filled completely by the values given.
	 */
	public abstract boolean addRows(Collection<T> valuesIn);
	
	/**
	 * Adds the number of rows specified. Adds rows below the existing matrix.
	 * @param numRows The number of rows to add.
	 */
	public abstract void addRows(long numRows);
	
	/**
	 * Adds a column to the matrix. Will be added to the right of the existing matrix.
	 */
	public abstract void addCol();
	
	/**
	 * Adds columns to the matrix until capacity is added for all the values given to be added, and the values are added to those rows. Values are added left to right to the new columns.
	 * @param valuesIn The values to add to the rows.
	 * @return If all the new columns added were filled completely by the values given.
	 */
	public abstract boolean addCols(Collection<T> valuesIn);
	
	/**
	 * Adds the number of columns specified. Adds columns to the right of the existing matrix.
	 * @param numCols The number of columns to add.
	 */
	public abstract void addCols(long numCols);
	
	/**
	 * Adds rows and columns until all the items in the collection are added.
	 *
	 * Starts by adding a column, then a row, and keeps switching between adding cols and rows.
	 *
	 * @param valuesIn The values to add.
	 * @return If the collection given fully fills out the rows and columns added.
	 */
	public boolean grow(Collection<T> valuesIn){
		//TODO
		return false;
	}
	
	/**
	 * Grows the matrix my the numbers specified.
	 * @param numCols The number of columns to expand the matrix by.
	 * @param numRows The number of rows to expand the matrix by.
	 */
	public void grow(long numCols, long numRows){
		boolean startEmpty = !this.hasRowsCols();
		this.addCols(numCols);
		this.addRows(numRows - (startEmpty ? 1L : 0L));
	}
	
	/**
	 * Grows the matrix my the number specified.
	 * @param numRowCols The number of rows and columns to expand the matrix by.
	 */
	public void grow(long numRowCols){
		this.grow(numRowCols, numRowCols);
	}
	
	/**
	 * Removes a row from this matrix.
	 * @return The elements that comprised the row.
	 * @throws IndexOutOfBoundsException If the row index given is out of bounds.
	 */
	public abstract List<T> removeRow(long rowIndex) throws IndexOutOfBoundsException;
	
	/**
	 * Removes a column from this matrix.
	 * @return The elements that comprised the column.
	 * @throws IndexOutOfBoundsException If the column index given is out of bounds.
	 */
	public abstract List<T> removeCol(long colIndex) throws IndexOutOfBoundsException;
	
	/**
	 * Replaces a particular node's value. Not to be used to remove a node by setting it's value to null or the {@link Matrix#defaultValue}
	 * @param nodeToReplace The coordinate of the node to replace.
	 * @param newValue The value to replace.
	 * @return The previously held value.
	 */
	public abstract T setValue(Coordinate nodeToReplace, T newValue);
	
	/**
	 * Sets a value based on its x/y coordinates. This implementation creates a new {@link Coordinate} and passes it to the other method ({@link Matrix#setValue(Coordinate, Object)})
	 * @param xIn The x index (column) of the spot to set.
	 * @param yIn The y index (row) of the spot to set.
	 * @param newValue The value to set the spot to.
	 * @return The previously held value.
	 */
	public T setValue(long xIn, long yIn, T newValue){
		return this.setValue(
			new Coordinate(this, xIn, yIn),
			newValue
		);
	}
	
	/**
	 * Replaces a row of values.
	 * @param rowIndex The index of the row to replace.
	 * @param newValues The values to use to replace the row.
	 * @return The values this method replaced. Ordered top to bottom.
	 */
	public abstract List<T> replaceRow(long rowIndex, Collection<T> newValues) throws IndexOutOfBoundsException;
	
	/**
	 * Replaces a column of values.
	 * @param colIndex The index of the column to replace.
	 * @param newValues The values to use to replace the column.
	 * @return The values this method replaced. Ordered left to right.
	 */
	public abstract List<T> replaceCol(long colIndex, Collection<T> newValues) throws IndexOutOfBoundsException;
	
	/**
	 * Trims the matrix to a given size. Trims from the largest indexes in.
	 * @param numCols The number of columns to remove.
	 * @param numRows The number of rows to remove.
	 */
	public void trim(long numCols, long numRows){
		for(long i = 0; i < numCols; i++){
			this.removeCol(this.getNumCols() - 1);
		}
		
		for(long i = 0; i < numRows; i++){
			this.removeRow(this.getNumRows() - 1);
		}
	}
	
	/**
	 * Determines if the column index given is valid or not.
	 * @param colNumIn The column number to determine if it is valid.
	 * @return If the number given if a valid column number or not.
	 */
	public boolean isValidColIndex(long colNumIn){
		return (colNumIn > -1) && ((this.numCols -1) >= colNumIn);
	}
	
	/**
	 * Determines if the row index given is valid or not.
	 * @param rowNumIn The row number to determine if it is valid.
	 * @return If the number given if a valid row number or not.
	 */
	public boolean isValidRowIndex(long rowNumIn){
		return (rowNumIn > -1) && ((this.numRows -1) >= rowNumIn);
	}
	
	/**
	 * Determines if this matrix holds any elements.
	 *
	 * Equivalent to calling {@link Matrix#numElements() numElements()} > 0
	 *
	 * @return If this matrix holds any elements.
	 */
	public boolean isEmpty(){
		return this.numElements() == 0;
	}
	
	/**
	 * Determines if the matrix is full.
	 *
	 * Definition of full: All spaces in the matrix hold a value.
	 *
	 * @return If the matrix is full.
	 */
	public boolean isFull(){
		return this.size() == this.numElements();
	}
	
	/**
	 * Gets the number of columns held by this matrix.
	 * @return The number of columns held by this matrix.
	 */
	public long getNumCols(){
		return this.numCols;
	}
	
	/**
	 * Gets the number of rows held by this matrix.
	 * @return The number of rows held by this matrix.
	 */
	public long getNumRows(){
		return this.numRows;
	}
	
	/**
	 * Gets the number of spots in this matrix to hold elements.
	 *
	 * @return The number of spots in this matrix to hold elements.
	 */
	public long size(){
		return this.getNumCols() * this.getNumRows();
	}
	
	/**
	 * Determines if this matrix actually has rows and columns.
	 * @return If this matrix actually has rows and columns.
	 */
	public boolean hasRowsCols(){
		return this.size() > 0;
	}
	
	/**
	 * Gets the number of elements actually held in the matrix.
	 * @return The number of elements in the matrix.
	 */
	public long numElements(){
		return this.numElementsHeld;
	}
	
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
	 * Returns a basic functional iterator. Does this by using {@link #get(long, long)}. Should work, but may not be efficient for your implementation by any means. Recommended to override in implementation if this would be a terribly inefficient way to iterate.
	 * <br />
	 *
	 * Iterates by going right->left, top->bottom. So, a row would be returned starting the left side, going right, continuing to the next row, and so on.
	 *
	 * @return A basic functional iterator.
	 */
	@Override
	public MatrixIterator<T> iterator() {
		return new MatrixIterator<T>() {
			
			@Override
			public boolean hasNext() {
				return isValidRowIndex(this.curRow);
			}
			
			@Override
			public T next() {
				if(!isValidColIndex(curCol + 1)){
					curRow++;
					curCol = -1;//-1 so we can increment before returning
				}
				
				if(!isValidRowIndex(this.curRow)){
					throw new NoSuchElementException("No more to iterate through.");
				}
				
				curCol++;
				return get(curCol, curRow);
			}
		};
	}
	
	/**
	 * Gets this matrix represented as a two dimensional matrix.
	 * @return This matrix as a 2d array.
	 */
	public Object[][] to2dArray(){
		Object[][] output = new Object[(int)this.getNumRows()][(int)this.getNumCols()];
		
		MatrixIterator<T> it = this.iterator();
		
		int curCol = -1;
		int curRow = 0;
		while (it.hasNext()){
			if(it.onNewRow()){
				curRow++;
				curCol = 0;
			}
			output[curRow][curCol] = it.next();
		}
		return output;
	}
}
