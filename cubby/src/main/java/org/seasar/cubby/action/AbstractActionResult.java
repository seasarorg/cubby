package org.seasar.cubby.action;

import org.seasar.cubby.controller.ActionContext;

abstract class AbstractActionResult implements ActionResult {

	/**
	 * 何も行いません。
	 */
	public void prerender(final ActionContext context) {
	}

}
