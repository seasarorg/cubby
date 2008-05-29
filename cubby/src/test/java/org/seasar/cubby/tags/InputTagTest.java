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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.jdom.Element;
import org.seasar.cubby.CubbyConstants;
import org.seasar.framework.util.StringUtil;

public class InputTagTest extends JspTagTestCase {

	InputTag tag;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new InputTag();
		setupSimpleTag(tag);
		setupErrors(context);
		setUpParams(context);
		setUpOutputValues(context);
	}

	void setUpParams(JspContext context) {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("stringField", new String[] { "paramsValue" });
		context.setAttribute(CubbyConstants.ATTR_PARAMS, map,
				PageContext.REQUEST_SCOPE);
	}

	void setUpOutputValues(JspContext context) {
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "outputValue" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map,
				PageContext.PAGE_SCOPE);
	}

	void setValidationFail() {
		context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, Boolean.TRUE,
				PageContext.REQUEST_SCOPE);
	}

	public void testDoTagCheckboxValueRequred() throws Exception {
		tag.setName("stringField");
		tag.setCheckedValue("value1");
		tag.setType("checkbox");
		try {
			tag.doTag();
			fail("checkboxではvalue属性は必須");
		} catch (JspException ex) {
			System.out.println(ex);
			assertTrue(true);
		}
	}

	public void testDoTagCheckbox1() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("outputValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "outputValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagCheckbox2() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagCheckboxWithCheckedValue1() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.setCheckedValue("checkedValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagCheckboxWithCheckedValue2() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.setCheckedValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagCheckboxWithCheckedValueError1() throws Exception {
		setValidationFail();

		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.setCheckedValue("value1");
		tag.doTag();
		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagCheckboxError1() throws Exception {
		setValidationFail();

		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("paramsValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagCheckboxError2() throws Exception {
		setValidationFail();

		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagRadioValueRequred() throws Exception {
		tag.setName("stringField");
		tag.setCheckedValue("value1");
		tag.setType("radio");
		try {
			tag.doTag();
			fail("radioではvalue属性は必須");
		} catch (JspException ex) {
			assertTrue(true);
		}
	}

	public void testDoTagRadio1() throws Exception {
		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("outputValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "outputValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagRadio2() throws Exception {
		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagRadioError1() throws Exception {
		setValidationFail();

		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("paramsValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagRadioError2() throws Exception {
		setValidationFail();

		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	public void testDoTagText1() throws Exception {
		tag.setType("text");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

	public void testDoTagText2() throws Exception {
		tag.setType("text");
		tag.setName("stringField");
		// tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "outputValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

	public void testDoTagTextError1() throws Exception {
		setValidationFail();

		tag.setType("text");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

	public void testDoTagTextError2() throws Exception {
		setValidationFail();

		tag.setType("text");
		tag.setName("stringField");
		// tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

}
