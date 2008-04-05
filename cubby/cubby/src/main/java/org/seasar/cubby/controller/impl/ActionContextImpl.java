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
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.framework.container.ComponentDef;

/**
 * アクションメソッドの実行時コンテキストの実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ActionContextImpl implements ActionContext {

	/** アクションの定義。 */
	private ActionDef actionDef;

	/** アクション。 */
	private Action action;

	/**
	 * {@inheritDoc}
	 */
	public void initialize(final ActionDef actionDef) {
		this.actionDef = actionDef;
		this.action = null;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isInitialized() {
		return this.actionDef != null;
	}

	/**
	 * {@inheritDoc}
	 */
	public ComponentDef getComponentDef() {
		return actionDef.getComponentDef();
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getMethod() {
		return actionDef.getMethod();
	}

	/**
	 * {@inheritDoc}
	 */
	public Action getAction() {
		if (action == null) {
			action = (Action) actionDef.getComponentDef().getComponent();
		}
		return action;
	}

}
