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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Enumeration;
import java.util.List;

import junit.framework.TestCase;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

/**
 * 
 * @author agata
 */
public class CubbyHttpServletRequestWrapperTest extends TestCase {

	@Override
	protected void setUp() throws Exception {
		super.setUp();
	}

	public void testGetAttributeNames() throws Exception {
		MockServletContextImpl context = new MockServletContextImpl("/cubby");
		MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(
				context, "servlet");
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
		Action action = new HogeAction();
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

	class HogeAction extends Action {
	}
}
