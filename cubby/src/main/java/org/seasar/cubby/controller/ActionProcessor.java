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

import javax.servlet.FilterChain;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;

/**
 * リクエストのパスを元にアクションメソッドを決定して実行するクラスです。
 * 
 * @author agata
 * @since 1.0.0
 */
public interface ActionProcessor {

	/**
	 * リクエストのパスを元にアクションメソッドを決定して実行します。
	 * <p>
	 * <ul>
	 * <li>リクエストパスを元に実行するアクションとそのアクションメソッドを決定します。</li>
	 * <li>アクションメソッドを実行します。</li>
	 * <li>アクションメソッドの実行結果である{@link ActionResult}を実行します。</li>
	 * </ul>
	 * </p>
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @param chain
	 *            フィルターチェイン
	 * @return 実行結果。アクションメソッドが存在しない場合はnull。
	 * @throws Exception
	 *             アクションの実行時に例外が発生した場合
	 */
	ActionResult process(HttpServletRequest request,
			HttpServletResponse response, FilterChain chain) throws Exception;

}
