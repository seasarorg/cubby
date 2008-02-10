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

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

/**
 * CubbyのJSP EL functionsを提供します。
 * 
 * @author baba
 * @since 1.0.0
 */
public class CubbyFunctions {

	/**
	 * 配列やコレクションに指定したオブジェクトが含まれるかどうかを判定します。
	 * 
	 * @param collection
	 *            配列や{@link Collection コレクション}
	 * @param obj
	 *            配列やコレクションにあるかどうかを調べる要素
	 * @return 配列やコレクションに指定したオブジェクトが含まれる場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static Boolean contains(final Object collection, final Object obj) {
		if (collection instanceof Collection) {
			return _contains((Collection<?>) collection, obj);
		} else if (collection != null && collection.getClass().isArray()) {
			return _contains(Arrays.asList((Object[]) collection), obj);
		} else {
			return false;
		}
	}

	/**
	 * 指定された要素が{@link Collection}内にあるかどうかを示します。
	 * 
	 * @param collection
	 *            コレクション
	 * @param obj
	 *            コレクションにあるかどうかを調べる要素
	 * @return 指定された要素が{@link Collection}内にある場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private static Boolean _contains(final Collection<?> collection,
			final Object obj) {
		return collection.contains(obj);
	}

	/**
	 * {@link Map}に指定したキーが含まれるかどうかを判定します。
	 * 
	 * @param map
	 *            マップ
	 * @param key
	 *            マップにあるかどうかが判定されるキー
	 * @return {@link Map}に指定したキーが含まれる場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static Boolean containsKey(final Map<?, ?> map, final Object key) {
		return map.containsKey(key);
	}

	/**
	 * {@link Map}に指定した値が含まれるかどうかを判定します。
	 * 
	 * @param map
	 *            マップ
	 * @param value
	 *            マップにあるかどうかを判定される値
	 * @return {@link Map}に指定した値が含まれる場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static Boolean containsValue(final Map<?, ?> map, final Object value) {
		return map.containsValue(value);
	}

	/**
	 * 指定したカンマ区切りの文字列をインデックス値でサイクルして出力します。
	 * <p>
	 * 主に行毎に色分けする場合に CSS のクラス名を出力する場合に使用します。
	 * </p>
	 * 
	 * @param index
	 *            インデックス
	 * @param classNames
	 *            カンマ区切りの文字列
	 * @return 指定したインデックスに対応する文字列
	 */
	public static String odd(final Integer index, final String classNames) {
		final String[] c = classNames.split(",");
		return c[index % c.length];
	}

	/**
	 * HTMLをエスケープします。
	 * <p>
	 * JSTLのoutタグの代わりに使用します。EL式で出力された文字列はエスケープされないため、エスケープを行いたい場合はこのfunctionを使用します。
	 * </p>
	 * 
	 * @param str
	 *            エスケープする文字列
	 * @return エスケープされた HTML
	 */
	public static String out(final Object str) {
		return str == null ? "" : CubbyUtils.escapeHtml(str.toString());
	}

	/**
	 * {@link Date}型のオブジェクトをフォーマットして出力します。
	 * <p>
	 * JSTL の dateFormat タグの代わりに使用します。
	 * </p>
	 * 
	 * @param date
	 *            日付/時刻文字列にフォーマットする日付/時刻値
	 * @param pattern
	 *            日付と時刻のフォーマットを記述するパターン
	 * @return フォーマットされた日付/時刻文字列
	 */
	public static String dateFormat(final Object date, final String pattern) {
		if (date instanceof Date) {
			final SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		} else {
			return "";
		}
	}

}
