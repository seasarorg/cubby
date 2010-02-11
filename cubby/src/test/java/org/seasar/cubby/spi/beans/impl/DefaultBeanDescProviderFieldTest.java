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

package org.seasar.cubby.spi.beans.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import org.junit.Test;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.IllegalAttributeException;

public class DefaultBeanDescProviderFieldTest {

	BeanDescProvider beanDescProvider = new DefaultBeanDescProvider();

	@Test
	public void invalidProperty() throws Exception {
		BeanDesc beanDesc = beanDescProvider.getBeanDesc(Bean.class);
		assertFalse(beanDesc.hasPropertyAttribute("aaa"));
		assertFalse(beanDesc.hasPropertyAttribute("bbb"));
		assertTrue(beanDesc.hasFieldAttribute("aaa"));
		assertTrue(beanDesc.hasFieldAttribute("bbb"));
		Attribute aaa = beanDesc.getFieldAttribute("aaa");
		assertTrue(aaa.isReadable());
		assertTrue(aaa.isWritable());
		Attribute bbb = beanDesc.getFieldAttribute("bbb");
		assertTrue(bbb.isReadable());
		assertFalse(bbb.isWritable());

		Bean bean = new Bean();

		assertEquals("abc", aaa.getValue(bean));
		aaa.setValue(bean, "abcd");
		assertEquals("abcd", aaa.getValue(bean));

		assertEquals("123", bbb.getValue(bean));
		try {
			bbb.setValue(bean, "1234");
			fail();
		} catch (IllegalAttributeException e) {
		}
		assertEquals("123", bbb.getValue(bean));
	}

	/**
     * 
     */
	public static class Bean {
		@SuppressWarnings("unused")
		private String aaa = "abc";

		@SuppressWarnings("unused")
		private final String bbb = "123";
	}

}
