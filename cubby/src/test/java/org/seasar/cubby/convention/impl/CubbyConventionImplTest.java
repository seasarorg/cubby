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
