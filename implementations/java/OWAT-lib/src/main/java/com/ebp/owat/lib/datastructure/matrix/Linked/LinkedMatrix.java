package com.ebp.owat.lib.datastructure.matrix.Linked;

import com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition.NodePosition;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LinkedMatrix<T> extends Matrix<T> {
	/** The number of elements held in the matrix. */
	protected long numElementsHeld = 0;

	protected Collection<NodePosition> referenceNodes = new LinkedList<>();









	@Override
	public void addRow() {
		//TODO
	}

	@Override
	public boolean addRows(Collection<T> valuesIn) {
		//TODO
		return false;
	}

	@Override
	public void addRows(long numRows) {
		//TODO
	}

	@Override
	public void addCol() {
		//TODO
	}

	@Override
	public boolean addCols(Collection<T> valuesIn) {
		//TODO
		return false;
	}

	@Override
	public void addCols(long numCols) {
		//TODO
	}

	@Override
	public List<T> removeRow(long rowIndex) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> removeCol(long colIndex) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public T setValue(Coordinate nodeToReplace, T newValue) {
		//TODO
		return null;
	}

	@Override
	public boolean hasValue(Coordinate node) {
		//TODO
		return false;
	}

	@Override
	public T clearNode(Coordinate nodeToClear) {
		//TODO
		return null;
	}

	@Override
	public List<T> replaceRow(Coordinate coordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> replaceCol(Coordinate coordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public long numElements() {
		//TODO
		return this.numElementsHeld;
	}

	@Override
	public T get(Coordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public List<T> getCol(long xIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> getCol(Coordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public List<T> getRow(long yIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> getRow(Coordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public Matrix<T> getSubMatrix(Coordinate topLeft, long height, long width) {
		//TODO
		return null;
	}

	@Override
	public void replaceSubMatrix(Matrix<T> matrix, Coordinate topLeft, long height, long width) {
		//TODO
	}
}
