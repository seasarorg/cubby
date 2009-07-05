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
package org.seasar.cubby.plugins.guice;

/**
 * 不正なモジュールが指定されたことを示すためにスローされます。
 * 
 * @author baba
 */
public class IllegalModuleException extends Exception {

	/** シリアルバージョン UID。 */
	private static final long serialVersionUID = 1L;

	/**
	 * 詳細メッセージに <code>null</code> を使用して、新規例外を構築します。
	 */
	public IllegalModuleException() {
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
	public IllegalModuleException(final String message, final Throwable cause) {
		super(message, cause);
	}

	/**
	 * 指定された詳細メッセージを使用して、新規例外を構築します。
	 * 
	 * @param message
	 *            詳細メッセージ
	 */
	public IllegalModuleException(final String message) {
		super(message);
	}

	/**
	 * 指定された原因および詳細メッセージを使用して新規例外を構築します。
	 * 
	 * @param cause
	 *            原因
	 */
	public IllegalModuleException(final Throwable cause) {
		super(cause);
	}

}
