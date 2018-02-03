package com.ebp.owat.lib.datastructure.matrix;

import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.*;

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
 *       1 |+++++++| <-- rows
 * Left  2 |+++++++|Right
 * (west)3 |+++++++|(east)
 *       . |+++++++|
 *       . ---------
 *       . Bottom ^
 *         (south)|cols
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
	 * Basic Constructor.
	 */
	protected Matrix(){}
	
	/**
	 * Calculates the number of rows and columns needed to hold a particular number of elements.
	 * @param numValues The number of values the matrix will need to hold.
	 * @return The number of rows and columns needed (calculates for a square)
	 */
	protected static long calcSquareSize(long numValues){
		return (long)Math.ceil(Math.sqrt((double)numValues));
	}
	
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
	 * Grows the matrix by the number specified.
	 * @param numRowCols The number of rows and columns to expand the matrix by.
	 */
	public void grow(long numRowCols){
		this.grow(numRowCols, numRowCols);
	}
	
	/**
	 * Grows a matrix to the size given, filling with the data given in a 'row first' manner.
	 *
	 * @param valuesIn The values to fill the matrix with.
	 * @param numRows The number of rows the matrix should have.
	 * @param numCols The number or cols the matrix should have.
	 * @return If the matrix was filled with the values given.
	 * @throws IllegalStateException If this is called and there are already rows or columns. Cannot be done due to the method of filling in the data used.
	 */
	public boolean grow(long numRows, long numCols, Collection<T> valuesIn) throws IllegalStateException{
		MatrixValidator.throwIfHasRowsCols(this);
		this.trimTo(0,0);//ensure
		
		this.addCols(numCols);
		
		//at this point, have 1 row with the # cols we need
		
		Queue<T> valuesToAdd = new LongLinkedList<>(valuesIn);
		boolean firstFowFilled = valuesToAdd.size() >= this.getNumCols();
		//add values to first row
		this.replaceRow(0, collectionFromQueue(valuesToAdd, this.getNumCols()));
		
		//add rows and fill with data for rest
		boolean lastFilled = firstFowFilled;
		for(long i = 1; i < numRows; i++){
			this.addRow();
			if(!valuesToAdd.isEmpty()){
				lastFilled = valuesToAdd.size() == this.getNumCols();
				this.replaceRow(i, collectionFromQueue(valuesToAdd, this.getNumCols()));
			}else{
				lastFilled = false;
			}
		}
		return lastFilled;
	}
	
	/**
	 *
	 * @param valuesIn The values to give the object.
	 * @throws IllegalStateException If the matrix is not empty when the method is called.
	 */
	public boolean grow(Collection<T> valuesIn) throws IllegalStateException{
		long numRowsCols = calcSquareSize(valuesIn.size());
		return this.grow(numRowsCols, numRowsCols, valuesIn);
	}
	
	/**
	 * Grows the matrix using the collection of values given.
	 *
	 * Does this by alternating growing rows and columns until there is nothing else to add.
	 *
	 * @param valuesIn The values to add to the matrix
	 */
	public boolean growAlternating(Collection<T> valuesIn){
		boolean lastResult = false;
		
		Queue<T> valuesLeft = new LongLinkedList<>(valuesIn);
		
		boolean onRow = true;
		while (!valuesLeft.isEmpty()){
			long numToGive = (this.hasRowsCols() ? (onRow ? this.getNumCols() : this.getNumRows()) : 1);
			
			Collection<T> valuesToGive = collectionFromQueue(valuesLeft, numToGive);
			
			if(onRow){
				lastResult = this.addRows(valuesToGive);
			}else{
				lastResult = this.addCols(valuesToGive);
			}
			onRow = !onRow;
		}
		
		return lastResult;
	}
	
	/**
	 * Removes a row from this matrix.
	 * @return The elements that comprised the row.
	 * @throws IndexOutOfBoundsException If the row index given is out of bounds.
	 */
	public abstract List<T> removeRow(long rowIndex) throws IndexOutOfBoundsException;
	
	/**
	 * Removes a single row from this matrix, removes the bottom most, highest indexed row.
	 * @return The elements that comprised the row. Null if empty before removal.
	 */
	public List<T> removeRow(){
		if(this.hasRowsCols()){
			return this.removeRow(this.getNumRows() - 1);
		}
		return null;
	}
	
	/**
	 * Removes a column from this matrix.
	 * @return The elements that comprised the column.
	 * @throws IndexOutOfBoundsException If the column index given is out of bounds.
	 */
	public abstract List<T> removeCol(long colIndex) throws IndexOutOfBoundsException;
	
	/**
	 * Removes a single column from this matrix, removes the rightmost, highest indexed column.
	 * @return The elements that comprised the column. Null if empty before removal.
	 */
	public List<T> removeCol(){
		if(this.hasRowsCols()){
			return this.removeCol(this.getNumCols() - 1);
		}
		return null;
	}
	
	/**
	 * Replaces a particular value's value. Not to be used to remove a value by setting it's value to null or the {@link Matrix#defaultValue}
	 * @param nodeToReplace The coordinate of the value to replace.
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
	 * Determines if the matrix has a value at the given index.
	 * @param node The coordinate of the node.
	 * @return If the node has a value or not.
	 */
	public abstract boolean hasValue(Coordinate node);
	
	/**
	 * Determines if the matrix has a value at the given index.
	 * TODO:: test
	 * @param xIn The x index of the node.
	 * @param yIn The y index of the node.
	 * @return If the node has a value or not.
	 */
	public boolean hasValue(long xIn, long yIn){
		return this.hasValue(
			new Coordinate(this, xIn, yIn)
		);
	}
	
	/**
	 * Clears the value at the coordinate given.
	 * @param nodeToClear The coordinate of the value to clear the value of.
	 * @return The value previously at the value. If nothing set, returns {@link Matrix#defaultValue}
	 */
	public abstract T clearNode(Coordinate nodeToClear);
	
	/**
	 * Clears the value at the coordinate given.
	 * @param xIn The x index of the value to clear the value of.
	 * @param yIn The y index of the value to clear the value of.
	 * @return The value previously at the value. If nothing set, returns {@link Matrix#defaultValue}
	 */
	public T clearNode(long xIn, long yIn){
		return this.clearNode(new Coordinate(this, xIn, yIn));
	}
	
	/**
	 * Replaces a row of values.
	 *
	 * If not enough values given to fill row, only replaces those values it can (starting from the left, 0, working right).
	 *
	 * If given more values than needed to fill row, only the first number of values needed to fill row are used.
	 *
	 * @param coordinate A coordinate of the row to replace.
	 * @param newValues The values to use to replace the row.
	 * @return The values this method replaced. Ordered top to bottom.
	 */
	public abstract List<T> replaceRow(Coordinate coordinate, Collection<T> newValues) throws IndexOutOfBoundsException;
	
	/**
	 * Replaces a row of values.
	 *
	 * If not enough values given to fill row, only replaces those values it can (starting from the left, 0, working right).
	 *
	 * If given more values than needed to fill row, only the first number of values needed to fill row are used.
	 *
	 * @param rowIndex The index of the row to replace.
	 * @param newValues The values to use to replace the row.
	 * @return The values this method replaced. Ordered top to bottom.
	 */
	public List<T> replaceRow(long rowIndex, Collection<T> newValues) throws IndexOutOfBoundsException{
		return this.replaceRow(new Coordinate(this, 0, rowIndex), newValues);
	}
	
	/**
	 * Replaces a column of values.
	 *
	 * If not enough values given to fill column, only replaces those values it can (starting from the top, 0, working down).
	 *
	 * If given more values than needed to fill column, only the first number of values needed to fill column are used.
	 *
	 * @param coordinate The coordinate of the column to replace.
	 * @param newValues The values to use to replace the column.
	 * @return The values this method replaced. Ordered left to right.
	 */
	public abstract List<T> replaceCol(Coordinate coordinate, Collection<T> newValues) throws IndexOutOfBoundsException;
	
	/**
	 * Replaces a column of values.
	 *
	 * If not enough values given to fill column, only replaces those values it can (starting from the top, 0, working down).
	 *
	 * If given more values than needed to fill column, only the first number of values needed to fill column are used.
	 *
	 * @param colIndex The index of the column to replace.
	 * @param newValues The values to use to replace the column.
	 * @return The values this method replaced. Ordered left to right.
	 */
	public List<T> replaceCol(long colIndex, Collection<T> newValues) throws IndexOutOfBoundsException{
		return this.replaceCol(new Coordinate(this, colIndex, 0), newValues);
	}
	
	/**
	 * Trims the matrix by the number of rows and columns given. Trims from the largest indexes in.
	 * @param numCols The number of columns to remove.
	 * @param numRows The number of rows to remove.
	 */
	public void trimBy(long numCols, long numRows){
		for(long i = 0; i < numCols && this.hasRowsCols(); i++){
			this.removeCol();
		}
		for(long i = 0; i < numRows && this.hasRowsCols(); i++){
			this.removeRow();
		}
	}
	
	/**
	 * Trims the matrix to a given size. Trims from the largest indexes in.
	 * @param numCols The number of columns that should remain.
	 * @param numRows The number of rows that should remain.
	 */
	public void trimTo(long numCols, long numRows){
		while(this.getNumCols() > numCols){
			this.removeCol();
		}
		while(this.getNumRows() > numRows){
			this.removeRow();
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
		return !this.isEmpty() && this.size() == this.numElements();
	}
	
	/**
	 * Gets the number of columns held by this matrix.
	 * @return The number of columns held by this matrix.
	 */
	public long getNumCols(){
		return this.numCols;
	}
	
	public long getWidth(){return this.getNumCols();}
	
	/**
	 * Gets the number of rows held by this matrix.
	 * @return The number of rows held by this matrix.
	 */
	public long getNumRows(){
		return this.numRows;
	}
	
	public long getHeight(){return this.getNumRows();}
	
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
	 * @param xIn The x index of the value to get
	 * @param yIn The y index of the value to get
	 * @return The value at the point given.
	 * @throws IndexOutOfBoundsException If either of the indexes are out of bounds.
	 */
	public T get(long xIn, long yIn) throws IndexOutOfBoundsException{
		return this.get(new Coordinate(this, xIn, yIn));
	}
	
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
				if(!this.hasNext()){
					throw new NoSuchElementException("No more to iterate through.");
				}
				T val = get(curCol++, curRow);
				if(!isValidColIndex(curCol)){
					curRow++;
					curCol = 0;//-1 so we can increment before returning
				}
				return val;
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
		
		int curCol = 0;
		int curRow = -1;
		while (it.hasNext()){
			if(it.onNewRow()){
				curRow++;
				curCol = 0;
			}
			output[curRow][curCol++] = it.next();
		}
		return output;
	}
	
	protected Collection<T> collectionFromQueue(Queue<T> queue, long numVals){
		LongLinkedList<T> output = new LongLinkedList<>();
		
		for(long i = 0; i < numVals && !queue.isEmpty(); i++){
			output.addLast(queue.poll());
		}
		return output;
	}
}
