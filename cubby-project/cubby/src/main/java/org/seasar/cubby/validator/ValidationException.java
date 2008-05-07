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

/**
 * 入力検証に失敗した場合に後続の入力検証を実行しないようにするためにスローする例外です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class ValidationException extends RuntimeException {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 1L;

	/** メッセージ。 */
	private final String message;

	/** フィールド名。 */
	private final String[] fieldNames;

	/**
	 * 新規例外を構築します。
	 */
	public ValidationException() {
		this(null);
	}

	/**
	 * 新規例外を構築します。
	 * 
	 * @param message
	 *            メッセージ
	 * @param fieldNames
	 *            フィールド名
	 */
	public ValidationException(String message, String... fieldNames) {
		this.message = message;
		this.fieldNames = fieldNames;
	}

	/**
	 * この例外がメッセージを含むかを示します。
	 * 
	 * @return この例外がメッセージを含む場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	public boolean hasMessage() {
		return message != null;
	}

	/**
	 * メッセージを取得します。
	 * 
	 * @return メッセージ
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * フィールド名を取得します。
	 * 
	 * @return フィールド名
	 */
	public String[] getFieldNames() {
		return fieldNames;
	}

}
