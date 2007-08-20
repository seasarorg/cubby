package org.seasar.cubby.controller.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import org.seasar.extension.unit.S2TestCase;

public class PopulatorImplTest extends S2TestCase {
	private PopulatorImpl populator;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		include("app.dicon");
	}

	public void testEmptyString2Integer() {
		TestForm form = new TestForm();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num1", new String[] { "100" });
		populator.populate(params, form);
		assertEquals("文字列から数値に変換", new Integer(100), form.getNum1());
	}

	public void testEmptyStringArray2IntegerArray() {
		TestForm form = new TestForm();
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("num2", null);
		populator.populate(params, form);
		assertNull("値なしの場合", form.getNum2());
	}

	public static class TestForm {
		public static final String DATE_PATTERN = "yyyy-MM-dd";

		Date date;

		Integer num1;

		Integer[] num2;

		public Date getDate() {
			return date;
		}

		public void setDate(Date date) {
			this.date = date;
		}

		public Integer getNum1() {
			return num1;
		}

		public void setNum1(Integer num1) {
			this.num1 = num1;
		}

		public Integer[] getNum2() {
			return num2;
		}

		public void setNum2(Integer[] num2) {
			this.num2 = num2;
		}
	}
}
