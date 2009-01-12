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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;
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
 * @since 2.0.0
 */
public abstract class AbstractRequestParserProvider implements
		RequestParserProvider {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(AbstractRequestParserProvider.class);

	/** 要求解析器の {@link Comparator}。 */
	private final Comparator<RequestParser> requestParserComparator = new Comparator<RequestParser>() {

		/**
		 * {@inheritDoc}
		 * <p>
		 * 優先順位の昇順にソートします。
		 * </p>
		 */
		public int compare(final RequestParser reuqestParser1,
				final RequestParser reuqestParser2) {
			return reuqestParser1.getPriority() - reuqestParser2.getPriority();
		}

	};

	/**
	 * {@inheritDoc}
	 */
	public Map<String, Object[]> getParameterMap(
			final HttpServletRequest request) {
		for (final RequestParser requestParser : sort(getRequestParsers())) {
			if (requestParser.isParsable(request)) {
				if (logger.isDebugEnabled()) {
					logger.debug(format("DCUB0016", requestParser));
				}
				final Map<String, Object[]> parameterMap = requestParser
						.getParameterMap(request);
				return parameterMap;
			}
		}
		throw new NullPointerException("requestParser");
	}

	/**
	 * 要求解析器のコレクションをソートします。
	 * 
	 * @param requestParsers
	 *            リクエスト解析器のコレクション
	 * @return ソートされた {@link Iterable}
	 */
	protected Iterable<RequestParser> sort(
			final Collection<RequestParser> requestParsers) {
		final List<RequestParser> requestParserList = new ArrayList<RequestParser>(
				requestParsers);
		Collections.sort(requestParserList, requestParserComparator);
		return requestParserList;
	}

	/**
	 * 要求解析機の一覧を取得します。
	 * 
	 * @return 要求解析機の一覧
	 */
	protected abstract Collection<RequestParser> getRequestParsers();

}
