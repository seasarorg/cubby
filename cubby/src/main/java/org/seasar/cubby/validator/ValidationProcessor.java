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

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;

/**
 * 入力検証処理です。
 * 
 * @author baba
 * @since 1.0.0
 */
public interface ValidationProcessor {

	/**
	 * 入力検証を行います。
	 * <p>
	 * 入力検証はフェーズごとに実行され、そのフェーズの入力検証でエラーがあった({@link ActionErrors}
	 * にメッセージが登録された)場合には {@link ValidationException} をスローします。
	 * </p>
	 * 
	 * @throws ValidationException
	 *             入力検証にエラーがあった場合
	 */
	void process() throws ValidationException;

	/**
	 * {@link #process()} で発生した {@link ValidationException} を処理します。
	 * <p>
	 * <ul>
	 * <li>{@link ValidationException} にメッセージが指定されていた場合はそれを
	 * {@link ActionErrors} に設定</li>
	 * <li>リクエストの属性 {@link CubbyConstants#ATTR_VALIDATION_FAIL} に
	 * <code>true</code> を設定</li>
	 * <li>{@link ValidationRules#fail(String)} の呼び出し</li>
	 * </ul>
	 * </p>
	 * 
	 * @param e
	 * @return {@link ValidationRules#fail(String)} が返す値
	 * @since 1.1.0
	 */
	ActionResult handleValidationException(ValidationException e);

}
