package org.seasar.cubby.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * 
 * @author baba
 *
 */
public interface RequestParser {

	Map<String, Object[]> getParameterMap(HttpServletRequest request);

}