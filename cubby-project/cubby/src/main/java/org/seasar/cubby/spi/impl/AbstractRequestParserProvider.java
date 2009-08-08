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
package org.seasar.cubby.spi.impl;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.impl.DefaultRequestParser;
import org.seasar.cubby.spi.RequestParserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * {@link RequestParser} のプロバイダの抽象的な実装です。
 * <p>
 * このクラスのサブクラスは {{@link #getRequestParsers()} をオーバーライドして使用可能な要求解析機の一覧を返してください。
 * </p>
 * 
 * @author baba
 */
public abstract class AbstractRequestParserProvider implements
		RequestParserProvider {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractRequestParserProvider.class);

	/** デフォルトの {@link RequestParser} */
	private final RequestParser defaultRequestParser = new DefaultRequestParser();

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object[]> getParameterMap(
			final HttpServletRequest request) {
		final RequestParser requestParser = findRequestParser(request);
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0016", requestParser));
		}
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		return parameterMap;
	}

	/**
	 * 要求を解析できる {@link RequestParser} を検索します。
	 * 
	 * @param request
	 *            要求
	 * @return 要求を解析できる {@link RequestParser}
	 */
	protected RequestParser findRequestParser(final HttpServletRequest request) {
		for (final RequestParser requestParser : getRequestParsers()) {
			if (requestParser.isParsable(request)) {
				return requestParser;
			}
		}
		return defaultRequestParser;
	}

	/**
	 * 要求解析機の一覧を取得します。
	 * 
	 * @return 要求解析機の一覧
	 */
	protected abstract Collection<RequestParser> getRequestParsers();

}
