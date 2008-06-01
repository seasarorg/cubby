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

import javax.servlet.jsp.PageContext;

import org.jdom.Element;
import org.seasar.cubby.CubbyConstants;

public class LinkTagTest extends SimpleTagTestCase {

	private LinkTag tag;

	@Override
	protected void setUp() throws Exception {
		include(getClass().getName().replace('.', '/') + ".dicon");
		super.setUp();
		tag = new LinkTag();
		setupSimpleTag(tag);
		context.setAttribute(CubbyConstants.ATTR_CONTEXT_PATH, "/brabra",
				PageContext.REQUEST_SCOPE);
	}

	public void testDoTag() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("foo");
		tag.doTag();

		System.out.println(context.getResult());

		assertEquals("URL出力", "/brabra/mockFormTagTest/foo", context
				.getResult());
	}

	public void testDoTagWithParam() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("bar");
		ParamTag paramTag1 = new ParamTag();
		paramTag1.setParent(tag);
		paramTag1.setName("id");
		paramTag1.setValue("123");
		setupSimpleTag(paramTag1);
		ParamTag paramTag2 = new ParamTag();
		paramTag2.setParent(tag);
		paramTag2.setName("token");
		paramTag2.setValue("abc");
		setupSimpleTag(paramTag2);
		jspBody.addChild(paramTag1);
		jspBody.addChild(paramTag2);
		tag.doTag();

		System.out.println(context.getResult());

		assertEquals("パラメータ付きでURL出力",
				"/brabra/mockFormTagTest/bar/123?token=abc", context
						.getResult());
	}

	public void testDoTagOutputTag() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("foo");
		tag.setTag("a");
		tag.setAttr("href");
		jspBody.setBody("body");
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "タグ出力";
		assertEquals(message, "a", element.getName());
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/brabra/mockFormTagTest/foo", element
				.getAttributeValue("href"));
		assertEquals(message, 0, element.getChildren().size());
		assertEquals(message, "body", element.getValue());
	}

	public void testDoTagOutputTagWithParam() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("bar");
		tag.setTag("img");
		tag.setAttr("src");
		ParamTag paramTag1 = new ParamTag();
		paramTag1.setParent(tag);
		paramTag1.setName("id");
		paramTag1.setValue("123");
		setupSimpleTag(paramTag1);
		ParamTag paramTag2 = new ParamTag();
		paramTag2.setParent(tag);
		paramTag2.setName("token");
		paramTag2.setValue("abc");
		setupSimpleTag(paramTag2);
		jspBody.addChild(paramTag1);
		jspBody.addChild(paramTag2);
		tag.doTag();

		System.out.println(context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "パラメータ付きでタグ出力";
		assertEquals(message, "img", element.getName());
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/brabra/mockFormTagTest/bar/123?token=abc",
				element.getAttributeValue("src"));
		assertEquals(message, 0, element.getChildren().size());
	}
}
