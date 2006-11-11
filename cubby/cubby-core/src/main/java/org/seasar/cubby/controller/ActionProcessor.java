package org.seasar.cubby.controller;

public interface ActionProcessor {
	String processAction(ActionContext action) throws Throwable;
}
