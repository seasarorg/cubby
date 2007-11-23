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

