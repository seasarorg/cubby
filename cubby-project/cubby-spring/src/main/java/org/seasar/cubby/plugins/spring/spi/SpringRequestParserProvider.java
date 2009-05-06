package org.seasar.cubby.plugins.spring.spi;

import java.util.Collection;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * リクエストパーサプロバイダの実装クラスです。
 * 
 * @author suzuki-kei
 * @author someda
 * @since 2.0.0
 */
public class SpringRequestParserProvider extends AbstractRequestParserProvider {

	@Autowired
	private Collection<RequestParser> requestParsers;

	@Override
	protected Collection<RequestParser> getRequestParsers() {
		return requestParsers;
	}

}
