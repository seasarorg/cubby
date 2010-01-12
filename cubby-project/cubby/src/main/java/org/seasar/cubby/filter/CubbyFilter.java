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
package org.seasar.cubby.filter;

import static org.seasar.cubby.CubbyConstants.ATTR_FILTER_CHAIN;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_ROUTING;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.plugin.Plugin;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugin.RequestProcessingInvocation;
import org.seasar.cubby.plugin.RoutingInvocation;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.routing.Routing;
import org.seasar.cubby.spi.PathResolverProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.RequestParserProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Cubby フィルター。
 * <p>
 * 要求を解析し、対応するアクションが登録されている場合はアクションを実行します。
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class CubbyFilter implements Filter {

	/** ロガー */
	private static final Logger logger = LoggerFactory
			.getLogger(CubbyFilter.class);

	/** ルーティングの対象外とするパスの初期パラメータ名。 */
	public static final String IGNORE_PATH_PATTERN = "ignorePathPattern";

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
	 *            フィルタ設定のためのオブジェクト
	 * @throws ServletException
	 *             初期化処理で例外が発生した場合
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

	/**
	 * フィルター処理を行います。
	 * <p>
	 * 要求された URI に対応する情報が
	 * {@link #routing(HttpServletRequest, HttpServletResponse, List)}
	 * から取得できた場合は、
	 * {@link #processRequest(HttpServletRequest, HttpServletResponse, PathInfo)}
	 * によって要求を処理します。URI に対応する情報が取得できなかった場合はフィルタチェインで次のフィルタに処理を委譲します。
	 * </p>
	 * 
	 * @param req
	 *            要求
	 * @param res
	 *            応答
	 * @param chain
	 *            フィルターチェーン
	 * @throws IOException
	 *             要求の転送や要求のチェーンがこの例外をスローする場合
	 * @throws ServletException
	 *             要求の転送や要求のチェーンがこの例外をスローする場合
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		final String path = RequestUtils.getPath(request);
		if (logger.isDebugEnabled()) {
			logger.debug(format("DCUB0006", path));
		}

		final PathInfo pathInfo = findPathInfo(request, response, path,
				ignorePathPatterns);

		if (pathInfo != null) {
			request.setAttribute(ATTR_FILTER_CHAIN, chain);
			try {
				processRequest(request, response, pathInfo);
			} catch (final Exception e) {
				if (e instanceof IOException) {
					throw (IOException) e;
				} else if (e instanceof ServletException) {
					throw (ServletException) e;
				} else {
					throw new ServletException(e);
				}
			}
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * {@link PathInfo} を検索します。
	 * <p>
	 * <ul>
	 * <li>リクエストの属性 {@link CubbyConstants#ATTR_ROUTING} に値({@link Routing}
	 * のインスタンス)が設定されている(= アクション間のフォワード)場合はその値をもとにした {@link PathInfo} を</li>
	 * <li>指定されたパスが処理対象外({@link #isIgnorePath(String, List)} == true)の場合は
	 * <code>null</code> を</li>
	 * <li>上記以外の場合は
	 * {@link #routing(HttpServletRequest, HttpServletResponse, String)} の戻り値を</li>
	 * </ul>
	 * それぞれ返します。
	 * </p>
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param path
	 *            パス
	 * @param ignorePathPatterns
	 *            除外するパスのパターンのリスト
	 * @return 検索した {@link PathInfo}
	 */
	protected PathInfo findPathInfo(final HttpServletRequest request,
			final HttpServletResponse response, final String path,
			List<Pattern> ignorePathPatterns) {
		final PathInfo pathInfo;
		final Routing routing = RequestUtils
				.getAttribute(request, ATTR_ROUTING);
		if (routing != null) {
			request.removeAttribute(ATTR_ROUTING);
			pathInfo = new ForwardFromActionPathInfo(routing);
		} else if (isIgnorePath(path, ignorePathPatterns)) {
			pathInfo = null;
		} else {
			final PathResolver pathResolver = createPathResolver();
			pathInfo = invokeRouting(request, response, path, pathResolver);
		}
		return pathInfo;
	}

	/**
	 * 指定された path が ignorePathPatterns にマッチするかを示します。
	 * 
	 * @param path
	 *            パス
	 * @param ignorePathPatterns
	 *            対象外パターンのリスト
	 * @return path が ignorePathPatterns にマッチする場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private static boolean isIgnorePath(final String path,
			final List<Pattern> ignorePathPatterns) {
		for (final Pattern pattern : ignorePathPatterns) {
			final Matcher matcher = pattern.matcher(path);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 指定されたパスに対応するアクションを決定し、 {@link PathInfo} を返します。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param path
	 *            パス
	 * @param pathResolver
	 *            パスのリゾルバ
	 * @return 要求に対応する内部フォワード情報、URI と要求メソッドに対応する内部フォワード情報がない場合や URI
	 *         が対象外とするパスのパターンにマッチする場合は <code>null</code>
	 */
	protected PathInfo invokeRouting(final HttpServletRequest request,
			final HttpServletResponse response, final String path,
			final PathResolver pathResolver) {
		final RoutingInvocation routingInvocation = new RoutingInvocationImpl(
				path, pathResolver, request, response);
		try {
			return routingInvocation.proceed();
		} catch (final Exception e) {
			logger.warn("routing failed.", e);
			return null;
		}
	}

	/**
	 * 要求処理の実行情報の実装です。
	 * 
	 * @author baba
	 */
	static class RoutingInvocationImpl implements RoutingInvocation {

		/** パス。 */
		private final String path;

		/** パスのリゾルバ。 */
		private final PathResolver pathResolver;

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** プラグインのイテレータ。 */
		private final Iterator<Plugin> pluginsIterator;

		/**
		 * インスタンス化します。
		 * 
		 * @param path
		 *            パス
		 * @param pathResolver
		 *            パスのリゾルバ
		 * @param request
		 *            要求
		 * @param response
		 *            応答
		 */
		public RoutingInvocationImpl(final String path,
				final PathResolver pathResolver,
				final HttpServletRequest request,
				final HttpServletResponse response) {
			this.path = path;
			this.pathResolver = pathResolver;
			this.request = request;
			this.response = response;

			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public PathInfo proceed() throws Exception {
			if (pluginsIterator.hasNext()) {
				final Plugin plugin = pluginsIterator.next();
				return plugin.invokeRouting(this);
			} else {
				final PathInfo pathInfo = pathResolver.getPathInfo(path,
						request.getMethod(), request.getCharacterEncoding());
				return pathInfo;
			}
		}

		/**
		 * {@inheritDoc}
		 */
		public String getPath() {
			return path;
		}

		/**
		 * {@inheritDoc}
		 */
		public PathResolver getPathResolver() {
			return pathResolver;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletRequest getRequest() {
			return request;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletResponse getResponse() {
			return response;
		}

	}

	/**
	 * 指定された {@link PathInfo} に対する処理を行います。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @param pathInfo
	 *            パスの情報
	 * @throws Exception
	 *             要求の処理で例外が発生した場合
	 */
	protected void processRequest(final HttpServletRequest request,
			final HttpServletResponse response, final PathInfo pathInfo)
			throws Exception {
		final HttpServletRequest wrappedRequest = new CubbyHttpServletRequestWrapper(
				request, pathInfo.getURIParameters());
		final RequestProcessingInvocation invocation = new RequestProcessingInvocationImpl(
				this, wrappedRequest, response, pathInfo);
		invocation.proceed();
	}

	/**
	 * 指定された要求のパラメータをパースして {@link Map} に変換します。
	 * 
	 * @param request
	 *            要求
	 * @return パース結果の {@link Map}
	 */
	protected Map<String, Object[]> parseRequest(
			final HttpServletRequest request) {
		final RequestParserProvider requestParserProvider = ProviderFactory
				.get(RequestParserProvider.class);
		final Map<String, Object[]> parameterMap = requestParserProvider
				.getParameterMap(request);
		return parameterMap;
	}

	/**
	 * 要求処理の実行情報の実装です。
	 * 
	 * @author baba
	 */
	static class RequestProcessingInvocationImpl implements
			RequestProcessingInvocation {

		/** CubbyFilter */
		private CubbyFilter cubbyFilter;

		/** 要求。 */
		private final HttpServletRequest request;

		/** 応答。 */
		private final HttpServletResponse response;

		/** パスから取得した情報。 */
		private final PathInfo pathInfo;

		/** プラグインのイテレータ。 */
		private final Iterator<Plugin> pluginsIterator;

		/**
		 * インスタンス化します。
		 * 
		 * @param cubbyFilter
		 *            CubbyFilter
		 * @param request
		 *            要求
		 * @param response
		 *            応答
		 * @param pathInfo
		 *            パスから取得した情報
		 */
		public RequestProcessingInvocationImpl(final CubbyFilter cubbyFilter,
				final HttpServletRequest request,
				final HttpServletResponse response, final PathInfo pathInfo) {
			this.cubbyFilter = cubbyFilter;
			this.request = request;
			this.response = response;
			this.pathInfo = pathInfo;

			final PluginRegistry pluginRegistry = PluginRegistry.getInstance();
			this.pluginsIterator = pluginRegistry.getPlugins().iterator();
		}

		/**
		 * {@inheritDoc}
		 */
		public Void proceed() throws Exception {
			if (pluginsIterator.hasNext()) {
				final Plugin plugin = pluginsIterator.next();
				plugin.invokeRequestProcessing(this);
			} else {
				final HttpServletRequest request = getRequest();
				final Map<String, Object[]> parameterMap = cubbyFilter
						.parseRequest(request);
				request.setAttribute(ATTR_PARAMS, parameterMap);
				final Routing routing = pathInfo.dispatch(parameterMap);
				final Command command = new Command() {

					public void execute(final HttpServletRequest request,
							final HttpServletResponse response)
							throws Exception {
						final ActionProcessor actionProcessor = cubbyFilter
								.createActionProcessor();
						final ActionResultWrapper actionResultWrapper = actionProcessor
								.process(request, response, routing);
						actionResultWrapper.execute(request, response);
					}

				};
				ThreadContext.runInContext(request, response, command);
			}
			return null;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletRequest getRequest() {
			return request;
		}

		/**
		 * {@inheritDoc}
		 */
		public HttpServletResponse getResponse() {
			return response;
		}

		/**
		 * {@inheritDoc}
		 */
		public PathInfo getPathInfo() {
			return pathInfo;
		}

	}

	/**
	 * {@link PathResolver} を生成します。
	 * 
	 * @return {@link PathResolver}
	 */
	protected PathResolver createPathResolver() {
		final PathResolverProvider pathResolverProvider = ProviderFactory
				.get(PathResolverProvider.class);
		final PathResolver pathResolver = pathResolverProvider
				.getPathResolver();
		return pathResolver;
	}

	/**
	 * {@link ActionProcessor} を生成します。
	 * 
	 * @return {@link ActionProcessor}
	 */
	protected ActionProcessor createActionProcessor() {
		final ActionProcessor actionProcessor = new ActionProcessorImpl();
		return actionProcessor;
	}

	/**
	 * アクションからフォワードされてきたときに使用する {@link PathInfo} です。
	 * 
	 * @author baba
	 */
	class ForwardFromActionPathInfo implements PathInfo {

		private final Routing routing;

		private final Map<String, String[]> uriParameters = Collections
				.emptyMap();

		public ForwardFromActionPathInfo(final Routing routing) {
			this.routing = routing;
		}

		/**
		 * {@inheritDoc}
		 */
		public Map<String, String[]> getURIParameters() {
			return uriParameters;
		}

		/**
		 * {@inheritDoc}
		 */
		public Routing dispatch(final Map<String, Object[]> parameterMap) {
			return routing;
		}

	}

}
