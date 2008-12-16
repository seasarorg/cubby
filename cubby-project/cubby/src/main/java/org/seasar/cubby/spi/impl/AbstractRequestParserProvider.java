/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.spi.RequestParserProvider;

/**
 * リクエスト解析器プロバイダの抽象的な実装です。
 * 
 * @author baba
 * @since 2.0.0
 */
public abstract class AbstractRequestParserProvider implements
		RequestParserProvider {

	/** リクエスト解析器の {@link Comparator}。 */
	private Comparator<RequestParser> requestParserComparator = new Comparator<RequestParser>() {

		/**
		 * {@inheritDoc}
		 * <p>
		 * 優先順位の昇順にソートします。
		 * </p>
		 */
		public int compare(RequestParser reuqestParser1,
				RequestParser reuqestParser2) {
			return reuqestParser1.getPriority() - reuqestParser2.getPriority();
		}

	};

	/**
	 * {@inheritDoc}
	 */
	public RequestParser getRequestParser(final HttpServletRequest request) {
		for (final RequestParser requestParser : sort(getRequestParsers())) {
			if (requestParser.isParsable(request)) {
				return requestParser;
			}
		}
		return null;
	}

	/**
	 * リクエスト解析器のコレクションをソートします。
	 * 
	 * @param requestParsers
	 *            リクエスト解析器のコレクション
	 * @return ソートされた {@link Iterable}
	 */
	protected Iterable<RequestParser> sort(
			Collection<RequestParser> requestParsers) {
		final List<RequestParser> requestParserList = new ArrayList<RequestParser>(
				requestParsers);
		Collections.sort(requestParserList, requestParserComparator);
		return requestParserList;
	}

	/**
	 * 
	 * @return
	 */
	protected abstract Collection<RequestParser> getRequestParsers();

}
