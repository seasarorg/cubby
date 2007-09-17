package org.seasar.cubby.tags;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

public class FormTagTest extends JspTagTestCase {

	FormTag tag;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new FormTag();
		setupSimpleTag(tag);
		context.setAttribute("fieldErrors", new HashMap<String, String>(), PageContext.REQUEST_SCOPE);
	}

	public void testDoTag1() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		tag.doTag();
		assertNull("フォームオブジェクトは除去されていること", context.findAttribute("__form"));
		assertEquals("フォームオブジェクトが指定",
				"<form action=\"/todo/save\" >\n</form>\n", context.getResult());
	}

	public void testDoTag2() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		TextareaTag textareaTag = new TextareaTag();
		textareaTag.setDynamicAttribute(null, "name", "stringField");
		jspBody.addChildTag(textareaTag);
		tag.doTag();
		assertEquals("フォームオブジェクトが指定、子要素がある場合",
				"<form action=\"/todo/save\" >\n<textarea name=\"stringField\" >value1</textarea>\n</form>\n", context.getResult());
	}
}
