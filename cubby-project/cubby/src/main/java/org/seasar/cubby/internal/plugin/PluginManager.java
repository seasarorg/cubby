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

package org.seasar.cubby.internal.plugin;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.ServletContext;

import org.seasar.cubby.internal.util.ServiceLoader;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * プラグインマネージャ。
 * 
 * @author baba
 */
public class PluginManager {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(PluginManager.class);

	/** プラグインレジストリ。 */
	private final PluginRegistry pluginRegistry;

	/**
	 * インスタンス化します。
	 * 
	 * @param pluginRegistry
	 *            プラグインレジストリ
	 */
	public PluginManager(final PluginRegistry pluginRegistry) {
		this.pluginRegistry = pluginRegistry;
	}

	/**
	 * プラグインの初期化とレジストリへの登録を行います。
	 * 
	 * @param servletContext
	 *            サーブレットコンテキスト
	 * @throws Exception
	 *             プラグインの初期化や準備に失敗した場合
	 */
	public void init(final ServletContext servletContext) throws Exception {
		final Collection<Plugin> plugins = loadPlugins();
		initializePlugins(servletContext, plugins);
		registerPlugins(pluginRegistry, plugins);
		readyPlugins(plugins);
	}

	/**
	 * プラグインの破棄を行います。
	 */
	public void destroy() {
		final Set<? extends Plugin> plugins = pluginRegistry.getPlugins();
		destroyPlugins(plugins);
		pluginRegistry.clear();
	}

	/**
	 * プラグインをロードします。
	 * 
	 * @return ロードしたプラグインのコレクション
	 */
	protected Collection<Plugin> loadPlugins() {
		final Set<Plugin> plugins = new LinkedHashSet<Plugin>();
		for (final Plugin plugin : ServiceLoader.load(Plugin.class)) {
			plugins.add(plugin);
		}
		return plugins;
	}

	/**
	 * プラグインを初期化します。
	 * 
	 * @param servletContext
	 *            呼び出し元が現在実行している {@link ServletContext} への参照
	 * @param plugins
	 *            プラグインのコレクション
	 * @throws Exception
	 *             プラグインの初期化に失敗した場合
	 */
	protected void initializePlugins(final ServletContext servletContext,
			final Collection<Plugin> plugins) throws Exception {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0019", plugin));
			}
			plugin.initialize(servletContext);
			if (logger.isInfoEnabled()) {
				logger.info(format("ICUB0002", plugin));
			}
		}
	}

	/**
	 * プラグインをレジストリに登録します。
	 * 
	 * @param pluginRegistry
	 *            プラグインのレジストリ
	 * @param plugins
	 *            登録するプラグインのコレクション
	 */
	protected void registerPlugins(final PluginRegistry pluginRegistry,
			final Collection<Plugin> plugins) {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0020", plugin));
			}
			pluginRegistry.register(plugin);
			if (logger.isInfoEnabled()) {
				logger.info(format("ICUB0003", plugin));
			}
		}
	}

	/**
	 * プラグインを準備します。
	 * 
	 * @param plugins
	 *            プラグインのコレクション
	 * @throws Exception
	 *             プラグインの準備に失敗した場合
	 */
	private void readyPlugins(final Collection<Plugin> plugins)
			throws Exception {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0021", plugin));
			}
			plugin.ready();
			if (logger.isInfoEnabled()) {
				logger.info(format("ICUB0004", plugin));
			}
		}
	}

	/**
	 * プラグインを破棄します。
	 * 
	 * @param plugins
	 *            プラグインのコレクション
	 */
	protected void destroyPlugins(final Set<? extends Plugin> plugins) {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0022", plugin));
			}
			plugin.destroy();
			if (logger.isInfoEnabled()) {
				logger.info(format("ICUB0005", plugin));
			}
		}
	}

}
