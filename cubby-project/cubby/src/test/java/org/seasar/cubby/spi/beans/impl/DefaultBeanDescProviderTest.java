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
import org.seasar.cubby.spi.beans.AttributeNotFoundException;

public class DefaultBeanDescProviderTest {

	BeanDescProvider beanDescProvider = new DefaultBeanDescProvider();

	@Test
	public void attribute() throws Exception {
		BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		Attribute propDesc = beanDesc.getPropertyAttribute("aaa");
		assertEquals("aaa", propDesc.getName());
		assertEquals(String.class, propDesc.getType());

		propDesc = beanDesc.getPropertyAttribute("CCC");
		assertEquals("CCC", propDesc.getName());
		assertEquals(boolean.class, propDesc.getType());

		propDesc = beanDesc.getPropertyAttribute("eee");
		assertEquals("eee", propDesc.getName());
		assertEquals(String.class, propDesc.getType());

		try {
			propDesc = beanDesc.getPropertyAttribute("fff");
			fail();
			assertEquals("fff", propDesc.getName());
			assertEquals(Boolean.class, propDesc.getType());
		} catch (AttributeNotFoundException e) {

		}

		assertFalse(beanDesc.hasPropertyAttribute("hhh"));
		assertFalse(beanDesc.hasPropertyAttribute("iii"));
	}

	@Test
	public void invalidProperty() throws Exception {
		BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean2.class);
		assertTrue("1", beanDesc.hasPropertyAttribute("aaa"));
		assertFalse("1", beanDesc.hasPropertyAttribute("bbb"));
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
		public String getBbb(Object a) {
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
		public void setEee(String eee) {
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
		public MyBean setHhh(String hhh) {
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
		public Number add(Number arg1, Number arg2) {
			return Integer.valueOf(3);
		}

		/**
		 * @param arg1
		 * @param arg2
		 * @return
		 */
		public int add2(int arg1, int arg2) {
			return arg1 + arg2;
		}

		/**
		 * @param arg
		 * @return
		 */
		public Integer echo(Integer arg) {
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
		private Boolean bbb;

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
		public MyBean2(int num, String text, MyBean bean1, MyBean2 bean2) {
		}

		/**
		 * @param i
		 */
		public void setAaa(int i) {
		}

		/**
		 * @param s
		 */
		public void setAaa(String s) {
		}

		public Boolean isBbb() {
			return bbb;
		}
	}

}
