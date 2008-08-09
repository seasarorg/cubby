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

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;

/**
 * {@link org.seasar.cubby.action.ActionResult} のラッパです。
 * 
 * @author baba
 * @since 1.1.0
 */
public interface ActionResultWrapper {

	/**
	 * ラップされた {@link org.seasar.cubby.action.ActionResult} を実行します。
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @throws Exception
	 *             {@link org.seasar.cubby.action.ActionResult} の実行で例外が発生した場合
	 */
	void execute(HttpServletRequest request, HttpServletResponse response)
			throws Exception;

	/**
	 * ラップされた {@link org.seasar.cubby.action.ActionResult} を取得します。
	 * 
	 * @return ラップされた {@link org.seasar.cubby.action.ActionResult}
	 */
	ActionResult getActionResult();

}
