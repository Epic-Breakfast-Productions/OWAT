package com.ebp.owat.lib.datastructure.matrix.Linked;

import com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition.NodePosition;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

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
	public T setValue(MatrixCoordinate nodeToReplace, T newValue) {
		//TODO
		return null;
	}

	@Override
	public boolean hasValue(MatrixCoordinate node) {
		//TODO
		return false;
	}

	@Override
	public T clearNode(MatrixCoordinate nodeToClear) {
		//TODO
		return null;
	}

	@Override
	public List<T> replaceRow(MatrixCoordinate matrixCoordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> replaceCol(MatrixCoordinate matrixCoordinate, Collection<T> newValues) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public long numElements() {
		//TODO
		return this.numElementsHeld;
	}

	@Override
	public T get(MatrixCoordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public List<T> getCol(long xIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> getCol(MatrixCoordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public List<T> getRow(long yIn) throws IndexOutOfBoundsException {
		//TODO
		return null;
	}

	@Override
	public List<T> getRow(MatrixCoordinate coordIn) {
		//TODO
		return null;
	}

	@Override
	public Matrix<T> getSubMatrix(MatrixCoordinate topLeft, long height, long width) {
		//TODO
		return null;
	}

	@Override
	public void replaceSubMatrix(Matrix<T> matrix, MatrixCoordinate topLeft, long height, long width) {
		//TODO
	}
}
