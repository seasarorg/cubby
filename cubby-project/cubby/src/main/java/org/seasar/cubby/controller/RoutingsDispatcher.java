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
package org.seasar.cubby.controller;

import java.util.Map;

import org.seasar.cubby.routing.Routing;

/**
 * リクエストパラメータに応じたルーティングを割り当てるためのクラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public interface RoutingsDispatcher {

	/**
	 * 指定されたルーティングの {@link Map} からリクエストパラメータに応じたルーティングを割り当てます。
	 * <p>
	 * リクエストパラメータに応じたルーティングがない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param routings
	 *            リクエストパラメータ名とルーティングの {@link Map}
	 * @param parameterMap
	 *            リクエストパラメータの {@link Map}
	 * @return リクエストパラメータに応じたルーティング
	 */
	Routing dispatch(Map<String, Routing> routings,
			Map<String, Object[]> parameterMap);

}
