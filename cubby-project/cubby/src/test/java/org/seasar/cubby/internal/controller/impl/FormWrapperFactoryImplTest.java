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
package org.seasar.cubby.internal.controller.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.internal.controller.FormWrapper;
import org.seasar.cubby.internal.controller.FormWrapperFactory;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.mock.MockConverterProvider;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class FormWrapperFactoryImplTest {

	public FormWrapperFactory formWrapperFactory;

	@Before
	public void setup() {
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						throw new LookupException();
					}
				}));
		ProviderFactory.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		ProviderFactory.bind(ConverterProvider.class).toInstance(
				new MockConverterProvider());

		formWrapperFactory = new FormWrapperFactoryImpl();
	}

	@After
	public void teadown() {
		ProviderFactory.clear();
	}

	@Test
	public void beanToMap() {
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);

		TestBean bean = new TestBean();
		bean.setDate(cal.getTime());
		bean.setNum1(5);
		bean.setNum2(new Integer[] { 2, 3, 4 });
		bean.setNum3(Arrays.asList(new String[] { "abc", "def" }));

		FormWrapper formWrapper = formWrapperFactory.create(bean);
		String[] date = formWrapper.getValues("date");
		assertEquals(1, date.length);
		assertEquals("2006-01-01", date[0]);

		String[] num1 = formWrapper.getValues("num1");
		assertEquals(1, num1.length);
		assertEquals("5", num1[0]);

		String[] num2 = formWrapper.getValues("num2");
		assertEquals(3, num2.length);
		assertEquals("2", num2[0]);
		assertEquals("3", num2[1]);
		assertEquals("4", num2[2]);

		String[] num3 = formWrapper.getValues("num3");
		assertEquals(2, num3.length);
		assertEquals("abc", num3[0]);
		assertEquals("def", num3[1]);

		String[] noprop = formWrapper.getValues("noprop");
		assertNull(noprop);
	}

	@Test
	public void beanToMap2() {
		TestBean bean = new TestBean();
		bean.setNum2(new Integer[] { null, null, null });
		bean.setNum3(Arrays.asList(new String[] { null, null }));

		FormWrapper formWrapper = formWrapperFactory.create(bean);
		String[] date = formWrapper.getValues("date");
		assertNull(date);

		String[] num1 = formWrapper.getValues("num1");
		assertNull(num1);

		String[] num2 = formWrapper.getValues("num2");
		assertEquals(3, num2.length);
		assertNull(num2[0]);
		assertNull(num2[1]);
		assertNull(num2[2]);

		String[] num3 = formWrapper.getValues("num3");
		assertEquals(2, num3.length);
		assertNull(num3[0]);
		assertNull(num3[1]);

		String[] noprop = formWrapper.getValues("noprop");
		assertNull(noprop);
	}

	public static class TestBean {

		Date date;

		Integer num1;

		Integer[] num2;

		List<String> num3;

		public Date getDate() {
			if (date == null) {
				return null;
			}
			return new Date(date.getTime());
		}

		public void setDate(Date date) {
			if (date == null) {
				this.date = null;
			} else {
				this.date = new Date(date.getTime());
			}
		}

		public Integer getNum1() {
			return num1;
		}

		public void setNum1(Integer num1) {
			this.num1 = num1;
		}

		public Integer[] getNum2() {
			return num2 == null ? null : num2.clone();
		}

		public void setNum2(Integer[] num2) {
			this.num2 = num2 == null ? null : num2.clone();
		}

		public List<String> getNum3() {
			return num3;
		}

		public void setNum3(List<String> num3) {
			this.num3 = num3;
		}

	}

}
