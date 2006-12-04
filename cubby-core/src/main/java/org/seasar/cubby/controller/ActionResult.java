package org.seasar.cubby.controller;

public interface ActionResult {
	void execute(ActionContext action) throws Exception;
}
