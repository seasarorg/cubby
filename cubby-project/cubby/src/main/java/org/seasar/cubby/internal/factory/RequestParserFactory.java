package org.seasar.cubby.internal.factory;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.RequestParser;

public interface RequestParserFactory {

	/**
	 * 指定されたリクエストを解析可能なリクエスト解析器を取得します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return 指定されたリクエストを解析可能なリクエスト解析器
	 */
	RequestParser getRequestParser(final HttpServletRequest request);

}
