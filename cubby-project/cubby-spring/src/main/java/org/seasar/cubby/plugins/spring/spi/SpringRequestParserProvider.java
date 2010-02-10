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
package org.seasar.cubby.plugins.spring.spi;

import java.util.Collection;
import java.util.Collections;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.spi.impl.AbstractRequestParserProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;

/**
 * リクエストパーサプロバイダの実装クラスです。
 * 
 * @author suzuki-kei
 * @author someda
 * @since 2.0.0
 */
public class SpringRequestParserProvider extends AbstractRequestParserProvider {

	private Collection<RequestParser> requestParsers;

	@Override
	protected Collection<RequestParser> getRequestParsers() {
		return requestParsers;
	}

	@Autowired
	public SpringRequestParserProvider(
			final ApplicationContext applicationContext) {
		@SuppressWarnings("unchecked")
		final Collection<RequestParser> requestParsers = (Collection<RequestParser>) applicationContext
				.getBean("requestParsers");
		this.requestParsers = Collections
				.unmodifiableCollection(requestParsers);
	}

}
