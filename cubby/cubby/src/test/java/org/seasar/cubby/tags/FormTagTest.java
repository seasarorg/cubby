package org.seasar.cubby.tags;

import java.util.HashMap;

import javax.servlet.jsp.PageContext;

import org.jdom.Element;

public class FormTagTest extends JspTagTestCase {

	FormTag tag;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new FormTag();
		setupBodyTag(tag);
		context.setAttribute("fieldErrors", new HashMap<String, String>(), PageContext.REQUEST_SCOPE);
	}

	public void testDoTag1() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		tag.doStartTag();
		tag.doEndTag();

		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトが指定";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
		assertNull("フォームオブジェクトは除去されていること", context.findAttribute("__form"));
//		assertEquals("フォームオブジェクトが指定",
//				"<form action=\"/todo/save\" >\n</form>\n", context.getResult());
	}

	public void testDoTag2() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		TextareaTag textareaTag = new TextareaTag();
		setupSimpleTag(textareaTag);
		textareaTag.setDynamicAttribute(null, "name", "stringField");
		jspBody.addChildTag(textareaTag);
		tag.doStartTag();
		textareaTag.doTag();
		tag.doEndTag();

		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトが指定、子要素がある場合";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
		assertEquals(message, 1, element.getChildren().size());
		Element child = element.getChild("textarea");
		assertEquals(message, 1, child.getAttributes().size());
		assertEquals(message, "stringField", child.getAttributeValue("name"));
		assertEquals(message, "value1", child.getValue());
//		assertEquals("フォームオブジェクトが指定、子要素がある場合",
//				"<form action=\"/todo/save\" >\n" +
//				"<textarea name=\"stringField\" >value1</textarea>\n" +
//				"</form>\n", context.getResult());
	}
	public void testDoTagEmptyBody() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		tag.doStartTag();
		tag.doEndTag();

		Element element = getResultAsElementFromContext();
		String message = "Bodyが空の場合";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
//		assertEquals("Bodyが空の場合",
//				"<form action=\"/todo/save\" >\n</form>\n", context.getResult());
	}
}
