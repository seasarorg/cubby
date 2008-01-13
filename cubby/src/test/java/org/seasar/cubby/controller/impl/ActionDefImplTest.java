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

