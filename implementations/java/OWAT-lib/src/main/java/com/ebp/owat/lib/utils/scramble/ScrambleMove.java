package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.Arrays;
import java.util.Objects;

import static com.ebp.owat.lib.utils.scramble.ScrambleConstants.*;

public class ScrambleMove {
	private static boolean useOpCode = true;
	
	public final ScrambleMoves move;
	private final long[] args;
	
	public ScrambleMove(ScrambleMoves move, long ... args) {
		this.move = move;
		if (args.length != this.move.numArgs) {
			throw new IllegalArgumentException("Invalid number of arguments passed for the move given.");
		}
		this.args = args;
	}
	
	public long getArg(int argIndex){
		return this.args[argIndex];
	}
	
	private String getOpOrStr(){
		if(isUsingOpCode()){
			return this.move.opCode;
		}
		return this.move.opStr;
	}
	
	/**
	 * Turns this move into it's string representation.
	 * @param sbIn If null, simply returns the string. If given a StringBuilder, adds to the string builder and returns null.
	 * @return If sbIn is null, returns the string. Else just returns null.
	 */
	public String toKeyString(StringBuilder sbIn){
		StringBuilder sb;
		if(sbIn != null){
			sb = sbIn;
			sb.append(this.getOpOrStr());
		}else{
			sb = new StringBuilder(getOpOrStr());
		}
		sb.append(OP_SEP);
		
		switch (this.move) {
			case SWAP:
				sb.append(this.args[Swap.X1])
					.append(COORD_SEP)
					.append(this.args[Swap.Y1])
					.append(ARG_SEP)
					.append(this.args[Swap.X2])
					.append(COORD_SEP)
					.append(this.args[Swap.Y2]);
				break;
			case SWAP_ROW:
			case SWAP_COL:
				sb.append(this.args[SwapRow.ROWCOL1])
					.append(ARG_SEP)
					.append(this.args[SwapRow.ROWCOL2]);
				break;
			case SLIDE_ROW:
			case SLIDE_COL:
				sb.append(this.args[SlideCol.ROWCOL])
					.append(ARG_SEP)
					.append(this.args[SlideCol.NUMTOSLIDE] * -1L);
				break;
			case ROT_BOX:
				sb.append(this.args[RotateBox.ROTNUM] * -1L)
					.append(ARG_SEP)
					.append(this.args[RotateBox.X])
					.append(COORD_SEP)
					.append(this.args[RotateBox.Y])
					.append(ARG_SEP)
					.append(this.args[RotateBox.SIZE]);
				break;
		}
		sb.append(MOVE_END);
		if(sbIn != null){
			return null;
		}
		return sb.toString();
	}
	
	public String toKeyString() {
		return this.toKeyString(null);
	}
	
	public static ScrambleMove parse(String move) {
		ScrambleMoves sm = ScrambleMoves.determineMove(move);
		String sanitizedMove = move.replaceAll("\\s", "");
		sanitizedMove = sanitizedMove.substring(sanitizedMove.lastIndexOf(":") + 1, sanitizedMove.length() - 1);
		String[] moveParts = sanitizedMove.split("(" + ARG_SEP + "|" + COORD_SEP + "|" + COORD_SEP_CAP + ")");
		long[] args = new long[moveParts.length];
		for (int i = 0; i < moveParts.length; i++) {
			args[i] = Long.parseLong(moveParts[i]);
		}
		return new ScrambleMove(sm, args);
	}
	
	public static LongLinkedList<ScrambleMove> parseMulti(String moves) {
		LongLinkedList<ScrambleMove> output = new LongLinkedList<>();
		
		String[] moveStrings = moves.split(MOVE_SEP_REGEX);
		
		for (String curMove : moveStrings) {
			output.addLast(parse(curMove));
		}
		return output;
	}
	
	public static synchronized boolean isUsingOpCode(){
		return useOpCode;
	}
	
	public static synchronized void useOpCode(boolean use){
		useOpCode = use;
	}
	
	
	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ScrambleMove that = (ScrambleMove) o;
		return move == that.move &&
			Arrays.equals(args, that.args);
	}
	
	@Override
	public int hashCode() {
		int result = Objects.hash(move);
		result = 31 * result + Arrays.hashCode(args);
		return result;
	}
}