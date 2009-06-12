package org.seasar.cubby.wiki;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import net.sf.oval.localization.context.OValContextRenderer;
import net.sf.oval.localization.message.MessageResolver;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.converter.Converter;
import org.seasar.cubby.plugins.guice.AbstractCubbyModule;
import org.seasar.cubby.plugins.oval.validation.RequestLocaleMessageResolver;
import org.seasar.cubby.plugins.oval.validation.RequestLocaleOvalContextRenderer;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.PathTemplateParser;
import org.seasar.cubby.routing.impl.PathResolverImpl;
import org.seasar.cubby.routing.impl.PathTemplateParserImpl;
import org.seasar.cubby.util.ActionUtils;
import org.seasar.cubby.wiki.action.PageAction;
import org.seasar.cubby.wiki.converter.PageConverter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.google.inject.AbstractModule;
import com.google.inject.Injector;
import com.google.inject.matcher.AbstractMatcher;
import com.google.inject.matcher.Matcher;
import com.google.inject.servlet.ServletModule;

public class ApplicationModule extends AbstractModule {

	@Override
	protected void configure() {
		install(new ServletModule());
		install(new JpaModule());
		install(new ApplicationCubbyModule());

		bind(MessageResolver.class).to(RequestLocaleMessageResolver.class);
		bind(OValContextRenderer.class).to(
				RequestLocaleOvalContextRenderer.class);

		bindInterceptor(anyConcreteClass(), anyActionMethod(),
				new LoggingInterceptor());
	}

	protected Matcher<Class<?>> anyConcreteClass() {
		return ANY_CONCRETE_CLASS;
	}

	protected Matcher<Method> anyActionMethod() {
		return ANY_ACTION_METHOD;
	}

	protected void bindActionMethodInterceptor(
			MethodInterceptor... interceptors) {
		bindInterceptor(ANY_CONCRETE_CLASS, ANY_ACTION_METHOD, interceptors);
	}

	static Matcher<Class<?>> ANY_CONCRETE_CLASS = new AbstractMatcher<Class<?>>() {
		public boolean matches(Class<?> clazz) {
			return !Modifier.isAbstract(clazz.getModifiers());
		}
	};

	static Matcher<Method> ANY_ACTION_METHOD = new AbstractMatcher<Method>() {
		public boolean matches(Method method) {
			return ActionUtils.isActionMethod(method);
		}
	};

	static class LoggingInterceptor implements MethodInterceptor {
		private static Logger logger = LoggerFactory
				.getLogger(LoggingInterceptor.class);

		public Object invoke(MethodInvocation invocation) throws Throwable {
			logger.debug("call " + invocation.getMethod());
			return invocation.proceed();
		}
	}

	private static class ApplicationCubbyModule extends AbstractCubbyModule {

		@Override
		protected PathResolver getPathResolver() {
			PathTemplateParser pathTemplateParser = new PathTemplateParserImpl();
			PathResolver pathResolver = new PathResolverImpl(pathTemplateParser);
			pathResolver.add(PageAction.class);
			return pathResolver;
		}

		@Override
		protected Collection<Converter> createConverters(Injector injector) {
			Set<Converter> converters = new HashSet<Converter>(super
					.createConverters(injector));
			converters.add(injector.getInstance(PageConverter.class));
			return Collections.unmodifiableCollection(converters);
		}

	}
}
