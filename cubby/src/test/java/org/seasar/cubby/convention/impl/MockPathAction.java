package org.seasar.cubby.convention.impl;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

@Path("foo/{id}")
public class MockPathAction {

	public ActionResult update() {
		return null;
	}

	@Path("create")
	public ActionResult insert() {
		return null;
	}

	@Path("delete/{value,[0-9]+}")
	public ActionResult delete() {
		return null;
	}

//	@Path("{name}")
//	public ActionResult name() {
//		return null;
//	}

}
