/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

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
