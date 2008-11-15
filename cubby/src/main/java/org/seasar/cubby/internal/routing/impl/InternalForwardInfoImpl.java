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
package org.seasar.cubby.internal.routing.impl;

import java.util.Map;

import org.seasar.cubby.internal.routing.InternalForwardInfo;
import org.seasar.cubby.internal.routing.Routing;

/**
 * 内部フォワード情報の実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
class InternalForwardInfoImpl implements InternalForwardInfo {

	/** 内部フォワード先のパス。 */
	private final String internalForwardPath;

	/** リクエストパラメータ名と対応するルーティングのマッピング。 */
	private final Map<String, Routing> onSubmitRoutings;

	/**
	 * インスタンス化します。
	 * 
	 * @param internalForwardPath
	 *            内部フォワードパス
	 * @param routing
	 *            ルーティング
	 * @param uriParameters
	 *            URI パラメータ
	 */
	public InternalForwardInfoImpl(final String internalForwardPath,
			final Map<String, Routing> onSubmitRoutings) {
		this.internalForwardPath = internalForwardPath;
		this.onSubmitRoutings = onSubmitRoutings;
	}

	/**
	 * {@inheritDoc}
	 */
	public String getInternalForwardPath() {
		return internalForwardPath;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Routing> getOnSubmitRoutings() {
		return onSubmitRoutings;
	}

}
