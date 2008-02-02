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

import org.seasar.cubby.controller.ActionDef;
import org.seasar.framework.container.ComponentDef;

/**
 * アクションの定義の実装です。
 * 
 * @author baba
 */
class ActionDefImpl implements ActionDef {

	/** アクションクラスのコンポーネント定義 */
	private final ComponentDef componentDef;

	/** アクションメソッド */
	private final Method method;

	/**
	 * インスタンス化します。
	 * 
	 * @param componentDef
	 *            アクションクラスのコンポーネント定義
	 * @param method
	 *            アクションメソッド
	 */
	ActionDefImpl(final ComponentDef componentDef, final Method method) {
		this.componentDef = componentDef;
		this.method = method;
	}

	/**
	 * {@inheritDoc}
	 */
	public ComponentDef getComponentDef() {
		return componentDef;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return method;
	}

}
