package org.seasar.cubby.internal.routing.impl;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;

public class MockPriorityAction extends Action {

	@Accept(RequestMethod.PUT)
	@Path(priority=100)
	public ActionResult update() {
		return null;
	}
}
