/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.internal.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.PathInfo;

/**
 * 要求を処理します。
 * 
 * @author someda
 * @author baba
 */
public interface RequestProcessor {

	/**
	 * 指定された {@link PathInfo} に対する処理を行います。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param pathInfo
	 *            パスの情報
	 * @throws Exception
	 *             要求の処理で例外が発生した場合
	 */
	void process(HttpServletRequest request, HttpServletResponse response,
			PathInfo pathInfo) throws Exception;

}
