package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.node.Node;

/**
 * Created by stewy on 3/28/17.
 */
public abstract class ScramblingMatrix<T extends Node> extends Matrix{
	/** If we have read in the original data set. */
	private boolean originalDataReadIn = false;
	
	public boolean hasOriginalData(){
		return this.originalDataReadIn;
	}
}
