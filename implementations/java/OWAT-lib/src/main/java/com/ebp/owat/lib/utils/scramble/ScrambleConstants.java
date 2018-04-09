package com.ebp.owat.lib.utils.scramble;

import java.util.regex.Pattern;

/**
 * Constants for dealing with scrambling moves.
 */
public class ScrambleConstants {
	/** The flags to pass to the pattern for java's regex */
	static final int PATTERN_FLAG = Pattern.CASE_INSENSITIVE;

	// these are for the regexes and serialization of moves
	static final char MOVE_END = ';';
	static final char ARG_SEP = ',';
	static final char COORD_SEP = 'x';
	static final char COORD_SEP_CAP = 'P';
	static final char OP_SEP = ':';
	
	static final String MOVE_SEP_REGEX = "(?<=" + ScrambleConstants.MOVE_END + ")";

	/*
		These are for determining what values are what for what moves.
	 */

	public static class Swap {
		public static final String OPNO = "1";
		public static final String OP = "sw";
		public static final int X1 = 0;
		public static final int Y1 = 1;
		public static final int X2 = 2;
		public static final int Y2 = 3;
	}
	
	private static abstract class SwapRowCol {
		public static final int ROWCOL1 = 0;
		public static final int ROWCOL2 = 1;
	}
	
	public static class SwapRow extends SwapRowCol {
		public static final String OPNO = "2";
		public static final String OP = "swr";
	}
	
	public static class SwapCol extends SwapRowCol {
		public static final String OPNO = "3";
		public static final String OP = "swc";
	}
	
	private static abstract class SlideRowCol {
		public static final int ROWCOL = 0;
		public static final int NUMTOSLIDE = 1;
	}
	
	public static class SlideRow extends SlideRowCol {
		public static final String OPNO = "4";
		public static final String OP = "slr";
	}
	
	public static class SlideCol extends SlideRowCol {
		public static final String OPNO = "5";
		public static final String OP = "slc";
	}
	
	public static class RotateBox {
		public static final String OPNO = "6";
		public static final String OP = "rot";
		public static final int ROTNUM = 0;
		public static final int X = 1;
		public static final int Y = 2;
		public static final int SIZE = 3;
	}
}
