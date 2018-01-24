package com.ebp.owat.lib.datastructure.matrix.Linked;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Enum to describe which value is held in the spot in the hashmap.
 */
public enum FixedNodePosition {
	TOP_LEFT(NodeDir.NORTH, NodeDir.WEST),
	TOP_RIGHT(NodeDir.NORTH, NodeDir.EAST),
	BOT_LEFT(NodeDir.SOUTH, NodeDir.WEST),
	BOT_RIGHT(NodeDir.SOUTH, NodeDir.EAST);
	
	private List<NodeDir> toBorder = new LinkedList<>();
	
	FixedNodePosition(NodeDir ... directionsToBorder){
		toBorder.addAll(Arrays.asList(directionsToBorder));
	}
	
	public List<NodeDir> getBorderDirs(){
		return new LinkedList<>(this.toBorder);
	}
}
