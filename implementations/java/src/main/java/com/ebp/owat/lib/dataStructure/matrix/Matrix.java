package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.node.Node;
import com.ebp.owat.lib.dataStructure.matrix.set.NodeList;

import java.math.BigInteger;

/**
 *  Abstract class of a matrix to hold all the information and perform scrambling operations on.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public abstract class Matrix<T extends Node> {
	
	public abstract Node getNode(BigInteger row, BigInteger col);
	
	public abstract NodeList getRow(BigInteger row);
	public abstract NodeList getCol(BigInteger col);
}//Matrix
