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
package org.seasar.cubby.servlet;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.seasar.cubby.internal.util.ServiceLoader;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * サーブレットコンテキストの開始、シャットダウンの通知を受けて、Cubby の初期化、シャットダウン処理を行います。
 * <p>
 * Web アプリケーションの配備記述子として設定してください。
 * </p>
 * 
 * @author baba
 */
public class CubbyServletContextListener implements ServletContextListener {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(CubbyServletContextListener.class);

	/**
	 * {@inheritDoc}
	 * <p>
	 * プラグインの初期化とレジストリへの登録を行います。
	 * </p>
	 * 
	 * @see Plugins#initialize(ServletContextEvent)
	 */
	public void contextInitialized(final ServletContextEvent event) {
		final Set<Plugin> plugins = loadPlugins();
		initializePlugins(event, plugins);
		final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		registerPlugins(pluginRegistry, plugins);
	}

	protected Set<Plugin> loadPlugins() {
		final Set<Plugin> plugins = new HashSet<Plugin>();
		for (final Plugin plugin : ServiceLoader.load(Plugin.class)) {
			plugins.add(plugin);
		}
		return plugins;
	}

	protected void initializePlugins(final ServletContextEvent event,
			final Set<Plugin> plugins) {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0019", plugin));
			}
			plugin.contextInitialized(event);
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0020", plugin));
			}
		}
	}

	protected void registerPlugins(final PluginRegistry pluginRegistry,
			final Set<Plugin> plugins) {
		for (final Plugin plugin : plugins) {
			pluginRegistry.register(plugin);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * プラグインの破棄を行います。
	 * </p>
	 * 
	 * @see Plugins#destroy(ServletContextEvent)
	 */
	public void contextDestroyed(final ServletContextEvent event) {
		final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		final Set<? extends Plugin> plugins = pluginRegistry.getPlugins();
		destroyPlugins(event, plugins);
	}

	protected void destroyPlugins(final ServletContextEvent event,
			final Set<? extends Plugin> plugins) {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0021", plugin));
			}
			plugin.contextInitialized(event);
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0022", plugin));
			}
		}
	}

}
