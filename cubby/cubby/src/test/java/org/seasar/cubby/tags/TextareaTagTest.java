package org.seasar.cubby.tags;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.jsp.PageContext;

import org.jdom.Element;
import org.seasar.cubby.CubbyConstants;
import org.seasar.framework.util.StringUtil;

public class TextareaTagTest extends JspTagTestCase {

	TextareaTag tag;
	
	@Override
	protected void setUp() throws Exception {
		super.setUp();
		tag = new TextareaTag();
		setupSimpleTag(tag);
		setupErrors(context);
		jspBody.setBody("Dummy Body Text");
	}

	public void testDoTag1() throws Exception {
		tag.setName("content");
		tag.setValue("value1");
//		tag.setDynamicAttribute(null, "name", "content");
//		tag.setDynamicAttribute(null, "value", "value1");
		tag.setDynamicAttribute(null, "id", "content");
		tag.doTag();
		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトが空でvalueが指定されている場合";
		assertEquals(message, "value1", element.getValue());
		assertEquals(message, 2, element.getAttributes().size());
		assertEquals(message, "content", element.getAttributeValue("id"));
		assertEquals(message, "content", element.getAttributeValue("name"));
	}

	public void testDoTag2() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		Map<String, String[]> map = new HashMap<String, String[]>();
		map.put("stringField", new String[] { "value1" });
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
		context.setAttribute("__form", form, PageContext.REQUEST_SCOPE);
		tag.setName("stringField");
//		tag.setDynamicAttribute(null, "name", "stringField");
		tag.doTag();
		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトとname指定の場合";
		assertEquals(message, "value1", element.getValue());
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
	}

	public void testDoTag3() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");
		Map<String, String[]> map = new HashMap<String, String[]>();
		context.setAttribute(CubbyConstants.ATTR_OUTPUT_VALUES, map, PageContext.PAGE_SCOPE);
		tag.setName("stringField");
//		tag.setDynamicAttribute(null, "name", "stringField");
		tag.doTag();
		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトが空でnameが指定されている場合";
		assertTrue(message, StringUtil.isEmpty(element.getValue()));
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "stringField", element.getAttributeValue("name"));
	}

}
