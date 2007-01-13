package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionMethod;
import org.seasar.cubby.controller.Controller;

public class ActionContextImpl implements ActionContext {
	
	private final Controller controller;

	private final ActionMethod holder;

	private final HttpServletRequest request;

	private final HttpServletResponse response;

	private ActionFilter currentFilter;

	public ActionContextImpl(HttpServletRequest request,
			HttpServletResponse response, Controller controller, ActionMethod holder) {
		this.request = request;
		this.response = response;
		this.controller = controller;
		this.holder = holder;
	}

	public Controller getController() {
		return controller;
	}

	public Method getMethod() {
		return holder.getMethod();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public ActionMethod getActionMethod() {
		return holder;
	}

	public ActionFilter getCurrentFilter() {
		return currentFilter;
	}

	public void setCurrentFilter(ActionFilter currentFilter) {
		this.currentFilter = currentFilter;
	}
}
