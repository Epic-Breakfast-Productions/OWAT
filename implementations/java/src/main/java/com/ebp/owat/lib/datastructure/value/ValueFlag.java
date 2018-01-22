package com.ebp.owat.lib.datastructure.value;

public enum ValueFlag {
	/** Flag for determining if the value is part of the original data or not. */
	IS_ORIGINAL((byte)1),
	/** For the {@link BitValue} Node. Holds the value of that value. */
	VALUE((byte)128);
	
	public final byte offset;
	
	ValueFlag(byte offset){
		this.offset = offset;
	}
	
	public boolean getFlag(byte data){
		return (this.offset & data) == this.offset;
	}
	
	public byte setFlag(byte data){
		return (byte)(this.offset | data);
	}
}
