package org.seasar.cubby.controller;

public interface ActionProcessor {
	ActionResult processAction(ActionContext action) throws Throwable;
}
