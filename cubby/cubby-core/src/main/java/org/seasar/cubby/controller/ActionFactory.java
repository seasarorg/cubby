package org.seasar.cubby.controller;

import javax.servlet.FilterConfig;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionFactory {
	void initialize(FilterConfig config);

	// TODO appUriを引数から除外してカプセル化する
	ActionContext createAction(String appUri, HttpServletRequest request,
			HttpServletResponse response);
}
