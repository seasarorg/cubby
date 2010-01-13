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
package org.seasar.cubby.tags;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.URLBodyEncoder;

/**
 * JSP functions を提供します。
 * 
 * @author baba
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
		if (collection instanceof Collection<?>) {
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
		return c[index % c.length].trim();
	}

	/**
	 * HTMLをエスケープします。
	 * <p>
	 * JSTLのoutタグの代わりに使用します。EL式で出力された文字列はエスケープされないため、
	 * エスケープを行いたい場合はこのfunctionを使用します。
	 * </p>
	 * 
	 * @param str
	 *            エスケープする文字列
	 * @return エスケープされた HTML
	 */
	public static String out(final Object str) {
		return str == null ? "" : TagUtils.escapeHtml(str.toString());
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

	/**
	 * 指定された条件によって属性を出力するかしないかを制御します。
	 * <p>
	 * <code>condition</code> が <code>true</code> のときは <code>value</code>
	 * を属性の値として出力し、 <code>condition</code> が <code>false</code> のときは属性自体を出力しません。
	 * 条件によって disabled や checked などの属性の出力する・しないを制御したい場合に使用します。
	 * 出力する・しないの制御はカスタムタグで行うので、t:input/t:select/t:textarea と組み合わせて使用してください。
	 * </p>
	 * <p>
	 * 
	 * <pre>
	 * &lt;t:input name=&quot;foo&quot; disabled=&quot;${f:render(cond == true, \&quot;disabled\&quot;)} /&gt;
	 * </pre>
	 * 
	 * 上記の例では、<code>cond == true</code> の場合には input
	 * タグの属性に「disabled="disabled"」が出力され、 <code>cond == false</code> の場合には
	 * disabled 属性が出力されません。
	 * </p>
	 * 
	 * @param condition
	 *            属性を出力する条件
	 * @param value
	 * @return condition が <code>true</code> の場合は
	 *         value、そうでない場合は属性を出力しないことを示すオブジェクト
	 */
	public static Object ifrender(final Boolean condition, final Object value) {
		if (condition) {
			return value;
		}
		return TagUtils.REMOVE_ATTRIBUTE;
	}

	/**
	 * 文字列を Base64 でエンコードします。
	 * <p>
	 * JSTL の url タグの代わりに使用します。 {@code
	 * HttpServletRequest#getCharacterEncoding()}で取得した文字コードでエンコードされます。
	 * </p>
	 * <p>
	 * 例：<br/>
	 * ${f:url('abc あいう'))} -> abc+%E3%81%82%E3%81%84%E3%81%86
	 * </p>
	 * 
	 * @param str
	 *            エンコードする文字列
	 * @return エンコードされた文字列
	 * @see HttpServletRequest#setCharacterEncoding(String)
	 * @see HttpServletRequest#getCharacterEncoding()
	 * @throws UnsupportedEncodingException
	 */
	public static String url(final Object str)
			throws UnsupportedEncodingException {
		if (str == null) {
			return "";
		}
		final ThreadContext currentContext = ThreadContext.getCurrentContext();
		final String characterEncoding = currentContext.getRequest()
				.getCharacterEncoding();
		return URLBodyEncoder.encode(str.toString(), characterEncoding);
	}

}
