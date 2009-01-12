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
 * アクションハンドラーはアクションの実行要求を処理するためのハンドラーです。
 * 
 * @author baba
 * @since 2.0.0
 */
public interface ActionHandler {

	/**
	 * アクションの実行要求を処理します。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param actionContext
	 *            アクションのコンテキスト
	 * @param actionHandlerChain
	 *            ハンドラーのチェーン
	 * @return 実行結果
	 * @throws Exception
	 *             ハンドラーの実行中に例外が発生した場合
	 */
	ActionResult handle(HttpServletRequest request,
			HttpServletResponse response, ActionContext actionContext,
			ActionHandlerChain actionHandlerChain) throws Exception;

}
