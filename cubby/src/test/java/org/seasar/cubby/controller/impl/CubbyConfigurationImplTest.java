package org.seasar.cubby.controller.impl;

import junit.framework.TestCase;

import org.seasar.cubby.action.impl.FormatPatternImpl;
import org.seasar.framework.container.impl.S2ContainerImpl;
import org.seasar.framework.container.impl.ThreadSafeS2ContainerImpl;

public class CubbyConfigurationImplTest extends TestCase {

    public void testConstructor() throws Throwable {
        CubbyConfigurationImpl cubbyConfigurationImpl = new CubbyConfigurationImpl(new ThreadSafeS2ContainerImpl());
        assertNotNull("cubbyConfigurationImpl.getRequestParser()", cubbyConfigurationImpl.getRequestParser());
        assertEquals("cubbyConfigurationImpl.getFormatPattern().getDatePattern()", "yyyy-MM-dd", cubbyConfigurationImpl.getFormatPattern().getDatePattern());
    }
    
    public void testGetFormatPattern() throws Throwable {
        FormatPatternImpl result = (FormatPatternImpl) new CubbyConfigurationImpl(new S2ContainerImpl()).getFormatPattern();
        assertEquals("result.getDatePattern()", "yyyy-MM-dd", result.getDatePattern());
    }
    
    public void testGetRequestParser() throws Throwable {
        DefaultRequestParserImpl result = (DefaultRequestParserImpl) new CubbyConfigurationImpl(new S2ContainerImpl()).getRequestParser();
        assertNotNull("result", result);
    }
    
    public void testConstructorThrowsNullPointerException() throws Throwable {
        try {
            new CubbyConfigurationImpl(null);
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
        }
    }
}

