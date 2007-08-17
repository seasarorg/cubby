package org.seasar.cubby.examples.smartdeploy;

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
			try {
				DataSource dataSource = SingletonS2Container
						.getComponent("dataSource");
				Connection connection = dataSource.getConnection();
				InputStream input = Thread.currentThread().getContextClassLoader()
						.getResourceAsStream("ddl.sql");
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
