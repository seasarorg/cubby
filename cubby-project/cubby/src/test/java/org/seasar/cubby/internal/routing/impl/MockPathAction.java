package org.seasar.cubby.internal.routing.impl;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;

@Path("foo/{id}")
public class MockPathAction extends Action {

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

	@Path("{name}")
	public ActionResult name() {
		return null;
	}

	@Path("update")
	@Accept(RequestMethod.PUT)
	public ActionResult update2() {
		// メソッドがPUTなのでエラーにならない
		return null;
	}

}
