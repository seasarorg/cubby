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
package org.seasar.cubby.exception;

import org.seasar.framework.exception.SRuntimeException;

/**
 * アクションメソッドが <code>null</code> を返した場合にスローされる実行時例外です。
 * 
 * @author baba
 * @since 1.0.0
 */
public class ActionRuntimeException extends SRuntimeException {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 5185053339795669542L;

	/**
	 * 指定されたメッセージコードとその置換文字列および原因を使用して新規例外を構築します。
	 * 
	 * @param messageCode
	 *            メッセージコード
	 * @param args
	 *            メッセージコードに対応するメッセージに対する置換文字列の配列
	 * @param cause
	 *            この例外の原因
	 */
	public ActionRuntimeException(final String messageCode,
			final Object[] args, final Throwable cause) {
		super(messageCode, args, cause);
	}

	/**
	 * 指定されたメッセージコードとその置換文字列を使用して新規例外を構築します。
	 * 
	 * @param messageCode
	 *            メッセージコード
	 * @param args
	 *            メッセージコードに対応するメッセージに対する置換文字列の配列
	 */
	public ActionRuntimeException(final String messageCode, final Object[] args) {
		super(messageCode, args);
	}

	/**
	 * 指定されたメッセージコードを使用して新規例外を構築します。
	 * 
	 * @param messageCode
	 *            メッセージコード
	 */
	public ActionRuntimeException(final String messageCode) {
		super(messageCode);
	}

}