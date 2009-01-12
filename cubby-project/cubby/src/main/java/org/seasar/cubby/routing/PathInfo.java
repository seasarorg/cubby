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
package org.seasar.cubby.routing;

import java.util.Map;

/**
 * パスから取得した情報です。
 * 
 * @author baba
 * @since 2.0.0
 */
public interface PathInfo {

	/**
	 * URI から抽出したパラメータを取得します。
	 * 
	 * @return URI から抽出したパラメータ
	 */
	Map<String, String[]> getURIParameters();

	/**
	 * 指定された要求パラメータの {@link Map} に対応するルーティングを取得します。
	 * 
	 * @param parameterMap
	 *            要求パラメータの {@link Map}
	 * @return ルーティング
	 */
	Routing dispatch(Map<String, Object[]> parameterMap);

}
