package org.seasar.cubby.plugins.guice;

import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.seasar.cubby.action.Action;
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
import org.seasar.cubby.factory.ConverterFactory;
import org.seasar.cubby.factory.PathResolverFactory;
import org.seasar.cubby.factory.RequestParserFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceConverterFactory;
import org.seasar.cubby.plugins.guice.factory.GuicePathResolverFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceRequestParserFactory;
import org.seasar.cubby.plugins.guice.factory.GuicePathResolverFactory.ActionClassesFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Inject;
import com.google.inject.Injector;
import com.google.inject.Provider;
import com.google.inject.Singleton;
import com.google.inject.TypeLiteral;

public abstract class AbstractCubbyModule extends AbstractModule {

	public void configure() {
		configureRequestParserProvider();
		configureConvertersProvider();
		configureActionClassesProvider();

		bind(RequestParserFactory.class).to(GuiceRequestParserFactory.class)
				.in(Singleton.class);
		bind(ConverterFactory.class).to(GuiceConverterFactory.class).in(
				Singleton.class);
		bind(PathResolverFactory.class).to(GuicePathResolverFactory.class).in(
				Singleton.class);
	}

	protected void configureConvertersProvider() {
		bind(new TypeLiteral<Collection<Converter>>() {
		}).toProvider(new Provider<Collection<Converter>>() {

			@Inject
			public Injector injector;

			public Collection<Converter> get() {
				return createConverters(injector);
			}

		}).in(Singleton.class);
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

	protected void configureRequestParserProvider() {
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

	protected void configureActionClassesProvider() {
		bind(ActionClassesFactory.class).toInstance(
				new ActionClassesFactoryImpl());
		// bind(new TypeLiteral<Collection<Class<Action>>>() {
		// }).toProvider(new Provider<Collection<Class<Action>>>() {
		//
		// public Collection<Class<Action>> get() {
		// return getActionClasses();
		// }
		//
		// }).in(Singleton.class);
	}

	protected abstract Collection<Class<? extends Action>> getActionClasses();

	class ActionClassesFactoryImpl implements ActionClassesFactory {

		public Collection<Class<? extends Action>> getActionClasses() {
			return AbstractCubbyModule.this.getActionClasses();
		}

	}

}
