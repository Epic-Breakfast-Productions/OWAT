package com.ebp.owat.lib.utils.scramble;

import java.util.regex.Pattern;

public enum ScrambleMoves {
	SWAP(1,"sw",4, "\\s*sw\\s*:\\s*\\d+\\s*x\\s*\\d+\\s*,\\s*\\d+\\s*x\\s*\\d+\\s*;\\s*"),
	SWAP_ROW(2,"swr",2, "\\s*swr\\s*:\\s*\\d+\\s*,\\s*\\d+\\s*;\\s*"),
	SWAP_COL(3,"swc",2, "\\s*swc\\s*:\\s*\\d+\\s*,\\s*\\d+\\s*;\\s*"),
	SLIDE_ROW(4,"slr",2, "\\s*slr\\s*:\\s*\\d+\\s*,\\s*\\d+\\s*;\\s*"),
	SLIDE_COL(5,"slc",2, "\\s*slc\\s*:\\s*\\d+\\s*,\\s*\\d+\\s*;\\s*"),
	ROT_CLOCK(6,"rcl",5, "\\s*rcl\\s*:\\s*(0|1|2|3)\\s*,\\s*\\d+\\s*x\\s*\\d+\\s*,\\s*\\d+\\s*x\\s*\\d+\\s*;\\s*"),
	ROT_CCLOCK(7,"rcc",5, "\\s*rcc\\s*:\\s*(0|1|2|3)\\s*,\\s*\\d+\\s*x\\s*\\d+\\s*,\\s*\\d+\\s*x\\s*\\d+\\s*;\\s*");
	private final int PATTERN_FLAG = Pattern.CASE_INSENSITIVE;
	
	public final int opCode;
	public final String opStr;
	public final int numArgs;
	public final Pattern regex;
	public final Pattern regexWholeString;
	
	ScrambleMoves(int opCode, String opStr, int numArgs, String regex){
		this.opCode = opCode;
		this.opStr = opStr;
		this.numArgs = numArgs;
		this.regex = Pattern.compile(regex, PATTERN_FLAG);
		this.regexWholeString = Pattern.compile("^"+regex+"$", PATTERN_FLAG);
	}
	
	public boolean isValid(String move){
		return this.regexWholeString.matcher(move).matches();
	}
	
	/**
	 *
	 * @param move
	 * @return
	 * @throws IllegalArgumentException If string given does not match any move.
	 */
	public static ScrambleMoves determineMove(String move){
		for(ScrambleMoves curMove : ScrambleMoves.values()){
			if(curMove.isValid(move)){
				return curMove;
			}
		}
		throw new IllegalArgumentException("String given was not a valid move.");
	}
}
