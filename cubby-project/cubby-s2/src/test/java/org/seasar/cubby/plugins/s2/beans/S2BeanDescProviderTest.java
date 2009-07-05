/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.plugins.s2.beans;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Test;
import org.seasar.cubby.plugins.s2.spi.S2BeanDescProvider;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;

public class S2BeanDescProviderTest {

	BeanDescProvider beanDescProvider = new S2BeanDescProvider();

	@Test
	public void propertyDesc() throws Exception {
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		assertEquals(5, beanDesc.findtPropertyAttributes().size());
		Attribute propDesc = beanDesc.getPropertyAttribute("aaa");
		assertEquals("aaa", propDesc.getName());
		assertEquals(String.class, propDesc.getType());
		assertTrue(propDesc.isReadable());
		assertFalse(propDesc.isWritable());

		propDesc = beanDesc.getPropertyAttribute("CCC");
		assertEquals("CCC", propDesc.getName());
		assertEquals(boolean.class, propDesc.getType());
		assertTrue(propDesc.isReadable());
		assertFalse(propDesc.isWritable());

		propDesc = beanDesc.getPropertyAttribute("eee");
		assertEquals("eee", propDesc.getName());
		assertEquals(String.class, propDesc.getType());
		assertTrue(propDesc.isReadable());
		assertTrue(propDesc.isWritable());

		propDesc = beanDesc.getPropertyAttribute("fff");
		assertEquals("fff", propDesc.getName());
		assertEquals(Boolean.class, propDesc.getType());

		assertFalse(beanDesc.hasPropertyAttribute("hhh"));
		assertFalse(beanDesc.hasPropertyAttribute("iii"));
	}

	@Test
	public void invalidProperty() throws Exception {
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean2.class);
		assertEquals("1", false, beanDesc.hasPropertyAttribute("aaa"));
	}

	/**
     * 
     */
	public static interface MyInterface {
		/**
         * 
         */
		String HOGE = "hoge";
	}

	/**
     * 
     */
	public static interface MyInterface2 extends MyInterface {
		/**
         * 
         */
		String HOGE = "hoge2";
	}

	/**
     * 
     */
	public static class MyBean implements MyInterface2 {

		private String aaa;

		private String eee;

		private Boolean fff;

		/**
         * 
         */
		public String ggg;

		/**
		 * @return
		 */
		public String getAaa() {
			return aaa;
		}

		/**
		 * @param a
		 * @return
		 */
		public String getBbb(final Object a) {
			return null;
		}

		/**
		 * @return
		 */
		public boolean isCCC() {
			return true;
		}

		/**
		 * @return
		 */
		public Object isDdd() {
			return null;
		}

		/**
		 * @return
		 */
		public String getEee() {
			return eee;
		}

		/**
		 * @param eee
		 */
		public void setEee(final String eee) {
			this.eee = eee;
		}

		/**
		 * @return
		 */
		public Boolean isFff() {
			return fff;
		}

		/**
		 * @param hhh
		 * @return
		 */
		public MyBean setHhh(final String hhh) {
			return this;
		}

		/**
         * 
         */
		public void getIii() {
		}

		/**
		 * @param arg1
		 * @param arg2
		 * @return
		 */
		public Number add(final Number arg1, final Number arg2) {
			return Integer.valueOf(3);
		}

		/**
		 * @param arg1
		 * @param arg2
		 * @return
		 */
		public int add2(final int arg1, final int arg2) {
			return arg1 + arg2;
		}

		/**
		 * @param arg
		 * @return
		 */
		public Integer echo(final Integer arg) {
			return arg;
		}

		/**
         * 
         */
		public void throwException() {
			throw new IllegalStateException("hoge");
		}
	}

	/**
     * 
     */
	public class MyBean2 {
		/**
         * 
         */
		public MyBean2() {
		}

		/**
		 * @param num
		 * @param text
		 * @param bean1
		 * @param bean2
		 */
		public MyBean2(final int num, final String text, final MyBean bean1,
				final MyBean2 bean2) {
		}

		/**
		 * @param i
		 */
		public void setAaa(final int i) {
		}

		/**
		 * @param s
		 */
		public void setAaa(final String s) {
		}
	}

}
