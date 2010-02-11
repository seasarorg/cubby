/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

package org.seasar.cubby.plugins.gson;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.plugins.gson.spi.GsonJsonProvider;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.spi.Provider;

/**
 * <a href="http://code.google.com/p/google-gson/">Google Gson</a> による JSON
 * 処理を行うためのプラグインです。
 * <p>
 * このプラグインが提供するプロバイダは以下の通りです。
 * <ul>
 * <li>{@link JsonProvider}</li>
 * </ul>
 * </p>
 * 
 * @see <a href="http://code.google.com/p/google-gson/">Google&nbsp;Gson</a>
 * @see <a
 *      href="http://sites.google.com/site/gson/gson-user-guide">Gson&nbsp;User&nbsp;Guide</a>
 * @author baba
 */
public class GsonPlugin extends AbstractPlugin {

	/** JSON のプロバイダ。 */
	private final JsonProvider jsonProvider = new GsonJsonProvider();

	/**
	 * インスタンス化します。
	 */
	public GsonPlugin() {
		support(JsonProvider.class);
	}

	/**
	 * {@inheritDoc}
	 */
	public <S extends Provider> S getProvider(final Class<S> service) {
		if (JsonProvider.class.equals(service)) {
			return service.cast(jsonProvider);
		}
		return null;
	}

}
