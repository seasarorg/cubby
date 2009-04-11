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
package org.seasar.cubby.plugins.guice;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;

import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.spi.ActionHandlerChainProvider;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.cubby.spi.RequestParserProvider;

import com.google.inject.Guice;
import com.google.inject.Injector;
import com.google.inject.Module;

/**
 * Cubby を <a href="http://code.google.com/p/google-guice/">Google Guice</a>
 * に統合するためのプラグインです。
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
 * <p>
 * このプラグインが提供するプロバイダは以下の通りです。
 * <ul>
 * <li>{@link BeanDescProvider}</li>
 * <li>{@link ContainerProvider}</li>
 * <li>{@link RequestParserProvider}</li>
 * <li>{@link ActionHandlerChainProvider}</li>
 * <li>{@link PathResolverProvider}</li>
 * <li>{@link ConverterProvider}</li>
 * </ul>
 * </p>
 * 
 * @see <a href="http://code.google.com/p/google-guice/">Google&nbsp;Guice</a>
 * @see <a
 *      href="http://docs.google.com/Doc?id=dd2fhx4z_5df5hw8">User's&nbsp;Guide<
 *      / a >
 * @author baba
 */
public class GuicePlugin implements Plugin {

	/** モジュールの WEB 配備記述子の初期化パラメータ名 */
	public static final String MODULE_INIT_PARAM_NAME = "cubby.guice.module";

	/** このプラグインが提供するサービスプロバイダのセット。 */
	private static final Set<Class<? extends Provider>> SUPPORTED_SERVICES;
	static {
		final Set<Class<? extends Provider>> services = new HashSet<Class<? extends Provider>>();
		services.add(BeanDescProvider.class);
		services.add(ContainerProvider.class);
		services.add(RequestParserProvider.class);
		services.add(ActionHandlerChainProvider.class);
		services.add(PathResolverProvider.class);
		services.add(ConverterProvider.class);
		SUPPORTED_SERVICES = Collections.unmodifiableSet(services);
	}

	/** インジェクタ。 */
	private Injector injector;

	/**
	 * {@inheritDoc}
	 */
	public void initialize(final ServletConfig config) {
		final ServletContext servletContext = config.getServletContext();
		final String moduleClassName = servletContext
				.getInitParameter(MODULE_INIT_PARAM_NAME);
		final Module module = createModule(moduleClassName);
		this.injector = Guice.createInjector(module);
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
		return service.cast(injector.getInstance(service));
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Class<? extends Provider>> getSupportedServices() {
		return SUPPORTED_SERVICES;
	}

	/**
	 * インジェクタを設定します。
	 * 
	 * @param injector
	 *            インジェクタ
	 */
	public void setInjector(final Injector injector) {
		this.injector = injector;
	}

	/**
	 * 指定されたクラス名のモジュールを生成します。
	 * 
	 * @param moduleClassName
	 *            モジュールのクラス名
	 * @return インジェクタ
	 */
	protected Module createModule(final String moduleClassName) {
		final ClassLoader loader = Thread.currentThread()
				.getContextClassLoader();
		try {
			final Class<?> clazz = Class.forName(moduleClassName, true, loader);
			final Module module = Module.class.cast(clazz.newInstance());
			return module;
		} catch (final ClassNotFoundException e) {
			throw new IllegalArgumentException("Illegal module "
					+ moduleClassName, e);
		} catch (InstantiationException e) {
			throw new IllegalArgumentException("Illegal module "
					+ moduleClassName, e);
		} catch (IllegalAccessException e) {
			throw new IllegalArgumentException("Illegal module "
					+ moduleClassName, e);
		}
	}

}
