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

package org.seasar.cubby.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.cubby.internal.plugin.PluginManager;
import org.seasar.cubby.plugin.PluginRegistry;

/**
 * プラグインの初期化などを行う <code>Servlet</code> です。
 * 
 * @author baba
 */
public class CubbyServlet extends GenericServlet {

	/** シリアルバージョン UID。 */
	private static final long serialVersionUID = 1L;

	/** プラグインマネージャ。 */
	private transient PluginManager pluginManager;

	/**
	 * サーブレットをインスタンス化します。
	 */
	public CubbyServlet() {
		super();
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * プラグインの初期化とレジストリへの登録を行います。
	 * </p>
	 */
	@Override
	public void init(final ServletConfig config) throws ServletException {
		super.init(config);
		pluginManager = buildPluginManager();
		try {
			pluginManager.init(config.getServletContext());
		} catch (final Exception e) {
			throw new ServletException(e);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * プラグインの破棄を行います。
	 * </p>
	 * 
	 * @see PluginManager#destroy()
	 */
	@Override
	public void destroy() {
		super.destroy();
		pluginManager.destroy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void service(final ServletRequest req, final ServletResponse res)
			throws ServletException, IOException {
		// do nothing
	}

	/**
	 * プラグインマネージャを構築します。
	 * 
	 * @return プラグインマネージャ
	 */
	protected PluginManager buildPluginManager() {
		return new PluginManager(PluginRegistry.getInstance());
	}

}
