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
package org.seasar.cubby.tags;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.controller.FormWrapper;

public class TagUtilsTest {

	@Test
	public void addClassName() throws Exception {
		Map<String, Object> dyn = new HashMap<String, Object>();
		dyn.put("class", "testString");
		TagUtils.addCSSClassName(dyn, "testTagUtilsClassName");
		assertEquals("(HashMap) dyn.get(\"class\")",
				"testString testTagUtilsClassName", dyn.get("class"));
	}

	@Test
	public void addClassName1() throws Exception {
		Map<String, Object> dyn = new HashMap<String, Object>();
		TagUtils.addCSSClassName(dyn, "testTagUtilsClassName");
		assertEquals("(HashMap) dyn.size()", 1, dyn.size());
		assertEquals("(HashMap) dyn.get(\"class\")", "testTagUtilsClassName",
				dyn.get("class"));
	}

	@Test
	public void errors() throws Exception {
		ActionErrors result = TagUtils.errors(new MockJspContext());
		assertNull("result", result);
	}

	// @Test
	// public void formValue() throws Exception {
	// Integer specifiedValue = -2;
	// Integer result = (Integer) TagUtils.formValue(new MockJspContext(),
	// new String[0], "testTagUtilsName", 2, specifiedValue);
	// assertEquals("result", specifiedValue, result);
	// }
	//
	// @Test
	// public void formValue1() throws Exception {
	// String[] strings = new String[3];
	// strings[0] = "testString";
	// String result = (String) TagUtils.formValue(new MockJspContext(),
	// strings, "testString", 0, null);
	// assertEquals("result", "testString", result);
	// }
	//
	// @Test
	// public void formValue2() throws Exception {
	// Integer specifiedValue = new Integer(0);
	// Integer result = (Integer) TagUtils.formValue(new MockJspContext(),
	// new String[0], "testTagUtilsName", 0, specifiedValue);
	// assertSame("result", specifiedValue, result);
	// }
	//
	// @Test
	// public void formValue3() throws Exception {
	// String result = (String) TagUtils.formValue(new MockJspContext(),
	// new String[0], "testTagUtilsName", 1, null);
	// assertEquals("result", "", result);
	// }
	//
	// @Test
	// public void formValue4() throws Exception {
	// String result = (String) TagUtils.formValue(new MockJspContext(),
	// new String[0], "testTagUtilsName", null, null);
	// assertEquals("result", "", result);
	// }
	//
	// @Test
	// public void formValue5() throws Exception {
	// Boolean specifiedValue = Boolean.FALSE;
	// Boolean result = (Boolean) TagUtils.formValue(new MockJspContext(),
	// new String[0], "testTagUtilsName", new Integer(-1),
	// specifiedValue);
	// assertSame("result", specifiedValue, result);
	// }
	//
	// @Test
	// public void formValue6() throws Exception {
	// String[] strings = new String[3];
	// Object result = TagUtils.formValue(new MockJspContext(), strings,
	// "testString", new Integer(0), null);
	// assertNull("result", result);
	// }
	//
	// @Test
	// public void formValueValidationFail1() throws Exception {
	// MockJspContext context = new MockJspContext();
	// context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true,
	// PageContext.REQUEST_SCOPE);
	// String result = (String) TagUtils.formValue(context, new String[0],
	// "testString", 0, null);
	// assertEquals("result", "", result);
	// }
	//
	// @Test
	// public void formValueValidationFail2() throws Exception {
	// MockJspContext context = new MockJspContext();
	// context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true,
	// PageContext.REQUEST_SCOPE);
	// String result = (String) TagUtils.formValue(context, new String[0],
	// "testString", 0, "aaa");
	// assertEquals("result", "aaa", result);
	// }
	//
	// @Test
	// public void formValueValidationFail3() throws Exception {
	// MockJspContext context = new MockJspContext();
	// context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true,
	// PageContext.REQUEST_SCOPE);
	// HashMap<String, Object[]> params = new HashMap<String, Object[]>();
	// params.put("testString", new String[] { "bbb" });
	// context.setAttribute(CubbyConstants.ATTR_PARAMS, params,
	// PageContext.REQUEST_SCOPE);
	// String result = (String) TagUtils.formValue(context, new String[0],
	// "testString", 0, "aaa");
	// assertEquals("result", "bbb", result);
	// }

	@Test
	public void isChecked() throws Exception {
		Object[] values = new Object[1];
		values[0] = "";
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	@Test
	public void isChecked1() throws Exception {
		boolean result = TagUtils.contains(new ArrayList<Integer>(100),
				"testTagUtilsValue");
		assertFalse("result", result);
	}

	@Test
	public void isChecked2() throws Exception {
		Object[] values = new Object[2];
		values[1] = "testString";
		boolean result = TagUtils.contains(values, "testString");
		assertTrue("result", result);
	}

	@Test
	public void isChecked3() throws Exception {
		Object[] values = new Object[0];
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	@Test
	public void isChecked4() throws Exception {
		Object[] values = new Object[3];
		values[0] = "";
		boolean result = TagUtils.contains(values, "");
		assertTrue("result", result);
	}

	@Test
	public void isChecked5() throws Exception {
		boolean result = TagUtils.contains("testString", "testString");
		assertTrue("result", result);
	}

	@Test
	public void isChecked6() throws Exception {
		Object[] values = new Object[1];
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	@Test
	public void isChecked7() throws Exception {
		Object[] values = new Object[3];
		values[1] = Integer.valueOf(100);
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	@Test
	public void isChecked8() throws Exception {
		boolean result = TagUtils.contains(Boolean.FALSE, "testTagUtilsValue");
		assertFalse("result", result);
	}

	@Test
	public void isChecked9() throws Exception {
		Object[] values = new Object[4];
		values[0] = "testString";
		boolean result = TagUtils.contains(values, "testString");
		assertTrue("result", result);
	}

	@Test
	public void isChecked10() throws Exception {
		Object[] values = new Object[2];
		values[0] = Integer.valueOf(-2);
		values[1] = "testString";
		boolean result = TagUtils.contains(values, "testString");
		assertTrue("result", result);
	}

	@Test
	public void multipleFormValues() throws Exception {
		String[] strings = new String[2];
		Map<String, String[]> outputValues = new HashMap<String, String[]>();
		outputValues.put("name", strings);
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new MockFormWrapper(outputValues), "testString", "name");
		assertEquals(1, result.length);
		assertNull("strings[0]", strings[0]);
	}

	@Test
	public void multipleFormValues1() throws Exception {
		Map<String, String[]> outputValues = new HashMap<String, String[]>();
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new MockFormWrapper(outputValues), "testTagUtilsName",
				"checked");
		assertEquals(1, result.length);
		assertEquals(result[0], "checked");
	}

	@Test
	public void multipleFormValues2() throws Exception {
		Map<String, String[]> outputValues = new HashMap<String, String[]>();
		outputValues.put("testTagUtilsCheckedValue", new String[0]);
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new MockFormWrapper(outputValues), "testTagUtilsName",
				"testTagUtilsCheckedValue");
		assertEquals("result.length", 1, result.length);
		assertEquals("result[0]", "testTagUtilsCheckedValue", result[0]);
	}

	// @Test
	// public void multipleFormValues3() throws Exception {
	// Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
	// null, "testTagUtilsName", null);
	// assertEquals("result.length", 0, result.length);
	// }
	//
	// @Test
	// public void multipleFormValues4() throws Exception {
	// Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
	// null, "testTagUtilsName");
	// assertEquals("result.length", 0, result.length);
	// }

	@Test
	public void multipleFormValues5() throws Exception {
		String[] strings = new String[0];
		Map<String, String[]> outputValues = new HashMap<String, String[]>();
		outputValues.put("testString", strings);
		String[] result = (String[]) TagUtils.multipleFormValues(
				new MockJspContext(), new MockFormWrapper(outputValues),
				"testString");
		assertSame("result", strings, result);
	}

	@Test
	public void multipleFormValues6() throws Exception {
		Map<String, String[]> outputValues = new HashMap<String, String[]>();
		outputValues.put("testTagUtilsName", new String[0]);
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new MockFormWrapper(outputValues), "testTagUtilsName");
		assertEquals("result.length", 0, result.length);
	}

	@Test
	public void multipleFormValues7() throws Exception {
		String[] strings = new String[3];
		Map<String, String[]> outputValues = new HashMap<String, String[]>();
		outputValues.put("testString", strings);
		String[] result = (String[]) TagUtils.multipleFormValues(
				new MockJspContext(), new MockFormWrapper(outputValues),
				"testString");
		assertSame("result", strings, result);
		assertNull("strings[0]", strings[0]);
	}

	@Test
	public void toAttr() throws Exception {
		String result = TagUtils.toAttr(new HashMap<String, Object>());
		assertEquals("result", "", result);
	}

	@Test
	public void toAttr1() throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("testString", Integer.valueOf(-32));
		String result = TagUtils.toAttr(map);
		assertEquals("result", "testString=\"-32\" ", result);
	}

	@Test
	public void addClassNameThrowsNullPointerException() throws Exception {
		try {
			TagUtils.addCSSClassName(null, "testTagUtilsClassName");
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	@Test
	public void errorsThrowsNullPointerException() throws Exception {
		try {
			TagUtils.errors(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	@Test
	public void isCheckedThrowsClassCastException() throws Exception {
		char[] values = new char[2];
		try {
			TagUtils.contains(values, "testTagUtilsValue");
			fail("Expected ClassCastException to be thrown");
		} catch (ClassCastException ex) {
			assertEquals("ex.getClass()", ClassCastException.class, ex
					.getClass());
		}
	}

	@Test
	public void isCheckedThrowsNullPointerException() throws Exception {
		try {
			TagUtils.contains(null, "testTagUtilsValue");
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	@Test
	public void toAttrThrowsNullPointerException() throws Exception {
		try {
			TagUtils.toAttr(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	static class MockFormWrapper implements FormWrapper {

		private final Map<String, String[]> outputValues;

		public MockFormWrapper(Map<String, String[]> outputValues) {
			this.outputValues = outputValues;
		}

		public boolean hasValues(String name) {
			return outputValues.containsKey(name);
		}

		public String[] getValues(String name) {
			return outputValues.get(name);
		}

	}

	@Test
	public void testGetContextPath() {
		JspContext jspContext = new MockJspContext();

		jspContext.setAttribute(CubbyConstants.ATTR_CONTEXT_PATH, "",
				PageContext.REQUEST_SCOPE);
		assertEquals("", TagUtils.getContextPath(jspContext));

		jspContext.setAttribute(CubbyConstants.ATTR_CONTEXT_PATH, "/",
				PageContext.REQUEST_SCOPE);
		assertEquals("", TagUtils.getContextPath(jspContext));

		jspContext.setAttribute(CubbyConstants.ATTR_CONTEXT_PATH, "/a",
				PageContext.REQUEST_SCOPE);
		assertEquals("/a", TagUtils.getContextPath(jspContext));
	}

}
