package com.ebp.owat.lib.utils.scramble;

import com.ebp.owat.lib.datastructure.value.BitValue;
import com.ebp.owat.lib.utils.scramble.key.ScrambleKey;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;

public class ScrambleKeyTest {
	
	private static final ObjectMapper om = new ObjectMapper();
	
	private Logger LOGGER = LoggerFactory.getLogger(ScrambleKeyTest.class);

	@Test
	public void testScrambleKeySerialization() throws IOException {
		ScrambleKey key = new ScrambleKey(0L, 0L, BitValue.class);
		
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 1L, 1L));
		key.addMove(new ScrambleMove(ScrambleMoves.SWAP_ROW, 2L, 2L));
		
		String json = om.writeValueAsString(key);
		
		LOGGER.info("Result: {}", json);
		
		key = om.readValue(json, ScrambleKey.class);
		
		LOGGER.info("Done.");
	}
}
