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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultFormatPattern;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
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
import org.seasar.cubby.plugins.guice.spi.GuiceActionHandlerChainProvider;
import org.seasar.cubby.plugins.guice.spi.GuiceContainerProvider;
import org.seasar.cubby.plugins.guice.spi.GuiceConverterProvider;
import org.seasar.cubby.plugins.guice.spi.GuicePathResolverProvider;
import org.seasar.cubby.plugins.guice.spi.GuiceRequestParserProvider;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.spi.ActionHandlerChainProvider;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public abstract class AbstractCubbyModule extends AbstractModule {

	public void configure() {
		// ContainerProvider
		bind(ContainerProvider.class).to(GuiceContainerProvider.class).in(
				Singleton.class);

		// BeanDescProvider
		bind(BeanDescProvider.class).to(DefaultBeanDescProvider.class).in(
				Singleton.class);

		// RequestParserProvider
		bind(new TypeLiteral<Collection<RequestParser>>() {
		}).toProvider(new Provider<Collection<RequestParser>>() {

			@Inject
			public Injector injector;

			public Collection<RequestParser> get() {
				return createRequestParsers(injector);
			}

		}).in(Singleton.class);
		bind(RequestParserProvider.class).to(GuiceRequestParserProvider.class)
				.in(Singleton.class);

		// ConverterProvider
		bind(new TypeLiteral<Collection<Converter>>() {
		}).toProvider(new Provider<Collection<Converter>>() {

			@Inject
			public Injector injector;

			public Collection<Converter> get() {
				return createConverters(injector);
			}

		}).in(Singleton.class);
		bind(ConverterProvider.class).to(GuiceConverterProvider.class).in(
				Singleton.class);

		// ActionHandlerChainProvider
		bind(new TypeLiteral<Collection<ActionHandler>>() {
		}).toProvider(new Provider<Collection<ActionHandler>>() {

			@Inject
			public Injector injector;

			public Collection<ActionHandler> get() {
				return createActionHandlers(injector);
			}

		}).in(Singleton.class);
		bind(ActionHandlerChainProvider.class).to(
				GuiceActionHandlerChainProvider.class).in(Singleton.class);

		// PathResolverProvider
		bind(PathResolver.class).toInstance(getPathResolver());
		bind(PathResolverProvider.class).to(GuicePathResolverProvider.class)
				.in(Singleton.class);

		configureMessagesBehaviour();
		configureFormatPattern();
	}

	protected Collection<RequestParser> createRequestParsers(
			final Injector injector) {
		final Set<RequestParser> requestParsers = new HashSet<RequestParser>();
		requestParsers.add(injector.getInstance(DefaultRequestParser.class));
		// requestParsers.add(injector.getInstance(MultipartRequestParser.class));
		return Collections.unmodifiableCollection(requestParsers);
	}

	protected Collection<Converter> createConverters(final Injector injector) {
		final Set<Converter> converters = new HashSet<Converter>();
		converters.add(injector.getInstance(BigDecimalConverter.class));
		converters.add(injector.getInstance(BigIntegerConverter.class));
		converters.add(injector.getInstance(BooleanConverter.class));
		converters.add(injector.getInstance(ByteArrayFileItemConverter.class));
		converters.add(injector.getInstance(ByteConverter.class));
		converters.add(injector.getInstance(CharacterConverter.class));
		converters.add(injector.getInstance(DateConverter.class));
		converters.add(injector.getInstance(DoubleConverter.class));
		converters.add(injector.getInstance(EnumConverter.class));
		converters.add(injector.getInstance(FloatConverter.class));
		converters
				.add(injector.getInstance(InputStreamFileItemConverter.class));
		converters.add(injector.getInstance(IntegerConverter.class));
		converters.add(injector.getInstance(LongConverter.class));
		converters.add(injector.getInstance(ShortConverter.class));
		converters.add(injector.getInstance(SqlDateConverter.class));
		converters.add(injector.getInstance(SqlTimeConverter.class));
		converters.add(injector.getInstance(SqlTimestampConverter.class));
		return Collections.unmodifiableCollection(converters);
	}

	protected Collection<ActionHandler> createActionHandlers(Injector injector) {
		final List<ActionHandler> actionHandlers = new ArrayList<ActionHandler>();
		actionHandlers.add(injector.getInstance(InitializeActionHandler.class));
		actionHandlers.add(injector
				.getInstance(ParameterBindingActionHandler.class));
		actionHandlers.add(injector.getInstance(ValidationActionHandler.class));
		actionHandlers.add(injector.getInstance(InvocationActionHandler.class));
		return Collections.unmodifiableCollection(actionHandlers);
	}

	protected abstract PathResolver getPathResolver();

	protected void configureMessagesBehaviour() {
		bind(MessagesBehaviour.class).to(DefaultMessagesBehaviour.class).in(
				Singleton.class);
	}

	protected void configureFormatPattern() {
		bind(FormatPattern.class).to(DefaultFormatPattern.class).in(
				Singleton.class);
	}

}
