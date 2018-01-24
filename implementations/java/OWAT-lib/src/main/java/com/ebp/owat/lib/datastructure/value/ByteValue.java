package com.ebp.owat.lib.datastructure.value;

/**
 *  A Node that holds a ByteValue.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class ByteValue extends Value<Byte> {
	/** The value of the value. Big 'B' Byte to prevent unnecessary auto unboxing. */
	public final Byte value;
	
	public ByteValue(Byte value) {
		this.value = value;
	}
	
	public ByteValue(Byte value, boolean isOriginal) {
		this.value = value;
		if(isOriginal){
			this.flags = ValueFlag.IS_ORIGINAL.setFlag(this.flags);
		}
	}
	
	@Override
	public Byte getValue() {
		return this.value;
	}
}
