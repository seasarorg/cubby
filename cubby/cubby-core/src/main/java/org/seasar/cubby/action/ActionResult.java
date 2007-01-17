package org.seasar.cubby.action;

import org.seasar.cubby.controller.ActionContext;

public interface ActionResult {
	void execute(ActionContext action) throws Exception;
}
