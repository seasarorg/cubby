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

package org.seasar.cubby.plugins.s2;

import javax.el.ELResolver;
import javax.servlet.ServletContext;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import org.seasar.cubby.plugin.AbstractPlugin;
import org.seasar.cubby.plugins.s2.el.S2BeanELResolver;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;

/**
 * Cubby を <a href="http://s2container.seasar.org/2.4/ja/">S2Container</a>
 * に統合するためのプラグインです。
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
 * @see <a href="http://s2container.seasar.org/2.4/ja/">S2Container</a>
 * @author baba
 */
public class S2ContainerPlugin extends AbstractPlugin {

	/** ロガー。 */
	private static final Logger logger = Logger
			.getLogger(S2ContainerPlugin.class);

	static {
		// HACK
		// In "mvn tomcat:run", JspFactory.getDefaultFactory() returns null.
		// because static initializer of "JspRuntimeContext" has not been
		// executed yet.
		// Like "JasperListener" in "server.xml"
		try {
			Class.forName("org.apache.jasper.compiler.JspRuntimeContext");
		} catch (final Exception e) {
			if (logger.isDebugEnabled()) {
				logger.debug(e.getMessage());
			}
		}
	}

	/**
	 * インスタンス化します。
	 */
	public S2ContainerPlugin() {
		support(BeanDescProvider.class);
		support(ContainerProvider.class);
		support(RequestParserProvider.class);
		support(PathResolverProvider.class);
		support(ConverterProvider.class);
	}

	/** サーブレットコンテキスト。 */
	private ServletContext servletContext;

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void initialize(final ServletContext servletContext)
			throws Exception {
		super.initialize(servletContext);
		this.servletContext = servletContext;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link S2BeanELResolver} を {@link JspApplicationContext} に登録します。
	 * </p>
	 */
	@Override
	public void ready() {
		final JspFactory jspFactory = JspFactory.getDefaultFactory();
		if (jspFactory != null) {
			final JspApplicationContext jspApplicationContext = jspFactory
					.getJspApplicationContext(servletContext);
			final S2Container container = SingletonS2ContainerFactory
					.getContainer();
			final ELResolver[] elResolvers = (ELResolver[]) container
					.findAllComponents(ELResolver.class);
			for (final ELResolver elResolver : elResolvers) {
				jspApplicationContext.addELResolver(elResolver);
				logger.log("ICUB0001", new Object[] { elResolver });
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public <S extends Provider> S getProvider(final Class<S> service) {
		if (this.isSupport(service)) {
			final S2Container container = SingletonS2ContainerFactory
					.getContainer();
			return service.cast(container.getComponent(service));
		} else {
			return null;
		}
	}

}
