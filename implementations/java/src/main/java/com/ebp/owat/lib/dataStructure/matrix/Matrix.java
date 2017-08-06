package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.set.LongLinkedList;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  Abstract class of a matrix to hold all the information and perform scrambling operations on.
 *
 * Created by Greg Stewart on 3/23/17.
 */
public class Matrix<T> {
	/**
	 * Enum to describe which node is held in the spot in the hashmap.
	 *
	 * TODO:: add data/method here to determine where these should be?
	 */
	public enum FixedNodePos{
		TOP_LEFT(MatrixNode.NodeDir.NORTH, MatrixNode.NodeDir.WEST),
		TOP_RIGHT(MatrixNode.NodeDir.NORTH, MatrixNode.NodeDir.EAST),
		BOT_LEFT(MatrixNode.NodeDir.SOUTH, MatrixNode.NodeDir.WEST),
		BOT_RIGHT(MatrixNode.NodeDir.SOUTH, MatrixNode.NodeDir.EAST);
		
		private List<MatrixNode.NodeDir> toBorder = new LinkedList<>();
		
		FixedNodePos(MatrixNode.NodeDir ... directionsToBorder){
			toBorder.addAll(Arrays.asList(directionsToBorder));
		}
		
		public List<MatrixNode.NodeDir> getBorderDirs(){
			return new LinkedList<>(this.toBorder);
		}
	}
	
	/**
	 * A set of nodes that we know the positions of.
	 */
	protected ConcurrentHashMap<FixedNodePos, MatrixNode<T>> fixedNodes = new ConcurrentHashMap<>(FixedNodePos.values().length);
	
	/** The number of rows held by this object. */
	protected long numRows = 0L;
	/** The number of columns held by this object. */
	protected long numCols = 0L;
	
	/**
	 * Basic Constructor.
	 */
	private Matrix(){}
	
	public Matrix(LongLinkedList<T> valuesIn){
		this();
		//TODO:: this
	}
	
	public void addRow(){
		if(this.fixedNodes.isEmpty()){
			this.fixedNodes.put(FixedNodePos.TOP_LEFT, new MatrixNode<T>());
		}
		resetFixedPoints();
	}
	
	public void addCol(){
	
	}
	
	public void addRow(List<T> valuesIn){
	
	}
	
	public List<List<T>> to2dArray(){
		//TODO
		return null;
	}
	
	/**
	 * Resets the fixed points in the
	 */
	private void resetFixedPoints(){
		//TODO
	}
	
	//TODO:: iterator(s)
	
}//Matrix
