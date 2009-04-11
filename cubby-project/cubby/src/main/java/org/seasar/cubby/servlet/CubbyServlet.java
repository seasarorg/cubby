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

import java.io.IOException;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.cubby.internal.util.ServiceLoader;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * プラグインの初期化などを行う <code>Servlet</code> です。
 * 
 * @author baba
 */
public class CubbyServlet extends GenericServlet {

	/** シリアルバージョン UID。 */
	private static final long serialVersionUID = 1L;

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(CubbyServlet.class);

	/**
	 * {@inheritDoc}
	 * <p>
	 * プラグインの初期化とレジストリへの登録を行います。
	 * </p>
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);

		final Collection<Plugin> plugins = loadPlugins();
		initializePlugins(config, plugins);
		final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		registerPlugins(pluginRegistry, plugins);
		readyPlugins(plugins);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * プラグインの破棄を行います。
	 * </p>
	 * 
	 * @see Plugins#destroy(ServletContextEvent)
	 */
	@Override
	public void destroy() {
		super.destroy();
		final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
		final Set<? extends Plugin> plugins = pluginRegistry.getPlugins();
		destroyPlugins(plugins);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void service(ServletRequest req, ServletResponse res)
			throws ServletException, IOException {
		// do nothing
	}

	/**
	 * プラグインをロードします。
	 * 
	 * @return ロードしたプラグインのコレクション
	 */
	protected Collection<Plugin> loadPlugins() {
		final Set<Plugin> plugins = new HashSet<Plugin>();
		for (final Plugin plugin : ServiceLoader.load(Plugin.class)) {
			plugins.add(plugin);
		}
		return plugins;
	}

	/**
	 * プラグインを初期化します。
	 * 
	 * @param config
	 *            <code>CubbyServlet</code> の設定や初期化パラメータが含まれている
	 *            <code>ServletConfig</code> オブジェクト
	 * @param plugins
	 *            プラグインのコレクション
	 */
	protected void initializePlugins(final ServletConfig config,
			final Collection<Plugin> plugins) {
		for (final Plugin plugin : plugins) {
			if (logger.isDebugEnabled()) {
				logger.debug(format("DCUB0019", plugin));
			}
			plugin.initialize(config);
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
	 */
	private void readyPlugins(final Collection<Plugin> plugins) {
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
