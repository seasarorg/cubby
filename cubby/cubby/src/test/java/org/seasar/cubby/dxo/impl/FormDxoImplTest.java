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
package org.seasar.cubby.dxo.impl;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.dxo.FormDxo;
import org.seasar.extension.unit.S2TestCase;

/**
 * 
 * @author agata
 * @author baba
 */
public class FormDxoImplTest extends S2TestCase {

	public FormDxo formDxo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include(getClass().getName().replace('.', '/') + ".dicon");
	}

	public void testBeanToMapNullSource() {
		TestBean bean = null;
		Map<String, String[]> map = new HashMap<String, String[]>();
		formDxo.convert(bean, map);
	}

	public void testMapToBeanNullSource() {
		TestBean bean = new TestBean();
		Map<String, Object[]> map = null;
		formDxo.convert(map, bean);
	}

	public void testBeanToMap() {
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);

		TestBean bean = new TestBean();
		bean.setDate(cal.getTime());
		bean.setNum1(5);
		bean.setNum2(new Integer[] { 2, 3, 4 });
		bean.setNum3(Arrays.asList(new String[] { "abc", "def" }));

		Map<String, String[]> map = new HashMap<String, String[]>();

		formDxo.convert(bean, map);
		assertTrue(map.containsKey("date"));
		String[] values = map.get("date");
		assertEquals(1, values.length);
		assertEquals("2006-01-01", values[0]);
	}

	public void testMapToBean() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("date", new Object[] { "2006-01-01" });

		TestBean bean = new TestBean();

		formDxo.convert(map, bean);
		Calendar cal = Calendar.getInstance();
		cal.set(2006, 0, 1);
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		assertEquals(format.format(cal.getTime()), format
				.format(bean.getDate()));
	}

	public void testMapToBean_OneValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num1", new Object[] { "1" });
		map.put("num2", new Object[] { "2" });
		map.put("num3", new Object[] { "def" });

		TestBean bean = new TestBean();

		formDxo.convert(map, bean);
		assertNotNull(bean.getNum1());
		assertEquals(Integer.valueOf(1), bean.getNum1());
		assertNotNull(bean.getNum2());
		assertEquals(1, bean.getNum2().length);
		assertEquals(Integer.valueOf(2), bean.getNum2()[0]);
		assertNotNull(bean.getNum3());
		assertEquals(1, bean.getNum3().size());
		assertEquals("def", bean.getNum3().get(0));
	}

	public void testMapToBean_MultiValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new Object[] { "1", "2" });
		map.put("num3", new Object[] { "abc", "def" });

		TestBean bean = new TestBean();

		formDxo.convert(map, bean);
		assertNotNull(bean.getNum2());
		assertEquals(2, bean.getNum2().length);
		assertEquals(Integer.valueOf(1), bean.getNum2()[0]);
		assertEquals(Integer.valueOf(2), bean.getNum2()[1]);
		assertNotNull(bean.getNum3());
		assertEquals(2, bean.getNum3().size());
		assertEquals("abc", bean.getNum3().get(0));
		assertEquals("def", bean.getNum3().get(1));
	}

	public void testMapToBean_MultiValueIncludesEmptyValue() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("num2", new String[] { "1", "", "2" });

		TestBean bean = new TestBean();

		formDxo.convert(map, bean);
		assertEquals(3, bean.getNum2().length);
		assertEquals(Integer.valueOf(1), bean.getNum2()[0]);
		assertEquals(null, bean.getNum2()[1]);
		assertEquals(Integer.valueOf(2), bean.getNum2()[2]);
	}

	public void testNoCloneableObject() {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		NoDefaultConstructorBean bean = new NoDefaultConstructorBean("1");
		map.put("bean", new Object[] { bean });

		NoDefaultConstructorBeanHolder dest = new NoDefaultConstructorBeanHolder();

		formDxo.convert(map, dest);
		assertNotNull(dest.bean);
		assertSame(bean, dest.bean);
	}

	public static class TestBean {

		Date date;

		Integer num1;

		Integer[] num2;

		List<String> num3;

		public Date getDate() {
			return new Date(date.getTime());
		}

		public void setDate(Date date) {
			this.date = new Date(date.getTime());
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

	public static class NoDefaultConstructorBeanHolder {
		public NoDefaultConstructorBean bean;
	}

	public static class NoDefaultConstructorBean {
		public String prop1;

		public NoDefaultConstructorBean(String prop1) {
			this.prop1 = prop1;
		}
	}
}
