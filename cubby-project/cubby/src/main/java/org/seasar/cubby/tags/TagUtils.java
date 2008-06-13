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
package org.seasar.cubby.tags;

import static java.lang.Boolean.TRUE;
import static javax.servlet.jsp.PageContext.REQUEST_SCOPE;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspContext;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.util.StringUtil;

/**
 * カスタムタグで使用するユーティリティクラスです。
 * 
 * @author baba
 * @since 1.0.0
 */
class TagUtils {

	/** リクエストスコープから{@link ActionErrors}を取得するためのキー。 */
	private static final String ATTR_ERRORS = "errors";

	/**
	 * 指定されたJSPコンテキストから{@link ActionErrors}を取得します。
	 * 
	 * @param context
	 *            JSPコンテキスト
	 * @return アクションで発生したエラー
	 */
	public static ActionErrors errors(final JspContext context) {
		return (ActionErrors) context.getAttribute(ATTR_ERRORS, REQUEST_SCOPE);
	}

	/**
	 * 指定されたJSPコンテキストから指定されたパラメータ名に対応するリクエストパラメータを取得します。
	 * 
	 * @param context
	 *            JSPコンテキスト
	 * @param name
	 *            パラメータ名
	 * @return リクエストパラメータ
	 */
	@SuppressWarnings("unchecked")
	private static Object[] paramValues(final JspContext context,
			final String name) {
		final Map<String, Object[]> valuesMap = (Map<String, Object[]>) context
				.getAttribute(ATTR_PARAMS, REQUEST_SCOPE);
		final Object[] values;
		if (valuesMap == null || !valuesMap.containsKey(name)) {
			values = new Object[0];
		} else {
			values = valuesMap.get(name);
		}
		return values;
	}

	/**
	 * フォーム値の{@link Map}から指定されたフィールドの値を取得します。
	 * 
	 * @param valuesMap
	 *            フォーム値の{@link Map}
	 * @param name
	 *            フィールド名
	 * @return フィールドの値
	 */
	private static Object[] formValues(final Map<String, String[]> valuesMap,
			final String name) {
		final Object[] values;
		if (valuesMap == null || !valuesMap.containsKey(name)) {
			values = new Object[0];
		} else {
			values = valuesMap.get(name);
		}
		return values;
	}

	/**
	 * 指定されたフィールド名に対応するフォームのフィールドへの出力値を取得します。
	 * 
	 * @param context
	 *            JSPコンテキスト
	 * @param outputValuesMap
	 *            フォームへ出力する値の{@link Map}
	 * @param name
	 *            フィールド名
	 * @return フォームのフィールドへの出力値
	 */
	public static Object[] multipleFormValues(final JspContext context,
			final Map<String, String[]> outputValuesMap, final String name) {
		return multipleFormValues(context, outputValuesMap, name, null);
	}

	/**
	 * 指定されたフィールド名に対応するフォームのフィールドへの出力値を取得します。
	 * 
	 * @param context
	 *            JSPコンテキスト
	 * @param outputValuesMap
	 *            フォームへ出力する値の{@link Map}
	 * @param name
	 *            フィールド名
	 * @param checkedValue
	 *            チェック済みにする値
	 * @return フォームのフィールドへの出力値
	 */
	public static Object[] multipleFormValues(final JspContext context,
			final Map<String, String[]> outputValuesMap, final String name,
			final String checkedValue) {
		final Object[] values;
		if (isValidationFail(context)) {
			values = paramValues(context, name);
		} else {
			if (checkedValue != null) {
				values = new Object[] { checkedValue };
			} else {
				values = formValues(outputValuesMap, name);
			}
		}
		return values;
	}

	/**
	 * 指定されたフィールド名に対応するフォームのフィールドへの出力値を取得します。
	 * 
	 * @param context
	 *            JSPコンテキスト
	 * @param outputValuesMap
	 *            フォームへ出力する値の{@link Map}
	 * @param name
	 *            フィールド名
	 * @param index
	 *            インデックス
	 * @param specifiedValue
	 *            エラーがない場合に設定する値
	 * @return フォームのフィールドへの出力値
	 */
	public static Object formValue(final JspContext context,
			final Map<String, String[]> outputValuesMap, final String name,
			final Integer index, final Object specifiedValue) {
		final Object value;

		if (isValidationFail(context)) {
			final Object[] values = paramValues(context, name);
			value = value(values, index);
		} else {
			if (specifiedValue == null) {
				final Object[] values = formValues(outputValuesMap, name);
				value = value(values, index);
			} else {
				value = specifiedValue;
			}
		}

		return value;
	}

	/**
	 * オブジェクトの配列から指定されたインデックスの値を取得します。
	 * <p>
	 * values が <code>null</code> の場合や index が要素数を越えていた場合は空文字を返します。index が
	 * <code>null</code> の場合は配列の最初の要素を返します。
	 * </p>
	 * 
	 * @param values
	 *            オブジェクトの配列
	 * @param index
	 *            インデックス
	 * @return 指定されたインデックスの要素
	 */
	private static Object value(final Object[] values, final Integer index) {
		final Object value;
		if (values == null) {
			value = "";
		} else {
			if (index == null) {
				value = getElement(values, 0);
			} else {
				value = getElement(values, index);
			}
		}
		return value;
	}

	/**
	 * オブジェクトの配列から指定されたインデックスの要素を取得します。
	 * <p>
	 * index が要素数を越えていた場合は空文字を返します。
	 * </p>
	 * 
	 * @param values
	 *            オブジェクトの配列
	 * @param index
	 *            インデックス
	 * @return 指定されたインデックスの要素
	 */
	private static Object getElement(final Object[] values, final Integer index) {
		final Object value;
		if (values.length <= index) {
			value = "";
		} else {
			value = values[index];
		}
		return value;
	}

	/**
	 * 指定されたJSPコンテキストのアクションが入力検証に失敗したかどうかを示します。
	 * 
	 * @param context
	 *            JSPコンテキスト
	 * @return アクションが入力検証に失敗した場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 * @see CubbyConstants#ATTR_VALIDATION_FAIL
	 */
	private static boolean isValidationFail(final JspContext context) {
		return TRUE.equals(context.getAttribute(ATTR_VALIDATION_FAIL,
				REQUEST_SCOPE));
	}

	/**
	 * 指定された{@link Map}をHTMLタグの属性へ変換します。
	 * 
	 * @param map
	 *            属性のマップ
	 * @return HTMLタグの属性
	 */
	public static String toAttr(final Map<String, Object> map) {
		final StringBuilder builder = new StringBuilder();
		for (final Entry<String, Object> entry : map.entrySet()) {
			final String key = entry.getKey();
			if ("value".equals(key) || "checkedValue".equals(key)) {
				continue;
			}
			builder.append(key);
			builder.append("=\"");
			builder.append(CubbyUtils.escapeHtml(entry.getValue()));
			builder.append("\" ");
		}
		return builder.toString();
	}

	/**
	 * 指定されたオブジェクトが特定の文字列を含むかを示します。
	 * <p>
	 * 指定されたオブジェクトが配列や{@link Collection}の場合は、その要素の文字列表現が指定された文字列と同値かを示します。
	 * 指定されたオブジェクトが配列や{@link Collection}でない場合は、そのオブジェクトの文字列表現が指定された文字列と同値かを示します。
	 * </p>
	 * 
	 * @param obj
	 *            オブジェクト
	 * @param str
	 *            文字列
	 * @return 指定されたオブジェクトが特定の文字列を含む場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	public static boolean contains(final Object obj, final String str) {
		if (obj instanceof Collection) {
			return ((Collection<?>) obj).contains(str);
		} else if (obj.getClass().isArray()) {
			for (final Object value : (Object[]) obj) {
				if (equalsAsString(value, str)) {
					return true;
				}
			}
			return false;
		} else {
			return equalsAsString(obj, str);
		}
	}

	/**
	 * 指定された値が文字列として同値かを示します。
	 * 
	 * @param obj1
	 *            比較するオブジェクト1
	 * @param obj2
	 *            比較するオブジェクト2
	 * @return obj1とobj2が文字列として同値の場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private static boolean equalsAsString(final Object obj1, final Object obj2) {
		if (obj1 == obj2) {
			return true;
		} else if (obj1 == null) {
			return false;
		} else {
			return obj1.toString().equals(obj2.toString());
		}
	}

	/**
	 * Dynamic-Attributesに指定されたclass属性を追加します。
	 * 
	 * @param dyn
	 *            Dynamic-Attributes
	 * @param className
	 *            class属性の名前
	 */
	public static void addClassName(final Map<String, Object> dyn,
			final String className) {
		String classValue = (String) dyn.get("class");
		if (StringUtil.isEmpty(classValue)) {
			classValue = className;
		} else {
			classValue = classValue + " " + className;
		}
		dyn.put("class", classValue);
	}

}