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

import javax.servlet.ServletContext;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.cubby.spi.RequestParserProvider;

import com.google.inject.Injector;
import com.google.inject.servlet.GuiceServletContextListener;

/**
 * Cubby を <a href="http://code.google.com/p/google-guice/">Google Guice</a>
 * に統合するためのプラグインです。
 * <p>
 * {@link GuiceServletContextListener} のサブクラスを web.xml に登録して {@link Injector}
 * を初期化して下さい。 Cubby では {@link CubbyGuiceServletContextListener}
 * を提供しているのでこれを使用することができます。
 * </p>
 * <p>
 * このプラグインが提供するプロバイダは以下の通りです。
 * <ul>
 * <li>{@link BeanDescProvider}</li>
 * <li>{@link ContainerProvider}</li>
 * <li>{@link RequestParserProvider}</li>
 * <li>{@link PathResolverProvider}</li>
 * <li>{@link ConverterProvider}</li>
 * </ul>
 * </p>
 * 
 * @see <a href="http://code.google.com/p/google-guice/">Google&nbsp;Guice</a>
 * @author baba
 */
public class GuicePlugin extends AbstractPlugin {

	/** モジュールの WEB 配備記述子の初期化パラメータ名 */
	public static final String MODULE_INIT_PARAM_NAME = "cubby.guice.module";

	/** インジェクタ */
	private Injector injector;

	/**
	 * インスタンス化します。
	 */
	public GuicePlugin() {
		support(BeanDescProvider.class);
		support(ContainerProvider.class);
		support(RequestParserProvider.class);
		support(PathResolverProvider.class);
		support(ConverterProvider.class);
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(final ServletContext servletContext)
			throws Exception {
		super.initialize(servletContext);

		this.injector = (Injector) servletContext.getAttribute(Injector.class
				.getName());
		if (this.injector == null) {
			throw new IllegalStateException(Injector.class.getName()
					+ "is not confugured.");
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void destroy() {
		this.injector = null;
		super.destroy();
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <S extends Provider> S getProvider(final Class<S> service) {
		if (this.isSupport(service)) {
			return service.cast(this.injector.getInstance(service));
		} else {
			return null;
		}
	}

	/**
	 * インジェクタを取得します。
	 * 
	 * @return インジェクタ
	 */
	public Injector getInjector() {
		return injector;
	}

}
