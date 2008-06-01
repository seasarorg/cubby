package org.seasar.cubby.tags;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

public class MockFormTagTestAction {

	public ActionResult foo() {
		return null;
	}

	@Path("{id}")
	public ActionResult bar() {
		return null;
	}

}
