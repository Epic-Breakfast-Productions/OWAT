package com.ebp.owat.lib.datastructure.matrix.Hash;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.Plane;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.*;

/**
 * Matrix created by inserting elements into a {@link java.util.HashMap}, with a {@link com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate Coordinate} as the key for the value.
 *
 * Best used for smaller matrices.
 *
 * Created by Greg Stewart on 10/15/17.
 *
 * @param <T> The type of object this matrix holds.
 */
public class HashedMatrix<T>  extends Matrix<T> {
	
	/**
	 * The map of values the matrix holds.
	 */
	protected HashMap<Coordinate, T> valueMap = new HashMap<>();
	
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
	public boolean addRows(Collection<T> valuesIn) {
		Queue<T> valuesToAdd = new LinkedList<>(valuesIn);
		
		long curRow = this.getNumRows();
		
		while(!valuesToAdd.isEmpty()){
			this.addRow();
			for(long i = 0; i < this.getNumCols(); i++){
				//add values to new row
				if(valuesToAdd.isEmpty()){
					return false;
				}
				this.setValue(i, curRow, valuesToAdd.poll());
			}
			curRow++;
		}
		return true;
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
		if(this.numCols == 0){
			this.numCols++;
		}
		this.numRows += numRows;
	}
	
	/**
	 * Adds a column to the matrix. Will be added to the bottom of the existing matrix.
	 */
	@Override
	public void addCol() {
		if(this.numRows == 0){
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
	public boolean addCols(Collection<T> valuesIn) {
		Queue<T> valuesToAdd = new LinkedList<>(valuesIn);
		
		long curCol = this.getNumCols();
		
		while(!valuesToAdd.isEmpty()){
			this.addCol();
			for(long i = 0; i < this.getNumRows(); i++){
				//add values to new row
				if(valuesToAdd.isEmpty()){
					return false;
				}
				this.setValue(curCol, i, valuesToAdd.poll());
			}
			curCol++;
		}
		return true;
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
		if(this.numRows == 0){
			this.numRows++;
		}
		this.numCols += numCols;
	}
	
	/**
	 * Removes a row from this matrix.
	 *
	 * @param rowIndex The index of the row to remove.
	 * @return The elements that comprised the row.
	 * @throws IndexOutOfBoundsException If the row index given is out of bounds.
	 */
	@Override
	public List<T> removeRow(long rowIndex) throws IndexOutOfBoundsException {
		MatrixValidator.throwIfBadIndex(this, rowIndex, Plane.Y);
		
		List<T> removedItems = this.getRow(rowIndex);
		
		for(T curElement : removedItems){
			if(curElement != this.defaultValue) {
				this.numElementsHeld--;
			}
		}
		
		this.numRows--;
		if(this.numRows == 0){
			this.numCols = 0;
			this.numElementsHeld = 0;
			this.valueMap.clear();
		}else {
			List<Coordinate> coordsToMove = new LongLinkedList<>();
			List<Coordinate> coordsToRemove = new LongLinkedList<>();
			
			for(Map.Entry<Coordinate, T> curEntry : this.valueMap.entrySet()){
				if(curEntry.getKey().getRow() > rowIndex){
					coordsToMove.add(curEntry.getKey());
				}else if(curEntry.getKey().getRow() == rowIndex){
					coordsToRemove.add(curEntry.getKey());
				}
			}
			
			for(Coordinate curCoord : coordsToRemove) {
				this.valueMap.remove(curCoord);
			}
			
			for(Coordinate curCoord : coordsToMove){
				T curVal = this.valueMap.remove(curCoord);
				
				curCoord.setY(curCoord.getRow() - 1);
				this.valueMap.put(curCoord, curVal);
			}
		}
		
		return removedItems;
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
		MatrixValidator.throwIfBadIndex(this, colIndex, Plane.X);
		
		List<T> removedItems = this.getCol(colIndex);
		
		for(T curElement : removedItems){
			if(curElement != this.defaultValue) {
				this.numElementsHeld--;
			}
		}
		
		this.numCols--;
		if(this.numCols == 0){
			this.numRows = 0;
			this.numElementsHeld = 0;
			this.valueMap.clear();
		}else {
			List<Coordinate> coordsToMove = new LongLinkedList<>();
			List<Coordinate> coordsToRemove = new LongLinkedList<>();
			
			for(Map.Entry<Coordinate, T> curEntry : this.valueMap.entrySet()){
				if(curEntry.getKey().getCol() > colIndex){
					coordsToMove.add(curEntry.getKey());
				}else if(curEntry.getKey().getCol() == colIndex){
					coordsToRemove.add(curEntry.getKey());
				}
			}
			
			for(Coordinate curCoord : coordsToRemove) {
				this.valueMap.remove(curCoord);
			}
			
			for(Coordinate curCoord : coordsToMove){
				T curVal = this.valueMap.remove(curCoord);
				
				curCoord.setX(curCoord.getCol() - 1);
				this.valueMap.put(curCoord, curVal);
			}
		}
		return removedItems;
	}
	
	@Override
	public T setValue(Coordinate nodeToReplace, T newValue) {
		MatrixValidator.throwIfNotOnMatrix(this, nodeToReplace);
		T valToReturn = this.get(nodeToReplace);
		
		if(!this.valueMap.containsKey(nodeToReplace)){
			this.numElementsHeld++;
		}
		
		this.valueMap.put(nodeToReplace, newValue);
		return valToReturn;
	}
	
	@Override
	public boolean hasValue(Coordinate node){
		return this.valueMap.containsKey(node);
	}
	
	@Override
	public T clearNode(Coordinate nodeToClear) {
		T clearedVal = this.get(nodeToClear);
		
		if(this.valueMap.containsKey(nodeToClear)){
			this.numElementsHeld--;
			this.valueMap.remove(nodeToClear);
		}
		
		return clearedVal;
	}
	
	@Override
	public List<T> replaceRow(Coordinate coordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		MatrixValidator.throwIfNotOnMatrix(this, coordinate);
		
		List<T> output = this.getRow(coordinate);
		
		long curCol = 0;
		for(T curVal : newValues){
			Coordinate curCoord = new Coordinate(this, curCol, coordinate.getY());
			boolean hadVal = this.hasValue(curCoord);
			boolean hasNewVal = !this.isDefaultValue(curVal);
			
			if(hasNewVal){
				this.setValue(curCoord, curVal);
			}else if(hadVal){
				this.clearNode(curCoord);
			}
			
			curCol++;
			if(!isValidColIndex(curCol)){
				break;
			}
		}
		
		return output;
	}
	
	@Override
	public List<T> replaceCol(Coordinate coordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		MatrixValidator.throwIfNotOnMatrix(this, coordinate);
		
		List<T> output = this.getCol(coordinate);
		
		long curRow = 0;
		for(T curVal : newValues){
			Coordinate curCoord = new Coordinate(this, coordinate.getX(), curRow);
			boolean hadVal = this.hasValue(curCoord);
			boolean hasNewVal = !this.isDefaultValue(curVal);
			
			if(hasNewVal){
				this.setValue(curCoord, curVal);
			}else if(hadVal){
				this.clearNode(curCoord);
			}
			
			curRow++;
			if(!isValidColIndex(curRow)){
				break;
			}
		}
		
		return output;
	}
	
	/**
	 * Gets a value from this matrix.
	 *
	 * @param coordIn The coordinate to use. Coordinate must be on this matrix.
	 * @return The value at the coordinate given.
	 */
	@Override
	public T get(Coordinate coordIn) {
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);
		return this.valueMap.getOrDefault(coordIn, this.defaultValue);
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
		MatrixValidator.throwIfBadIndex(this, xIn, Plane.X);
		LinkedList<T> output = new LinkedList<>();
		
		for(long i = 0; i < this.getNumRows(); i++){
			output.addLast(this.get(xIn, i));
		}
		
		return output;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the column from.
	 * @return The list of elements in the column specified.
	 */
	@Override
	public List<T> getCol(Coordinate coordIn) {
		return this.getCol(coordIn.getX());
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
		MatrixValidator.throwIfBadIndex(this, yIn, Plane.Y);
		LinkedList<T> output = new LinkedList<>();
		
		for(long i = 0; i < this.getNumCols(); i++){
			output.addLast(this.get(i, yIn));
		}
		
		return output;
	}
	
	/**
	 * Gets the row the coordinate is on.
	 *
	 * @param coordIn The coordinate to get the row from.
	 * @return The list of elements in the row specified.
	 */
	@Override
	public List<T> getRow(Coordinate coordIn) {
		return this.getRow(coordIn.getY());
	}
	
	@Override
	public Matrix<T> getSubMatrix(Coordinate topLeft, long height, long width) {
		MatrixValidator.throwIfNotOnMatrix(this, topLeft);
		MatrixValidator.throwIfBadIndex(this,topLeft.getY() + height - 1, Plane.Y);
		MatrixValidator.throwIfBadIndex(this,topLeft.getX() + width - 1, Plane.X);
		
		Matrix<T> output = new HashedMatrix<>();
		output.grow(height, width);
		
		Coordinate curThisCoord = topLeft.clone();
		for(long curY = 0; curY < height; curY++){
			curThisCoord.setY(curY + topLeft.getY());
			for(long curX = 0; curX < width; curX++){
				Coordinate curOutCoord = new Coordinate(output, curX, curY);
				curThisCoord.setX(curX + topLeft.getX());
				if(this.hasValue(curThisCoord)) {
					output.setValue(curOutCoord, this.get(curThisCoord));
				}
			}
		}
		
		return output;
	}
	
	@Override
	public void replaceSubMatrix(Matrix<T> subMatrix, Coordinate topLeft, long height, long width) {
		MatrixValidator.throwIfNotOnMatrix(this, topLeft);
		MatrixValidator.throwIfBadIndex(this,topLeft.getY() + height - 1, Plane.Y);
		MatrixValidator.throwIfBadIndex(this,topLeft.getX() + width - 1, Plane.X);
		
		Coordinate curThatCoord = new Coordinate(subMatrix);
		for(long curY = 0; curY < height; curY++){
			curThatCoord.setY(curY);
			for(long curX = 0; curX < width; curX++){
				curThatCoord.setX(curX);
				Coordinate curThisCoord = new Coordinate(this, curX + topLeft.getX(), curY + topLeft.getY());
				
				boolean hadVal = this.hasValue(curThisCoord);
				boolean hasNewVal = subMatrix.hasValue(curThatCoord);
				
				if(hasNewVal){
					this.setValue(curThisCoord, subMatrix.get(curThatCoord));
				}else if(hadVal){
					this.clearNode(curThisCoord);
				}
				
			}
		}
	}
}
