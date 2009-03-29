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
package org.seasar.cubby.plugins.s2;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import javax.el.ELResolver;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.jsp.JspApplicationContext;
import javax.servlet.jsp.JspFactory;

import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugins.s2.el.S2BeanELResolver;
import org.seasar.cubby.spi.ActionHandlerChainProvider;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.Provider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

/**
 * Cubby を <a href="http://s2container.seasar.org/2.4/ja/">S2Container</a>
 * に統合するためのプラグインです。
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
 * @see <a href="http://s2container.seasar.org/2.4/ja/">S2Container</a>
 * @author baba
 */
public class S2ContainerPlugin implements Plugin {

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

	/**
	 * {@inheritDoc}
	 * <p>
	 * {@link S2BeanELResolver} を {@link JspApplicationContext} に登録します。
	 * </p>
	 */
	public void contextInitialized(final ServletContextEvent event) {
		final ServletContext servletContext = event.getServletContext();
		final JspApplicationContext jspApplicationContext = JspFactory
				.getDefaultFactory().getJspApplicationContext(servletContext);
		final ELResolver elResolver = new S2BeanELResolver();
		jspApplicationContext.addELResolver(elResolver);
		servletContext.log("Registered " + elResolver);
	}

	/**
	 * {@inheritDoc}
	 */
	public void contextDestroyed(final ServletContextEvent event) {
	}

	/**
	 * {@inheritDoc}
	 */
	public <S extends Provider> S getProvider(final Class<S> service) {
		final S2Container container = SingletonS2ContainerFactory
				.getContainer();
		return service.cast(container.getComponent(service));
	}

	/**
	 * {@inheritDoc}
	 */
	public Set<Class<? extends Provider>> getSupportedServices() {
		return SUPPORTED_SERVICES;
	}

}
