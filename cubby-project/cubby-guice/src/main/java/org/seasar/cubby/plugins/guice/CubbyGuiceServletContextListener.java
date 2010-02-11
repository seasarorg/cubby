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

package org.seasar.cubby.plugins.guice;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * {@link Injector} を初期化するための {@code Servlet} コンテキストのリスナです。
 * <p>
 * アプリケーションが使用するモジュールのクラス名を WEB 配備記述子の初期化パラメータ {@value #MODULE_INIT_PARAM_NAME}
 * に指定してください。
 * </p>
 * <p>
 * 例
 * 
 * <pre>
 * &lt;context-param&gt;
 *   &lt;param-name&gt;{@value #MODULE_INIT_PARAM_NAME}&lt;/paanm-name&gt;
 *   &lt;param-value&gt;com.example.ApplicationModule&lt;/param-value&gt;
 * &lt;/context-param&gt;
 * </pre>
 * 
 * </p>
 * 
 * @author baba
 */
public class CubbyGuiceServletContextListener extends
		GuiceServletContextListener {

	private static final Logger logger = LoggerFactory
			.getLogger(CubbyGuiceServletContextListener.class.getName());

	/** モジュールの WEB 配備記述子の初期化パラメータ名 */
	public static final String MODULE_INIT_PARAM_NAME = "cubby.guice.module";

	/** インスタンス化するモジュールのクラス名 */
	private String[] moduleClassNames;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void contextInitialized(ServletContextEvent servletContextEvent) {
		final ServletContext servletContext = servletContextEvent
				.getServletContext();
		final String moduleClassNamesString = servletContext
				.getInitParameter(MODULE_INIT_PARAM_NAME);
		if (moduleClassNamesString == null
				|| moduleClassNamesString.length() == 0) {
			throw new IllegalArgumentException("No context parameter \""
					+ MODULE_INIT_PARAM_NAME + "\", please set Module FQCN");
		}
		this.moduleClassNames = moduleClassNamesString.split(",");

		super.contextInitialized(servletContextEvent);
	}

	/**
	 * 指定されたクラス名のモジュールを生成します。
	 * 
	 * @param moduleClassName
	 *            モジュールのクラス名
	 * @return インジェクタ
	 */
	protected Module createModule(final String moduleClassName) {
		if (logger.isInfoEnabled()) {
			logger.info("Instantiates " + moduleClassName);
		}

		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		try {
			final Class<?> clazz = Class.forName(moduleClassName, true, loader);
			final Module module = Module.class.cast(clazz.newInstance());
			return module;
		} catch (final ClassNotFoundException e) {
			throw new IllegalArgumentException("Illegal module "
					+ moduleClassName, e);
		} catch (final InstantiationException e) {
			throw new IllegalArgumentException("Illegal module "
					+ moduleClassName, e);
		} catch (final IllegalAccessException e) {
			throw new IllegalArgumentException("Illegal module "
					+ moduleClassName, e);
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	protected Injector getInjector() {
		final List<Module> modules = new ArrayList<Module>();
		for (final String moduleClassName : this.moduleClassNames) {
			final Module module = createModule(moduleClassName.trim());
			modules.add(module);
		}
		return Guice.createInjector(modules);
	}

}
