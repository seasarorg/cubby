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

package org.seasar.cubby.action;

import javax.servlet.ServletRequest;

import org.seasar.cubby.CubbyConstants;

/**
 * 要求の属性に設定されたアクションコンテキストへアクセスするためのヘルパークラスです。
 * 
 * @since 2.0.5
 * @author baba
 */
class ActionContextHelper {

	/** 要求 */
	private final ServletRequest request;

	/** アクションのコンテキスト */
	private ActionContext actionContext;

	/**
	 * 指定された要求の属性に設定されたアクションコンテキストへアクセスするヘルパーを生成します。
	 * 
	 * @param request
	 *            要求
	 */
	public ActionContextHelper(final ServletRequest request) {
		this.request = request;
		this.actionContext = null;
	}

	/**
	 * 要求の属性からアクションコンテキストを取得します。
	 * 
	 * @return アクションコンテキスト
	 */
	public ActionContext getActionContext() {
		// lazy initialization
		if (this.actionContext == null) {
			final ActionContext actionContext = (ActionContext) request
					.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT);
			if (actionContext == null) {
				throw new IllegalStateException(
						"ActionContext might not been initialized yet");
			}
			this.actionContext = actionContext;
		}
		return actionContext;
	}

}
