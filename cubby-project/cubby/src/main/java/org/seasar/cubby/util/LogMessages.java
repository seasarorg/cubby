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
package org.seasar.cubby.util;

import java.text.MessageFormat;
import java.util.ResourceBundle;

/**
 * ログに出力するメッセージのためのユーティリティクラスです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class LogMessages {

	/** リソースバンドル。 */
	private static ResourceBundle resourceBundle = ResourceBundle
			.getBundle("CUBMessages");

	/**
	 * 指定されたキーの文字列を取得し、指定された引数をフォーマットします。
	 * 
	 * @param key
	 *            望ましい文字列のキー
	 * @param arguments
	 *            フォーマットするかまたは置き換えるオブジェクトからなる配列
	 * @return フォーマットされた文字列
	 */
	public static String format(String key, Object... arguments) {
		return MessageFormat.format(resourceBundle.getString(key), arguments);
	}

}
