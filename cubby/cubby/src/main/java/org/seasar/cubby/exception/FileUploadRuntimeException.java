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
 * multipart/form-data 形式でのファイルアップロードに失敗した場合にスローされる実行時例外です。
 * 
 * @author baba
 */
public class FileUploadRuntimeException extends SRuntimeException {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = -4519684364519402697L;

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
	public FileUploadRuntimeException(String messageCode, Object[] args,
			Throwable cause) {
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
	public FileUploadRuntimeException(String messageCode, Object[] args) {
		super(messageCode, args);
	}

	/**
	 * 指定されたメッセージコードを使用して新規例外を構築します。
	 * 
	 * @param messageCode
	 *            メッセージコード
	 */
	public FileUploadRuntimeException(String messageCode) {
		super(messageCode);
	}

}
