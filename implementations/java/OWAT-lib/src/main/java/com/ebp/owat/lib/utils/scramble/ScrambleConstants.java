package com.ebp.owat.lib.utils.scramble;

import java.util.regex.Pattern;

public class ScrambleConstants {
	static final int PATTERN_FLAG = Pattern.CASE_INSENSITIVE;
	
	static final char MOVE_END = ';';
	static final char ARG_SEP = ',';
	static final char COORD_SEP = 'x';
	static final char COORD_SEP_CAP = 'P';
	static final char OP_SEP = ':';
	
	static final String SWAP		= "sw";
	static final String SWAP_ROW	= "swr";
	static final String SWAP_COL	= "swc";
	static final String SLIDE_ROW	= "slr";
	static final String SLIDE_COL	= "slc";
	static final String ROT_CLOCK	= "rcl";
	static final String ROT_CCLOCK	= "rcc";
}
