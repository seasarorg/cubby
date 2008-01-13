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

import java.util.Map;
import java.util.PropertyResourceBundle;

import javax.servlet.http.HttpServletRequest;

import org.seasar.extension.unit.S2TestCase;

public class ThreadContextTest extends S2TestCase {

	public HttpServletRequest request;

    public void testGetMessagesMap() throws Throwable {
        Map<?, ?> result = ThreadContext.getMessagesMap();
        assertEquals("result.size()", 13, result.size());
        assertEquals("(HashMap) result.get(\"valid.arrayMaxSize\")", "{0}は{1}以下選択してください。", result.get("valid.arrayMaxSize"));
    }
    
    public void testGetMessagesResourceBundle() throws Throwable {
        PropertyResourceBundle result = (PropertyResourceBundle) ThreadContext.getMessagesResourceBundle();
        assertTrue("result.getKeys().hasMoreElements()", result.getKeys().hasMoreElements());
    }
    
    public void testGetRequest() throws Throwable {
        ThreadContext.setRequest(request);
        HttpServletRequest result = ThreadContext.getRequest();
        assertSame("ThreadContext.getRequest()", request, result);
    }
    
    public void testRemove() throws Throwable {
        ThreadContext.setRequest(request);
        ThreadContext.remove();
        assertNull("ThreadContext.remove()", ThreadContext.getRequest());
    }
    
}

