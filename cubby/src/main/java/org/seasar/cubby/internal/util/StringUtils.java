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

package org.seasar.cubby.internal.util;

/**
 * 文字列操作を行うためのユーティリティクラスです。
 * 
 * @author baba
 */
public class StringUtils {

	/**
	 * 空かどうかを返します。
	 * 
	 * @param text
	 *            文字列
	 * @return 空かどうか
	 */
	public static final boolean isEmpty(final String text) {
		return text == null || text.length() == 0;
	}

	/**
	 * ケースインセンシティブで文字列同士が等しいかどうか返します。どちらもnullの場合は、<code>true</code>を返します。
	 * 
	 * @param target1
	 *            文字列1
	 * @param target2
	 *            文字列2
	 * @return ケースインセンシティブで文字列同士が等しいか
	 */
	public static boolean equalsIgnoreCase(final String target1,
			final String target2) {
		return (target1 == null) ? (target2 == null) : target1
				.equalsIgnoreCase(target2);
	}

	/**
	 * ブランクかどうか返します。
	 * 
	 * @param str
	 *            文字列
	 * @return ブランクかどうか
	 */
	public static boolean isBlank(final String str) {
		if (str == null || str.length() == 0) {
			return true;
		}
		for (int i = 0; i < str.length(); i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * ブランクではないかどうか返します。
	 * 
	 * @param str
	 *            文字列
	 * @return ブランクではないかどうか
	 * @see #isBlank(String)
	 */
	public static boolean isNotBlank(final String str) {
		return !isBlank(str);
	}

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
