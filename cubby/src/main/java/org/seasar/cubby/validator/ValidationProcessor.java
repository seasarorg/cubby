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
package org.seasar.cubby.validator;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;

/**
 * 入力検証を行うクラスです。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface ValidationProcessor {

	/**
	 * 指定されたリクエストパラメータの入力検証を行います。
	 * 
	 * @param errors
	 *            アクションで発生したエラー
	 * @param params
	 *            リクエストパラメータの{@link Map}
	 * @param form
	 *            フォームオブジェクト
	 * @param rules
	 * @return 入力検証でエラーを発見した場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	boolean process(ActionErrors errors, Map<String, Object[]> params,
			Object form, ValidationRules rules);

}
