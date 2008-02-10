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

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.StringTokenizer;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.routing.InternalForwardInfo;
import org.seasar.cubby.routing.PathResolver;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * リクエストされたURLを適切なアクションに振り分けるフィルタ。
 * <p>
 * {@link PathResolver} によって {@link InternalForwardInfo}
 * を抽出し、そこに保持された情報をもとにフォワードします。
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class RequestRoutingFilter implements Filter {

	/** ロガー */
	private static final Logger logger = Logger
			.getLogger(RequestRoutingFilter.class);

	/** ルーティングの対象外とするパスの初期パラメータ名 */
	public static final String IGNORE_PATH_PATTERN = "ignorePathPattern";

	/** ルーター */
	private static final Router router = new Router();

	/** ルーティングの対象外とするパスの正規表現パターンのリスト */
	private final List<Pattern> ignorePathPatterns = new ArrayList<Pattern>();

	/**
	 * このフィルタを初期化します。
	 * <p>
	 * 使用可能な初期化パラメータ <table> <thead>
	 * <th> 初期化パラメータ名 </th>
	 * <th> 初期化パラメータの値 </th>
	 * <th> 例 </th>
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
	 * この例では /img と /js 以下のパスをルーティングの対象外にします。 </td>
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
		if (!StringUtil.isEmpty(ignorePathPatternString)) {

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
	 * フィルタリングを行います。
	 * <p>
	 * リクエストされた URI に対応する内部フォワード用のパスが {@link Router} から取得できた場合は、
	 * その内部フォワード用のパスへフォワードします。フォワード先は {@link CubbyFilter} が処理することを期待します。
	 * マッチするパターンがなかった場合はフィルタチェインで次のフィルタに処理を移譲します。
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
	 * @see CubbyFilter
	 */
	public void doFilter(final ServletRequest request,
			final ServletResponse response, final FilterChain chain)
			throws IOException, ServletException {

		final InternalForwardInfo internalForwardInfo = router.routing(
				(HttpServletRequest) request, (HttpServletResponse) response,
				ignorePathPatterns);
		if (internalForwardInfo != null) {
			final String internalForwardPath = internalForwardInfo
					.getInternalForwardPath();
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0001", new Object[] { internalForwardPath });
			}
			final RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(internalForwardPath);
			requestDispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

	/**
	 * ルーター。
	 * 
	 * @author baba
	 * @since 1.0.0
	 */
	public static class Router {

		/** 空の対象外パターンのリスト */
		private static final List<Pattern> EMPTY_IGNORE_PATH_PATTERNS = Collections
				.emptyList();

		/**
		 * 対象外パターンを指定せずにルーティング処理を行い、内部フォワード情報を返します。
		 * 
		 * @param request
		 *            リクエスト
		 * @param response
		 *            レスポンス
		 * @return リクエスト URI に対応する内部フォワード情報、URI に対応する内部フォワード情報がない場合は
		 *         <code>null</code>
		 * @see #routing(HttpServletRequest, HttpServletResponse, List)
		 */
		public InternalForwardInfo routing(final HttpServletRequest request,
				final HttpServletResponse response) {
			return routing(request, response, EMPTY_IGNORE_PATH_PATTERNS);
		}

		/**
		 * リクエストのルーティング処理を行い、内部フォワード情報を返します。
		 * <p>
		 * このメソッドはリクエスト URI とメソッドに対応するフォワード情報を{@link PathResolver} によって決定します。
		 * </p>
		 * 
		 * @param request
		 *            リクエスト
		 * @param response
		 *            レスポンス
		 * @param ignorePathPatterns
		 *            対象外とするパスのパターン
		 * @return リクエストに対応する内部フォワード情報、URI とリクエストメソッドに対応する内部フォワード情報がない場合や URI
		 *         が対象外とするパスのパターンにマッチする場合は <code>null</code>
		 * @see PathResolver#getInternalForwardInfo(String, String)
		 * @see org.seasar.cubby.action.Path
		 * @see org.seasar.cubby.action.Accept
		 */
		public InternalForwardInfo routing(final HttpServletRequest request,
				final HttpServletResponse response,
				List<Pattern> ignorePathPatterns) {
			final String path = CubbyUtils.getPath(request);
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0006", new Object[] { path });
			}

			if (isIgnorePath(path, ignorePathPatterns)) {
				return null;
			}

			final S2Container container = SingletonS2ContainerFactory
					.getContainer();
			final PathResolver pathResolver = (PathResolver) container
					.getComponent(PathResolver.class);

			final InternalForwardInfo internalForwardInfo = pathResolver
					.getInternalForwardInfo(path, request.getMethod());
			return internalForwardInfo;
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
		private boolean isIgnorePath(final String path,
				List<Pattern> ignorePathPatterns) {
			for (final Pattern pattern : ignorePathPatterns) {
				final Matcher matcher = pattern.matcher(path);
				if (matcher.matches()) {
					return true;
				}
			}
			return false;
		}

	}

}
