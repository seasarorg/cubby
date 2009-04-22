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
package org.seasar.cubby.internal.controller.impl;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.controller.impl.DefaultFormatPattern;
import org.seasar.cubby.converter.ConversionHelper;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.internal.controller.FormWrapper;
import org.seasar.cubby.internal.controller.FormWrapperFactory;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.mock.MockConverterProvider;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class FormWrapperFactoryImplTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	private FormWrapperFactory formWrapperFactory;

	@Before
	public void setup() {
		final FormatPattern formatPattern = new DefaultFormatPattern();
		final BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						if (FormatPattern.class.equals(type)) {
							return type.cast(formatPattern);
						}
						throw new LookupException(type.getName());
					}
				}));
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		binderPlugin.bind(ConverterProvider.class).toInstance(
				new MockConverterProvider(new BraceConverter()));

		pluginRegistry.register(binderPlugin);

		formWrapperFactory = new FormWrapperFactoryImpl();
	}

	@After
	public void teadown() {
		pluginRegistry.clear();
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

	@Test
	public void beanToMap3() {
		AnnotatedBean bean = new AnnotatedBean();
		bean.setNormal("abc");
		bean.setSpecifiedName("def");
		bean.setSpecifiedConverter("{ghi}");
		bean.setSpecifiedNameAndConverter("{jkl}");

		FormWrapper formWrapper = formWrapperFactory.create(bean);

		String[] normal = formWrapper.getValues("normal");
		assertArrayEquals(new String[] { "abc" }, normal);

		String[] specifiedName = formWrapper.getValues("specifiedName");
		assertNull(specifiedName);

		String[] foo = formWrapper.getValues("foo");
		assertArrayEquals(new String[] { "def" }, foo);

		String[] specifiedConverter = formWrapper
				.getValues("specifiedConverter");
		assertArrayEquals(new String[] { "ghi" }, specifiedConverter);

		String[] specifiedNameAndConverter = formWrapper
				.getValues("specifiedNameAndConverter");
		assertNull(specifiedNameAndConverter);

		String[] bar = formWrapper.getValues("bar");
		assertArrayEquals(new String[] { "jkl" }, bar);
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

	public static class AnnotatedBean {

		private String normal;

		private String specifiedName;

		private String specifiedConverter;

		private String specifiedNameAndConverter;

		public String getNormal() {
			return normal;
		}

		@RequestParameter
		public void setNormal(String normal) {
			this.normal = normal;
		}

		public String getSpecifiedName() {
			return specifiedName;
		}

		@RequestParameter(name = "foo")
		public void setSpecifiedName(String specifiedName) {
			this.specifiedName = specifiedName;
		}

		public String getSpecifiedConverter() {
			return specifiedConverter;
		}

		@RequestParameter(converter = BraceConverter.class)
		public void setSpecifiedConverter(String specifiedConverter) {
			this.specifiedConverter = specifiedConverter;
		}

		public String getSpecifiedNameAndConverter() {
			return specifiedNameAndConverter;
		}

		@RequestParameter(name = "bar", converter = BraceConverter.class)
		public void setSpecifiedNameAndConverter(
				String specifiedNameAndConverter) {
			this.specifiedNameAndConverter = specifiedNameAndConverter;
		}

	}

	public static class BraceConverter implements Converter {

		public Object convertToObject(Object value, Class<?> objectType,
				ConversionHelper helper) {
			if (value == null) {
				return null;
			}
			return "{" + value + "}";
		}

		public String convertToString(Object value, ConversionHelper helper) {
			if (value == null) {
				return null;
			}
			return value.toString().substring(1, value.toString().length() - 1);
		}

		public Class<?> getObjectType() {
			return String.class;
		}

		public boolean canConvert(Class<?> parameterType, Class<?> objectType) {
			return false;
		}

	}

}
