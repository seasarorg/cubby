package org.seasar.cubby.util;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;

public class Hoge2Action extends Action {
	public ActionResult m1() {
		return null;
	}
	@Path("/hoge/m2")
	public ActionResult m2() {
		return null;
	}

}
