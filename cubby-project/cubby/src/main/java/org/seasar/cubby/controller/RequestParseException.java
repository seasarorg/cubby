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
package org.seasar.cubby.controller;

/**
 * リクエストのパースに失敗した場合にスローされる実行時例外です。
 * 
 * @author baba
 * @since 2.0.0
 */
public class RequestParseException extends RuntimeException {

	/** シリアルバージョンUID。 */
	private static final long serialVersionUID = 1L;

	public RequestParseException() {
		super();
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public RequestParseException(String message, Throwable cause) {
		super(message, cause);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public RequestParseException(String message) {
		super(message);
		// TODO 自動生成されたコンストラクター・スタブ
	}

	public RequestParseException(Throwable cause) {
		super(cause);
		// TODO 自動生成されたコンストラクター・スタブ
	}

}
