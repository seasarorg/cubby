package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Redirect;

public class SettingAction extends AbstractAction {

	public ActionResult index() {
		return new Redirect(SettingProfileAction.class, "index");
	}

}
