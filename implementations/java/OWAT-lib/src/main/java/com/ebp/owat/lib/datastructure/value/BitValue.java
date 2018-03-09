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
	
	private static boolean isBitSet(byte b, short bit){
		return (b & (1 << bit)) != 0;
	}
	
	/**
	 * Turns a byte into a list of 8 BitValues.3
	 *
	 * @param source The byte to get the info from.
	 * @return The list of 8 BitValues.
	 */
	public static List<BitValue> fromByte(byte source, boolean isOriginal){
		List<BitValue> vals = new LinkedList<>();
		
		for(short curBit = 0; curBit < 8; curBit++){
			boolean curVal = isBitSet(source, curBit);
			vals.add(
				new BitValue(curVal, isOriginal)
			);
		}
		
		return vals;
	}
	
	/**
	 * Turns exactly 8 bit vaules into a byte.
	 * @param sources The bit values to get info from.
	 * @return The byte that was made up from the sources.
	 * @throws IllegalArgumentException If the list given is not 8 bits long.
	 */
	public static byte toByte(List<BitValue> sources){
		if(sources.size() != 8){
			throw new IllegalArgumentException("Cannot parse a byte from a list of bits not 8 bits long.");
		}
		byte output = 0;
		
		for(short curBit = 0; curBit < 8; curBit++){
			if(sources.get(curBit).getValue()){
				output = (byte)(output | ((byte)1 << curBit));
			}
		}
		
		return output;
	}

	@Override
	public String toString() {
		return "{Value type BIT. Value: " + (this.getValue() ? "1" : "0") + "}";
	}
}
