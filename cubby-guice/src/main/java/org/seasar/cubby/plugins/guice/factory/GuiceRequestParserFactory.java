package org.seasar.cubby.plugins.guice.factory;

import java.util.Collection;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.factory.impl.AbstractRequestParserFactory;

import com.google.inject.Inject;

public class GuiceRequestParserFactory extends AbstractRequestParserFactory {

	private final Collection<RequestParser> requestParsers;

	@Inject
	public GuiceRequestParserFactory(
			final Collection<RequestParser> requestParsers) {
		this.requestParsers = requestParsers;
	}

	@Override
	public Collection<RequestParser> getRequestParsers() {
		return requestParsers;
	}

}
