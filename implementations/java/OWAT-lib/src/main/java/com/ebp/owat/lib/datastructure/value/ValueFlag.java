package com.ebp.owat.lib.datastructure.value;

/**
 * The flags that a value can be set to.
 */
public enum ValueFlag {
	/** Flag for determining if the value is part of the original data or not. */
	IS_ORIGINAL((byte)1),
	/** For the {@link BitValue} Node. Holds the value of that value. */
	VALUE((byte)128);

	/**
	 * The offset of the flag, for bitwise operations to get/set the flag.
	 */
	public final byte offset;

	/**
	 * Constructor to setup the flag.
	 * @param offset The offset of the flag.
	 */
	ValueFlag(byte offset){
		this.offset = offset;
	}

	/**
	 * Gets the flag value set in the byte.
	 * @param data The byte to get the flag out of.
	 * @return The value of the flag set in the byte.
	 */
	public boolean getFlag(byte data){
		return (this.offset & data) == this.offset;
	}

	/**
	 * Sets this flag in the byte.
	 * @param data The data to set the data in.
	 * @return The byte with this flag set.
	 */
	public byte setFlag(byte data){
		return (byte)(this.offset | data);
	}
}
