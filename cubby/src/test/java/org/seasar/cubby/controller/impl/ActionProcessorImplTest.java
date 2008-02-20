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

import static org.seasar.cubby.TestUtils.getPrivateField;
import junit.framework.TestCase;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDefBuilder;

public class ActionProcessorImplTest extends TestCase {

	public void testConstructor() throws Throwable {
        new ActionProcessorImpl();
        assertTrue("Test call resulted in expected outcome", true);
    }
    
    public void testSetActionContext() throws Throwable {
        ActionProcessorImpl actionProcessorImpl = new ActionProcessorImpl();
        ActionContext context = new ActionContextImpl();
        actionProcessorImpl.setActionContext(context);
        assertSame("actionProcessorImpl.context", context, getPrivateField(actionProcessorImpl, "context"));
    }
    
    public void testSetActionDefBuilder() throws Throwable {
        ActionProcessorImpl actionProcessorImpl = new ActionProcessorImpl();
        ActionDefBuilder actionDefBuilder = new ActionDefBuilderImpl();
        actionProcessorImpl.setActionDefBuilder(actionDefBuilder);
        assertSame("actionProcessorImpl.actionDefBuilder", actionDefBuilder, getPrivateField(actionProcessorImpl, "actionDefBuilder"));
    }
    
//    public void testProcessThrowsStringIndexOutOfBoundsException() throws Throwable {
//        ActionProcessorImpl actionProcessorImpl = new ActionProcessorImpl();
//        MockServletContext mockServletContext = new MockServletContext();
//        mockServletContext.setContextBasePath("testActionProcessorImplParam1");
//        HttpServletResponse response = mockServletContext.createHttpServletResponse();
//        try {
//            actionProcessorImpl.process(mockServletContext.createHttpServletRequest("testActionProcessorImplParam1"), response, null);
//            fail("Expected StringIndexOutOfBoundsException to be thrown");
//        } catch (StringIndexOutOfBoundsException ex) {
//            assertEquals("(MockHttpServletResponse) response", "text/html; charset=UTF-8", ((MockHttpServletResponse) response).getContentType());
//            assertEquals("(MockHttpServletResponse) response", "UTF-8", ((MockHttpServletResponse) response).getCharacterEncoding());
//            assertEquals("ex.getMessage()", "String index out of range: -1", ex.getMessage());
//            assertThrownBy(String.class, ex);
//            assertNull("actionProcessorImpl.cubbyConvention", getPrivateField(actionProcessorImpl, "cubbyConvention"));
//            assertNull("actionProcessorImpl.context", getPrivateField(actionProcessorImpl, "context"));
//            boolean actual = ((Logger) getPrivateField(ActionProcessorImpl.class, "logger")).isDebugEnabled();
//            assertFalse("actionProcessorImplActionProcessorImpl.logger.isDebugEnabled()", actual);
//        }
//    }
}

