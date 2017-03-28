package com.ebp.owat.lib.dataStructure.node.value;

/**
 * Describes a bit value to be held by a Node.
 *
 * NodeValue to hold a whole byte of information.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class ByteValue implements NodeValue<Byte> {
	/** The value this ByteValue holds. */
	private final byte thisValue;
	/** If this ByteValue is part of the original data set. */
	private final boolean isOriginal;
	
	/**
	 * Basic constructor. Defaults isOriginal to false.
	 * @param valueIn The value this ByteValue should hold.
	 */
	public ByteValue(byte valueIn){
		this.thisValue = valueIn;
		this.isOriginal = false;
	}
	
	/**
	 * Constructor to set isOriginal along with value.
	 *
	 * @param valueIn The value this ByteValue should hold.
	 * @param isOriginalIn If this value is part of the original data.
	 */
	public ByteValue(byte valueIn, boolean isOriginalIn){
		this.thisValue = valueIn;
		this.isOriginal = isOriginalIn;
	}
	
	@Override
	public Byte getValue() {
		return this.thisValue;
	}
	
	/**
	 * Gets the primitive byte value.
	 *
	 * @return The primitive of getValue()
	 */
	public byte getPrimValue(){return this.getValue();}
	
	@Override
	public boolean isOriginal() {
		return this.isOriginal;
	}
}
