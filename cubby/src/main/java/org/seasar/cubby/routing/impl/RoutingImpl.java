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
package org.seasar.cubby.routing.impl;

import java.lang.reflect.Method;
import java.util.List;
import java.util.regex.Pattern;

import org.seasar.cubby.action.RequestMethod;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.routing.Routing;

/**
 * ルーティングの実装。
 * 
 * @author baba
 * @since 1.1.0
 */
class RoutingImpl implements Routing {

	/** アクションクラス。 */
	private final Class<?> actionClass;

	/** アクソンメソッド。 */
	private final Method actionMethod;

	/** アクションのパス。 */
	private final String actionPath;

	/** URI パラメータ名。 */
	private final List<String> uriParameterNames;

	/** 正規表現パターン。 */
	private final Pattern pattern;

	/** リクエストメソッド。 */
	private final RequestMethod requestMethod;

	/** このルーティングを使用することを判断するためのパラメータ名。 */
	private final String onSubmit;

	/** 優先順位。 */
	private final int priority;

	/**
	 * インスタンス化します。
	 * 
	 * @param actionClass
	 *            アクションクラス
	 * @param actionMethod
	 *            アクションメソッド
	 * @param actionPath
	 *            アクションのパス
	 * @param uriParameterNames
	 *            URI パラメータ名
	 * @param pattern
	 *            正規表現パターン
	 * @param requestMethod
	 *            リクエストメソッド
	 * @param onSubmit
	 *            このルーティングを使用することを判断するためのパラメータ名
	 * @param priority
	 *            優先順位。手動登録の場合は登録順の連番。自動登録の場合は{@link Integer#MAX_VALUE}
	 *            が常にセットされます。
	 */
	RoutingImpl(final Class<?> actionClass, final Method actionMethod,
			final String actionPath, final List<String> uriParameterNames,
			final Pattern pattern, final RequestMethod requestMethod,
			final String onSubmit, final int priority) {
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
		this.actionPath = actionPath;
		this.uriParameterNames = uriParameterNames;
		this.pattern = pattern;
		this.requestMethod = requestMethod;
		this.onSubmit = onSubmit;
		this.priority = priority;
	}

	/**
	 * {@inheritDoc}
	 */
	public Class<?> getActionClass() {
		return actionClass;
	}

	/**
	 * {@inheritDoc}
	 */
	public Method getActionMethod() {
		return actionMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getActionPath() {
		return actionPath;
	}

	/**
	 * {@inheritDoc}
	 */
	public List<String> getUriParameterNames() {
		return uriParameterNames;
	}

	/**
	 * {@inheritDoc}
	 */
	public Pattern getPattern() {
		return pattern;
	}

	/**
	 * {@inheritDoc}
	 */
	public RequestMethod getRequestMethod() {
		return requestMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getOnSubmit() {
		return onSubmit;
	}

	/**
	 * {@inheritDoc}
	 */
	public int getPriority() {
		return this.priority;
	}

	/**
	 * {@inheritDoc}
	 */
	public boolean isAcceptable(final String requestMethod) {
		return StringUtils.equalsIgnoreCase(this.requestMethod.name(),
				requestMethod);
	}

	/**
	 * このオブジェクトの文字列表現を返します。
	 * 
	 * @return このオブジェクトの正規表現
	 */
	@Override
	public String toString() {
		return new StringBuilder().append("[regex=").append(this.pattern)
				.append(",actionMethod=").append(this.actionMethod).append(
						",uriParameterNames=").append(this.uriParameterNames)
				.append(",requestMethod=").append(this.requestMethod).append(
						",onSubmit=").append(onSubmit).append(",priority=")
				.append(this.priority).append(",auto=").append("]").toString();
	}
}
