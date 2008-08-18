package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Redirect;
import org.seasar.framework.aop.annotation.InvalidateSession;

public class LogoutAction extends Action {

	@InvalidateSession
	public ActionResult index() {
		return new Redirect("/");
	}

}
