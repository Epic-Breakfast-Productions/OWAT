package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.node.set.NodeList;

import java.math.BigInteger;

/**
 * Created by stewy on 3/23/17.
 */
public interface Matrix {
	public abstract Node getNode(BigInteger row, BigInteger col);
	
	public abstract NodeList getRow(BigInteger row);
	public abstract NodeList getCol(BigInteger col);
}//Matrix
