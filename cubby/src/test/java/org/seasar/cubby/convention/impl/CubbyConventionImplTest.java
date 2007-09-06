package org.seasar.cubby.convention.impl;

import org.seasar.cubby.convention.CubbyConvention;
import org.seasar.extension.unit.S2TestCase;

public class CubbyConventionImplTest extends S2TestCase {

	private CubbyConvention cubbyConvention;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testFromPathToActionName() {
		cubbyConvention.fromPathToActionDef("/login/login_process");
	}

}
