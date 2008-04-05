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
package org.seasar.cubby.interceptor;

import java.lang.reflect.Method;

import org.aopalliance.intercept.Interceptor;
import org.aopalliance.intercept.Invocation;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.action.Action;

/**
 * {@link Interceptor} で使用するユーティリティクラスです。
 * 
 * @author baba
 */
class InterceptorUtils {

	/**
	 * アクションを取得します。
	 * 
	 * @param invocation
	 *            実行メソッドの定義
	 * @return アクション
	 */
	static Action getAction(final MethodInvocation invocation) {
		return (Action) invocation.getThis();
	}

	/**
	 * アクションクラスを取得します。
	 * 
	 * @param invocation
	 *            実行メソッドの定義
	 * @return アクションクラス
	 */
	@SuppressWarnings("unchecked")
	static Class<? extends Action> getActionClass(
			final MethodInvocation invocation) {
		return (Class<? extends Action>) getTargetClass(invocation);
	}

	/**
	 * アクションメソッドを取得します。
	 * 
	 * @param invocation
	 *            実行メソッドの定義
	 * @return アクションメソッド
	 */
	static Method getMethod(final MethodInvocation invocation) {
		return invocation.getMethod();
	}

	/**
	 * バイトコードがエンハンスされる前のクラスを返します。
	 * 
	 * @param invocation
	 *            invocation
	 * @return エンハンス前のクラス
	 * @since 1.0.2
	 */
	private static Class<?> getTargetClass(final Invocation invocation) {
		final Class<?> thisClass = invocation.getThis().getClass();
		final Class<?> superClass = thisClass.getSuperclass();
		if (superClass == Object.class) {
			return thisClass.getInterfaces()[0];
		}
		return superClass;
	}

}
