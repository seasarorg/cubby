package org.seasar.cubby.controller.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.container.Container;
import org.seasar.cubby.controller.FormWrapper;
import org.seasar.cubby.controller.FormWrapperFactory;
import org.seasar.cubby.factory.ConverterFactory;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.mock.MockConverterFactory;

public class FormWrapperFactoryImplTest {

	public FormWrapperFactory formWrapperFactory;

	@Before
	public void setupContainerAndFormWrapperFactory() {
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (ConverterFactory.class.equals(type)) {
					return type.cast(new MockConverterFactory());
				}
				return null;
			}

		});
		formWrapperFactory = new FormWrapperFactoryImpl();
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
