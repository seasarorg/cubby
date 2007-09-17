package org.seasar.cubby.tags;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

public class InputTagTest extends JspTagTestCase {

	InputTag tag;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new InputTag();
		setupSimpleTag(tag);
		context.setAttribute("fieldErrors", new HashMap<String, String>(), PageContext.REQUEST_SCOPE);
	}

	public void testDoTagCheckbox1() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "checkedValue", "value1");
		tag.setType("checkbox");
		tag.doTag();
		assertEquals("valueが指定",
				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagCheckbox2() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "checkedValue", "value2");
		tag.setType("checkbox");
		tag.doTag();
		assertEquals("valueが指定",
				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  />\n", context.getResult());
	}

	public void testDoTagCheckbox3() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("checkbox");
		tag.doTag();
		assertEquals("valueが指定",
				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  checked=\"true\"/>\n", context.getResult());
	}

	public void testDoTagCheckbox4() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value2");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("checkbox");
		tag.doTag();
		assertEquals("valueが指定",
				"<input type=\"checkbox\" value=\"value1\" name=\"stringField\"  />\n", context.getResult());
	}

	public void testDoTagText1() throws Exception {
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("text");
		tag.doTag();
		assertEquals("valueが指定",
				"<input type=\"text\" value=\"value1\" name=\"stringField\" />\n", context.getResult());
	}

	public void testDoTagText2() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setType("text");
		tag.doTag();
		assertEquals("valueが指定",
				"<input type=\"text\" value=\"value1\" name=\"stringField\" />\n", context.getResult());
	}
}
