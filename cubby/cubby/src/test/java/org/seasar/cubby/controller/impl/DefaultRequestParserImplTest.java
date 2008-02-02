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
package org.seasar.cubby.controller.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;

public class DefaultRequestParserImplTest extends S2TestCase {
    
	public HttpServletRequest request;

	public CubbyConfiguration cubbyConfiguration;

    @Override
	protected void setUp() throws Exception {
		super.setUp();
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

    public void testRequestParserType() {
		RequestParser requestParser = cubbyConfiguration.getRequestParser();
		assertTrue(DefaultRequestParserImpl.class.isAssignableFrom(requestParser.getClass()));
    }

    public void testGetEmptyParameterMap() throws Throwable {
		RequestParser requestParser = cubbyConfiguration.getRequestParser();
    	Map<String, Object[]> parameterMap = requestParser.getParameterMap(request);
        assertEquals("parameterMap.size()", 0, parameterMap.size());
    }

    public void testGetParameterMap() throws Throwable {
		RequestParser requestParser = cubbyConfiguration.getRequestParser();
    	MockHttpServletRequest mock = (MockHttpServletRequest) request;
    	mock.setParameter("a", "12345");
    	mock.setParameter("b", new String[] { "abc", "def" });
    	Map<String, Object[]> parameterMap = requestParser.getParameterMap(request);
        assertEquals("parameterMap.size()", 2, parameterMap.size());
        Object[] a = parameterMap.get("a");
        assertEquals("a.length", 1, a.length);
        assertEquals("a[0]", "12345", a[0]);
        Object[] b = parameterMap.get("b");
        assertEquals("b.length", 2, b.length);
        assertEquals("b[0]", "abc", b[0]);
        assertEquals("b[1]", "def", b[1]);
    }

}
