package org.seasar.cubby.controller;

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface ActionProcessor {

	void process(HttpServletRequest request, HttpServletResponse response,
			FilterChain chain) throws Exception;

}
