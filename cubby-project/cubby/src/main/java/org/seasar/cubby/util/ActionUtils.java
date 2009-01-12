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
package org.seasar.cubby.util;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_CONTEXT;
import static org.seasar.cubby.internal.util.RequestUtils.getAttribute;

import java.lang.reflect.Method;
import java.util.Map;

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
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
	 * 実行中のスレッドに対応する要求の属性からオブジェクトを取得します。
	 * </p>
	 * 
	 * @return アクションコンテキスト
	 */
	public static ActionContext actionContext() {
		final HttpServletRequest request = ThreadContext.getRequest();
		return actionContext(request);
	}

	/**
	 * アクションコンテキストを取得します。
	 * <p>
	 * 指定された要求の属性からオブジェクトを取得します。
	 * </p>
	 * 
	 * @param request
	 *            要求
	 * @return アクションコンテキスト
	 */
	public static ActionContext actionContext(final ServletRequest request) {
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
	 * アクションエラーを取得します。
	 * 
	 * @param request
	 *            要求
	 * @return アクションエラー
	 */
	public static ActionErrors errors(final ServletRequest request) {
		return actionContext(request).getActionErrors();
	}

	/**
	 * 揮発性メッセージを取得します。
	 * 
	 * @return 揮発性メッセージ
	 */
	public static Map<String, Object> flash() {
		return actionContext().getFlashMap();
	}

	/**
	 * 揮発性メッセージを取得します。
	 * 
	 * @param request
	 *            要求
	 * @return 揮発性メッセージ
	 */
	public static Map<String, Object> flash(final ServletRequest request) {
		return actionContext(request).getFlashMap();
	}

	/**
	 * 指定されたメソッドがアクションメソッドかを示します。
	 * <p>
	 * アクションメソッドは以下の条件を満たす必要があります。
	 * </p>
	 * <ul>
	 * <li>publicなインスタンスメソッド</li>
	 * <li>戻り値が{@code ActionResult}</li>
	 * <li>引数が0</li>
	 * </ul>
	 * 
	 * @param method
	 *            メソッド
	 * @return 指定されたメソッドがアクションメソッドの場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static boolean isActionMethod(final Method method) {
		return ActionResult.class.isAssignableFrom(method.getReturnType())
				&& (method.getParameterTypes().length == 0);
	}

}
