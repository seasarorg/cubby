package org.seasar.cubby.controller;

import javax.servlet.http.HttpServletRequest;

/**
 * リクエスト解析器セレクタです。
 * 
 * @author baba
 * @since 1.1.0
 */
public interface RequestParserSelector {

	/**
	 * 指定されたリクエストを解析可能なリクエスト解析器を選択します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return 指定されたリクエストを解析可能なリクエスト解析器
	 */
	RequestParser select(final HttpServletRequest request);

}
