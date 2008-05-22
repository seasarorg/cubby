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
