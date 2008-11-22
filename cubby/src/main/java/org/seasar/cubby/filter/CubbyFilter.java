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
package org.seasar.cubby.filter;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_ROUTING;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.ContainerFactory;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.factory.RequestParserFactory;
import org.seasar.cubby.internal.routing.PathInfo;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.Routing;
import org.seasar.cubby.internal.routing.impl.RouterImpl;
import org.seasar.cubby.internal.util.CubbyUtils;
import org.seasar.cubby.internal.util.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cubby用のフィルター。
 * <p>
 * リクエストの処理を{@link ActionProcessor}に委譲します。
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class CubbyFilter implements Filter {

	/** ロガー。 */
	private static final Logger logger = LoggerFactory
			.getLogger(CubbyFilter.class);

	/** ルーティングの対象外とするパスの初期パラメータ名。 */
	public static final String IGNORE_PATH_PATTERN = "ignorePathPattern";

	// TODO
	private final Router router = new RouterImpl();

	// TODO
	private final ActionProcessor actionProcessor = new ActionProcessorImpl();

	/** ルーティングの対象外とするパスの正規表現パターンのリスト。 */
	private final List<Pattern> ignorePathPatterns = new ArrayList<Pattern>();

	/**
	 * このフィルタを初期化します。
	 * <p>
	 * 使用可能な初期化パラメータ
	 * <table>
	 * <thead>
	 * <th>初期化パラメータ名</th>
	 * <th>初期化パラメータの値</th>
	 * <th>例</th>
	 * </thead> <thead>
	 * <tr>
	 * <td>{@link #IGNORE_PATH_PATTERN}</td>
	 * <td>ルーティングの対象外とするパスの正規表現をカンマ区切りで指定します。 HotDeploy
	 * 時のパフォーマンスにも影響するので、画像やスクリプトを特定のディレクトリに
	 * 格納していてアクションを実行するパスと明確に区別できる場合はできる限り指定するようにしてください。</td>
	 * <td>
	 * 
	 * <pre>
	 * &lt;param-name&gt;ignorePathPattern&amp;lt/param-name&gt;
	 * &lt;param-value&gt;/img/.*,/js/.*&lt;param-name&gt;
	 * </pre>
	 * 
	 * この例では /img と /js 以下のパスをルーティングの対象外にします。</td>
	 * </tr>
	 * </thead>
	 * </p>
	 * 
	 * @param config
	 *            Filter 設定のためのオブジェクト
	 * @exception ServletException
	 *                初期化処理で例外が発生した場合
	 */
	public void init(final FilterConfig config) throws ServletException {
		final String ignorePathPatternString = config
				.getInitParameter(IGNORE_PATH_PATTERN);
		if (!StringUtils.isEmpty(ignorePathPatternString)) {

			for (final StringTokenizer tokenizer = new StringTokenizer(
					ignorePathPatternString, ","); tokenizer.hasMoreTokens();) {
				final String token = tokenizer.nextToken();
				final Pattern pattern = Pattern.compile(token);
				ignorePathPatterns.add(pattern);
			}
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public void destroy() {
	}

	// TODO コメント修正
	/**
	 * フィルター処理。
	 * <p>
	 * リクエストされた URI に対応する内部フォワード情報が {@link Router} から取得できた場合は、そこに設定されている
	 * {@link Info#getOnSubmitRoutings()} をリクエストに設定し、 {@link CubbyFilter}
	 * が処理することを期待します。 URI に対応する内部フォワード情報が取得できなかった場合はフィルタチェインで次のフィルタに処理を移譲します。
	 * </p>
	 * <p>
	 * リクエストの処理を {@link ActionProcessor} に委譲します。
	 * </p>
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @param chain
	 *            フィルタチェイン
	 * @throws IOException
	 *             リクエストディスパッチャやフィルタチェインで例外が発生した場合
	 * @throws ServletException
	 *             リクエストディスパッチャやフィルタチェインで例外が発生した場合
	 * @see Router#routing(HttpServletRequest, HttpServletResponse, List)
	 * @see ActionProcessor#process(HttpServletRequest, HttpServletResponse)
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		process((HttpServletRequest) request, (HttpServletResponse) response,
				chain);
	}

	private void process(final HttpServletRequest request,
			final HttpServletResponse response, final FilterChain chain)
			throws IOException, ServletException {
		final PathInfo pathInfo = getPathInfo(request, response);
		if (pathInfo != null) {
			final HttpServletRequest wrappedRequest = new CubbyHttpServletRequestWrapper(
					request, pathInfo.getURIParameters());

			final Map<String, Object[]> parameterMap = parseRequest(wrappedRequest);
			request.setAttribute(ATTR_PARAMS, parameterMap);

			final Map<String, Routing> routings = pathInfo
					.getOnSubmitRoutings();
			final Routing routing = dispatch(routings, parameterMap);

			invoke(wrappedRequest, response, routing);
		} else {
			chain.doFilter(request, response);
		}
	}

	private void invoke(final HttpServletRequest request,
			final HttpServletResponse response, final Routing routing)
			throws IOException, ServletException {
		ThreadContext.setRequest(request);
		try {
			final ActionResultWrapper actionResultWrapper = actionProcessor
					.process(request, response, routing);
			actionResultWrapper.execute(request, response);
		} catch (final Exception e) {
			if (e instanceof IOException) {
				throw (IOException) e;
			} else if (e instanceof ServletException) {
				throw (ServletException) e;
			} else {
				throw new ServletException(e);
			}
		} finally {
			ThreadContext.remove();
		}
	}

	/**
	 * リクエストをパースしてパラメータを取り出し、{@link Map}に変換して返します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return リクエストパラメータの{@link Map}
	 */
	private Map<String, Object[]> parseRequest(final HttpServletRequest request) {
		final Container container = ContainerFactory.getContainer();
		final RequestParserFactory requestParserFactory = container
				.lookup(RequestParserFactory.class);
		final RequestParser requestParser = requestParserFactory
				.getRequestParser(request);
		if (requestParser == null) {
			throw new NullPointerException("requestParser");
		}
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0016", requestParser));
		}
		final Map<String, Object[]> parameterMap = requestParser
				.getParameterMap(request);
		return parameterMap;
	}

	private PathInfo getPathInfo(HttpServletRequest request,
			HttpServletResponse response) {
		final Routing routing = CubbyUtils.getAttribute(request, ATTR_ROUTING);
		final PathInfo pathInfo;
		if (routing != null) {
			request.removeAttribute(ATTR_ROUTING);
			pathInfo = new PathInfoImpl(routing);
		} else {
			pathInfo = router.routing(request, response, ignorePathPatterns);
		}
		return pathInfo;
	}

	private Routing dispatch(Map<String, Routing> routings,
			Map<String, Object[]> parameterMap) {
		for (final String onSubmit : routings.keySet()) {
			if (parameterMap.containsKey(onSubmit)) {
				return routings.get(onSubmit);
			}
		}
		return routings.get(null);
	}

	private class PathInfoImpl implements PathInfo {

		private Map<String, Routing> routings;

		private Map<String, String[]> uriParameters = new HashMap<String, String[]>();

		public PathInfoImpl(Routing routing) {
			routings = new HashMap<String, Routing>();
			routings.put(null, routing);
		}

		public Map<String, Routing> getOnSubmitRoutings() {
			return routings;
		}

		public Map<String, String[]> getURIParameters() {
			return uriParameters;
		}

	}

}
