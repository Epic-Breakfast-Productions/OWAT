package com.ebp.owat.lib.dataStructure.matrix.utils;

/**
 * Describes a scrambling move that can happen. Correspond to the methods provided.
 */
public enum Move {
	SWAP_NODES("Sw", 1, 4);
	
	/** The string representation of this move. */
	public final String opCode;
	/** The move's op code number. */
	public final int opCodeNum;
	/** The number of params to expect from parsing this from the key file. */
	public final int numParams;
	
	Move(String opCode, int opCodeNum, int numParams){
		this.opCode = opCode;
		this.opCodeNum = opCodeNum;
		this.numParams = numParams;
	}
	
	/**
	 * Determines if the move OpCode given corresponds to this move's op code.
	 * @param moveIn The move OpCode given.
	 * @param isCondeNum If this method is to expect a code number instead of a full opcode. If true, will parse the int form the string given and calls {@link #isMove(int)}.
	 * @return If the move given is this move.
	 */
	public boolean isMove(String moveIn, boolean isCondeNum){
		if(isCondeNum){
			return this.isMove(Integer.parseInt(moveIn));
		}
		return this.opCode.equalsIgnoreCase(moveIn);
	}
	
	/**
	 * Determines if the number given corresponds to this move's op number.
	 * @param moveIn The code number to test.
	 * @return If the code number corresponds to this code number.
	 */
	public boolean isMove(int moveIn){
		return this.opCodeNum == moveIn;
	}
	
	/**
	 * Parses a move from a string.
	 * @param moveIn The move OpCode or number to parse.
	 * @param isCodeNum If we are to expect moveIn to be a number or the full opCode.
	 * @return The move moveIn corresponds to.
	 * @throws IllegalArgumentException If moveIn did not correspond to any moves.
	 */
	public static Move parseMove(String moveIn, boolean isCodeNum) throws IllegalArgumentException{
		for(Move curMove : Move.values()){
			if(curMove.isMove(moveIn, isCodeNum)){
				return curMove;
			}
		}
		if(isCodeNum) {
			throw new IllegalArgumentException("Invalid opCode number. OpCode number given: \"" + moveIn + "\"");
		}else{
			throw new IllegalArgumentException("Invalid opCode. OpCode given: \"" + moveIn + "\"");
		}
	}
}
