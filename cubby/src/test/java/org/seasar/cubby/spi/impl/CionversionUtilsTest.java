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
package org.seasar.cubby.spi.impl;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CionversionUtilsTest {

	@Test
	public void getWrapperClassIfPrimitive() {
		assertEquals(Character.class, ConversionUtils
				.getWrapperClassIfPrimitive(Character.TYPE));
		assertEquals(Byte.class, ConversionUtils
				.getWrapperClassIfPrimitive(Byte.TYPE));
		assertEquals(Short.class, ConversionUtils
				.getWrapperClassIfPrimitive(Short.TYPE));
		assertEquals(Integer.class, ConversionUtils
				.getWrapperClassIfPrimitive(Integer.TYPE));
		assertEquals(Long.class, ConversionUtils
				.getWrapperClassIfPrimitive(Long.TYPE));
		assertEquals(Double.class, ConversionUtils
				.getWrapperClassIfPrimitive(Double.TYPE));
		assertEquals(Float.class, ConversionUtils
				.getWrapperClassIfPrimitive(Float.TYPE));
		assertEquals(Boolean.class, ConversionUtils
				.getWrapperClassIfPrimitive(Boolean.TYPE));

		assertEquals(Character.class, ConversionUtils
				.getWrapperClassIfPrimitive(Character.class));
		assertEquals(Byte.class, ConversionUtils
				.getWrapperClassIfPrimitive(Byte.class));
		assertEquals(Short.class, ConversionUtils
				.getWrapperClassIfPrimitive(Short.class));
		assertEquals(Integer.class, ConversionUtils
				.getWrapperClassIfPrimitive(Integer.class));
		assertEquals(Long.class, ConversionUtils
				.getWrapperClassIfPrimitive(Long.class));
		assertEquals(Double.class, ConversionUtils
				.getWrapperClassIfPrimitive(Double.class));
		assertEquals(Float.class, ConversionUtils
				.getWrapperClassIfPrimitive(Float.class));
		assertEquals(Boolean.class, ConversionUtils
				.getWrapperClassIfPrimitive(Boolean.class));

		assertEquals(Object.class, ConversionUtils
				.getWrapperClassIfPrimitive(Object.class));
	}

}
