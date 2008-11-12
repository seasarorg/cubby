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
package org.seasar.cubby.controller.impl;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionResultWrapper;

/**
 * {@link org.seasar.cubby.action.ActionResult} のラッパの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
class ActionResultWrapperImpl implements ActionResultWrapper {

	/** アクションの実行結果。 */
	private final ActionResult actionResult;

	/** アクションコンテキスト。 */
	private final ActionContext actionContext;

	/**
	 * 指定されたアクションの実行結果をラップしたインスタンスを生成します。
	 * 
	 * @param actionResult
	 *            アクションの実行結果
	 * @param actionContext
	 *            アクションコンテキスト
	 */
	public ActionResultWrapperImpl(ActionResult actionResult,
			ActionContext actionContext) {
		super();
		this.actionResult = actionResult;
		this.actionContext = actionContext;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		actionResult.execute(actionContext, request, response);
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResult getActionResult() {
		return actionResult;
	}

}
