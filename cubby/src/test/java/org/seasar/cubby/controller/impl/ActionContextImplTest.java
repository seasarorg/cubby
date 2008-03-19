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

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.ActionDefBuilder;
import org.seasar.cubby.dxo.FormDxo;
import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockServletContext;

public class ActionContextImplTest extends S2TestCase {

	public ActionContext actionContext;

	public ActionDefBuilder actionDefBuilder;

	public MockAction mockAction;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testConstructor() throws Throwable {
		assertFalse("actionContextImpl.isInitialized()", actionContext
				.isInitialized());
	}

	public void testInitialize() throws Throwable {
		actionContext.initialize(null);
		assertFalse("actionContextImpl.isInitialized()", actionContext
				.isInitialized());
	}

	public void testIsInitialized() throws Throwable {
		assertFalse("result", actionContext.isInitialized());
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
		assertSame("actionContextImpl.getParameterBinder()", formDxo,
				actionContextImpl.getFormDxo());
	}

	public void testGetActionThrowsNullPointerException() throws Throwable {
		try {
			actionContext.getAction();
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
			assertNull("actionContextImpl.action", getPrivateField(
					actionContext, "action"));
			assertFalse("actionContextImpl.isInitialized()", actionContext
					.isInitialized());
		}
	}

	public void testGetComponentDefThrowsNullPointerException()
			throws Throwable {
		try {
			actionContext.getComponentDef();
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
			assertFalse("actionContextImpl.isInitialized()", actionContext
					.isInitialized());
		}
	}

	public void testGetFormBeanThrowsNullPointerException() throws Throwable {
		try {
			actionContext.getFormBean();
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
			assertFalse("actionContextImpl.isInitialized()", actionContext
					.isInitialized());
			assertNull("actionContextImpl.action", getPrivateField(
					actionContext, "action"));
		}
	}

	public void testGetMethodThrowsNullPointerException() throws Throwable {
		try {
			actionContext.getMethod();
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
			assertFalse("actionContextImpl.isInitialized()", actionContext
					.isInitialized());
		}
	}

	public void testInvokeThrowsNullPointerException() throws Throwable {
		try {
			actionContext.invoke();
			fail("Expected NullPointerException to be thrown");
		} catch (NullPointerException ex) {
			assertNull("ex.getMessage()", ex.getMessage());
			assertFalse("actionContextImpl.isInitialized()", actionContext
					.isInitialized());
			assertNull("actionContextImpl.action", getPrivateField(
					actionContext, "action"));
		}
	}

	public void testGetFormObject1() throws Exception {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						MockAction.class, "update"));
		ActionDef actionDef = actionDefBuilder.build(request);
		actionContext.initialize(actionDef);
		Object form = actionContext.getFormBean();
		assertSame(mockAction, form);
	}

	public void testGetFormObject2() throws Exception {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						MockAction.class, "legalForm"));
		ActionDef actionDef = actionDefBuilder.build(request);
		actionContext.initialize(actionDef);
		Object form = actionContext.getFormBean();
		assertSame(mockAction.form, form);
	}

	public void testGetFormObject3() throws Exception {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						MockAction.class, "illegalForm"));
		ActionDef actionDef = actionDefBuilder.build(request);
		actionContext.initialize(actionDef);
		try {
			actionContext.getFormBean();
			fail();
		} catch (ActionRuntimeException e) {
			// ok
			e.printStackTrace();
		}
	}

}
