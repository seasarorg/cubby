package org.seasar.cubby.controller;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionFactory {
	void initialize(FilterConfig config);

	ActionContext createAction(String appUri, HttpServletRequest request,
			HttpServletResponse response);
}
