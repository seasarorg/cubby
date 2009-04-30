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
package org.seasar.cubby.internal.controller;

import java.util.Map;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;

/**
 * リクエストパラメータをオブジェクトへバインドするクラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public interface RequestParameterBinder {

	/**
	 * リクエストパラメータを指定されたオブジェクトへバインドします。
	 * 
	 * @param parameterMap
	 *            リクエストパラメータの{@link Map}
	 * @param dest
	 *            リクエストパラメータをバインドするオブジェクト
	 * @param actionContext
	 *            アクションコンテキスト
	 * @param errors
	 *            型変換で発生したエラーを保持する
	 */
	void bind(Map<String, Object[]> parameterMap, Object dest,
			ActionContext actionContext, ActionErrors errors);

}