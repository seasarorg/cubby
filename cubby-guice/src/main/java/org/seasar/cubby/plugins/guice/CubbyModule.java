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

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FlashMap;
import org.seasar.cubby.action.impl.ActionContextImpl;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.action.impl.FlashMapImpl;
import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultFormatPattern;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
import org.seasar.cubby.controller.impl.MultipartRequestParser;
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
import org.seasar.cubby.plugins.guice.spi.GuiceContainerProvider;
import org.seasar.cubby.plugins.guice.spi.GuiceConverterProvider;
import org.seasar.cubby.plugins.guice.spi.GuicePathResolverProvider;
import org.seasar.cubby.plugins.guice.spi.GuiceRequestParserProvider;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;
import org.seasar.cubby.util.ActionUtils;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.Key;
import com.google.inject.Module;
import com.google.inject.Provides;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.servlet.RequestScoped;
import com.google.inject.spi.TypeEncounter;
import com.google.inject.spi.TypeListener;

/**
 * Cubby の設定を行う {@link Module} です。
 * 
 * @author baba
 */
public class CubbyModule extends AbstractModule {

	/** コンテナに登録されたアクションクラスのセット。 */
	private final Set<Class<?>> actionClasses = new LinkedHashSet<Class<?>>();

	/** コンテナからコンバータを取得するための {@link Key} のセット。 */
	private final Set<Key<? extends Converter>> converterKeys = new LinkedHashSet<Key<? extends Converter>>();

	/** コンテナから要求解析器を取得するための {@link Key} のセット。 */
	private final Set<Key<? extends RequestParser>> requestParserKeys = new LinkedHashSet<Key<? extends RequestParser>>();

	/**
	 * Cubby を構成します。
	 */
	@Override
	public void configure() {
		configureTypeListeners();
		configureProviders();
		configureComponents();
		configureDefaultRequestParsers();
		configureDefaultConverters();
	}

	/**
	 * {@link TypeListener} をバインドします。
	 */
	private void configureTypeListeners() {
		// action class listener
		bindListener(new AbstractMatcher<TypeLiteral<?>>() {

			public boolean matches(final TypeLiteral<?> typeLiteral) {
				final boolean matches = ActionUtils.isActionClass(typeLiteral
						.getRawType());
				return matches;
			}

		}, new TypeListener() {

			public <I> void hear(final TypeLiteral<I> typeLiteral,
					final TypeEncounter<I> typeEncounter) {
				final Class<?> actionClass = typeLiteral.getRawType();
				actionClasses.add(actionClass);
			}

		});

		// converter listener
		bindListener(new AbstractMatcher<TypeLiteral<?>>() {

			public boolean matches(final TypeLiteral<?> typeLiteral) {
				final boolean matches = Converter.class
						.isAssignableFrom(typeLiteral.getRawType());
				return matches;
			}

		}, new TypeListener() {

			public <I> void hear(final TypeLiteral<I> typeLiteral,
					final TypeEncounter<I> typeEncounter) {
				final Key<? extends Converter> key = key(typeLiteral);
				converterKeys.add(key);
			}

		});

		// request parser listener
		bindListener(new AbstractMatcher<TypeLiteral<?>>() {

			public boolean matches(final TypeLiteral<?> typeLiteral) {
				final boolean matches = RequestParser.class
						.isAssignableFrom(typeLiteral.getRawType());
				return matches;
			}

		}, new TypeListener() {

			public <I> void hear(final TypeLiteral<I> typeLiteral,
					final TypeEncounter<I> typeEncounter) {
				final Key<? extends RequestParser> key = key(typeLiteral);
				requestParserKeys.add(key);
			}

		});
	}

	/**
	 * 以下のクラスをバインドします。
	 * <ul>
	 * <li>{@link ContainerProvider}</li>
	 * <li>{@link BeanDescProvider}</li>
	 * <li>{@link RequestParserProvider}</li>
	 * <li>{@link ConverterProvider}</li>
	 * <li>{@link PathResolverProvider}</li>
	 * </ul>
	 */
	private void configureProviders() {
		bind(ContainerProvider.class).to(GuiceContainerProvider.class)
				.asEagerSingleton();
		bind(BeanDescProvider.class).to(DefaultBeanDescProvider.class)
				.asEagerSingleton();
		bind(RequestParserProvider.class).to(GuiceRequestParserProvider.class)
				.asEagerSingleton();
		bind(ConverterProvider.class).to(GuiceConverterProvider.class)
				.asEagerSingleton();
		bind(PathResolverProvider.class).to(GuicePathResolverProvider.class)
				.asEagerSingleton();
	}

	/**
	 * 以下のクラスをバインドします。
	 * <ul>
	 * <li>{@link MessagesBehaviour}</li>
	 * <li>{@link FormatPattern}</li>
	 * </ul>
	 */
	private void configureComponents() {
		bind(MessagesBehaviour.class).to(DefaultMessagesBehaviour.class)
				.asEagerSingleton();
		bind(FormatPattern.class).to(DefaultFormatPattern.class)
				.asEagerSingleton();
	}

	/**
	 * 標準の要求解析器をコンテナに登録します。
	 * <p>
	 * 以下の要求解析器を登録します。
	 * <ol>
	 * <li>{@link MultipartRequestParser}</li>
	 * <li>{@link DefaultRequestParser}</li>
	 * </ol>
	 * </p>
	 * <p>
	 * 要求を解析する場合は、コンテナに登録された順序で
	 * {@link RequestParser#isParsable(javax.servlet.http.HttpServletRequest)}
	 * が評価されて、最初に <code>true</code> を返したインスタンスを解析に使用します。
	 * {@link DefaultRequestParser#isParsable(javax.servlet.http.HttpServletRequest)}
	 * は、常に <code>true</code> を返すので、 このメソッドをオーバーライドする場合は
	 * {@link DefaultRequestParser} のインスタンスを最後に登録するようにしてください。
	 * </p>
	 */
	protected void configureDefaultRequestParsers() {
		bind(MultipartRequestParser.class).asEagerSingleton();
		bind(DefaultRequestParser.class).asEagerSingleton();
	}

	/**
	 * 標準のコンバータをコンテナに登録します。
	 * <p>
	 * 以下のコンバータが登録されます。
	 * <ul>
	 * <li>{@link BigDecimalConverter}</li>
	 * <li>{@link BigIntegerConverter}</li>
	 * <li>{@link BooleanConverter}</li>
	 * <li>{@link ByteArrayFileItemConverter}</li>
	 * <li>{@link ByteConverter}</li>
	 * <li>{@link CharacterConverter}</li>
	 * <li>{@link DateConverter}</li>
	 * <li>{@link DoubleConverter}</li>
	 * <li>{@link EnumConverter}</li>
	 * <li>{@link FloatConverter}</li>
	 * <li>{@link InputStreamFileItemConverter}</li>
	 * <li>{@link IntegerConverter}</li>
	 * <li>{@link LongConverter}</li>
	 * <li>{@link ShortConverter}</li>
	 * <li>{@link SqlDateConverter}</li>
	 * <li>{@link SqlTimeConverter}</li>
	 * <li>{@link SqlTimestampConverter}</li>
	 * </ul>
	 * </p>
	 */
	protected void configureDefaultConverters() {
		bind(BigDecimalConverter.class).asEagerSingleton();
		bind(BigIntegerConverter.class).asEagerSingleton();
		bind(BooleanConverter.class).asEagerSingleton();
		bind(ByteArrayFileItemConverter.class).asEagerSingleton();
		bind(ByteConverter.class).asEagerSingleton();
		bind(CharacterConverter.class).asEagerSingleton();
		bind(DateConverter.class).asEagerSingleton();
		bind(DoubleConverter.class).asEagerSingleton();
		bind(EnumConverter.class).asEagerSingleton();
		bind(FloatConverter.class).asEagerSingleton();
		bind(InputStreamFileItemConverter.class).asEagerSingleton();
		bind(IntegerConverter.class).asEagerSingleton();
		bind(LongConverter.class).asEagerSingleton();
		bind(ShortConverter.class).asEagerSingleton();
		bind(SqlDateConverter.class).asEagerSingleton();
		bind(SqlTimeConverter.class).asEagerSingleton();
		bind(SqlTimestampConverter.class).asEagerSingleton();
	}

	// Provider methods ------------------------------------------------

	@Provides
	@Singleton
	Collection<RequestParser> provideRequestParsers(final Injector injector) {
		final Set<RequestParser> requestParsers = new LinkedHashSet<RequestParser>();
		for (final Key<? extends RequestParser> key : requestParserKeys) {
			final RequestParser requestParser = injector.getInstance(key);
			requestParsers.add(requestParser);
		}
		return Collections.unmodifiableCollection(requestParsers);
	}

	@Provides
	@Singleton
	Collection<Converter> provideConverters(final Injector injector) {
		final Set<Converter> converters = new HashSet<Converter>();
		for (final Key<? extends Converter> key : converterKeys) {
			final Converter converter = injector.getInstance(key);
			converters.add(converter);
		}
		return Collections.unmodifiableCollection(converters);
	}

	@Provides
	@Singleton
	PathTemplateParser providePathTemplateParser() {
		final PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();
		return pathTemplateParser;
	}

	@Provides
	@Singleton
	PathResolver providePathResolver(final PathTemplateParser pathTemplateParser) {
		final PathResolver pathResolver = new PathResolverImpl(
				pathTemplateParser);
		for (final Class<?> actionClass : actionClasses) {
			pathResolver.add(actionClass);
		}
		return pathResolver;
	}

	@Provides
	@RequestScoped
	ActionErrors provideActionErrors() {
		final ActionErrors actionErrors = new ActionErrorsImpl();
		return actionErrors;
	}

	@Provides
	@RequestScoped
	FlashMap provideFlashMap(final HttpServletRequest request) {
		final FlashMap flashMap = new FlashMapImpl(request);
		return flashMap;
	}

	@Provides
	@RequestScoped
	ActionContext provideActionContext() {
		final ActionContext actionContext = new ActionContextImpl();
		return actionContext;
	}

	private static <T> Key<T> key(final TypeLiteral<?> typeLiteral) {
		@SuppressWarnings("unchecked")
		final Key<T> key = (Key<T>) Key.get(typeLiteral);
		return key;
	}

}
