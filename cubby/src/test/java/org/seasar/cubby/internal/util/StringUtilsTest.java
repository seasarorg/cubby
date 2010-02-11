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
import org.seasar.cubby.internal.util.StringUtils;

public class StringUtilsTest {

	@Test
	public void replace() throws Exception {
		assertEquals("1", "1bc45", StringUtils.replace("12345", "23", "bc"));
		assertEquals("2", "1234ef", StringUtils.replace("12345", "5", "ef"));
		assertEquals("3", "ab2345", StringUtils.replace("12345", "1", "ab"));
		assertEquals("4", "a234a", StringUtils.replace("12341", "1", "a"));
		assertEquals("5", "ab234abab234ab", StringUtils.replace("1234112341",
				"1", "ab"));
		assertEquals("6", "a\\nb", StringUtils.replace("a\nb", "\n", "\\n"));
	}

	@Test
	public void isBlank() throws Exception {
		assertEquals("1", true, StringUtils.isBlank(" "));
		assertEquals("2", true, StringUtils.isBlank(""));
		assertEquals("3", false, StringUtils.isBlank("a"));
		assertEquals("4", false, StringUtils.isBlank(" a "));
	}

	@Test
	public void isNotBlank() throws Exception {
		assertEquals("1", false, StringUtils.isNotBlank(" "));
		assertEquals("2", false, StringUtils.isNotBlank(""));
		assertEquals("3", true, StringUtils.isNotBlank("a"));
		assertEquals("4", true, StringUtils.isNotBlank(" a "));
	}

	@Test
	public void equalsIgnoreCase() throws Exception {
		assertEquals("1", true, StringUtils.equalsIgnoreCase("a", "a"));
		assertEquals("2", true, StringUtils.equalsIgnoreCase("a", "A"));
		assertEquals("3", true, StringUtils.equalsIgnoreCase("A", "a"));
		assertEquals("4", true, StringUtils.equalsIgnoreCase(null, null));
		assertEquals("5", false, StringUtils.equalsIgnoreCase("a", null));
		assertEquals("6", false, StringUtils.equalsIgnoreCase(null, "a"));
		assertEquals("7", false, StringUtils.equalsIgnoreCase("a", "b"));
	}

}