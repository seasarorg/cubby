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

import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.dxo.FormDxo;

public class ActionContextImplTest extends TestCase {

    public void testConstructor() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
    }
    
    public void testInitialize() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        actionContextImpl.initialize(null);
        assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
    }
    
    public void testIsInitialized() throws Throwable {
        boolean result = new ActionContextImpl().isInitialized();
        assertFalse("result", result);
    }
    
    public void testSetParameterBinder() throws Throwable {
        FormDxo formDxo = new FormDxo() {
			public void convert(Map<String, Object[]> src, Object dest) {
			}
			public void convert(Object src, Map<String, String[]> dest) {
			}
        };
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        actionContextImpl.setFormDxo(formDxo);
        assertSame("actionContextImpl.getParameterBinder()", formDxo, actionContextImpl.getFormDxo());
    }
    
    public void testGetActionThrowsNullPointerException() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        try {
            actionContextImpl.getAction();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertNull("actionContextImpl.action", getPrivateField(actionContextImpl, "action"));
            assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
        }
    }
    
    public void testGetComponentDefThrowsNullPointerException() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        try {
            actionContextImpl.getComponentDef();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
        }
    }
    
    public void testGetFormBeanThrowsNullPointerException() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        try {
            actionContextImpl.getFormBean();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
            assertNull("actionContextImpl.action", getPrivateField(actionContextImpl, "action"));
        }
    }
    
    public void testGetMethodThrowsNullPointerException() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        try {
            actionContextImpl.getMethod();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
        }
    }
    
    public void testGetValidationThrowsNullPointerException() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        try {
            actionContextImpl.getValidation();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
        }
    }
    
    public void testInvokeThrowsNullPointerException() throws Throwable {
        ActionContextImpl actionContextImpl = new ActionContextImpl();
        try {
            actionContextImpl.invoke();
            fail("Expected NullPointerException to be thrown");
        } catch (NullPointerException ex) {
            assertNull("ex.getMessage()", ex.getMessage());
            assertFalse("actionContextImpl.isInitialized()", actionContextImpl.isInitialized());
            assertNull("actionContextImpl.action", getPrivateField(actionContextImpl, "action"));
        }
    }
}

