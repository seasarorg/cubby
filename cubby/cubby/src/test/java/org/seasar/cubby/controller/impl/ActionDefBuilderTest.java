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

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockServletContext;

public class ActionDefBuilderTest extends S2TestCase {

	public S2Container container;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testFromPathToActionDefReturnsNull1() {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext.createRequest("/none");
		ActionDefBuilder actionDefBuilder = new ActionDefBuilder(container);
		ActionDef actionDef = actionDefBuilder.build(request);
		assertNull(actionDef);
	}

	public void testFromPathToActionDefReturnsNull2() {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						MockAction.class, ""));
		ActionDefBuilder actionDefBuilder = new ActionDefBuilder(container);
		ActionDef actionDef = actionDefBuilder.build(request);
		assertNull(actionDef);
	}

	public void testFromPathToActionDefReturnsNull3() {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						MockAction.class, "none"));
		ActionDefBuilder actionDefBuilder = new ActionDefBuilder(container);
		ActionDef actionDef = actionDefBuilder.build(request);
		assertNull(actionDef);
	}

	public void testFromPathToActionDefReturnsNull4() {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						NoDeployedAction.class, "none"));
		ActionDefBuilder actionDefBuilder = new ActionDefBuilder(container);
		ActionDef actionDef = actionDefBuilder.build(request);
		assertNull(actionDef);
	}

	public void testFromPathToActionDef() {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext
				.createRequest(CubbyUtils.getInternalForwardPath(
						MockAction.class, "update"));
		ActionDefBuilder actionDefBuilder = new ActionDefBuilder(container);
		ActionDef actionDef = actionDefBuilder.build(request);
		assertNotNull(actionDef);
		assertEquals(MockAction.class, actionDef.getActionClass());
		assertEquals("update", actionDef.getMethod().getName());
	}

	private class NoDeployedAction extends Action {
		public ActionResult update() {
			return null;
		}
	}
}
