package com.ebp.owat.lib.datastructure.matrix.hash;

import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;
import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * Matrix created by inserting elements into a {@link java.util.HashMap}, with a {@link MatrixCoordinate MatrixCoordinate} as the key for the value.
 * <p>
 * Best used for smaller matrices.
 * <p>
 * Created by Greg Stewart on 10/15/17.
 *
 * @param <T> The type of object this matrix holds.
 */
public class HashedMatrix<T>  extends Matrix<T> {

	/**
	 * The map of values the matrix holds.
	 */
	protected HashMap<MatrixCoordinate, T> valueMap = new HashMap<>();

	@Override
	public void addRow() {
		if(this.numCols == 0){
			this.numCols++;
		}
		this.numRows++;
	}

	@Override
	public void addCol() {
		if(this.numRows == 0){
			this.numRows++;
		}
		this.numCols++;
	}

	@Override
	public List<T> removeRow(){
		if(!this.hasRowsCols()){
			return null;
		}
		long rowIndex = this.getNumRows() - 1;
		List<T> removedItems = this.getRow(rowIndex);
		
		this.numRows--;
		if(this.numRows == 0){
			this.numCols = 0;
			this.valueMap.clear();
		}else {
			List<MatrixCoordinate> coordsToMove = new LongLinkedList<>();
			List<MatrixCoordinate> coordsToRemove = new LongLinkedList<>();
			
			for(Map.Entry<MatrixCoordinate, T> curEntry : this.valueMap.entrySet()){
				if(curEntry.getKey().getRow() > rowIndex){
					coordsToMove.add(curEntry.getKey());
				}else if(curEntry.getKey().getRow() == rowIndex){
					coordsToRemove.add(curEntry.getKey());
				}
			}
			
			for(MatrixCoordinate curCoord : coordsToRemove) {
				this.valueMap.remove(curCoord);
			}
			
			for(MatrixCoordinate curCoord : coordsToMove){
				T curVal = this.valueMap.remove(curCoord);
				
				curCoord.setY(curCoord.getRow() - 1);
				this.valueMap.put(curCoord, curVal);
			}
		}
		
		return removedItems;
	}

	@Override
	public List<T> removeCol(){
		if(!this.hasRowsCols()){
			return null;
		}
		long colIndex = this.getNumCols() - 1;
		List<T> removedItems = this.getCol(colIndex);

		this.numCols--;
		if(this.numCols == 0){
			this.numRows = 0;
			this.valueMap.clear();
		}else {
			List<MatrixCoordinate> coordsToMove = new LongLinkedList<>();
			List<MatrixCoordinate> coordsToRemove = new LongLinkedList<>();
			
			for(Map.Entry<MatrixCoordinate, T> curEntry : this.valueMap.entrySet()){
				if(curEntry.getKey().getCol() > colIndex){
					coordsToMove.add(curEntry.getKey());
				}else if(curEntry.getKey().getCol() == colIndex){
					coordsToRemove.add(curEntry.getKey());
				}
			}
			
			for(MatrixCoordinate curCoord : coordsToRemove) {
				this.valueMap.remove(curCoord);
			}
			
			for(MatrixCoordinate curCoord : coordsToMove){
				T curVal = this.valueMap.remove(curCoord);
				
				curCoord.setX(curCoord.getCol() - 1);
				this.valueMap.put(curCoord, curVal);
			}
		}
		return removedItems;
	}
	
	@Override
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		MatrixValidator.throwIfNotOnMatrix(this, nodeToReplace);
		T valToReturn = this.get(nodeToReplace);
		
		this.valueMap.put(nodeToReplace, newValue);
		return valToReturn;
	}
	
	@Override
	public boolean hasValue(MatrixCoordinate node){
		return this.valueMap.containsKey(node);
	}
	
	@Override
	public T clearNode(MatrixCoordinate nodeToClear) {
		T clearedVal = this.get(nodeToClear);
		
		if(this.valueMap.containsKey(nodeToClear)){
			this.valueMap.remove(nodeToClear);
		}
		
		return clearedVal;
	}

	@Override
	public long numElements() {
		return this.valueMap.size();
	}

	@Override
	public T get(MatrixCoordinate coordIn) {
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);
		return this.valueMap.getOrDefault(coordIn, this.defaultValue);
	}

	@Override
	public List<T> getCol(MatrixCoordinate coordIn){
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);
		LinkedList<T> output = new LinkedList<>();

		for(long i = 0; i < this.getNumRows(); i++){
			output.addLast(this.get(coordIn.getX(), i));
		}

		return output;
	}

	@Override
	public List<T> getRow(MatrixCoordinate coordIn){
		MatrixValidator.throwIfNotOnMatrix(this, coordIn);
		LinkedList<T> output = new LinkedList<>();

		for(long i = 0; i < this.getNumCols(); i++){
			output.addLast(this.get(i, coordIn.getY()));
		}

		return output;
	}

	@Override
	protected Matrix<T> getNewInstance() {
		return new HashedMatrix<>();
	}

}
