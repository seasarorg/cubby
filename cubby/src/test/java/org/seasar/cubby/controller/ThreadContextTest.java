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
package org.seasar.cubby.controller;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.container.Container;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.mock.MockContainerProvider;

public class ThreadContextTest {

	@Before
	public void setupContainer() {
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (MessagesBehaviour.class.equals(type)) {
					return type.cast(new DefaultMessagesBehaviour());
				}
				return null;
			}

		});
		ThreadContext.remove();
	}

	@Test
	public void getMessagesMap() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(Locale.JAPANESE);
		replay(request);
		Map<?, ?> result = ThreadContext.getMessagesMap();
		assertEquals("result.size()", 13, result.size());
		assertEquals("(HashMap) result.get(\"valid.arrayMaxSize\")",
				"{0}は{1}以下選択してください。", result.get("valid.arrayMaxSize"));
	}

	@Test
	public void getMessagesResourceBundle() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(Locale.JAPANESE);
		replay(request);
		PropertyResourceBundle result = (PropertyResourceBundle) ThreadContext
				.getMessagesResourceBundle();
		assertTrue("result.getKeys().hasMoreElements()", result.getKeys()
				.hasMoreElements());
	}

	@Test
	public void getRequest() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		replay(request);
		ThreadContext.setRequest(request);
		HttpServletRequest result = ThreadContext.getRequest();
		assertSame("ThreadContext.getRequest()", request, result);
		verify(request);
	}

	@Test
	public void remove() {
		HttpServletRequest request = createMock(HttpServletRequest.class);
		replay(request);
		ThreadContext.setRequest(request);
		ThreadContext.remove();
		assertNull("ThreadContext.remove()", ThreadContext.getRequest());
		verify(request);
	}

}
