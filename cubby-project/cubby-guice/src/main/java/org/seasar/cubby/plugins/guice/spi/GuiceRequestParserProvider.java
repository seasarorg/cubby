package org.seasar.cubby.plugins.guice.spi;

import java.util.Collection;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.spi.impl.AbstractRequestParserProvider;
import org.seasar.cubby.plugins.guice.InjectorFactory;

import com.google.inject.Inject;
import com.google.inject.Injector;

public class GuiceRequestParserProvider extends AbstractRequestParserProvider {

	@Inject
	private Collection<RequestParser> requestParsers;

	public GuiceRequestParserProvider() {
		final Injector injector = InjectorFactory.getInjector();
		injector.injectMembers(this);
	}

	@Override
	public Collection<RequestParser> getRequestParsers() {
		return requestParsers;
	}

}
