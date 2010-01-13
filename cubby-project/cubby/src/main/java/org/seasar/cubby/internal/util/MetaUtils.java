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
package org.seasar.cubby.internal.util;

import java.lang.reflect.Method;

import org.seasar.cubby.action.Accept;
import org.seasar.cubby.action.OnSubmit;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestMethod;

/**
 * アクションのメタ情報を扱うためのユーティリティクラスです。
 * 
 * @author baba
 */
public class MetaUtils {

	/** インデックスのメソッド名。 */
	private static final String INDEX_METHOD_NAME = "index";

	/** デフォルトの{@link Accept}アノテーション。 */
	private static final Accept DEFAULT_ACCEPT_ANNOTATION;
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
		if (path != null && !StringUtils.isEmpty(path.value())) {
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
		if (StringUtils.isEmpty(text)) {
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
			path = actionMethodName;
		} else {
			final String actionDirectory = getActionDirectory(actionClass);
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
		if (path != null && !StringUtils.isEmpty(path.value())) {
			if ("/".equals(path.value())) {
				actionName = "";
			} else {
				actionName = path.value();
			}
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
	 * 指定されたアクションメソッドが受付可能な要求メソッドを取得します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param method
	 *            アクションメソッド
	 * @return 受付可能な要求メソッド
	 */
	public static RequestMethod[] getAcceptableRequestMethods(
			final Class<?> actionClass, final Method method) {
		final Accept accept;
		if (method.isAnnotationPresent(Accept.class)) {
			accept = method.getAnnotation(Accept.class);
		} else if (actionClass.isAnnotationPresent(Accept.class)) {
			accept = actionClass.getAnnotation(Accept.class);
		} else {
			accept = DEFAULT_ACCEPT_ANNOTATION;
		}
		return accept.value();
	}

	/**
	 * アクションメソッドの{@link Path}アノテーションから優先度を取得します。
	 * 
	 * @param method
	 *            アクションメソッド
	 * @return 優先度。メソッドに{@link Path}アノテーションが設定されていない場合{@link Integer#MAX_VALUE}
	 */
	public static int getPriority(final Method method) {
		final Path path = method.getAnnotation(Path.class);
		return path != null ? path.priority() : Integer.MAX_VALUE;
	}

	/**
	 * 指定されたアクションメソッドを使用することを判断するためのパラメータ名を取得します。
	 * <p>
	 * パラメータ名によらずに実行する場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param method
	 *            アクションメソッド
	 * @return パラメータ名
	 */
	public static String getOnSubmit(final Method method) {
		final OnSubmit onSubmit = method.getAnnotation(OnSubmit.class);
		final String parameterName;
		if (onSubmit == null) {
			parameterName = null;
		} else {
			parameterName = onSubmit.value();
		}
		return parameterName;
	}

}
