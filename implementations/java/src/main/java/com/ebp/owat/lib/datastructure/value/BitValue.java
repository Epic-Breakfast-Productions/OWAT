package com.ebp.owat.lib.datastructure.value;

/**
 *  A Node that holds a BitValue.
 *
 *  Holds that bit value in the {@link #flags} byte to save space.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class BitValue extends Value<Boolean> {
	
	public BitValue(boolean value){
		if(value) {
			this.flags = ValueFlag.VALUE.setFlag(this.flags);
		}
	}
	
	public BitValue(boolean value, boolean isOriginal){
		this(value);
		if(isOriginal){
			this.flags = ValueFlag.IS_ORIGINAL.setFlag(this.flags);
		}
	}
	
	@Override
	public Boolean getValue() {
		return ValueFlag.VALUE.getFlag(this.flags);
	}
}
