package org.seasar.cubby.tags;

import java.util.Date;
import java.util.HashMap;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.PageContext;

import org.jdom.Element;
import org.seasar.framework.util.StringUtil;

public class InputTagTest extends JspTagTestCase {

	InputTag tag;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new InputTag();
		setupSimpleTag(tag);
		context.setAttribute("fieldErrors", new HashMap<String, String>(), PageContext.REQUEST_SCOPE);
	}

	public void testDoTagCheckboxValueRequred() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "checkedValue", "value1");
		tag.setType("checkbox");
		try {
			tag.doTag();
			fail("checkboxではvalue属性は必須");
		} catch (JspException ex) {
			assertTrue(true);
		}
	}
	
	public void testDoTagCheckbox1() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "checkedValue", "value1");
		tag.setType("checkbox");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagCheckbox2() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "checkedValue", "value2");
		tag.setType("checkbox");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  />\n", context.getResult());
	}

	public void testDoTagCheckbox3() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("checkbox");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagCheckbox4() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value2");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("checkbox");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "checkbox", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  />\n", context.getResult());
	}

	public void testDoTagRadioValueRequred() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "checkedValue", "value1");
		tag.setType("radio");
		try {
			tag.doTag();
			fail("checkboxではvalue属性は必須");
		} catch (JspException ex) {
			assertTrue(true);
		}
	}

	public void testDoTagRadio1() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "checkedValue", "value1");
		tag.setType("radio");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagRadio2() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "checkedValue", "value2");
		tag.setType("radio");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  />\n", context.getResult());
	}

	public void testDoTagRadio3() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("radio");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 4, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
		assertEquals(message, "true", element.getAttributeValue("checked"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagRadio4() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value2");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("radio");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "radio", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  />\n", context.getResult());
	}

	public void testDoTagText1() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("text");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"text\" value=\"value1\" name=\"stringField\" />\n", context.getResult());
	}

	public void testDoTagText2() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("text");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "stringField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"text\" value=\"value1\" name=\"stringField\" />\n", context.getResult());
	}

	public void testDoTagTextDate() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "dateField");
		tag.setDynamicAttribute(null, "value", new Date());
		tag.setType("text");
		tag.doTag();

		Element element = getResultAsElementFromContext();
		String message = "valueが指定";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 3, element.getAttributes().size());
		assertEquals(message, "text", element.getAttributeValue("type"));
		//assertEquals(message, "value1", element.getAttributeValue("value"));
		assertEquals(message, "dateField", element.getAttributeValue("name"));
//		assertEquals("valueが指定",
//				"<input type=\"text\" value=\"value1\" name=\"stringField\" />\n", context.getResult());
	}

}
