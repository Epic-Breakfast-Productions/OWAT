package com.ebp.owat.lib.datastructure.matrix.Linked;

import com.ebp.owat.lib.datastructure.matrix.Linked.utils.LinkedMatrixNode;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition.FixedNode;
import com.ebp.owat.lib.datastructure.matrix.Linked.utils.nodePosition.NodePosition;
import com.ebp.owat.lib.datastructure.matrix.Matrix;
import com.ebp.owat.lib.datastructure.matrix.utils.MatrixValidator;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.Coordinate;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.DistanceCalc;
import com.ebp.owat.lib.datastructure.matrix.utils.coordinate.MatrixCoordinate;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

public class LinkedMatrix<T> extends Matrix<T> {
	/**
	 * The frequency of how often reference nodes are added.
	 */
	private static final long ADD_REFERENCE_FREQUENCY = 100;

	/** The number of elements held in the matrix. */
	protected long numElementsHeld = 0;

	private long curAddCount = 0;

	/**
	 * @return If the added node should be made a reference.
	 */
	private boolean addedNode(){
		this.curAddCount++;
		if(curAddCount >= ADD_REFERENCE_FREQUENCY){
			this.curAddCount = 0;
			return true;
		}
		return false;
	}

	/** The nodes we have to keep track of. */
	protected Collection<NodePosition<T>> referenceNodes = new LinkedList<>();

	private void initFirstNode(){
		if(this.hasRowsCols()){
			throw new IllegalStateException("Cannot init nodes when we already have them.");
		}

		LinkedMatrixNode<T> headNode = new LinkedMatrixNode<>();

		for(FixedNode.FixedPosition curPos : FixedNode.FixedPosition.values()){
			this.referenceNodes.add(
				new FixedNode<>(this, headNode, curPos)
			);
		}
	}

	/**
	 * Inits this matrix if needed.
	 * @return If this actually init'ed the matrix.
	 */
	private boolean initIfNoRowsCols(){
		if(!this.hasRowsCols()){
			this.initFirstNode();
			return true;
		}
		return false;
	}

	/**
	 * Resets all the node positions in the matrix.
	 * @return If any of the positions moved.
	 */
	private boolean resetNodePositions(){
		boolean movedNodes = false;
		for(NodePosition<T> curPos : this.referenceNodes){
			movedNodes |= curPos.resetPosition();
		}
		return movedNodes;
	}

	/**
	 * Gets the node closest to the coordinate given.
	 * @param coord The coordinate we want to get.
	 * @return The node closest node to the coordinate given.
	 */
	private NodePosition<T> getClosestHeldPosition(MatrixCoordinate coord){
		NodePosition<T> output = null;
		for(NodePosition<T> curPosition : this.referenceNodes){
			if(output == null){
				output = curPosition;
				continue;
			}
			if(DistanceCalc.nodeIsCloserThan(coord, curPosition, output)){
				output = curPosition;
			}
		}
		if(output == null){
			//TODO:: handle this better?
			throw new IllegalStateException("No nodes held.");
		}
		return output.clone();
	}

	/**
	 * Gets the matrixNode at the coordinate given.
	 * @param coord The coordinate of the node to get.
	 * @return The node at the coordinate given.
	 */
	private LinkedMatrixNode<T> getMatrixNode(MatrixCoordinate coord){
		NodePosition<T> curPosition = this.getClosestHeldPosition(coord);

		while(!curPosition.baseCoordEquals(coord)){
			if(curPosition.getY() > coord.getY()){
				curPosition.moveNorth();
			}
			if(curPosition.getY() < coord.getY()){
				curPosition.moveSouth();
			}
			if(curPosition.getX() > coord.getX()){
				curPosition.moveEast();
			}
			if(curPosition.getX() < coord.getX()){
				curPosition.moveWest();
			}
		}
		return curPosition.getNode();
	}

	@Override
	public void addRow() {
		if(this.initIfNoRowsCols()){
			return;
		}

		this.numRows++;
		LinkedMatrixNode<T> lastNode = new LinkedMatrixNode<>();
		lastNode.setNorth(this.getMatrixNode(new MatrixCoordinate(this, 0, this.getNumRows())));
		for(long l = 1; l < this.getNumCols(); l++){
			LinkedMatrixNode<T> curNode = new LinkedMatrixNode<>();

			lastNode.setEast(curNode);

			curNode.getWest().getNorth().getEast().setSouth(curNode);

			if(this.addedNode()){
				this.referenceNodes.add(new NodePosition<>(this, curNode));
			}

			lastNode = curNode;
		}

		this.resetNodePositions();
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

		if(this.initIfNoRowsCols()){
			return;
		}

		this.numCols++;
		LinkedMatrixNode<T> lastNode = new LinkedMatrixNode<>();
		lastNode.setEast(this.getMatrixNode(new MatrixCoordinate(this, this.getNumCols(), 0)));
		for(long l = 1; l < this.getNumRows(); l++){
			LinkedMatrixNode<T> curNode = new LinkedMatrixNode<>();

			lastNode.setWest(curNode);

			curNode.getNorth().getEast().getSouth().setWest(curNode);

			if(this.addedNode()){
				this.referenceNodes.add(new NodePosition<>(this, curNode));
			}

			lastNode = curNode;
		}

		this.resetNodePositions();
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
		return this.getMatrixNode(coordIn).getValue(this.getDefaultValue());
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
