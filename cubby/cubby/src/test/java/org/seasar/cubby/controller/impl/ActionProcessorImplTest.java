package org.seasar.cubby.controller.impl;

import static org.seasar.cubby.TestUtils.getPrivateField;
import junit.framework.TestCase;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.cubby.convention.impl.CubbyConventionImpl;

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
    
    public void testSetCubbyConvention() throws Throwable {
        ActionProcessorImpl actionProcessorImpl = new ActionProcessorImpl();
        CubbyConvention cubbyConvention = new CubbyConventionImpl();
        actionProcessorImpl.setCubbyConvention(cubbyConvention);
        assertSame("actionProcessorImpl.cubbyConvention", cubbyConvention, getPrivateField(actionProcessorImpl, "cubbyConvention"));
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

