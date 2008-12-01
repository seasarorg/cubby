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

import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;

/**
 * メッセージの振る舞いです。
 * 
 * @author baba
 * @since 1.0.3
 */
public interface MessagesBehaviour {

	/**
	 * メッセージ表示用のリソースバンドルを取得します。
	 * 
	 * @param locale
	 *            リクエストのロケール
	 * @return メッセージ表示用のリソースバンドル
	 */
	ResourceBundle getBundle(Locale locale);

	/**
	 * 指定されたリソースバンドルを {@link Map} に変換します。
	 * 
	 * @param bundle
	 *            メッセージ表示用のリソースバンドル
	 * @return リソースバンドルを変換した {@link Map}
	 */
	Map<String, Object> toMap(ResourceBundle bundle);

}
