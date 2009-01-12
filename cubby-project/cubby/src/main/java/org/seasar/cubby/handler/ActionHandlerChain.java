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
package org.seasar.cubby.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;

/**
 * アクションハンドラーのチェーンです。
 * 
 * @see ActionHandler
 * @author baba
 * @since 2.0.0
 */
public interface ActionHandlerChain {

	/**
	 * チェーン内の次のハンドラーを呼び出します。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションのコンテキスト
	 * @return 次のハンドラーの実行結果
	 * @throws Exception
	 *             ハンドラーの実行で例外が発生した場合
	 */
	ActionResult chain(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext)
			throws Exception;

}
