package org.seasar.cubby.plugins.s2.factory;

import java.util.Arrays;
import java.util.Collection;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.factory.impl.AbstractRequestParserFactory;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;

public class S2RequestParserFactory extends AbstractRequestParserFactory {

	@Override
	protected Collection<RequestParser> getRequestParsers() {
		final S2Container container = SingletonS2ContainerFactory
				.getContainer();
		final RequestParser[] requestParsers = RequestParser[].class
				.cast(container.findAllComponents(RequestParser.class));
		return Arrays.asList(requestParsers);
	}

}
