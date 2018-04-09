package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.set.LongLinkedList;

import java.util.Arrays;
import java.util.Objects;

import static com.ebp.owat.lib.utils.scramble.ScrambleConstants.*;

/**
 * Describes a move for scrambling.
 */
public class ScrambleMove {
	/** If we should use the opcode or not. */
	private static boolean useOpCode = true;

	/** The type of move this is. */
	public final ScrambleMoves move;
	/** The arguments that make up this move. */
	private final long[] args;

	/**
	 * Constructor to set up a move.
	 * @param move The type of move this is.
	 * @param args The arguments that make up this move.
	 * @throws IllegalArgumentException If the number of arguments is invalid.
	 */
	public ScrambleMove(ScrambleMoves move, long ... args) {
		this.move = move;
		if (args.length != this.move.numArgs) {
			throw new IllegalArgumentException("Invalid number of arguments passed for the move given.");
		}
		this.args = args;
	}

	/**
	 * Gets a particular argument from the args
	 * @param argIndex The index of the argument to get.
	 * @return The value of the argument specified.
	 */
	public long getArg(int argIndex){
		return this.args[argIndex];
	}

	/**
	 * Gets the op number or string, whichever specified by {@link #isUsingOpCode()}
	 * @return The op number or string.
	 */
	private String getOpOrStr(){
		if(isUsingOpCode()){
			return this.move.opCode;
		}
		return this.move.opStr;
	}

	/**
	 * Turns this move into the move that will undo this one.
	 */
	public void toReverse(){
		switch (this.move){
			case SLIDE_COL:
			case SLIDE_ROW:
				this.args[SlideCol.NUMTOSLIDE] = this.args[SlideCol.NUMTOSLIDE] * -1L;
				break;
			case ROT_BOX:
				this.args[RotateBox.ROTNUM] = this.args[RotateBox.ROTNUM] * -1L;
				break;
		}
	}
	
	/**
	 * Turns this move into it's string representation.
	 * @param sbIn If null, simply returns the string. If given a StringBuilder, adds to the string builder and returns null.
	 * @return If sbIn is null, returns the string. Else just returns null.
	 */
	public String toKeyString(StringBuilder sbIn, boolean reverse){
		if(reverse){
			this.toReverse();
		}
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
					.append(this.args[SlideCol.NUMTOSLIDE]);
				break;
			case ROT_BOX:
				sb.append(this.args[RotateBox.ROTNUM])
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

	/**
	 * Turns this move into its key string. Reverses the move for later descrambling.
	 * @return The key string representing this moves reversal.
	 */
	public String toKeyString() {
		return this.toKeyString(null, true);
	}

	/**
	 * Parses a move from the string given.
	 * @param move The move as a string.
	 * @return The move parsed from the string.
	 */
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

	/**
	 * Parses multiple moves from a string.
	 * @param moves The string with moves to parse.
	 * @return A list of moves parsed from the string given.
	 */
	public static LongLinkedList<ScrambleMove> parseMulti(String moves) {
		LongLinkedList<ScrambleMove> output = new LongLinkedList<>();
		
		String[] moveStrings = moves.split(MOVE_SEP_REGEX);
		
		for (String curMove : moveStrings) {
			output.addLast(parse(curMove));
		}
		return output;
	}

	/**
	 * Determines if we are using the moves' opcode or not.
	 * @return If we are using the moves' opcode or not.
	 */
	public static synchronized boolean isUsingOpCode(){
		return useOpCode;
	}

	/**
	 * Sets the flag to use the moves' opcode or not.
	 * @param use If we are to use opcodes or numbers.
	 */
	public static synchronized void useOpCode(boolean use){
		useOpCode = use;
	}

	@Override
	protected ScrambleMove clone() {
		return new ScrambleMove(this.move, this.args.clone());
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