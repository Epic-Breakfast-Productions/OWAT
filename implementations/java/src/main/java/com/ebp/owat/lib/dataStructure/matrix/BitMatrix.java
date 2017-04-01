package com.ebp.owat.lib.dataStructure.matrix;

import com.ebp.owat.lib.dataStructure.io.NodeWriter;
import com.ebp.owat.lib.dataStructure.node.BitNode;

/**
 * Matrix to hold bit values.
 *
 * Created by Greg Stewart on 3/30/17.
 */
public class BitMatrix extends Matrix<BitNode> {
	
	
	
	/**
	 * Gets an output stream to read out the data.
	 *
	 * @return A stream to use to read out the data.
	 */
	@Override
	public NodeWriter<BitNode> getOutputStream() {
		return null;
	}
}
