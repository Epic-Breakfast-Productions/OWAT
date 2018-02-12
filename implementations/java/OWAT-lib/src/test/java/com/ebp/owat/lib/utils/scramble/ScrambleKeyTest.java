package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.utils.scramble.key.ScrambleKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Iterator;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

public class ScrambleKeyTest {
	
	private static final ObjectMapper om = new ObjectMapper();
	
	private Logger LOGGER = LoggerFactory.getLogger(ScrambleKeyTest.class);

	@Test
	public void testScrambleKeySerialization() throws IOException {
		ScrambleKey key = new ScrambleKey(0L, 0L, 0L, 0L, BitValue.class, -1);
		
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 1L, 1L));
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 2L, 2L));
		
		String json = om.writeValueAsString(key);
		
		LOGGER.info("Result: {}", json);
		
		ScrambleKey keyTwo = om.readValue(json, ScrambleKey.class);
		
		assertTrue(key.equals(keyTwo, false));
		LOGGER.info("Done.");
	}
	
	@Test
	public void testScrambleKeyMode() throws IOException {
		ScrambleKey key = new ScrambleKey(0L, 0L, 0L, 0L, BitValue.class, -1);
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 1L, 1L));
		
		try{
			key.getMovesIt();
			Assert.fail();
		}catch (IllegalStateException e){
			//nothing to do
		}
		
		key = om.readValue(om.writeValueAsString(key), ScrambleKey.class);
		
		key.getMovesIt();
		try{
			key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 1L, 1L));
			Assert.fail();
		}catch (IllegalStateException e){
			//nothing to do
		}
	}
	
	@Test
	public void testScrambleKeyAddMoveAndIterator() throws IOException {
		ScrambleKey key = new ScrambleKey(0L, 0L, 0L, 0L, BitValue.class, -1);
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 1L, 1L));
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_COL, 2L, 2L));
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 3L, 3L));
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_COL, 4L, 4L));
		
		key = om.readValue(om.writeValueAsString(key), ScrambleKey.class);
		
		Iterator<ScrambleMove> it = key.getMovesIt();
		
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP_COL, 4L, 4L), it.next());
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP_ROW, 3L, 3L), it.next());
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP_COL, 2L, 2L), it.next());
		assertEquals(new ScrambleMove(ScrambleMoves.SWAP_ROW, 1L, 1L), it.next());
		assertFalse(it.hasNext());
	}
}
