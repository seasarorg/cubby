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
package org.seasar.cubby.convention.impl;

import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockServletContext;

public class CubbyConventionImplTest extends S2TestCase {

	public CubbyConvention cubbyConvention;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testFromPathToActionDef() {
		MockServletContext servletContext = getServletContext();
		MockHttpServletRequest request = servletContext.createRequest("/login/login_process");
		ActionDef actionDef = cubbyConvention.fromPathToActionDef(request);
		assertNull(actionDef);
	}

}
