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
package org.seasar.cubby.controller.impl;

import java.util.Map;

import org.seasar.cubby.controller.RoutingsDispatcher;
import org.seasar.cubby.routing.Routing;

/**
 * リクエストパラメータに応じたルーティングを割り当てるためのクラスです。
 * 
 * @author baba
 * @since 1.1.0
 */
public class RoutingsDispatcherImpl implements RoutingsDispatcher {

	/**
	 * {@inheritDoc}
	 */
	public Routing dispatch(final Map<String, Routing> routings,
			final Map<String, Object[]> parameterMap) {
		for (final String onSubmit : routings.keySet()) {
			if (parameterMap.containsKey(onSubmit)) {
				return routings.get(onSubmit);
			}
		}
		return routings.get(null);
	}

}
