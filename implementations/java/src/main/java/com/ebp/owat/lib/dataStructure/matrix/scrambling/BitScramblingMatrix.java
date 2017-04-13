package com.ebp.owat.lib.dataStructure.matrix.scrambling;

import com.ebp.owat.lib.dataStructure.io.NodeReader;
import com.ebp.owat.lib.dataStructure.io.NodeWriter;
import com.ebp.owat.lib.dataStructure.matrix.OwatMatrixException;
import com.ebp.owat.lib.dataStructure.matrix.deScrambling.DeScramblingMatrix;
import com.ebp.owat.lib.dataStructure.node.BitNode;
import com.ebp.owat.lib.dataStructure.node.Node;

/**
 *
 * Created by stewy on 4/5/17.
 */
public class BitScramblingMatrix extends DeScramblingMatrix<BitNode> {
	/**
	 * Constructor to build using a provided node.
	 * <p>
	 * Assumes that the node given is the top right.
	 *
	 * @param nodeIn The node to use to build this object.
	 */
	public BitScramblingMatrix(Node nodeIn) {
		super(nodeIn);
	}
	
	/**
	 * Constructor to set the stream reader and optionally read in the data right away.
	 *
	 * @param readerIn The reader to use to read the data in.
	 * @param readIn   If we are to read the data in immediately or not.
	 */
	public BitScramblingMatrix(NodeReader readerIn, boolean readIn) {
		super(readerIn, readIn);
	}
	
	/**
	 * Constructor to set the stream reader in and read in the data into nodes.
	 *
	 * @param readerIn The reader to get the nodes from.
	 */
	public BitScramblingMatrix(NodeReader readerIn) {
		super(readerIn);
	}
	
	/**
	 * Reads in the data from a data stream.
	 *
	 * @throws OwatMatrixException If this matrix is not set up to read data in with a stream or it data is already read in.
	 */
	@Override
	public void readInData() throws OwatMatrixException {
	
	}
	
	/**
	 * Gets an output stream to read out the data.
	 *
	 * @return A stream to use to read out the data.
	 */
	@Override
	public NodeWriter getOutputStream() {
		return null;
	}
	
	@Override
	public void setInputStream(NodeReader readerIn) {
	
	}
}
