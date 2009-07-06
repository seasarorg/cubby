package org.seasar.cubby.internal.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class URLBodyEncoderTest {

	@Test
	public void encode() throws Exception {
		assertEquals("abc%20%E3%81%82%E3%81%84%E3%81%86", URLBodyEncoder
				.encode("abc あいう", "UTF-8"));
		assertEquals("abc%20%82%A0%82%A2%82%A4", URLBodyEncoder.encode(
				"abc あいう", "Windows-31J"));
	}

}
