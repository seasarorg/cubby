package org.seasar.cubby.controller;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

public class MockMultipartRequestParser implements MultipartRequestParser {
	
	public boolean isMultipart = false;
	public Map<String, Object> getMultipartParameterMapResult = new LinkedHashMap<String, Object>();
	
	public Map<String, Object> getMultipartParameterMap(HttpServletRequest request) {
		return getMultipartParameterMapResult;
	}

	public boolean isMultipart(HttpServletRequest request) {
		return isMultipart;
	}

}
