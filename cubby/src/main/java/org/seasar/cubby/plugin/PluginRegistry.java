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

package org.seasar.cubby.plugin;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.seasar.cubby.spi.Provider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * プラグインのレジストリです。
 * 
 * @author baba
 */
public class PluginRegistry {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(PluginRegistry.class);

	/** シングルトンインスタンス。 */
	private static final PluginRegistry INSTANCE = new PluginRegistry();

	/** プラグインのセット。 */
	private final Set<Plugin> plugins = new HashSet<Plugin>();

	/** サービスとそのサービスを提供するプラグインとのマッピング。 */
	private final Map<Class<? extends Provider>, Plugin> serviceToPlugins = new HashMap<Class<? extends Provider>, Plugin>();

	/**
	 * インスタンス化を禁止するためのコンストラクタ。
	 */
	private PluginRegistry() {
	}

	/**
	 * {@link PluginRegistry} のシングルトンを取得します。
	 * 
	 * @return {@link PluginRegistry} のシングルトン
	 */
	public static PluginRegistry getInstance() {
		return INSTANCE;
	}

	/**
	 * 登録されたプラグインをクリアします。
	 */
	public synchronized void clear() {
		this.plugins.clear();
		this.serviceToPlugins.clear();
	}

	/**
	 * 指定されたプラグインを登録します。
	 * 
	 * @param plugin
	 *            プラグイン
	 */
	public synchronized void register(final Plugin plugin) {
		this.plugins.add(plugin);
		for (final Class<? extends Provider> service : plugin
				.getSupportedServices()) {
			this.serviceToPlugins.put(service, plugin);
			if (logger.isInfoEnabled()) {
				logger.info(format("ICUB0001", plugin, service));
			}
		}
	}

	/**
	 * 指定されたサービスのプロバイダを取得します。
	 * 
	 * @param <S>
	 *            サービスの型
	 * @param service
	 *            サービス
	 * @return プロバイダ
	 * @throws IllegalArgumentException
	 *             <code>service</code> を提供するプラグインが登録されていない場合
	 * @throws IllegalStateException
	 *             {@link Plugin#getSupportedServices()} が <code>service</code>
	 *             を返すプラグインから取得したサービスプロバイダが <code>null</code> の場合
	 */
	public <S extends Provider> S getProvider(final Class<S> service) {
		final Plugin plugin = this.serviceToPlugins.get(service);
		if (plugin == null) {
			throw new IllegalArgumentException(format("ECUB0054", service));
		}
		final S provider = service.cast(plugin.getProvider(service));
		if (provider == null) {
			throw new IllegalStateException(format("ECUB0053", plugin, service));
		}
		return provider;
	}

	/**
	 * 登録されているプラグインのセットを取得します。
	 * 
	 * @return 登録されているプラグインのセット
	 */
	public Set<Plugin> getPlugins() {
		return plugins;
	}

	/**
	 * 登録されたプラグインから指定された型のプラグインを取得します。
	 * <p>
	 * 該当するプラグインが登録されていない場合は <code>null</code> を返します。
	 * </p>
	 * 
	 * @param <T>
	 *            プラグインの型
	 * @param pluginType
	 *            プラグインの型
	 * @return 指定された型のプラグイン
	 */
	public <T extends Plugin> T getPlugin(final Class<T> pluginType) {
		for (final Plugin plugin : plugins) {
			if (plugin.getClass().equals(pluginType)) {
				return pluginType.cast(plugin);
			}
		}
		return null;
	}

}
