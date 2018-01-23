package com.ebp.owat.lib.utils;

import java.util.regex.Pattern;

public enum ScrambleMoves {
	SWAP(1,"sw",4, ""),
	SWAP_ROW(2,"swr",2, ""),
	SWAP_COL(3,"swc",2, ""),
	SLIDE_ROW(4,"slr",2, ""),
	SLIDE_COL(5,"slc",2, ""),
	ROT_CLOCK(6,"rcl",5, ""),
	ROT_CCLOCK(7,"rcc",5, "");
	
	public final int opCode;
	public final String opStr;
	public final int numArgs;
	public final Pattern regex;
	
	ScrambleMoves(int opCode, String opStr, int numArgs, String regex){
		this.opCode = opCode;
		this.opStr = opStr;
		this.numArgs = numArgs;
		this.regex = Pattern.compile(regex);
	}
	
	public boolean isValid(String move){
		return this.regex.matcher(move).matches();
	}
	
	public static ScrambleMoves determineMove(String move){
		for(ScrambleMoves curMove : ScrambleMoves.values()){
			if(curMove.isValid(move)){
				return curMove;
			}
		}
		throw new IllegalArgumentException("String given was not a valid move.");
	}
}
