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
package org.seasar.cubby.filter;

import static org.easymock.EasyMock.anyObject;
import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.expectLastCall;
import static org.easymock.EasyMock.getCurrentArguments;
import static org.easymock.EasyMock.replay;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.easymock.IAnswer;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;

/**
 * 
 * @author agata
 */
public class CubbyHttpServletRequestWrapperTest {

	private HttpServletRequest request;

	@Before
	@SuppressWarnings("unchecked")
	public void setupRequest() {
		final Hashtable<String, Object> attributes = new Hashtable<String, Object>();
		request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(String.class.cast(anyObject())))
				.andStubAnswer(new IAnswer<Object>() {

					public Object answer() throws Throwable {
						return attributes.get(getCurrentArguments()[0]);
					}

				});
		request.setAttribute(String.class.cast(anyObject()), anyObject());
		expectLastCall().andStubAnswer(new IAnswer<Object>() {

			public Object answer() throws Throwable {
				attributes.put(String.class.cast(getCurrentArguments()[0]),
						getCurrentArguments()[1]);
				return null;
			}

		});
		expect(request.getAttributeNames()).andStubAnswer(
				new IAnswer<Enumeration>() {

					public Enumeration answer() throws Throwable {
						return attributes.keys();
					}

				});
		replay(request);
	}

	@Test
	public void getAttributeNames() throws Exception {
		request.setAttribute("a1", "a1");
		Collection<String> origNames = toCollection(request.getAttributeNames());
		assertTrue("追加済みの属性", origNames.contains("a1"));
		assertFalse("存在しない属性", origNames.contains("a2"));
		assertFalse("ラップ後の追加の属性", origNames
				.contains(CubbyConstants.ATTR_CONTEXT_PATH));
		assertFalse("ラップ後の追加の属性", origNames
				.contains(CubbyConstants.ATTR_MESSAGES));
		assertFalse("ラップ後の追加の属性", origNames
				.contains(CubbyConstants.ATTR_ACTION));

		CubbyHttpServletRequestWrapper wrapper = new CubbyHttpServletRequestWrapper(
				request);
		Action action = new MockAction();
		wrapper.setAttribute(CubbyConstants.ATTR_ACTION, action);

		Collection<String> wrappedNames = toCollection(wrapper
				.getAttributeNames());
		assertTrue("追加済みの属性", origNames.contains("a1"));
		assertFalse("存在しない属性", origNames.contains("a2"));
		assertTrue("ラップ後の追加の属性", wrappedNames
				.contains(CubbyConstants.ATTR_CONTEXT_PATH));
		assertTrue("ラップ後の追加の属性", wrappedNames
				.contains(CubbyConstants.ATTR_MESSAGES));
		assertTrue("ラップ後の追加の属性", wrappedNames
				.contains(CubbyConstants.ATTR_ACTION));
	}

	private Collection<String> toCollection(Enumeration<?> attributeNames) {
		List<String> names = new ArrayList<String>();
		while (attributeNames.hasMoreElements()) {
			names.add((String) attributeNames.nextElement());
		}
		return names;
	}

	private class MockAction extends Action {
	}

}
