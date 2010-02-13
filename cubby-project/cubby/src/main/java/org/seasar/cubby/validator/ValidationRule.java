/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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
 * 入力検証のルールです。
 * 
 * @author baba
 */
public interface ValidationRule {

	/**
	 * 要求パラメータにこの入力検証ルールを適用します。
	 * <p>
	 * 入力検証エラーの場合は指定された{@link ActionErrors}にメッセージを設定します。
	 * </p>
	 * 
	 * @param params
	 *            要求パラメータの{@link Map}
	 * @param form
	 *            フォームオブジェクト
	 * @param errors
	 *            アクションで発生したエラー
	 * @throws ValidationException
	 *             入力検証でエラーを検出し、検証を途中で中断する場合にスローされます
	 */
	void apply(Map<String, Object[]> params, Object form, ActionErrors errors)
			throws ValidationException;

}
