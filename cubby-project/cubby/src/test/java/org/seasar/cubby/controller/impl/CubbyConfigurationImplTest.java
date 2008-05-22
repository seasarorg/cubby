/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
package org.seasar.cubby.controller.impl;

import junit.framework.TestCase;

import org.seasar.cubby.action.impl.FormatPatternImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.framework.container.impl.ThreadSafeS2ContainerImpl;

public class CubbyConfigurationImplTest extends TestCase {

	public void testConstructor() throws Throwable {
		CubbyConfigurationImpl cubbyConfigurationImpl = new CubbyConfigurationImpl(
				new ThreadSafeS2ContainerImpl());
		assertEquals(
				"cubbyConfigurationImpl.getFormatPattern().getDatePattern()",
				"yyyy-MM-dd", cubbyConfigurationImpl.getFormatPattern()
						.getDatePattern());
	}

	public void testGetFormatPattern() throws Throwable {
		FormatPatternImpl result = (FormatPatternImpl) new CubbyConfigurationImpl(
				new S2ContainerImpl()).getFormatPattern();
		assertEquals("result.getDatePattern()", "yyyy-MM-dd", result
				.getDatePattern());
	}

	public void testConstructorThrowsNullPointerException() throws Throwable {
		try {
			new CubbyConfigurationImpl(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}
}
