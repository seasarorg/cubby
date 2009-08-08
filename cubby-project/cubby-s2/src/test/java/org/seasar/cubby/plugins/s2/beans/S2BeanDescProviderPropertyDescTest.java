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
import static org.junit.Assert.fail;

import java.math.BigDecimal;
import java.net.URL;
import java.sql.Timestamp;
import java.util.Calendar;

import org.junit.Test;
import org.seasar.cubby.plugins.s2.spi.S2BeanDescProvider;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.IllegalAttributeException;

public class S2BeanDescProviderPropertyDescTest {

	BeanDescProvider beanDescProvider = new S2BeanDescProvider();

	@Test
	public void setValue() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("fff");
		attribute.setValue(myBean, new BigDecimal(2));
		assertEquals(2, myBean.getFff());
	}

	@Test
	public void setValue_null() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("fff");
		attribute.setValue(myBean, null);
		assertEquals(0, myBean.getFff());
	}

	@Test
	public void setValue_notWritable() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("aaa");
		try {
			attribute.setValue(myBean, null);
			fail();
		} catch (final IllegalAttributeException e) {
			System.out.println(e);
		}
	}

	@Test
	public void setValue_notWritableWithField() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("jjj");
		try {
			attribute.setValue(myBean, null);
			fail();
		} catch (final IllegalAttributeException e) {
			System.out.println(e);
		}
	}

	@Test
	public void getValue_notReable() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("iii");
		try {
			attribute.getValue(myBean);
			fail();
		} catch (final IllegalAttributeException e) {
			System.out.println(e);
		}
	}

	@Test
	public void getValue_notReableWithField() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("kkk");
		try {
			attribute.getValue(myBean);
			fail();
		} catch (final IllegalAttributeException e) {
			System.out.println(e);
		}
	}

	@Test
	public void setIllegalValue() throws Exception {
		final MyBean myBean = new MyBean();
		final BeanDesc beanDesc = beanDescProvider.getBeanDesc(MyBean.class);
		final Attribute attribute = beanDesc.getPropertyAttribute("fff");
		try {
			attribute.setValue(myBean, "hoge");
			fail("1");
		} catch (final IllegalAttributeException ex) {
			System.out.println(ex);
		}
	}

	/**
     * 
     */
	public static class MyBean {

		private int fff_;

		private BigDecimal ggg_;

		private Timestamp hhh_;

		private String jjj;

		String kkk;

		private URL url_;

		private Calendar cal;

		/**
         * 
         */
		public String str;

		/**
		 * @return
		 */
		public String getAaa() {
			return null;
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
			return null;
		}

		/**
		 * @param eee
		 */
		public void setEee(final String eee) {
		}

		/**
		 * @return
		 */
		public int getFff() {
			return fff_;
		}

		/**
		 * @param fff
		 */
		public void setFff(final int fff) {
			fff_ = fff;
		}

		/**
		 * @return
		 */
		public String getJjj() {
			return jjj;
		}

		/**
		 * @param kkk
		 */
		public void setKkk(final String kkk) {
			this.kkk = kkk;
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
		 * @return
		 */
		public BigDecimal getGgg() {
			return ggg_;
		}

		/**
		 * @param ggg
		 */
		public void setGgg(final BigDecimal ggg) {
			this.ggg_ = ggg;
		}

		/**
		 * @return
		 */
		public Timestamp getHhh() {
			return hhh_;
		}

		/**
		 * @param hhh
		 */
		public void setHhh(final Timestamp hhh) {
			this.hhh_ = hhh;
		}

		/**
		 * @param iii
		 */
		public void setIii(final String iii) {
		}

		/**
		 * @return
		 */
		public URL getURL() {
			return url_;
		}

		/**
		 * @param url
		 */
		public void setURL(final URL url) {
			url_ = url;
		}

		/**
		 * @return Returns the cal.
		 */
		public Calendar getCal() {
			return cal;
		}

		/**
		 * @param cal
		 *            The cal to set.
		 */
		public void setCal(final Calendar cal) {
			this.cal = cal;
		}
	}

}
