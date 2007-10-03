package org.seasar.cubby.controller.impl;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;

public class DefaultRequestParserImpl implements RequestParser {

	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameterMap(final HttpServletRequest request) {
		return request.getParameterMap();
	}

}
