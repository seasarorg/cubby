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

import org.seasar.cubby.internal.routing.PathInfo;
import org.seasar.cubby.internal.routing.Routing;

/**
 * パスから取得した情報の実装です。
 * 
 * @author baba
 * @since 1.0.0
 */
class PathInfoImpl implements PathInfo {

	/** URI から抽出したパラメータを取得します。 */
	private final Map<String, String[]> uriParameters;

	/** リクエストパラメータ名と対応するルーティングのマッピング。 */
	private final Map<String, Routing> onSubmitRoutings;

	/**
	 * インスタンス化します。
	 * 
	 * @param uriParameters
	 *            URI から抽出したパラメータ
	 * @param onSubmitRoutings
	 *            リクエストパラメータ名とルーティングのマッピングを取得します。
	 */
	public PathInfoImpl(final Map<String, String[]> uriParameters,
			final Map<String, Routing> onSubmitRoutings) {
		this.uriParameters = uriParameters;
		this.onSubmitRoutings = onSubmitRoutings;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, String[]> getURIParameters() {
		return uriParameters;
	}

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Routing> getOnSubmitRoutings() {
		return onSubmitRoutings;
	}

}
