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
package org.seasar.cubby.routing.impl;

import java.util.Map;

import org.seasar.cubby.routing.InternalForwardInfo;
import org.seasar.cubby.routing.impl.PathResolverImpl.Routing;

/**
 * 内部フォワード情報の実装です。
 * 
 * @author baba
 */
class InternalForwardInfoImpl implements InternalForwardInfo {

	/** 内部フォワード先のパス */
	private final String internalForwardPath;

	/** 内部フォワード先のアクションクラス名 */
	private final String actionClassName;

	/** 内部フォワード先のアクションメソッド名 */
	private final String methodName;

	/**
	 * インスタンス化します。
	 * 
	 * @param routing
	 *            ルーティング
	 * @param uriParameters
	 *            URI パラメータ
	 */
	public InternalForwardInfoImpl(final String internalForwardPath,
			final Routing routing, final Map<String, String> uriParameters) {
		this.internalForwardPath = internalForwardPath;
		this.actionClassName = routing.getActionClass().getCanonicalName();
		this.methodName = routing.getMethod().getName();
	}

	/**
	 * 内部フォワード先のパスを取得します。
	 * 
	 * @return 内部フォワード先のパス
	 */
	public String getInternalForwardPath() {
		return internalForwardPath;
	}

	/**
	 * 内部フォワード先のアクションクラス名を取得します。
	 * 
	 * @return 内部フォワード先のアクションクラス名
	 */
	public String getActionClassName() {
		return actionClassName;
	}

	/**
	 * 内部フォワード先のアクションメソッド名を取得します。
	 * 
	 * @return 内部フォワード先のアクションメソッド名
	 */
	public String getMethodName() {
		return methodName;
	}

}
