package com.ebp.owat.lib.dataStructure.node.value;

/**
 * Describes a bit value to be held by a Node.
 *
 * NodeValue to hold a single of information.
 *
 * Created by Greg Stewart on 3/26/17.
 */
public class BitValue implements NodeValue<Boolean> {
	
	/**
	 * The enumeration to keep track of which values are where in the value using bitmasks.
	 */
	private enum ValueBitmasks {
		VALUE((byte)1),
		IS_ORIGINAL((byte)(1 << 1));
		
		/** The bitmask this enumeration represents */
		private byte bitmaskValue;
		
		/** Basic constructor, sets the bitmask. */
		ValueBitmasks(byte maskValIn){
			this.bitmaskValue = maskValIn;
		}
		
		/** Gets the bitmask used to get a value out of the byte. */
		public byte getBitmaskAccess(){
			return bitmaskValue;
		}
		
		/** Gets the bitmask used to save a value. */
		public byte getBitmaskSave(){
			return (byte)~getBitmaskAccess();
		}
	}
	
	/** The values held by the nodeValue. Values are accessed via bitmask. */
	private byte valueInfo = 0;
	
	/**
	 * Basic constructor. Takes in a value, all other values are defaulted to false.
	 * @param valueIn The value this object will hold.
	 */
	public BitValue(boolean valueIn){
		this.setValue(valueIn);
	}
	
	/**
	 * Constructor to set up the actual value and if it is an original.
	 *
	 * @param valueIn The value to set this value as.
	 * @param isOriginal If this value is part of the original set.
	 */
	public BitValue(boolean valueIn, boolean isOriginal){
		this(valueIn);
		if(isOriginal) {
			this.setIsOriginal(true);
		}
	}
	
	/**
	 * Sets a value in the byte using a bitmask.
	 *
	 * @param value The value to set.
	 * @param bitmaskIn The bitmast to use to set correctly.
	 */
	private void setUsingBitmask(boolean value, ValueBitmasks bitmaskIn){
		if(value){
			this.valueInfo = (byte)(this.valueInfo | bitmaskIn.getBitmaskAccess());
		}else{
			this.valueInfo = (byte)(this.valueInfo & bitmaskIn.getBitmaskSave());
		}
	}
	
	/**
	 * Gets a value in the byte using a bitmask.
	 *
	 * @param bitmaskIn The bitmask to use to get the correct value.
	 * @return The value at the bitmask given.
	 */
	private boolean getUsingBitmask(ValueBitmasks bitmaskIn){
		return (this.valueInfo & bitmaskIn.getBitmaskAccess()) == bitmaskIn.getBitmaskAccess();
	}
	
	/**
	 * Sets the value of this value.
	 *
	 * @param valueIn The value to set.
	 * @return This BitValue object.
	 */
	private BitValue setValue(boolean valueIn){
		this.setUsingBitmask(valueIn, ValueBitmasks.VALUE);
		return this;
	}
	
	/**
	 * Sets if this BitValue is part of the original dataset.
	 * @param isOriginal If this is supposed to be a part of the original dataset.
	 * @return This BitValue object.
	 */
	private BitValue setIsOriginal(boolean isOriginal){
		this.setUsingBitmask(isOriginal, ValueBitmasks.IS_ORIGINAL);
		return this;
	}
	
	@Override
	public Boolean getValue() {
		return getUsingBitmask(ValueBitmasks.VALUE);
	}
	
	public boolean getPrimValue(){
		return this.getValue();
	}
	
	@Override
	public boolean isOriginal() {
		return getUsingBitmask(ValueBitmasks.IS_ORIGINAL);
	}
}
