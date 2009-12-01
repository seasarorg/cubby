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
package org.seasar.cubby.action;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * アクションのコンテキストです。
 * 
 * @author baba
 */
public interface ActionContext {

	/**
	 * アクションを取得します。
	 * 
	 * @return アクション
	 */
	Object getAction();

	/**
	 * アクションクラスを取得します。
	 * 
	 * @return アクションクラス
	 */
	Class<?> getActionClass();

	/**
	 * アクションメソッドを取得します。
	 * 
	 * @return アクションメソッド
	 */
	Method getActionMethod();

	/**
	 * 指定されたアクションからアクションメソッドに対応するフォームオブジェクトを取得します。
	 * 
	 * @return フォームオブジェクト
	 * @throws ActionException
	 *             &#064;Formでフォームオブジェクトとなるプロパティを指定しているが、そのプロパティが
	 *             <code>null</code> だった場合
	 */
	Object getFormBean();

	/**
	 * フォームオブジェクトのすべてのプロパティに要求パラメータをバインドするかを示します。
	 * 
	 * @return フォームオブジェクトのすべてのプロパティに要求パラメータをバインドする場合は <code>true</code>
	 *         、そうでない場合は <code>false</code>
	 */
	boolean isBindRequestParameterToAllProperties();

	/**
	 * アクションメソッドの実行前に呼ばれます。
	 * <p>
	 * {@link Action#invokeInitializeMethod(Method)} を呼び出します。
	 * </p>
	 */
	void invokeInitializeMethod();

	/**
	 * フォーワードの直前に呼ばれます。
	 * <p>
	 * {@link Action#invokePreRenderMethod(Method)} を呼び出します。
	 * </p>
	 */
	void invokePreRenderMethod();

	/**
	 * フォワードの直後に呼ばれます。
	 * <p>
	 * {@link Action#invokePostRenderMethod(Method)} を呼び出します。
	 * </p>
	 */
	void invokePostRenderMethod();

	/**
	 * アクションエラーを取得します。
	 * 
	 * @return アクションエラー
	 */
	ActionErrors getActionErrors();

	/**
	 * 揮発性メッセージを取得します。
	 * 
	 * @return 揮発性メッセージ
	 */
	Map<String, Object> getFlashMap();

	/**
	 * 揮発性メッセージをクリアします。
	 */
	void clearFlash();

}