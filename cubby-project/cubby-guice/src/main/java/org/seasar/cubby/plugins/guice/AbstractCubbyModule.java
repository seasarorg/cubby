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
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ConverterProvider;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.RequestParserProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Module;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

/**
 * Cubby の設定を行う {@link Module} の抽象クラスです。
 * 
 * @author baba
 */
public abstract class AbstractCubbyModule extends AbstractModule {

	/**
	 * Cubby を構成します。
	 */
	@Override
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

		// PathResolverProvider
		bind(PathResolver.class).toInstance(getPathResolver());
		bind(PathResolverProvider.class).to(GuicePathResolverProvider.class)
				.in(Singleton.class);

		configureMessagesBehaviour();
		configureFormatPattern();
	}

	/**
	 * リクエスト解析器のコレクションを取得します。
	 * <p>
	 * 戻り値のコレクションには以下の順序でリクエスト解析器のインスタンスが格納されます。
	 * <ol>
	 * <li>{@link MultipartRequestParser}</li>
	 * <li>{@link DefaultRequestParser}</li>
	 * </ol>
	 * </p>
	 * <p>
	 * 要求を解析する場合は、このメソッドの戻り値のコレクションの順序で
	 * {@link RequestParser#isParsable(javax.servlet.http.HttpServletRequest)}
	 * が評価されて、最初に <code>true</code> を返したインスタンスを解析に使用します。
	 * {@link DefaultRequestParser#isParsable(javax.servlet.http.HttpServletRequest)}
	 * は、常に <code>true</code> を返すので、 このメソッドをオーバーライドする場合は
	 * {@link DefaultRequestParser} のインスタンスがコレクションの最後になるようにしてください。
	 * </p>
	 * 
	 * @param injector
	 *            インジェクタ
	 * @return リクエスト解析器のコレクション
	 */
	protected Collection<RequestParser> createRequestParsers(
			final Injector injector) {
		final List<RequestParser> requestParsers = new ArrayList<RequestParser>();
		requestParsers.add(injector.getInstance(MultipartRequestParser.class));
		requestParsers.add(injector.getInstance(DefaultRequestParser.class));
		return Collections.unmodifiableCollection(requestParsers);
	}

	/**
	 * コンバーターのコレクションを取得します。
	 * <p>
	 * 戻り値のコレクションには以下のコンバータが含まれます。
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
	 * 
	 * @param injector
	 *            インジェクタ
	 * @return コンバーターのコレクション
	 */
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

	/**
	 * {@link PathResolver} を取得します。
	 * <p>
	 * サブクラスではアクションを登録済みの {@link PathResolver} を返してください。
	 * </p>
	 * 
	 * @return {@link PathResolver}
	 */
	protected abstract PathResolver getPathResolver();

	/**
	 * {@link MessagesBehaviour} を構成します。
	 * <p>
	 * {@link MessagesBehaviour} を {@link DefaultMessagesBehaviour} にバインドします。
	 * </p>
	 */
	protected void configureMessagesBehaviour() {
		bind(MessagesBehaviour.class).to(DefaultMessagesBehaviour.class).in(
				Singleton.class);
	}

	/**
	 * {@link FormatPattern} を構成します。
	 * <p>
	 * {@link FormatPattern} を {@link DefaultFormatPattern} にバインドします。
	 * </p>
	 */
	protected void configureFormatPattern() {
		bind(FormatPattern.class).to(DefaultFormatPattern.class).in(
				Singleton.class);
	}

}
