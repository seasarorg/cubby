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
package org.seasar.cubby.controller.impl;

import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.controller.RequestParserSelector;
import org.seasar.framework.container.S2Container;

/**
 * リクエスト解析器セレクタの実装です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class RequestParserSelectorImpl implements RequestParserSelector {

	/** コンテナ。 */
	private S2Container container;

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
	 * コンテナを設定します。
	 * 
	 * @param container
	 *            コンテナ
	 */
	public void setContainer(final S2Container container) {
		this.container = container;
	}

	/**
	 * {@inheritDoc}
	 */
	public RequestParser select(final HttpServletRequest request) {
		final S2Container root = container.getRoot();
		final RequestParser[] requestParsers = (RequestParser[]) root
				.findAllComponents(RequestParser.class);
		final List<RequestParser> requestParserList = Arrays
				.asList(requestParsers);
		Collections.sort(requestParserList, requestParserComparator);
		for (final RequestParser requestParser : requestParserList) {
			if (requestParser.isParsable(request)) {
				return requestParser;
			}
		}
		return null;
	}

}
