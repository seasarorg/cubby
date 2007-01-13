package org.seasar.cubby.controller;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionProcessor {
	void initialize();
	void execute(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Throwable;

}
