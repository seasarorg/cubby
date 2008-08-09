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

import static org.seasar.cubby.CubbyConstants.ATTR_ROUTINGS;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;
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
import org.seasar.cubby.routing.Router;
import org.seasar.cubby.routing.Routing;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.container.factory.SingletonS2ContainerFactory;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

/**
 * リクエストされたURLを適切なアクションに振り分けるフィルタ。
 * <p>
 * {@link Router} によって {@link InternalForwardInfo} を抽出し、そこに保持された情報をもとにフォワードします。
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class RequestRoutingFilter implements Filter {

	/** ロガー。 */
	private static final Logger logger = Logger
			.getLogger(RequestRoutingFilter.class);

	/** ルーティングの対象外とするパスの初期パラメータ名。 */
	public static final String IGNORE_PATH_PATTERN = "ignorePathPattern";

	/** ルーティングの対象外とするパスの正規表現パターンのリスト。 */
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
	 * リクエストされた URI に対応する内部フォワード情報が {@link Router} から取得できた場合は、そこに設定されている
	 * {@link InternalForwardInfo#getOnSubmitRoutings()} をリクエストに設定し、
	 * {@link InternalForwardInfo#getInternalForwardPath()} へフォワードします。 フォワード先は
	 * {@link CubbyFilter} が処理することを期待します。 URI
	 * に対応する内部フォワード情報が取得できなかった場合はフィルタチェインで次のフィルタに処理を移譲します。
	 * </p>
	 * 
	 * @param req
	 *            リクエスト
	 * @param res
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
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;

		final S2Container container = SingletonS2ContainerFactory
				.getContainer();
		final Router router = (Router) container.getComponent(Router.class);

		final InternalForwardInfo internalForwardInfo = router.routing(request,
				response, ignorePathPatterns);
		if (internalForwardInfo != null) {
			final String internalForwardPath = internalForwardInfo
					.getInternalForwardPath();
			final Map<String, Routing> onSubmitRoutings = internalForwardInfo
					.getOnSubmitRoutings();
			if (logger.isDebugEnabled()) {
				logger.log("DCUB0001", new Object[] { internalForwardPath,
						onSubmitRoutings });
				logger.log("DCUB0015", new Object[] { onSubmitRoutings });
			}
			request.setAttribute(ATTR_ROUTINGS, onSubmitRoutings);
			final RequestDispatcher requestDispatcher = request
					.getRequestDispatcher(internalForwardPath);
			requestDispatcher.forward(request, response);
		} else {
			chain.doFilter(request, response);
		}
	}

}
