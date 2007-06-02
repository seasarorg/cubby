package org.seasar.cubby.controller;

import java.util.Map;

public class ResolveResult {
	
	private final ActionMethod actionMethod;
	private final Map<String, String> uriParams;
	
	public ResolveResult(ActionMethod actionMethod, Map<String,String> uriParams) {
		this.actionMethod = actionMethod;
		this.uriParams = uriParams;
	}
	
	public ActionMethod getActionMethod() {
		return actionMethod;
	}
	
	public Map<String, String> getUriParams() {
		return uriParams;
	}
	
}
