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
import java.util.List;
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

import org.seasar.cubby.internal.controller.ActionProcessor;
import org.seasar.cubby.internal.routing.PathProcessor;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.impl.PathProcessorImpl;
import org.seasar.cubby.internal.routing.impl.RouterImpl;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.routing.PathInfo;

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

	/** ルーティングの対象外とするパスの初期パラメータ名。 */
	public static final String IGNORE_PATH_PATTERN = "ignorePathPattern";

	/** ルーティングの対象外とするパスの正規表現パターンのリスト。 */
	private final List<Pattern> ignorePathPatterns = new ArrayList<Pattern>();

	/** ルーター。 */
	private final Router router = new RouterImpl();

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

	// TODO コメント修正
	/**
	 * フィルター処理。
	 * <p>
	 * リクエストされた URI に対応する内部フォワード情報が {@link Router} から取得できた場合は、そこに設定されている
	 * {@link PathInfo#getOnSubmitRoutings()} をリクエストに設定し、 {@link CubbyFilter}
	 * が処理することを期待します。 URI に対応する内部フォワード情報が取得できなかった場合はフィルタチェインで次のフィルタに処理を委譲します。
	 * </p>
	 * <p>
	 * リクエストの処理を {@link PathProcessor} に委譲します。
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
	 * @see Router#routing(HttpServletRequest, HttpServletResponse, List)
	 * @see PathProcessor#process()
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final PathInfo pathInfo = router.routing(request, response,
				ignorePathPatterns);
		if (pathInfo != null) {
			final PathProcessor delegate = new PathProcessorImpl();
			delegate.process(request, response, pathInfo);
		} else {
			chain.doFilter(request, response);
		}
	}

}
