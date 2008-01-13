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

import java.io.IOException;
import java.io.StringReader;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.BodyTag;
import javax.servlet.jsp.tagext.SimpleTag;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.JDOMException;
import org.jdom.input.SAXBuilder;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.dxo.FormDxo;
import org.seasar.extension.unit.S2TestCase;

public class FormTagTest extends S2TestCase {

	protected MockJspFragment jspBody;

	protected MockJspContext context;

	public HttpServletRequest request;

	FormTag tag;

	FormDxo formDxo;

	@Override
	protected void setUp() throws Exception {
		super.setUp();
        include(getClass().getName().replace('.', '/') + ".dicon");
        jspBody = new MockJspFragment();
		context = new MockJspContext();
		jspBody.setJspContext(context);
		tag = new FormTag();
		setupBodyTag(tag);
		setupErrors(context);
	}

	protected void setupSimpleTag(SimpleTag tag) {
		tag.setJspBody(jspBody);
		tag.setJspContext(context);
	}

	protected void setupBodyTag(BodyTag tag) {
		tag.setPageContext(context);
	}

	protected Element getResultAsElementFromContext() throws JDOMException,
			IOException {
		String result = context.getResult();
		Document document = new SAXBuilder().build(new StringReader(result));
		Element element = document.getRootElement();
		return element;
	}

	public void setupErrors(JspContext context) {
		ActionErrorsImpl errors = new ActionErrorsImpl();
		context.setAttribute("errors", errors, PageContext.REQUEST_SCOPE);
	}

	public void testDoTag1() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		tag.doStartTag();
		tag.doEndTag();

		System.out.println(context.getResult());

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
		textareaTag.setName("stringField");
		jspBody.addChildTag(textareaTag);
		tag.doStartTag();
		textareaTag.doTag();
		tag.doEndTag();

		System.out.println(context.getResult());

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

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "Bodyが空の場合";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
//		assertEquals("Bodyが空の場合",
//				"<form action=\"/todo/save\" >\n</form>\n", context.getResult());
	}

}
