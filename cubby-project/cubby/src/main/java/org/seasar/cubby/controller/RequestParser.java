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
package org.seasar.cubby.controller;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

/**
 * リクエスト解析器です。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface RequestParser {

	/**
	 * 指定されたリクエストのリクエストパラメータ等から、アクションにバインドするパラメータを取得します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return アクションにバインドするパラメータの{@link Map}
	 */
	Map<String, Object[]> getParameterMap(HttpServletRequest request);

	/**
	 * このリクエスト解析器が解析可能なリクエストかを示します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return 解析可能なリクエストの場合は <code>true</code>、そうでない場合は <code>false</code>
	 * @since 1.1.0
	 */
	boolean isParsable(HttpServletRequest request);

	/**
	 * 優先順位を取得します。
	 * 
	 * @return
	 * @since 1.1.0
	 */
	int getPriority();

}
