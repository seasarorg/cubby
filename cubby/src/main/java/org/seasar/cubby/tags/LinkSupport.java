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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspTagException;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.framework.container.SingletonS2Container;

/**
 * リンク用の補助クラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
class LinkSupport {

	/** アクションクラス名。 */
	private String actionClassName;

	/** アクションメソッド名。 */
	private String actionMethodName;

	/** リクエストパラメータの {@link Map} */
	private final Map<String, List<String>> parameters = new HashMap<String, List<String>>();

	/**
	 * アクションクラスを設定します。
	 * 
	 * @param actionClassName
	 *            アクションクラス名
	 */
	public void setActionClassName(final String actionClassName) {
		this.actionClassName = actionClassName;
	}

	/**
	 * アクションメソッドを設定します。
	 * 
	 * @param actionMethodName
	 *            アクションメソッド名
	 */
	public void setActionMethodName(final String actionMethodName) {
		this.actionMethodName = actionMethodName;
	}

	/**
	 * リクエストパラメータを追加します。
	 * 
	 * @param name
	 *            パラメータ名
	 * @param value
	 *            値
	 */
	public void addParameter(final String name, final String value) {
		if (!parameters.containsKey(name)) {
			parameters.put(name, new ArrayList<String>());
		}
		final List<String> values = parameters.get(name);
		values.add(value);
	}

	/**
	 * リクエストパラメータの {@link Map} を取得します。
	 * 
	 * @return リクエストパラメータの {@link Map}
	 */
	public Map<String, String[]> getParameters() {
		final Map<String, String[]> parameters = new HashMap<String, String[]>();
		for (final Entry<String, List<String>> entry : this.parameters
				.entrySet()) {
			parameters.put(entry.getKey(), entry.getValue().toArray(
					new String[0]));
		}
		return parameters;
	}

	/**
	 * パスを取得します。
	 * <p>
	 * このパスにはコンテキストパスは含まれません。
	 * </p>
	 * 
	 * @param parameterSupport
	 * @return
	 * @throws JspTagException
	 */
	public String getPath() throws JspTagException {
		final Class<? extends Action> actionClass;
		try {
			actionClass = forName(actionClassName);
		} catch (final ClassNotFoundException e) {
			throw new JspTagException(e);
		}

		final PathResolver pathResolver = SingletonS2Container
				.getComponent(PathResolver.class);
		final String path = pathResolver.reverseLookup(actionClass,
				actionMethodName, getParameters());

		return path;
	}

	/**
	 * リンク可能かを示します。
	 * 
	 * @return リンク可能な場合は <code>true</code>、そうでない場合は <code>false</code>
	 */
	public boolean isLinkable() {
		return actionClassName != null && actionMethodName != null;
	}

	/**
	 * 特定の型のクラスオブジェクトを返します。
	 * 
	 * @param <T>
	 *            型
	 * @param className
	 *            クラス名
	 * @return クラスオブジェクト
	 * @throws ClassNotFoundException
	 *             指定されたクラスが見つからない場合
	 */
	@SuppressWarnings("unchecked")
	private static <T> Class<T> forName(final String className)
			throws ClassNotFoundException {
		return (Class<T>) Class.forName(className);
	}

}
