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

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

import org.jdom.Element;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.mock.MockConverterProvider;
import org.seasar.cubby.mock.MockPathResolverProvider;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

public class FormTagTest extends AbstractStandardTagTestCase {

	private FormTag tag;

	@Before
	public void setupProvider() {
		final PathResolver pathResolver = new PathResolverImpl();
		pathResolver.add("/mockFormTagTest/foo", MockFormTagTestAction.class,
				"foo", RequestMethod.GET, null, 0);
		pathResolver.add("/mockFormTagTest/bar/{id}",
				MockFormTagTestAction.class, "bar", RequestMethod.GET, null, 0);
		ProviderFactory.bind(PathResolverProvider.class).toInstance(
				new MockPathResolverProvider(pathResolver));
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						throw new LookupException();
					}

				}));
		ProviderFactory.bind(ConverterProvider.class).toInstance(
				new MockConverterProvider());
	}

	@Before
	public void setUp() throws Exception {
		tag = new FormTag();
		setupBodyTag(tag);
		setupErrors(context);
		context.setAttribute(CubbyConstants.ATTR_CONTEXT_PATH, "/brabra",
				PageContext.REQUEST_SCOPE);
	}

	@Test
	public void doTagNoChild() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		doLifecycle(tag);

		System.out.println(context.getResult());
		// "<form action=\"/todo/save\" >\n</form>\n"

		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトが指定";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
		assertNull("フォームオブジェクトは除去されていること", context.findAttribute("__form"));
	}

	@Test
	public void doTagEmptyBody() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");

		doLifecycle(tag);

		System.out.println(context.getResult());
		// "<form action=\"/todo/save\" >\n</form>\n"

		Element element = getResultAsElementFromContext();
		String message = "Bodyが空の場合";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
	}

	@Test
	public void doTagWithTextAreaTag() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setDynamicAttribute(null, "action", "/todo/save");
		doLifecycle(tag, new ChildrenFactory() {

			public List<JspTag> create() {
				TextareaTag textareaTag = new TextareaTag();
				textareaTag.setName("stringField");
				return Arrays.asList(new JspTag[] { textareaTag });
			}

		});

		System.out.println(context.getResult());
		// "<form action=\"/todo/save\" >\n" +
		// "<textarea name=\"stringField\" >value1</textarea>\n" +
		// "</form>\n"

		Element element = getResultAsElementFromContext();
		String message = "フォームオブジェクトが指定、子要素がある場合";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/todo/save", element.getAttributeValue("action"));
		assertEquals(message, 1, element.getChildren().size());
		Element child = element.getChild("textarea");
		assertEquals(message, 1, child.getAttributes().size());
		assertEquals(message, "stringField", child.getAttributeValue("name"));
		assertEquals(message, "value1", child.getValue());
	}

	@Test
	public void doTagWithSpecifiedAction() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("foo");
		doLifecycle(tag);

		System.out.println(context.getResult());
		// "<form action=\"/brabra/mockFormTagTest/bar/123?token=abc\" >\n" +
		// "</form>\n", context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "アクションクラス、メソッド指定";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/brabra/mockFormTagTest/foo", element
				.getAttributeValue("action"));
		assertEquals(message, 0, element.getChildren().size());
	}

	@Test
	public void doTagWithSpecifiedActionAndParam() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("bar");
		doLifecycle(tag, new ChildrenFactory() {

			public List<JspTag> create() {
				ParamTag paramTag1 = new ParamTag();
				paramTag1.setName("id");
				paramTag1.setValue("123");
				ParamTag paramTag2 = new ParamTag();
				paramTag2.setName("token");
				paramTag2.setValue("abc");
				return Arrays.asList(new JspTag[] { paramTag1, paramTag2 });
			}

		});

		System.out.println(context.getResult());
		// "<form action=\"/brabra/mockFormTagTest/bar/123?token=abc\" >\n" +
		// "</form>\n", context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "アクションクラス、メソッド指定、paramタグあり";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/brabra/mockFormTagTest/bar/123?token=abc",
				element.getAttributeValue("action"));
		assertEquals(message, 0, element.getChildren().size());
	}

	@Test
	public void doTagWithTextAreaAndSpecifiedActionAndParam() throws Exception {
		FormDto form = new FormDto();
		form.setStringField("value1");

		tag.setValue(form);
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("bar");
		doLifecycle(tag, new ChildrenFactory() {

			public List<JspTag> create() {
				ParamTag paramTag1 = new ParamTag();
				paramTag1.setName("id");
				paramTag1.setValue("123");
				ParamTag paramTag2 = new ParamTag();
				paramTag2.setName("token");
				paramTag2.setValue("abc");
				InputTag inputTag = new InputTag();
				inputTag.setType("text");
				inputTag.setName("stringField");
				return Arrays.asList(new JspTag[] { paramTag1, paramTag2,
						inputTag });
			}

		});

		System.out.println(context.getResult());
		// "<form action=\"/brabra/mockFormTagTest/bar/123?token=abc\" >\n" +
		// "</form>\n", context.getResult());

		Element element = getResultAsElementFromContext();
		String message = "アクションクラス、メソッド指定、paramタグあり";
		assertEquals(message, 1, element.getAttributes().size());
		assertEquals(message, "/brabra/mockFormTagTest/bar/123?token=abc",
				element.getAttributeValue("action"));
		assertEquals(message, 1, element.getChildren().size());
		Element child = element.getChild("input");
		assertEquals(message, 3, child.getAttributes().size());
		assertEquals(message, "text", child.getAttributeValue("type"));
		assertEquals(message, "stringField", child.getAttributeValue("name"));
		assertEquals(message, "value1", child.getAttributeValue("value"));
		assertEquals(message, "", child.getValue());
	}

}
