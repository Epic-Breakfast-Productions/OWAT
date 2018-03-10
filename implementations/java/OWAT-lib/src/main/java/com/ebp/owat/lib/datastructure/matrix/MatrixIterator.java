package com.ebp.owat.lib.datastructure.matrix;

import java.util.Iterator;

public abstract class MatrixIterator<T> implements Iterator<T> {
	//TODO:: rework to use coord
	protected long curRow = 0;
	protected long curCol = 0;
	
	/**
	 * Determines if we are on the
	 * @return
	 */
	public boolean onNewRow(){
		return this.hasNext() && curCol <= 0;
	}

	public abstract T peekNext();

	public long getCurRow(){
		return curRow;
	}

	public long getCurCol(){
		return curCol;
	}
}
