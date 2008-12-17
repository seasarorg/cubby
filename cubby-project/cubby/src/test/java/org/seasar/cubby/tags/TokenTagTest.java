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
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jdom.Element;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.util.TokenHelper;

public class TokenTagTest extends SimpleTagTestCase {

	private TokenTag tag;

	@Before
	public void setup() throws Exception {
		tag = new TokenTag();
		setupSimpleTag(tag);
	}

	@Test
	public void doTag1() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) context
				.getRequest();
		final HttpServletResponse response = (HttpServletResponse) context
				.getResponse();

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				tag.doTag();
				Element element = getResultAsElementFromContext();
				String message = "nameが指定されている場合";
				assertEquals(message, 3, element.getAttributes().size());
				assertEquals(message, "input", element.getName());
				assertEquals(message, "hidden", element
						.getAttributeValue("type"));
				assertTrue(message,
						element.getAttributeValue("value").length() != 0);
				assertEquals(message, TokenHelper.DEFAULT_TOKEN_NAME, element
						.getAttributeValue("name"));
				assertTrue(message, TokenHelper.validateToken(request
						.getSession(), element.getAttributeValue("value")));
				return null;
			}

		});

	}

	@Test
	public void doTag2() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) context
				.getRequest();
		final HttpServletResponse response = (HttpServletResponse) context
				.getResponse();

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				tag.setName("cubby.token2");
				tag.doTag();
				Element element = getResultAsElementFromContext();
				String message = "nameが指定されている場合";
				assertEquals(message, 3, element.getAttributes().size());
				assertEquals(message, "input", element.getName());
				assertEquals(message, "hidden", element
						.getAttributeValue("type"));
				assertTrue(message,
						element.getAttributeValue("value").length() != 0);
				assertEquals(message, "cubby.token2", element
						.getAttributeValue("name"));
				assertTrue(message, TokenHelper.validateToken(request
						.getSession(), element.getAttributeValue("value")));
				return null;
			}
		});
	}

	@Test
	public void testDoTag3() throws Exception {
		final HttpServletRequest request = (HttpServletRequest) context
				.getRequest();
		final HttpServletResponse response = (HttpServletResponse) context
				.getResponse();

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute() throws Exception {
				tag.setDynamicAttribute(null, "id", "token");
				tag.doTag();
				Element element = getResultAsElementFromContext();
				String message = "idが指定されている場合";
				assertEquals(message, 4, element.getAttributes().size());
				assertEquals(message, "input", element.getName());
				assertEquals(message, "token", element.getAttributeValue("id"));
				assertEquals(message, "hidden", element
						.getAttributeValue("type"));
				assertTrue(message,
						element.getAttributeValue("value").length() != 0);
				assertEquals(message, TokenHelper.DEFAULT_TOKEN_NAME, element
						.getAttributeValue("name"));
				assertTrue(message, TokenHelper.validateToken(request
						.getSession(), element.getAttributeValue("value")));
				return null;
			}
		});
	}

	@Test
	public void requestIsNull() throws Exception {
		ThreadContext.runInContext(null, null, new Command<Void>() {

			public Void execute() throws Exception {

				tag.setDynamicAttribute(null, "id", "token");
				try {
					tag.doTag();
					fail("ThreadContext.getRequest()がnullの場合、ここは通らない");
				} catch (IllegalStateException e) {
				}
				return null;
			}
		});
	}

}
