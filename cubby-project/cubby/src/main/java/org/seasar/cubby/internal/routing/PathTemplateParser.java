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
package org.seasar.cubby.internal.routing;

/**
 * パステンプレートのパーサーです。
 * 
 * @author baba
 * @since 1.1.1
 */
public interface PathTemplateParser {

	/** デフォルトの URI パラメータ正規表現 */
	static final String DEFAULT_URI_PARAMETER_REGEX = "[a-zA-Z0-9]+";

	/**
	 * 
	 * @param template
	 *            パステンプレート
	 * @param handler
	 *            プレースホルダのハンドラ
	 * @return
	 */
	String parse(String template, Handler handler);

	/**
	 * プレースホルダのハンドラ。
	 * 
	 * @author baba
	 * @since 1.1.1
	 */
	public interface Handler {

		/**
		 * プレースホルダをハンドルします。
		 * 
		 * @param name
		 *            パラメータ名
		 * @param regex
		 *            パラメータの正規表現
		 * @return プレースホルダを置換する文字列
		 */
		String handle(String name, String regex);

	}

}
