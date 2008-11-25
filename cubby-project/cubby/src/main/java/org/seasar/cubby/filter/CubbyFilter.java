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
import org.seasar.cubby.internal.routing.PathInfo;
import org.seasar.cubby.internal.routing.PathProcessor;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.impl.PathProcessorImpl;
import org.seasar.cubby.internal.util.StringUtils;

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
	 * {@link PathInfo#getOnSubmitRoutings()} をリクエストに設定し、 {@link CubbyFilter}
	 * が処理することを期待します。 URI に対応する内部フォワード情報が取得できなかった場合はフィルタチェインで次のフィルタに処理を委譲します。
	 * </p>
	 * <p>
	 * リクエストの処理を {@link PathProcessor} に委譲します。
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
	 * @see PathProcessor#process(HttpServletRequest, HttpServletResponse)
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		PathProcessor delegate = new PathProcessorImpl(
				HttpServletRequest.class.cast(req), HttpServletResponse.class.cast(res),
				ignorePathPatterns);
		if (delegate.hasPathInfo()) {
			delegate.process();
		} else {
			chain.doFilter(req, res);
		}
	}

}
