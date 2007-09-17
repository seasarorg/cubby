package org.seasar.cubby.tags;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

public class TextareaTagTest extends JspTagTestCase {

	TextareaTag tag;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new TextareaTag();
		setupSimpleTag(tag);
		context.setAttribute("fieldErrors", new HashMap<String, String>(), PageContext.REQUEST_SCOPE);
		jspBody.setBody("Dummy Body Text");
	}

	public void testDoTag1() throws Exception {
		tag.setDynamicAttribute(null, "name", "content");
		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "id", "content");
		tag.doTag();
		assertEquals("フォームオブジェクトが空でvalueが指定されている場合", 
				"<textarea name=\"content\" id=\"content\" >value1</textarea>\n", context.getResult());
	}

	public void testDoTag2() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.doTag();
		assertEquals("フォームオブジェクトとname指定の場合",
				"<textarea name=\"stringField\" >value1</textarea>", context.getResult());
	}

	public void testDoTag3() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		tag.setDynamicAttribute(null, "name", "stringField");
		tag.doTag();
		assertEquals("フォームオブジェクトが空でとnameが指定されている場合",
				"<textarea name=\"stringField\" ></textarea>", context.getResult());
	}
}
