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

package org.seasar.cubby.spi.container;

/**
 * 指定された型に代入可能なオブジェクトの取得に失敗したことを表す例外です。
 * 
 * @author baba
 */
public class LookupException extends RuntimeException {

	/** シリアルバージョン UID */
	private static final long serialVersionUID = 1L;

	/**
	 * 詳細メッセージに <code>null</code> を使用して、新規例外を構築します。
	 */
	public LookupException() {
		super();
	}

	/**
	 * 指定された詳細メッセージおよび原因を使用して新規例外を構築します。
	 * 
	 * @param message
	 *            詳細メッセージ
	 * @param cause
	 *            原因
	 */
	public LookupException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * 指定された詳細メッセージを使用して、新規例外を構築します。
	 * 
	 * @param message
	 *            詳細メッセージ
	 */
	public LookupException(final String message) {
		super(message);
	}

	/**
	 * 指定された原因および詳細メッセージを使用して新規例外を構築します
	 * 
	 * @param cause
	 *            原因
	 */
	public LookupException(final Throwable cause) {
		super(cause);
	}

}
