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

import static org.seasar.cubby.CubbyConstants.INTERNAL_FORWARD_DIRECTORY;

import java.lang.reflect.Method;
import java.util.Collection;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;
import org.seasar.framework.util.StringUtil;

/**
 * Cubby内部で使用するユーティリティクラスです。
 * 
 * @author baba
 * @since 1.0.0
 */
public class CubbyUtils {

	/** インデックスのメソッド名。 */
	private static final String INDEX_METHOD_NAME = "index";

	/** デフォルトの{@link Accept}アノテーション。 */
	private static Accept DEFAULT_ACCEPT_ANNOTATION;
	static {
		@Accept
		class AcceptDummy {
		}
		DEFAULT_ACCEPT_ANNOTATION = AcceptDummy.class
				.getAnnotation(Accept.class);
	}

	/**
	 * 指定されたアクションクラスに対応するディレクトリを取得します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @return アクションクラスに対応するディレクトリ
	 */
	public static String getActionDirectory(final Class<?> actionClass) {
		final String actionName;
		final Path path = actionClass.getAnnotation(Path.class);
		if (path != null && !StringUtil.isEmpty(path.value())) {
			actionName = path.value();
		} else {
			final String name = left(actionClass.getSimpleName(), "$");
			actionName = toFirstLower(name.replaceAll(
					"(.*[.])*([^.]+)(Action$)", "$2"));
		}
		return actionName;
	}

	/**
	 * 指定された文字列をセパレータで区切った左側の文字列を返します。
	 * 
	 * @param text
	 *            文字列
	 * @param sep
	 *            セパレータ
	 * @return セパレータで区切った左側の文字列
	 */
	private static String left(final String text, final String sep) {
		final int pos = text.indexOf(sep);
		if (pos != -1) {
			return text.substring(0, pos);
		}
		return text;
	}

	/**
	 * 指定された文字列の先頭1文字を小文字に変換します。
	 * 
	 * @param text
	 *            変換する文字列
	 * @return 先頭1文字を小文字にした文字列
	 */
	private static String toFirstLower(final String text) {
		if (StringUtil.isEmpty(text)) {
			throw new IllegalArgumentException("text is empty.");
		}
		final StringBuilder sb = new StringBuilder();
		sb.append(text.substring(0, 1).toLowerCase());
		if (text.length() > 1) {
			sb.append(text.substring(1));
		}
		return sb.toString();
	}

	/**
	 * 指定されたアクションメソッドのパスを取得します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @return アクションメソッドのパス
	 */
	public static String getActionPath(final Class<?> actionClass,
			final Method method) {
		final String path;
		final String actionMethodName = getActionMethodName(method);
		if (actionMethodName.startsWith("/")) {
			return path = actionMethodName;
		} else {
			final String actionDirectory = CubbyUtils
					.getActionDirectory(actionClass);
			if ("/".equals(actionDirectory)) {
				path = "/" + actionMethodName;
			} else {
				path = "/" + actionDirectory + "/" + actionMethodName;
			}
		}
		return path;
	}

	/**
	 * 指定されたアクションメソッドのアクションメソッド名を取得します。
	 * 
	 * @param method
	 *            アクションメソッド
	 * @return アクションメソッド名
	 */
	private static String getActionMethodName(final Method method) {
		final String actionName;
		final Path path = method.getAnnotation(Path.class);
		if (path != null && !StringUtil.isEmpty(path.value())) {
			actionName = path.value();
		} else {
			final String methodName = method.getName();
			if (INDEX_METHOD_NAME.equals(methodName)) {
				actionName = "";
			} else {
				actionName = methodName;
			}
		}
		return actionName;
	}

	/**
	 * 指定されたアクションメソッドが受付可能なリクエストメソッドを取得します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @return 受付可能なリクエストメソッド
	 */
	public static RequestMethod[] getAcceptableRequestMethods(
			final Class<?> actionClass, final Method method) {
		Accept accept = method.getAnnotation(Accept.class);
		if (accept == null) {
			accept = actionClass.getAnnotation(Accept.class);
			if (accept == null) {
				accept = DEFAULT_ACCEPT_ANNOTATION;
			}
		}
		return accept.value();
	}

	/**
	 * 指定されたオブジェクトのサイズを取得します。
	 * 
	 * @param value
	 *            オブジェクト
	 * @return オブジェクトのサイズ
	 */
	public static int getObjectSize(final Object value) {
		final int size;
		if (value == null) {
			size = 0;
		} else if (value.getClass().isArray()) {
			final Object[] array = (Object[]) value;
			size = array.length;
		} else if (value instanceof Collection) {
			final Collection<?> collection = (Collection<?>) value;
			size = collection.size();
		} else {
			size = 1;
		}
		return size;
	}

	/**
	 * リクエストのURIからコンテキストパスを除いたパスを返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return コンテキストパスを除いたパス
	 */
	public static String getPath(final HttpServletRequest request) {
		final String uri = request.getRequestURI();
		final String contextPath = request.getContextPath();
		final String path;
		if ("/".equals(contextPath)) {
			path = uri;
		} else {
			path = uri.substring(contextPath.length());
		}
		return path;
	}

	/**
	 * アクションクラスとメソッド名から内部フォワードのパスへ変換します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param methodName
	 *            メソッド名
	 * @return 内部フォワードパス
	 */
	public static String getInternalForwardPath(
			final Class<? extends Action> actionClass, final String methodName) {
		return "/" + INTERNAL_FORWARD_DIRECTORY + "/"
				+ actionClass.getCanonicalName() + "/" + methodName;
	}

	/**
	 * 指定されたクラスがアクションメソッドかを示します。
	 * 
	 * @param clazz
	 *            クラス
	 * @return 指定されたクラスがアクションクラスの場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static boolean isActionClass(final Class<?> clazz) {
		return Action.class.isAssignableFrom(clazz);
	}

	/**
	 * 指定されたメソッドがアクションメソッドかを示します。
	 * 
	 * @param method
	 *            メソッド
	 * @return 指定されたメソッドがアクションメソッドの場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static boolean isActionMethod(final Method method) {
		return method.getReturnType().isAssignableFrom(ActionResult.class)
				&& method.getParameterTypes().length == 0;
	}

	/**
	 * 指定された文字列のなかで、最初に出現した置換対象を置換文字列で置き換えます。
	 * 
	 * @param text
	 *            対象の文字列
	 * @param replace
	 *            置換対象
	 * @param with
	 *            置換文字列
	 * @return 最初に出現した置換対象を置換文字列で置き換えた文字列
	 */
	public static String replaceFirst(final String text, final String replace,
			final String with) {
		if (text == null || replace == null || with == null) {
			return text;
		}
		final int index = text.indexOf(replace);
		if (index == -1) {
			return text;
		}
		final StringBuilder builder = new StringBuilder(100);
		builder.append(text.substring(0, index));
		builder.append(with);
		builder.append(text.substring(index + replace.length()));
		return builder.toString();
	}

	/**
	 * 指定された文字列を区切り文字で区切った文字列の配列に変換します。
	 * 
	 * @param text
	 *            対象の文字列
	 * @param delim
	 *            区切り文字
	 * @return 指定された文字列を区切り文字で区切った文字列の配列
	 */
	public static String[] split2(final String text, final char delim) {
		if (text == null) {
			return null;
		}
		int index = text.indexOf(delim);
		if (index == -1) {
			return new String[] { text };
		}
		String[] tokens = new String[2];
		tokens[0] = text.substring(0, index);
		tokens[1] = text.substring(index + 1);
		return tokens;
	}

	/**
	 * 指定された文字列をHTMLとしてエスケープします。
	 * <p>
	 * <table> <thead>
	 * <tr>
	 * <th>変換前</th>
	 * <th>変換後</th>
	 * </tr>
	 * </thead> <tbody>
	 * <tr>
	 * <td>&amp;</td>
	 * <td>&amp;amp;</td>
	 * </tr>
	 * <tr>
	 * <td>&lt;</td>
	 * <td>&amp;lt;</td>
	 * </tr>
	 * <tr>
	 * <td>&gt;</td>
	 * <td>&amp;gt;</td>
	 * </tr>
	 * <tr>
	 * <td>&quot;</td>
	 * <td>&amp;quot;</td>
	 * </tr>
	 * <tr>
	 * <td>&#39</td>
	 * <td>&amp;#39</td>
	 * </tr>
	 * </tbody> </table>
	 * </p>
	 * 
	 * @param str
	 * @return エスケープされた文字列
	 */
	public static String escapeHtml(final Object str) {
		if (str == null) {
			return "";
		}
		String text;
		if (str instanceof String) {
			text = (String) str;
		} else {
			text = str.toString();
		}
		text = StringUtil.replace(text, "&", "&amp;");
		text = StringUtil.replace(text, "<", "&lt;");
		text = StringUtil.replace(text, ">", "&gt;");
		text = StringUtil.replace(text, "\"", "&quot;");
		text = StringUtil.replace(text, "'", "&#39;");
		return text;
	}

}