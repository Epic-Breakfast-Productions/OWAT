package com.ebp.owat.lib.utils.rand;

/**
 * Describes a random number generator for use with the rest of OWAT
 *
 * TODO:: implement generator from: https://api.random.org/
 * TODO:: https://github.com/TheJavaGuy/rng
 *
 * Created by Greg Stewart on 4/6/17.
 */
public abstract class OwatRandGenerator {
	protected static final String CHARSET = "0123456789abcdefghijklmnopqrstuvwxyz`~!@#$%^&*()-_=+\\]}[{'\";:/?.>,<";
	protected byte[] byteBuff = new byte[1];
	
	/**
	 * Default constructor.
	 */
	public OwatRandGenerator(){}
	
	/**
	 * Gets a random long, no consideration of bounds. Allways positive
	 * @return A random long.
	 */
	public abstract long nextLong();
	
	/**
	 * Gets a random long, with a maximum possible value.
	 * @param upperBound
	 * @return
	 */
	public abstract long nextLong(long upperBound);
	
	/**
	 * Gets a random long.
	 * @param lowerBound
	 * @param upperBound
	 * @return
	 */
	public long nextLong(long lowerBound, long upperBound){
		return this.nextLong(upperBound - lowerBound) + lowerBound;
	}
	
	/**
	 * Gets a byte with a random value.
	 * TODO:: test
	 * @return A byte with a random value.
	 */
	public byte nextByte(){
		return (byte)this.nextLong(Byte.MAX_VALUE + 1);
	}
	
	/**
	 * Gets a random character in the form of a byte.
	 * TODO:: test
	 * @return A random character in the form of a byte.
	 */
	public byte nextByteChar(){
		return (byte) CHARSET.charAt((int)this.nextLong(CHARSET.length()));
	}
	
	/**
	 * Gets a random value from the set brought in.
	 * https://stackoverflow.com/questions/450807/how-do-i-make-the-method-return-type-generic
	 * @param vals The values to choose from.
	 * @param <T> The type of object.
	 * @return A random value from the set given.
	 */
	@SafeVarargs
	public final <T> T getRandValue(T ... vals){
		if(vals == null || vals.length == 0){
			throw new IllegalArgumentException("Cannot choose from an empty or null set.");
		}
		return vals[(int)this.nextLong(vals.length-1)];
	}
}
