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
package org.seasar.cubby.plugins.gson.spi;

import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.JsonProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

import com.google.gson.Gson;

/**
 * {@link http://code.google.com/p/google-gson/ google-gson} を用いる
 * {@link JsonProvider} の実装です。
 * 
 * @see <a href="http://sites.google.com/site/gson/gson-user-guide">Gson User Guide</a>
 * @author baba
 */
public class GsonJsonProvider implements JsonProvider {

	/**
	 * {@inheritDoc}
	 */
	public String toJson(final Object o) {
		final Gson gson = buildGson();
		return gson.toJson(o);
	}

	/**
	 * 
	 * @return
	 */
	private Gson buildGson() {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		try {
			return container.lookup(Gson.class);
		} catch (final LookupException e) {
			return new Gson();
		}
	}

}
