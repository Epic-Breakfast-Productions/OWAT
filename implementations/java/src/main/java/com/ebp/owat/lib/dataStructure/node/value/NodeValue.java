package com.ebp.owat.lib.dataStructure.node.value;

/**
 * Describes the actions a value that is held by a Node should be able to take.
 *
 * Created by Greg Stewart on 3/24/17.
 */
public interface NodeValue<T> {
	/**
	 * Gets the value this NodeValue holds.
	 * @return The value of this NodeValue
	 */
	public abstract T getValue();
	
	/**
	 * Gets whether or not this NodeValue is part of the original data.
	 * @return If this NodeValue is part of the original data.
	 */
	public abstract boolean isOriginal();
}
