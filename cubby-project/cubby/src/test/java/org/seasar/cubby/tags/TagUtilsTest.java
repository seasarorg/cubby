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
package org.seasar.cubby.tags;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import junit.framework.TestCase;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionErrors;

public class TagUtilsTest extends TestCase {

	public void testConstructor() throws Throwable {
		new TagUtils();
		assertTrue("Test call resulted in expected outcome", true);
	}

	@SuppressWarnings("unchecked")
	public void testAddClassName() throws Throwable {
		Map dyn = new HashMap();
		dyn.put("class", "testString");
		TagUtils.addClassName(dyn, "testTagUtilsClassName");
		assertEquals("(HashMap) dyn.get(\"class\")",
				"testString testTagUtilsClassName", dyn.get("class"));
	}

	@SuppressWarnings("unchecked")
	public void testAddClassName1() throws Throwable {
		Map dyn = new HashMap();
		TagUtils.addClassName(dyn, "testTagUtilsClassName");
		assertEquals("(HashMap) dyn.size()", 1, dyn.size());
		assertEquals("(HashMap) dyn.get(\"class\")", "testTagUtilsClassName",
				dyn.get("class"));
	}

	public void testErrors() throws Throwable {
		ActionErrors result = TagUtils.errors(new MockJspContext());
		assertNull("result", result);
	}

	public void testFormValue() throws Throwable {
		Integer specifiedValue = -2;
		Integer result = (Integer) TagUtils.formValue(new MockJspContext(),
				new HashMap<String, String[]>(), "testTagUtilsName", 2,
				specifiedValue);
		assertEquals("result", specifiedValue, result);
	}

	public void testFormValue1() throws Throwable {
		String[] strings = new String[3];
		strings[0] = "testString";
		Map<String, String[]> outputValuesMap = new HashMap<String, String[]>();
		outputValuesMap.put("testString", strings);
		String result = (String) TagUtils.formValue(new MockJspContext(),
				outputValuesMap, "testString", 0, null);
		assertEquals("result", "testString", result);
	}

	public void testFormValue2() throws Throwable {
		Integer specifiedValue = new Integer(0);
		Integer result = (Integer) TagUtils.formValue(new MockJspContext(),
				new HashMap<String, String[]>(), "testTagUtilsName", 0,
				specifiedValue);
		assertSame("result", specifiedValue, result);
	}

	public void testFormValue3() throws Throwable {
		String result = (String) TagUtils.formValue(new MockJspContext(),
				new HashMap<String, String[]>(), "testTagUtilsName", 1, null);
		assertEquals("result", "", result);
	}

	public void testFormValue4() throws Throwable {
		String result = (String) TagUtils
				.formValue(new MockJspContext(),
						new HashMap<String, String[]>(), "testTagUtilsName",
						null, null);
		assertEquals("result", "", result);
	}

	public void testFormValue5() throws Throwable {
		Boolean specifiedValue = Boolean.FALSE;
		Boolean result = (Boolean) TagUtils.formValue(new MockJspContext(),
				new HashMap<String, String[]>(), "testTagUtilsName",
				new Integer(-1), specifiedValue);
		assertSame("result", specifiedValue, result);
	}

	@SuppressWarnings("unchecked")
	public void testFormValue6() throws Throwable {
		String[] strings = new String[3];
		Map outputValuesMap = new HashMap();
		outputValuesMap.put("testString", strings);
		Object result = TagUtils.formValue(new MockJspContext(),
				outputValuesMap, "testString", new Integer(0), null);
		assertNull("result", result);
	}

	public void testFormValueValidationFail1() throws Throwable {
		MockJspContext context = new MockJspContext();
		context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true,
				PageContext.REQUEST_SCOPE);
		String result = (String) TagUtils.formValue(context,
				new HashMap<String, String[]>(), "testString", 0, null);
		assertEquals("result", "", result);
	}

	public void testFormValueValidationFail2() throws Throwable {
		MockJspContext context = new MockJspContext();
		context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true,
				PageContext.REQUEST_SCOPE);
		String result = (String) TagUtils.formValue(context,
				new HashMap<String, String[]>(), "testString", 0, "aaa");
		assertEquals("result", "aaa", result);
	}

	public void testFormValueValidationFail3() throws Throwable {
		MockJspContext context = new MockJspContext();
		context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, true,
				PageContext.REQUEST_SCOPE);
		HashMap<String, Object[]> params = new HashMap<String, Object[]>();
		params.put("testString", new String[] { "bbb" });
		context.setAttribute(CubbyConstants.ATTR_PARAMS, params,
				PageContext.REQUEST_SCOPE);
		HashMap<String, String[]> outputValuesMap = new HashMap<String, String[]>();
		String result = (String) TagUtils.formValue(context, outputValuesMap,
				"testString", 0, "aaa");
		assertEquals("result", "bbb", result);
	}

	public void testIsChecked() throws Throwable {
		Object[] values = new Object[1];
		values[0] = "";
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	@SuppressWarnings("unchecked")
	public void testIsChecked1() throws Throwable {
		boolean result = TagUtils.contains(new ArrayList(100),
				"testTagUtilsValue");
		assertFalse("result", result);
	}

	public void testIsChecked2() throws Throwable {
		Object[] values = new Object[2];
		values[1] = "testString";
		boolean result = TagUtils.contains(values, "testString");
		assertTrue("result", result);
	}

	public void testIsChecked3() throws Throwable {
		Object[] values = new Object[0];
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	public void testIsChecked4() throws Throwable {
		Object[] values = new Object[3];
		values[0] = "";
		boolean result = TagUtils.contains(values, "");
		assertTrue("result", result);
	}

	public void testIsChecked5() throws Throwable {
		boolean result = TagUtils.contains("testString", "testString");
		assertTrue("result", result);
	}

	public void testIsChecked6() throws Throwable {
		Object[] values = new Object[1];
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	public void testIsChecked7() throws Throwable {
		Object[] values = new Object[3];
		values[1] = new Integer(100);
		boolean result = TagUtils.contains(values, "testTagUtilsValue");
		assertFalse("result", result);
	}

	public void testIsChecked8() throws Throwable {
		boolean result = TagUtils.contains(Boolean.FALSE, "testTagUtilsValue");
		assertFalse("result", result);
	}

	public void testIsChecked9() throws Throwable {
		Object[] values = new Object[4];
		values[0] = "testString";
		boolean result = TagUtils.contains(values, "testString");
		assertTrue("result", result);
	}

	public void testIsChecked10() throws Throwable {
		Object[] values = new Object[2];
		values[0] = new Integer(-2);
		values[1] = "testString";
		boolean result = TagUtils.contains(values, "testString");
		assertTrue("result", result);
	}

	@SuppressWarnings("unchecked")
	public void testMultipleFormValues() throws Throwable {
		String[] strings = new String[2];
		Map outputValuesMap = new HashMap();
		outputValuesMap.put("testString", strings);
		String[] result = (String[]) TagUtils.multipleFormValues(
				new MockJspContext(), outputValuesMap, "testString", null);
		assertSame("result", strings, result);
		assertNull("strings[0]", strings[0]);
	}

	@SuppressWarnings("unchecked")
	public void testMultipleFormValues1() throws Throwable {
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new HashMap(), "testTagUtilsName", null);
		assertEquals("result.length", 0, result.length);
	}

	@SuppressWarnings("unchecked")
	public void testMultipleFormValues2() throws Throwable {
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new HashMap(), "testTagUtilsName", "testTagUtilsCheckedValue");
		assertEquals("result.length", 1, result.length);
		assertEquals("result[0]", "testTagUtilsCheckedValue", result[0]);
	}

	public void testMultipleFormValues3() throws Throwable {
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				null, "testTagUtilsName", null);
		assertEquals("result.length", 0, result.length);
	}

	public void testMultipleFormValues4() throws Throwable {
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				null, "testTagUtilsName");
		assertEquals("result.length", 0, result.length);
	}

	@SuppressWarnings("unchecked")
	public void testMultipleFormValues5() throws Throwable {
		Map outputValuesMap = new HashMap();
		String[] strings = new String[0];
		outputValuesMap.put("testString", strings);
		String[] result = (String[]) TagUtils.multipleFormValues(
				new MockJspContext(), outputValuesMap, "testString");
		assertSame("result", strings, result);
	}

	@SuppressWarnings("unchecked")
	public void testMultipleFormValues6() throws Throwable {
		Object[] result = TagUtils.multipleFormValues(new MockJspContext(),
				new HashMap(), "testTagUtilsName");
		assertEquals("result.length", 0, result.length);
	}

	@SuppressWarnings("unchecked")
	public void testMultipleFormValues7() throws Throwable {
		Map outputValuesMap = new HashMap();
		String[] strings = new String[3];
		outputValuesMap.put("testString", strings);
		String[] result = (String[]) TagUtils.multipleFormValues(
				new MockJspContext(), outputValuesMap, "testString");
		assertSame("result", strings, result);
		assertNull("strings[0]", strings[0]);
	}

	@SuppressWarnings("unchecked")
	public void testToAttr() throws Throwable {
		String result = TagUtils.toAttr(new HashMap());
		assertEquals("result", "", result);
	}

	@SuppressWarnings("unchecked")
	public void testToAttr1() throws Throwable {
		Map map = new HashMap();
		map.put("testString", new Integer(-32));
		String result = TagUtils.toAttr(map);
		assertEquals("result", "testString=\"-32\" ", result);
	}

	public void testAddClassNameThrowsNullPointerException() throws Throwable {
		try {
			TagUtils.addClassName(null, "testTagUtilsClassName");
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	public void testErrorsThrowsNullPointerException() throws Throwable {
		try {
			TagUtils.errors(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	public void testIsCheckedThrowsClassCastException() throws Throwable {
		char[] values = new char[2];
		try {
			TagUtils.contains(values, "testTagUtilsValue");
			fail("Expected ClassCastException to be thrown");
		} catch (ClassCastException ex) {
			assertEquals("ex.getClass()", ClassCastException.class, ex
					.getClass());
		}
	}

	public void testIsCheckedThrowsNullPointerException() throws Throwable {
		try {
			TagUtils.contains(null, "testTagUtilsValue");
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}

	public void testToAttrThrowsNullPointerException() throws Throwable {
		try {
			TagUtils.toAttr(null);
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
		}
	}
}
