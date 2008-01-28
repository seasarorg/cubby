package org.seasar.cubby.convention.impl;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

@Path("/")
public class MockRootAction {

	public ActionResult index() {
		return null;
	}

	public ActionResult dummy1() {
		return null;
	}

}
