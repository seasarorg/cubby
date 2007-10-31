package org.seasar.cubby.convention.impl;

import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.extension.unit.S2TestCase;
import org.seasar.framework.mock.servlet.MockHttpServletRequestImpl;
import org.seasar.framework.mock.servlet.MockServletContextImpl;

public class CubbyConventionImplTest extends S2TestCase {

	public CubbyConvention cubbyConvention;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testFromPathToActionName() {
		MockServletContextImpl servletContext = new MockServletContextImpl(
				"hoge");
		MockHttpServletRequestImpl request = new MockHttpServletRequestImpl(
				servletContext, "foo");
		ActionDef actionDef = cubbyConvention.fromPathToActionDef(request, "/login/login_process");
		assertNull(actionDef);
	}

}
