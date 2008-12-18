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

import org.seasar.cubby.action.ActionResult;

/**
 * 入力検証に失敗した場合に後続の入力検証を実行しないようにするためにスローする例外です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ValidationException extends RuntimeException {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 1L;

	/** 入力検証でエラーがあった場合の振る舞い。 */
	private final ValidationFailBehaviour behaviour;

	/**
	 * 新規例外を構築します。
	 * 
	 * @param behaviour
	 *            入力検証でエラーがあった場合の振る舞い
	 */
	public ValidationException(final ValidationFailBehaviour behaviour) {
		this.behaviour = behaviour;
	}

	/**
	 * 新規例外を構築します。
	 */
	public ValidationException() {
		this(new ErrorPageValidationFailBehaviour());
	}

	/**
	 * 新規例外を構築します。
	 * 
	 * @param errorMessage
	 *            メッセージ
	 * @param fieldNames
	 *            フィールド名
	 */
	public ValidationException(final String errorMessage,
			final String... fieldNames) {
		this(new ErrorPageValidationFailBehaviour(errorMessage, fieldNames));
	}

	/**
	 * 新規例外を構築します。
	 * 
	 * @param actionResult
	 *            エラーページの遷移などを行う {@link ActionResult}
	 */
	public ValidationException(final ActionResult actionResult) {
		this(new ActionResultValidationFailBehaviour(actionResult));
	}

	/**
	 * 入力検証でエラーがあった場合の振る舞いを取得します。
	 * 
	 * @return 入力検証でエラーがあった場合の振る舞い
	 */
	public ValidationFailBehaviour getBehaviour() {
		return behaviour;
	}

}
