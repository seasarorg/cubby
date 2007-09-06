package org.seasar.cubby.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public interface MultipartRequestParser {
	Map<String,Object> getMultipartParameterMap(HttpServletRequest request);

	boolean isMultipart(HttpServletRequest request);
}
