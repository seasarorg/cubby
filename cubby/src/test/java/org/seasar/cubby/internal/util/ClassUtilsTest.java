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
package org.seasar.cubby.internal.util;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;

public class ClassUtilsTest {

	@Test
	public void concatName() {
		assertNull(ClassUtils.concatName(null, null));
		assertEquals("abc", ClassUtils.concatName("abc", null));
		assertEquals("abc", ClassUtils.concatName("abc", ""));
		assertEquals("Def", ClassUtils.concatName(null, "Def"));
		assertEquals("Def", ClassUtils.concatName("", "Def"));
		assertEquals("abc.Def", ClassUtils.concatName("abc", "Def"));
	}

	@Test
	public void forName() {
		Class<ClassUtils> clazz = ClassUtils
				.forName("org.seasar.cubby.internal.util.ClassUtils");
		assertTrue(clazz.isAssignableFrom(ClassUtils.class));

		try {
			ClassUtils.forName("nothing");
			fail();
		} catch (IllegalStateException e) {
			assertTrue(e.getCause() instanceof ClassNotFoundException);
		}
	}

	@Test
	public void getWrapperClassIfPrimitive() {
		assertEquals(Character.class, ClassUtils
				.getWrapperClassIfPrimitive(Character.TYPE));
		assertEquals(Byte.class, ClassUtils
				.getWrapperClassIfPrimitive(Byte.TYPE));
		assertEquals(Short.class, ClassUtils
				.getWrapperClassIfPrimitive(Short.TYPE));
		assertEquals(Integer.class, ClassUtils
				.getWrapperClassIfPrimitive(Integer.TYPE));
		assertEquals(Long.class, ClassUtils
				.getWrapperClassIfPrimitive(Long.TYPE));
		assertEquals(Double.class, ClassUtils
				.getWrapperClassIfPrimitive(Double.TYPE));
		assertEquals(Float.class, ClassUtils
				.getWrapperClassIfPrimitive(Float.TYPE));
		assertEquals(Boolean.class, ClassUtils
				.getWrapperClassIfPrimitive(Boolean.TYPE));

		assertEquals(Character.class, ClassUtils
				.getWrapperClassIfPrimitive(Character.class));
		assertEquals(Byte.class, ClassUtils
				.getWrapperClassIfPrimitive(Byte.class));
		assertEquals(Short.class, ClassUtils
				.getWrapperClassIfPrimitive(Short.class));
		assertEquals(Integer.class, ClassUtils
				.getWrapperClassIfPrimitive(Integer.class));
		assertEquals(Long.class, ClassUtils
				.getWrapperClassIfPrimitive(Long.class));
		assertEquals(Double.class, ClassUtils
				.getWrapperClassIfPrimitive(Double.class));
		assertEquals(Float.class, ClassUtils
				.getWrapperClassIfPrimitive(Float.class));
		assertEquals(Boolean.class, ClassUtils
				.getWrapperClassIfPrimitive(Boolean.class));

		assertEquals(Object.class, ClassUtils
				.getWrapperClassIfPrimitive(Object.class));
	}

}
