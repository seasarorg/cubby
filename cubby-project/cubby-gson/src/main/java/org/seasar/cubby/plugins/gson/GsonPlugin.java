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
package org.seasar.cubby.plugins.gson;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;

import org.seasar.cubby.plugin.Plugin;
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
public class GsonPlugin implements Plugin {

	/** このプラグインが提供するサービスプロバイダのセット。 */
	private static final Set<Class<? extends Provider>> SUPPORTED_SERVICES;
	static {
		final Set<Class<? extends Provider>> services = new HashSet<Class<? extends Provider>>();
		services.add(JsonProvider.class);
		SUPPORTED_SERVICES = Collections.unmodifiableSet(services);
	}

	/** JSON のプロバイダ。 */
	private final JsonProvider jsonProvider = new GsonJsonProvider();

	/**
	 * {@inheritDoc}
	 */
	public void initialize(final ServletConfig config) {
	}

	/**
	 * {@inheritDoc}
	 */
	public void ready() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	/**
	 * {@inheritDoc}
	 */
	public <S extends Provider> S getProvider(final Class<S> service) {
		if (JsonProvider.class.equals(service)) {
			return service.cast(jsonProvider);
		}
		throw new IllegalArgumentException("Unsupported service " + service);
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Class<? extends Provider>> getSupportedServices() {
		return SUPPORTED_SERVICES;
	}

}
