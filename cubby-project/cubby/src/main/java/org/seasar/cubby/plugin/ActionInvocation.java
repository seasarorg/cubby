/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.plugin;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;

/**
 * アクションメソッドの実行情報です。
 * 
 * @author baba
 */
public interface ActionInvocation extends Invocation<ActionResult> {

	/**
	 * 要求を取得します。
	 * 
	 * @return 要求
	 */
	HttpServletRequest getRequest();

	/**
	 * 応答を取得します。
	 * 
	 * @return 応答
	 */
	HttpServletResponse getResponse();

	/**
	 * アクションのコンテキストを取得します。
	 * 
	 * @return アクションのコンテキスト
	 */
	ActionContext getActionContext();

}
