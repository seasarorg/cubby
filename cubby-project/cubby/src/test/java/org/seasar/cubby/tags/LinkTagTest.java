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

import java.util.Arrays;
import java.util.List;

import javax.servlet.jsp.PageContext;
import javax.servlet.jsp.tagext.JspTag;

import org.jdom.Element;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.factory.PathResolverFactory;
import org.seasar.cubby.internal.routing.PathResolver;
import org.seasar.cubby.internal.routing.impl.PathResolverImpl;
import org.seasar.cubby.mock.MockContainerProvider;

public class LinkTagTest extends AbstractStandardTagTestCase {

	private LinkTag tag;

	@Before
	public void setup() throws Exception {
		tag = new LinkTag();
		setupBodyTag(tag);
		setupErrors(context);
		context.setAttribute(CubbyConstants.ATTR_CONTEXT_PATH, "/brabra",
				PageContext.REQUEST_SCOPE);
	}

	@Before
	public void setupContainer() {
		final PathResolver pathResolver = new PathResolverImpl();
		pathResolver.add("/mockFormTagTest/foo", MockFormTagTestAction.class,
				"foo");
		pathResolver.add("/mockFormTagTest/bar/{id}",
				MockFormTagTestAction.class, "bar");

		final PathResolverFactory pathResolverFactory = new PathResolverFactory() {

			public PathResolver getPathResolver() {
				return pathResolver;
			}

		};
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (PathResolverFactory.class.equals(type)) {
					return type.cast(pathResolverFactory);
				}
				return null;
			}

		});
	}

	@Test
	public void doTag() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("foo");
		doLifecycle(tag);

		System.out.println(context.getResult());

		assertEquals("URL出力", "/brabra/mockFormTagTest/foo", context
				.getResult());
	}

	@Test
	public void doTagWithParam() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("bar");
		doLifecycle(tag, new ChildrenFactory() {
			public List<JspTag> create() {
				ParamTag paramTag1 = new ParamTag();
				paramTag1.setParent(tag);
				paramTag1.setName("id");
				paramTag1.setValue("123");
				ParamTag paramTag2 = new ParamTag();
				paramTag2.setParent(tag);
				paramTag2.setName("token");
				paramTag2.setValue("abc");
				return Arrays.asList(new JspTag[] { paramTag1, paramTag2 });
			}
		});

		System.out.println(context.getResult());

		assertEquals("パラメータ付きでURL出力",
				"/brabra/mockFormTagTest/bar/123?token=abc", context
						.getResult());
	}

	@Test
	public void doTagOutputTag() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("foo");
		tag.setTag("a");
		tag.setAttr("href");
		jspBody.setBody("body");
		doLifecycle(tag);

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

	@Test
	public void doTagOutputTagWithParam() throws Exception {
		tag.setActionClass(MockFormTagTestAction.class.getCanonicalName());
		tag.setActionMethod("bar");
		tag.setTag("img");
		tag.setAttr("src");
		doLifecycle(tag, new ChildrenFactory() {
			public List<JspTag> create() {
				ParamTag paramTag1 = new ParamTag();
				paramTag1.setParent(tag);
				paramTag1.setName("id");
				paramTag1.setValue("123");
				ParamTag paramTag2 = new ParamTag();
				paramTag2.setParent(tag);
				paramTag2.setName("token");
				paramTag2.setValue("abc");
				return Arrays.asList(new JspTag[] { paramTag1, paramTag2 });
			}
		});

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
