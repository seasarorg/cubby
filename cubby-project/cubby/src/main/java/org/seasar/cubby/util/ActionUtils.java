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

import javax.servlet.ServletRequest;
import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionClass;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ThreadContext;

/**
 * アクションのユーティリティクラスです。
 * 
 * @author baba
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
		final ThreadContext currentContext = ThreadContext.getCurrentContext();
		final HttpServletRequest request = currentContext.getRequest();
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
	 * 指定されたクラスがアクションクラスかを示します。
	 * <p>
	 * アクションクラスは以下のいずれかの条件を満たす必要があります。
	 * </p>
	 * <ul>
	 * <li>{@link Action} を実装している</li>
	 * <li>{@link ActionClass} で修飾されている</li>
	 * </ul>
	 * 
	 * @param clazz
	 *            クラス
	 * @return 指定されたクラスがアクションクラスの場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static boolean isActionClass(final Class<?> clazz) {
		if (Action.class.isAssignableFrom(clazz)) {
			return true;
		}
		if (clazz.isAnnotationPresent(ActionClass.class)) {
			return true;
		}
		return false;
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
