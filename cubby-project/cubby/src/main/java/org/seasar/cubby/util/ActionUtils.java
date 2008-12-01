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
package org.seasar.cubby.util;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CONTEXT;
import static org.seasar.cubby.internal.util.CubbyUtils.getAttribute;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.internal.controller.ThreadContext;

/**
 * アクションのユーティリティクラスです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ActionUtils {

	/**
	 * アクションコンテキストを取得します。
	 * <p>
	 * 実行中のスレッドに対応するリクエストの属性からオブジェクトを取得します。
	 * </p>
	 * 
	 * @return アクションコンテキスト
	 */
	public static ActionContext actionContext() {
		final HttpServletRequest request = ThreadContext.getRequest();
		final ActionContext actionContext = getAttribute(request,
				ATTR_ACTION_CONTEXT);
		return actionContext;
	}

	/**
	 * アクションエラーを取得します。
	 * 
	 * @return アクションエラー
	 */
	public static ActionErrors errors() {
		return actionContext().getActionErrors();
	}

	/**
	 * 揮発性メッセージを取得します。
	 * 
	 * @return 揮発性メッセージ
	 */
	public static Map<String, Object> flash() {
		return actionContext().getFlashMap();
	}

}
