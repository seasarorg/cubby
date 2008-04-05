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

import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;

/**
 * アクションの定義です。
 * 
 * @author baba
 * @since 1.0.0
 */
class ActionDef {

	/** アクション。 */
	private final Action action;

	/** アクションクラス。 */
	private final Class<? extends Action> actionClass;

	/** アクションメソッド。 */
	private final Method method;

	/**
	 * インスタンス化します。
	 * 
	 * @param componentDef
	 *            アクションクラスのコンポーネント定義
	 * @param method
	 *            アクションメソッド
	 */
	ActionDef(final Action action, final Class<? extends Action> actionClass,
			final Method method) {
		this.action = action;
		this.actionClass = actionClass;
		this.method = method;
	}

	/**
	 * アクションを取得します。
	 * 
	 * @return アクション
	 */
	public Action getAction() {
		return action;
	}

	/**
	 * アクションクラスを取得します。
	 * 
	 * @return アクションクラス
	 */
	public Class<? extends Action> getActionClass() {
		return actionClass;
	}

	/**
	 * アクションメソッドを取得します。
	 * 
	 * @return アクションメソッド
	 */
	public Method getMethod() {
		return method;
	}

}
