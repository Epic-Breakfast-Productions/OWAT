package com.ebp.owat.lib.datastructure.value;

import java.util.Objects;

/**
 * A value that holds a byte.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class ByteValue extends Value<Byte> {
	/** The value of the value. Big 'B' Byte to prevent unnecessary auto unboxing. */
	public final Byte value;

	/**
	 * Constructor to set the value.
	 * @param value The value to set.
	 */
	public ByteValue(Byte value) {
		this.value = value;
	}

	/**
	 * Constructor to set the value and the is original flag.
	 * @param value The value to set.
	 * @param isOriginal If the value is original or not.
	 */
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

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		if (!super.equals(o)) return false;
		ByteValue byteValue = (ByteValue) o;
		return Objects.equals(value, byteValue.value);
	}

	@Override
	public int hashCode() {
		return Objects.hash(super.hashCode(), value);
	}

	@Override
	public String toString() {
		return " {Value type BYTE. Value: " + this.getValue() + "} ";
	}
}
