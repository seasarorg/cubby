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
package org.seasar.cubby.admin.servlet;

class StringUtils {

	/**
	 * 文字列を置き換えます。
	 * 
	 * @param text
	 *            テキスト
	 * @param fromText
	 *            置き換え対象のテキスト
	 * @param toText
	 *            置き換えるテキスト
	 * @return 結果
	 */
	public static final String replace(final String text,
			final String fromText, final String toText) {

		if (text == null || fromText == null || toText == null) {
			return null;
		}
		final StringBuilder builder = new StringBuilder(100);
		int pos = 0;
		int pos2 = 0;
		while (true) {
			pos = text.indexOf(fromText, pos2);
			if (pos == 0) {
				builder.append(toText);
				pos2 = fromText.length();
			} else if (pos > 0) {
				builder.append(text.substring(pos2, pos));
				builder.append(toText);
				pos2 = pos + fromText.length();
			} else {
				builder.append(text.substring(pos2));
				break;
			}
		}
		return builder.toString();
	}

}
