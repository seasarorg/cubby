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
package org.seasar.cubby.controller;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.framework.container.ComponentDef;

/**
 * アクションメソッドの実行時コンテキストです。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public interface ActionContext {

	/**
	 * このコンテキストを初期化します。
	 * 
	 * @param actionDef
	 *            アクションの定義
	 */
	void initialize(ActionDef actionDef);

	/**
	 * このコンテキストが初期化されているかを示します。
	 * 
	 * @return このコンテキストが初期化されている場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	boolean isInitialized();

	/**
	 * アクションのコンポーネントの定義を取得します。
	 * 
	 * @return コンポーネントの定義
	 */
	ComponentDef getComponentDef();

	/**
	 * アクションのオブジェクトを取得します。
	 * 
	 * @return アクションのオブジェクト
	 */
	Action getAction();

	/**
	 * アクションメソッドを取得します。
	 * 
	 * @return アクションメソッド
	 */
	Method getMethod();

}
