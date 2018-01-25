package com.ebp.owat.lib.utils.scramble;

import java.util.regex.Pattern;

import static com.ebp.owat.lib.utils.scramble.ScrambleConstants.*;

public enum ScrambleMoves {
	SWAP		(1,ScrambleConstants.SWAP,4, "\\s*("+ScrambleConstants.SWAP+"|1)\\s*"+OP_SEP+"\\s*\\d+\\s*"+COORD_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+COORD_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*"),
	SWAP_ROW	(2,ScrambleConstants.SWAP_ROW,2, "\\s*("+ScrambleConstants.SWAP_ROW+"|2)\\s*"+OP_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*"),
	SWAP_COL	(3,ScrambleConstants.SWAP_COL,2, "\\s*("+ScrambleConstants.SWAP_COL+"|3)\\s*"+OP_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*"),
	SLIDE_ROW	(4,ScrambleConstants.SLIDE_ROW,2, "\\s*("+ScrambleConstants.SLIDE_ROW+"|4)\\s*"+OP_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*"),
	SLIDE_COL	(5,ScrambleConstants.SLIDE_COL,2, "\\s*("+ScrambleConstants.SLIDE_COL+"|5)\\s*"+OP_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*"),
	ROT_CLOCK	(6,ScrambleConstants.ROT_CLOCK,5, "\\s*("+ScrambleConstants.ROT_CLOCK+"|6)\\s*"+OP_SEP+"\\s*(0|1|2|3)\\s*"+ARG_SEP+"\\s*\\d+\\s*"+COORD_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+COORD_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*"),
	ROT_CCLOCK	(7,ScrambleConstants.ROT_CCLOCK,5, "\\s*("+ScrambleConstants.ROT_CCLOCK+"|7)\\s*"+OP_SEP+"\\s*(0|1|2|3)\\s*"+ARG_SEP+"\\s*\\d+\\s*"+COORD_SEP+"\\s*\\d+\\s*"+ARG_SEP+"\\s*\\d+\\s*"+COORD_SEP+"\\s*\\d+\\s*"+MOVE_END+"\\s*");
	
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
