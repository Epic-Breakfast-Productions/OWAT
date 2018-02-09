package com.ebp.owat.lib.datastructure.value;

import java.util.LinkedList;
import java.util.List;

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
	
	/**
	 * Turns a byte into a list of 8 BitValues.
	 * TODO:: test
	 * @param source The byte to get the info from.
	 * @return The list of 8 BitValues.
	 */
	public static List<BitValue> fromByte(byte source, boolean isOriginal){
		List<BitValue> vals = new LinkedList<>();
		
		for(byte curOffset = 1; curOffset <= 128; curOffset = (byte)(curOffset >> 1)){
			boolean curVal = (source & curOffset) == curOffset;
			vals.add(new BitValue(curVal, isOriginal));
		}
		
		return vals;
	}
	
	
	public static byte toBytes(List<BitValue> sources){
		//TODO
		return 0;
	}
}
