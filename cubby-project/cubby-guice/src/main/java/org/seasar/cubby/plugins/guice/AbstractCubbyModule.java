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
package org.seasar.cubby.plugins.guice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParserImpl;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.converter.impl.BigDecimalConverter;
import org.seasar.cubby.converter.impl.BigIntegerConverter;
import org.seasar.cubby.converter.impl.BooleanConverter;
import org.seasar.cubby.converter.impl.ByteArrayFileItemConverter;
import org.seasar.cubby.converter.impl.ByteConverter;
import org.seasar.cubby.converter.impl.CharacterConverter;
import org.seasar.cubby.converter.impl.DateConverter;
import org.seasar.cubby.converter.impl.DoubleConverter;
import org.seasar.cubby.converter.impl.EnumConverter;
import org.seasar.cubby.converter.impl.FloatConverter;
import org.seasar.cubby.converter.impl.InputStreamFileItemConverter;
import org.seasar.cubby.converter.impl.IntegerConverter;
import org.seasar.cubby.converter.impl.LongConverter;
import org.seasar.cubby.converter.impl.ShortConverter;
import org.seasar.cubby.converter.impl.SqlDateConverter;
import org.seasar.cubby.converter.impl.SqlTimeConverter;
import org.seasar.cubby.converter.impl.SqlTimestampConverter;
import org.seasar.cubby.handler.ActionHandler;
import org.seasar.cubby.handler.impl.InitializeActionHandler;
import org.seasar.cubby.handler.impl.InvocationActionHandler;
import org.seasar.cubby.handler.impl.ParameterBindingActionHandler;
import org.seasar.cubby.handler.impl.ValidationActionHandler;
import org.seasar.cubby.plugins.guice.spi.GuiceActionHandlerChainProvider.ActionHandlerClassesFactory;
import org.seasar.cubby.plugins.guice.spi.GuiceConverterProvider.ConverterClassesFactory;
import org.seasar.cubby.routing.PathResolver;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public abstract class AbstractCubbyModule extends AbstractModule {

	public void configure() {
		configureRequestParsersProvider();
		configureConverterClassesProvider();
		// configureActionClassesProvider();
		configureActionHandlerClassesProvider();
		configurePathResolver();

		// bind(RequestParserFactory.class).to(GuiceRequestParserFactory.class)
		// .in(Singleton.class);
		// bind(ConverterFactory.class).to(GuiceConverterFactory.class).in(
		// Singleton.class);
		// bind(PathResolverFactory.class).to(GuicePathResolverFactory.class).in(
		// Singleton.class);
		// bind(ActionHandlerChainFactory.class).to(
		// GuiceActionHandlerChainFactory.class).in(Singleton.class);
	}

	protected void configureConverterClassesProvider() {
		bind(ConverterClassesFactory.class).toInstance(
				new ConverterClassesFactoryImpl());
	}

	protected void configureRequestParsersProvider() {
		bind(new TypeLiteral<Collection<RequestParser>>() {
		}).toProvider(new Provider<Collection<RequestParser>>() {

			@Inject
			public Injector injector;

			public Collection<RequestParser> get() {
				return createRequestParsers(injector);
			}

		}).in(Singleton.class);
	}

	protected Collection<RequestParser> createRequestParsers(
			final Injector injector) {
		final Set<RequestParser> requestParsers = new HashSet<RequestParser>();
		requestParsers
				.add(injector.getInstance(DefaultRequestParserImpl.class));
		// requestParsers.add(injector
		// .getInstance(MultipartRequestParserImpl.class));
		return Collections.unmodifiableCollection(requestParsers);
	}

	// protected void configureActionClassesProvider() {
	// bind(ActionClassesFactory.class).toInstance(
	// new ActionClassesFactoryImpl());
	// }

	// protected abstract Collection<Class<?>> getActionClasses();

	protected Collection<Class<? extends Converter>> getConverterClasses() {
		List<Class<? extends Converter>> converterClasses = new ArrayList<Class<? extends Converter>>();
		converterClasses.add(BigDecimalConverter.class);
		converterClasses.add(BigIntegerConverter.class);
		converterClasses.add(BooleanConverter.class);
		converterClasses.add(ByteArrayFileItemConverter.class);
		converterClasses.add(ByteConverter.class);
		converterClasses.add(CharacterConverter.class);
		converterClasses.add(DateConverter.class);
		converterClasses.add(DoubleConverter.class);
		converterClasses.add(EnumConverter.class);
		converterClasses.add(FloatConverter.class);
		converterClasses.add(InputStreamFileItemConverter.class);
		converterClasses.add(IntegerConverter.class);
		converterClasses.add(LongConverter.class);
		converterClasses.add(ShortConverter.class);
		converterClasses.add(SqlDateConverter.class);
		converterClasses.add(SqlTimeConverter.class);
		converterClasses.add(SqlTimestampConverter.class);
		return Collections.unmodifiableCollection(converterClasses);
	}

	private class ConverterClassesFactoryImpl implements
			ConverterClassesFactory {

		public Collection<Class<? extends Converter>> getConverterClasses() {
			return AbstractCubbyModule.this.getConverterClasses();
		}

	}

	// private class ActionClassesFactoryImpl implements ActionClassesFactory {
	//
	// public Collection<Class<?>> getActionClasses() {
	// return AbstractCubbyModule.this.getActionClasses();
	// }
	//
	// }

	protected void configureActionHandlerClassesProvider() {
		bind(ActionHandlerClassesFactory.class).toInstance(
				new ActionHandlersFactoryImpl());
	}

	private class ActionHandlersFactoryImpl implements
			ActionHandlerClassesFactory {

		public Collection<Class<? extends ActionHandler>> getActionHandlerClasses() {
			return AbstractCubbyModule.this.getActionHandlerClasses();
		}

	}

	protected Collection<Class<? extends ActionHandler>> getActionHandlerClasses() {
		Collection<Class<? extends ActionHandler>> actionHandlerClasses = new ArrayList<Class<? extends ActionHandler>>();
		actionHandlerClasses.add(InitializeActionHandler.class);
		actionHandlerClasses.add(ParameterBindingActionHandler.class);
		actionHandlerClasses.add(ValidationActionHandler.class);
		actionHandlerClasses.add(InvocationActionHandler.class);
		return Collections.unmodifiableCollection(actionHandlerClasses);
	}

	protected void configurePathResolver() {
		bind(PathResolver.class).toInstance(getPathResolver());
	}

	protected abstract PathResolver getPathResolver();

}
