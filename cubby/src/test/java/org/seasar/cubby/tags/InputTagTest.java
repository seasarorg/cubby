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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.jdom.Element;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.internal.util.StringUtils;

public class InputTagTest extends SimpleTagTestCase {

	private InputTag tag;

	@Before
	public void setup() throws Exception {
		tag = new InputTag();
		setupSimpleTag(tag);
		setupErrors(context);
		setUpParams(context);
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "outputValue" });
		tag.setParent(new MockFormTag(map));
	}

	void setUpParams(JspContext context) {
		Map<String, Object[]> map = new HashMap<String, Object[]>();
		map.put("stringField", new String[] { "paramsValue" });
		context.setAttribute(CubbyConstants.ATTR_PARAMS, map,
				PageContext.REQUEST_SCOPE);
	}

	void setValidationFail() {
		context.setAttribute(CubbyConstants.ATTR_VALIDATION_FAIL, Boolean.TRUE,
				PageContext.REQUEST_SCOPE);
	}

	@Test
	public void doTagCheckboxValueRequred() throws Exception {
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

	@Test
	public void doTagCheckbox1() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("outputValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "outputValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "checked", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	@Test
	public void doTagCheckbox2() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagCheckboxWithCheckedValue1() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.setCheckedValue("checkedValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagCheckboxWithCheckedValue2() throws Exception {
		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.setCheckedValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "checked", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagCheckboxWithCheckedValueError1() throws Exception {
		setValidationFail();

		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.setCheckedValue("value1");
		tag.doTag();
		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagCheckboxError1() throws Exception {
		setValidationFail();

		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("paramsValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "checked", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	@Test
	public void doTagCheckboxError2() throws Exception {
		setValidationFail();

		tag.setType("checkbox");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagRadioValueRequred() throws Exception {
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

	@Test
	public void doTagRadio1() throws Exception {
		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("outputValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "outputValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "checked", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	@Test
	public void doTagRadio2() throws Exception {
		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagRadioError1() throws Exception {
		setValidationFail();

		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("paramsValue");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "checked", element.getAttributeValue("checked"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// checked=\"true\"/>\n", context.getResult());
	}

	@Test
	public void doTagRadioError2() throws Exception {
		setValidationFail();

		tag.setType("radio");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"checkbox\" value=\"value1\" name=\"stringField\"
		// />\n", context.getResult());
	}

	@Test
	public void doTagText1() throws Exception {
		tag.setType("text");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

	@Test
	public void doTagText2() throws Exception {
		tag.setType("text");
		tag.setName("stringField");
		// tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "outputValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

	@Test
	public void doTagTextError1() throws Exception {
		setValidationFail();

		tag.setType("text");
		tag.setName("stringField");
		tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

	@Test
	public void doTagTextError2() throws Exception {
		setValidationFail();

		tag.setType("text");
		tag.setName("stringField");
		// tag.setValue("value1");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtils.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "paramsValue", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		// assertEquals("valueが指定",
		// "<input type=\"text\" value=\"value1\" name=\"stringField\" />\n",
		// context.getResult());
	}

}
