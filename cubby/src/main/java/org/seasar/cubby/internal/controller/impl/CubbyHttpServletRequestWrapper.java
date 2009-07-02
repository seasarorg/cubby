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
package org.seasar.cubby.internal.controller.impl;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTEXT_PATH;
import static org.seasar.cubby.CubbyConstants.ATTR_MESSAGES;

import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.IteratorEnumeration;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;

/**
 * 特別な属性を取得するためにサーブレットへの要求をラップします。
 * <p>
 * <ul>
 * <li>{@link #getAttribute(String)}</li>
 * <li>{@link #getAttributeNames()}</li>
 * </ul>
 * 上記メソッドでは、ラップされた要求の属性に加えて以下のような属性を使用することができます。
 * <table>
 * <thead>
 * <tr>
 * <th>属性名</th>
 * <th>値</th>
 * <th>型</th>
 * </tr>
 * </thead><tbody>
 * <tr>
 * <td>{@link CubbyConstants#ATTR_CONTEXT_PATH}</td>
 * <td>コンテキストパス</td>
 * <td>{@link String}</td>
 * </tr>
 * <tr>
 * <td>{@link CubbyConstants#ATTR_ACTION}</td>
 * <td>アクション</td>
 * <td>{@link org.seasar.cubby.action.Action}</td>
 * </tr>
 * <tr>
 * <td>{@link CubbyConstants#ATTR_MESSAGES}</td>
 * <td>メッセージリソース</td>
 * <td>{@link java.util.Map}</td>
 * </tr>
 * <tr>
 * <td>アクションのプロパティ名</td>
 * <td>アクションのプロパティ値</td>
 * <td>任意</td>
 * </tr>
 * </table>
 * これらの属性は通常の属性よりも優先されるのでご注意ください。
 * </p>
 * <p>
 * また、以下のリクエストパラメータに関するメソッドは、通常のリクエストパラメータに加え、URI パラメータも対象として処理します。
 * <ul>
 * <li>{@link #getParameter(String)}</li>
 * <li>{@link #getParameterMap()}</li>
 * <li>{@link #getParameterNames()}</li>
 * <li>{@link #getParameterValues(String)}</li>
 * </ul>
 * </p>
 * 
 * @author baba
 */
class CubbyHttpServletRequestWrapper extends HttpServletRequestWrapper {

	/** URI パラメータの {@link Map} です。 */
	private final Map<String, String[]> uriParameters;

	/**
	 * 指定された要求をラップした要求オブジェクトを構築します。
	 * 
	 * @param request
	 *            要求
	 * @param uriParameters
	 *            URI パラメータの {@link Map}
	 */
	public CubbyHttpServletRequestWrapper(final HttpServletRequest request,
			final Map<String, String[]> uriParameters) {
		super(request);
		this.uriParameters = uriParameters;
	}

	/**
	 * 指定された属性の値を <code>Object</code> として返します。指定された名前の属性が存在しない場合は、
	 * <code>null</code> を返します。
	 * 
	 * @param name
	 *            属性の名前を指定する <code>String</code>
	 * @return 属性の値を含む <code>Object</code>。属性が存在しない場合は <code>null</code>
	 */
	@Override
	public Object getAttribute(final String name) {
		final Object value;
		if (ATTR_CONTEXT_PATH.equals(name)) {
			value = this.getContextPath();
		} else if (ATTR_MESSAGES.equals(name)) {
			value = ThreadContext.getMessagesMap();
		} else {
			final Object action = super.getAttribute(ATTR_ACTION);
			if (action != null) {
				final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action
						.getClass());
				if (beanDesc.hasPropertyAttribute(name)) {
					final Attribute attribute = beanDesc
							.getPropertyAttribute(name);
					if (attribute.isReadable()) {
						value = attribute.getValue(action);
					} else {
						value = super.getAttribute(name);
					}
				} else {
					value = super.getAttribute(name);
				}
			} else {
				value = super.getAttribute(name);
			}
		}
		return value;
	}

	/**
	 * この要求で利用できる属性の名前が格納された <code>Enumeration</code> を返します。利用できる属性が要求にない場合は、空の
	 * <code>Enumeration</code> を返します。
	 * 
	 * @return 要求に付随する属性の名前が格納された文字列の <code>Enumeration</code>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getAttributeNames() {
		final Set attributeNames = new HashSet();

		attributeNames.add(ATTR_CONTEXT_PATH);
		attributeNames.add(ATTR_ACTION);
		attributeNames.add(ATTR_MESSAGES);

		final Object action = super.getAttribute(ATTR_ACTION);
		if (action != null) {
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action
					.getClass());
			for (final Attribute attribute : beanDesc.findtPropertyAttributes()) {
				if (attribute.isReadable()) {
					attributeNames.add(attribute.getName());
				}
			}
		}

		final Enumeration defaultAttributeNames = super.getAttributeNames();
		while (defaultAttributeNames.hasMoreElements()) {
			attributeNames.add(defaultAttributeNames.nextElement());
		}
		return new IteratorEnumeration(attributeNames.iterator());
	}

	/**
	 * 要求パラメータの値を <code>String</code> として返します。
	 * <p>
	 * パラメータが存在しない場合は、<code>null</code> を返します。
	 * </p>
	 * 
	 * @param name
	 *            パラメータの名前を指定する <code>String</code>
	 * @return パラメータの単一の値を表す <code>String</code>
	 */
	@Override
	public String getParameter(final String name) {
		final String[] parameters = this.getParameterValues(name);
		if (parameters == null) {
			return null;
		} else {
			return parameters[0];
		}
	}

	/**
	 * この要求に含まれるパラメータの名前を格納した、<code>String</code> オブジェクトの
	 * <code>Enumeration</code> を返します。
	 * <p>
	 * パラメータが要求にない場合、このメソッドは空の <code>Enumeration</code> を返します。
	 * </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Enumeration getParameterNames() {
		return new IteratorEnumeration(this.getParameterMap().keySet()
				.iterator());
	}

	/**
	 * 指定された要求パラメータのすべての値が格納された <code>String</code> オブジェクトの配列を返します。
	 * <p>
	 * パラメータが存在しない場合は、<code>null</code> を返します。
	 * </p>
	 * 
	 * @param name
	 *            取得したいパラメータの名前を表す <code>String</code>
	 * @return パラメータの値が格納された <code>String</code> オブジェクトの配列
	 */
	@SuppressWarnings("unchecked")
	@Override
	public String[] getParameterValues(final String name) {
		final Map<String, String[]> parameterMap = this.getParameterMap();
		return parameterMap.get(name);
	}

	/**
	 * この要求から取得できるパラメータを <code>java.util.Map</code> で返します。
	 * 
	 * @return キーとしてパラメータ名、マップ値としてパラメータ値が格納された不変の <code>java.util.Map</code>。
	 *         <p>
	 *         パラメータマップ内のキーは <code>String</code> 型。パラメータマップ内の値は
	 *         <code>String</code> の配列型
	 *         </p>
	 */
	@SuppressWarnings("unchecked")
	@Override
	public Map getParameterMap() {
		final Map<String, String[]> parameterMap = buildParameterMap(
				(HttpServletRequest) getRequest(), uriParameters);
		return parameterMap;
	}

	/**
	 * 要求パラメータを構築します。
	 * 
	 * @param request
	 *            要求
	 * @param uriParameters
	 *            URI パラメータの {@link Map}
	 * @return URI パラメータを含むリクエストパラメータの {@link Map}
	 */
	private Map<String, String[]> buildParameterMap(
			final HttpServletRequest request,
			final Map<String, String[]> uriParameters) {
		final Map<String, List<String>> extendedParameterMap = new HashMap<String, List<String>>();

		final Map<?, ?> originalParameterMap = request.getParameterMap();
		for (final Entry<?, ?> entry : originalParameterMap.entrySet()) {
			final String name = (String) entry.getKey();
			final List<String> values = new ArrayList<String>();
			for (final String value : (String[]) entry.getValue()) {
				values.add(value);
			}
			extendedParameterMap.put(name, values);
		}
		for (final Entry<String, String[]> entry : uriParameters.entrySet()) {
			final String name = entry.getKey();
			if (extendedParameterMap.containsKey(name)) {
				final List<String> values = extendedParameterMap.get(name);
				for (final String value : entry.getValue()) {
					values.add(value);
				}
			} else {
				final List<String> values = new ArrayList<String>();
				for (final String value : entry.getValue()) {
					values.add(value);
				}
				extendedParameterMap.put(name, values);
			}
		}

		final Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		for (final Entry<String, List<String>> entry : extendedParameterMap
				.entrySet()) {
			parameterMap.put(entry.getKey(), entry.getValue().toArray(
					new String[0]));
		}
		return parameterMap;
	}

}
