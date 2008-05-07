package org.seasar.cubby.routing.impl;

import static org.seasar.cubby.action.RequestMethod.DELETE;
import static org.seasar.cubby.action.RequestMethod.PUT;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

@Path("/")
public class MockRootAction extends Action {

	public ActionResult index() {
		return null;
	}

	public ActionResult dummy1() {
		return null;
	}

	@Path("dummy1")
	@Accept({ PUT, DELETE })
	public ActionResult dummy2() {
		return null;
	}
}
