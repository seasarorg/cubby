package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;

import junit.framework.TestCase;

import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.container.impl.SimpleComponentDef;

public class ActionDefImplTest extends TestCase {

	public void testConstructor() throws Throwable {
    	ComponentDef componentDef = new SimpleComponentDef(ActionDefImpl.class);
        ActionDefImpl actionDefImpl = new ActionDefImpl(componentDef, null);
        assertSame("actionDefImpl.getComponentDef()", componentDef, actionDefImpl.getComponentDef());
        assertNull("actionDefImpl.getMethod()", actionDefImpl.getMethod());
    }
    
    public void testGetComponentDef() throws Throwable {
    	ComponentDef componentDef = new SimpleComponentDef(ActionDefImpl.class);
        ActionDefImpl actionDefImpl = new ActionDefImpl(componentDef, null);
        ComponentDef result = actionDefImpl.getComponentDef();
        assertSame("result", componentDef, result);
    }
    
    public void testGetMethod() throws Throwable {
    	ComponentDef componentDef = new SimpleComponentDef(ActionDefImpl.class);
        ActionDefImpl actionDefImpl = new ActionDefImpl(componentDef, null);
        Method result = actionDefImpl.getMethod();
        assertNull("result", result);
    }
}

