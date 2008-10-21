package org.seasar.cubby.examples;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.SQLException;

import javax.sql.DataSource;

import org.h2.tools.RunScript;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.exception.SQLRuntimeException;

public class H2ScriptRunner implements ScriptRunner {

	public void execute(String resourceName) {
		DataSource dataSource = SingletonS2Container
				.getComponent(DataSource.class);
		try {
			Connection connection = dataSource.getConnection();
			InputStream input = Thread.currentThread().getContextClassLoader()
					.getResourceAsStream(resourceName);
			Reader reader = new InputStreamReader(input, "UTF-8");
			RunScript.execute(connection, reader);
		} catch (SQLException e) {
			throw new SQLRuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			throw new IORuntimeException(e);
		}
	}

}
