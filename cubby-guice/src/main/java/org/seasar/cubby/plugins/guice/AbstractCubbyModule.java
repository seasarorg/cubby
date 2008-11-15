package org.seasar.cubby.plugins.guice;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.controller.RequestParser;
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
import org.seasar.cubby.handler.ActionHandlerChainFactory;
import org.seasar.cubby.handler.impl.ExceptionActionHandler;
import org.seasar.cubby.handler.impl.InitializeActionHandler;
import org.seasar.cubby.handler.impl.InvocationActionHandler;
import org.seasar.cubby.handler.impl.ValidationActionHandler;
import org.seasar.cubby.internal.controller.impl.DefaultRequestParserImpl;
import org.seasar.cubby.internal.factory.ConverterFactory;
import org.seasar.cubby.internal.factory.PathResolverFactory;
import org.seasar.cubby.internal.factory.RequestParserFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceActionHandlerChainFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceConverterFactory;
import org.seasar.cubby.plugins.guice.factory.GuicePathResolverFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceRequestParserFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceActionHandlerChainFactory.ActionHandlerClassesFactory;
import org.seasar.cubby.plugins.guice.factory.GuiceConverterFactory.ConverterClassesFactory;
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
		configureConverterClassesProvider();
		configureActionClassesProvider();
		configureActionHandlerClassesProvider();

		bind(RequestParserFactory.class).to(GuiceRequestParserFactory.class)
				.in(Singleton.class);
		bind(ConverterFactory.class).to(GuiceConverterFactory.class).in(
				Singleton.class);
		bind(PathResolverFactory.class).to(GuicePathResolverFactory.class).in(
				Singleton.class);
		bind(ActionHandlerChainFactory.class).to(
				GuiceActionHandlerChainFactory.class).in(Singleton.class);
	}

	protected void configureConverterClassesProvider() {
		bind(ConverterClassesFactory.class).toInstance(
				new ConverterClassesFactoryImpl());
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
	}

	protected abstract Collection<Class<? extends Action>> getActionClasses();

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

	private class ActionClassesFactoryImpl implements ActionClassesFactory {

		public Collection<Class<? extends Action>> getActionClasses() {
			return AbstractCubbyModule.this.getActionClasses();
		}

	}

	protected void configureActionHandlerClassesProvider() {
		bind(ActionHandlerClassesFactory.class).toInstance(
				new ActionHandlerClassesFactoryImpl());
	}

	private class ActionHandlerClassesFactoryImpl implements
			ActionHandlerClassesFactory {

		public List<Class<? extends ActionHandler>> getActionHandlerClasses() {
			return AbstractCubbyModule.this.getActionHandlerClasses();
		}

	}

	protected List<Class<? extends ActionHandler>> getActionHandlerClasses() {
		List<Class<? extends ActionHandler>> actionHandlerClasses = new ArrayList<Class<? extends ActionHandler>>();
		actionHandlerClasses.add(ExceptionActionHandler.class);
		actionHandlerClasses.add(InitializeActionHandler.class);
		actionHandlerClasses.add(ValidationActionHandler.class);
		actionHandlerClasses.add(InvocationActionHandler.class);
		return Collections.unmodifiableList(actionHandlerClasses);
	}

}
