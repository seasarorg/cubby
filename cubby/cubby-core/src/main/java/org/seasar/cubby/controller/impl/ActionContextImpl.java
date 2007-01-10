package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionHolder;
import org.seasar.cubby.controller.Controller;

public class ActionContextImpl implements ActionContext {
	
	private final Controller controller;

	private final ActionHolder holder;

	private final HttpServletRequest request;

	private final HttpServletResponse response;

	private ActionFilter currentFilter;

	private final Map<String, Object> uriParams;

	@SuppressWarnings("unchecked")
	public ActionContextImpl(HttpServletRequest request,
			HttpServletResponse response, Controller controller, ActionHolder holder, Map<String, Object> uriParams) {
		this.request = request;
		this.response = response;
		this.controller = controller;
		this.holder = holder;
		this.uriParams = uriParams;
	}

	public Controller getController() {
		return controller;
	}

	public Method getMethod() {
		return holder.getActionMethod();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public ActionHolder getActionHolder() {
		return holder;
	}

	public ActionFilter getCurrentFilter() {
		return currentFilter;
	}

	public void setCurrentFilter(ActionFilter currentFilter) {
		this.currentFilter = currentFilter;
	}

	public Map<String, Object> getUriParams() {
		return uriParams;
	}
}
