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
package org.seasar.cubby.examples;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.SQLException;

import javax.servlet.ServletRequestEvent;
import javax.servlet.ServletRequestListener;
import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.seasar.framework.container.SingletonS2Container;

/**
 * このサンプル用のDDLを実行してデータベースを初期化します。
 */
public class RunDdlServletRequestListener implements ServletRequestListener {

	private boolean initialized = false;

	public void requestInitialized(ServletRequestEvent event) {
		if (!initialized) {
			initialize();
		}
	}

	private synchronized void initialize() {
		if (!initialized) {
			try {
				DataSource dataSource = SingletonS2Container
						.getComponent(DataSource.class);
				Connection connection = dataSource.getConnection();
				InputStream input = Thread.currentThread()
						.getContextClassLoader().getResourceAsStream("ddl.sql");
				Reader reader = new InputStreamReader(input);
				RunScript.execute(connection, reader);
			} catch (SQLException e) {
				throw new RuntimeException(e);
			}
			initialized = true;
		}
	}

	public void requestDestroyed(ServletRequestEvent event) {
	}

}
