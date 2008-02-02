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
package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;

/**
 * アクションメソッド実行後の処理を表す結果オブジェクトのインターフェイス
 * 
 * @author agata
 */
public interface ActionResult {

	/**
	 * 処理を実行します。
	 * 
	 * @param context
	 *            アクションコンテキスト
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @throws Exception
	 */
	void execute(ActionContext context, HttpServletRequest request,
			HttpServletResponse response) throws Exception;

	/**
	 * フォワード直前の処理を実行します。
	 * 
	 * @param context
	 *            アクションコンテキスト
	 */
	void prerender(ActionContext context);

}