/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.spi;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;

/**
 * {@link RequestParser} のプロバイダです。
 * 
 * @author baba
 * @since 2.0.0
 */
public interface RequestParserProvider extends Provider {

	/**
	 * 指定された要求を解析可能な解析器によって解析し、要求パラメータの {@link Map} を返します。
	 * 
	 * @param request
	 *            要求
	 * @return 要求パラメータの {@link Map}
	 */
	Map<String, Object[]> getParameterMap(HttpServletRequest request);

}
