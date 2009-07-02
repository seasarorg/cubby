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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
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

import org.seasar.cubby.internal.util.StringUtils;

/**
 * 適用されたリクエストに対して、異常系の HTTP ステータスコードを返す {@link Filter} です。
 * 
 * @author baba
 * @since 1.1.0
 */
public class SendErrorFilter implements Filter {

	/** レスポンスの HTTP ステータスコードの初期パラメータ名。 */
	public static final String STATUS_CODE = "statusCode";

	/** 対象外とするパスの初期パラメータ名。 */
	public static final String IGNORE_PATH_PATTERN = "ignorePathPattern";

	/** レスポンスの HTTP ステータスコード (デフォルトは 403 Forbidden)。 */
	private int statusCode = HttpServletResponse.SC_FORBIDDEN;

	/** 対象外とするパスの正規表現パターンのリスト。 */
	private final List<Pattern> ignorePathPatterns = new ArrayList<Pattern>();

	/**
	 * このフィルタを初期化します。
	 * <p>
	 * <table>
	 * <caption>使用可能な初期化パラメータ</caption>
	 * <thead>
	 * <th>初期化パラメータ名</th>
	 * <th>初期化パラメータの値</th>
	 * <th>例</th>
	 * </thead>
	 * <thead>
	 * <tr>
	 * <td>{@link #STATUS_CODE}</td>
	 * <td>レスポンスの HTTP ステータスコードを指定します。指定しなかった場合は
	 * {@link HttpServletResponse#SC_FORBIDDEN} を返します。</td>
	 * <td></td>
	 * <tr>
	 * <td>{@link #IGNORE_PATH_PATTERN}</td>
	 * <td>対象外とするパスの正規表現をカンマ区切りで指定します。 filter-mapping の url-pattern
	 * で指定する、このフィルタを適用する URL のうち、適用を除外したいパスを指定してください。</td>
	 * <td>
	 * 
	 * <pre>
	 * &lt;filter&gt;
	 *   &lt;filter-name&gt;sendErrorFilter&lt;/filter-name&gt;
	 *   &lt;filter-class&gt;org.seasar.cubby.filter.SendErrorFilter&lt;/filter-class&gt;
	 *   &lt;init-param&gt;
	 *     &lt;param-name&gt;statusCode;&lt;/param-name&gt;
	 *     &lt;param-value&gt;404&lt;param-name&gt;
	 *   &lt;/init-param&gt;
	 *   &lt;init-param&gt;
	 *     &lt;param-name&gt;ignorePathPattern&lt;/param-name&gt;
	 *     &lt;param-value&gt;/index.jsp&lt;param-name&gt;
	 *   &lt;/init-param&gt;
	 * &lt;/filter&gt;
	 * 
	 * &lt;filter-mapping&gt;
	 *   &lt;filter-name&gt;sendErrorFilter&lt;filter-name&gt;
	 *   &lt;url-pattern&gt;*.jsp&lt;url-pattern&gt;
	 *   &lt;dispatcher&gt;REQUEST&lt;/dispatcher&gt;
	 * &lt;/filter-mapping&gt;
	 * </pre>
	 * 
	 * この例では、 /index.jsp を除く *.jsp にリクエストがあった場合に HTTP ステータスコード 404 (Not Found)
	 * を返します。</td>
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
		final String statusCodeString = config.getInitParameter(STATUS_CODE);
		if (statusCodeString != null) {
			statusCode = Integer.parseInt(statusCodeString);
		}
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
	 * {@link HttpServletResponse#sendError(int)} によって、異常系の HTTP ステータスコードを返します。
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

		if (isIgnore(request)) {
			chain.doFilter(request, response);
		} else {
			response.sendError(statusCode);
		}
	}

	/**
	 * 指定されたリクエストがこのフィルタの対象外であるかを示します。
	 * 
	 * @param request
	 *            リクエスト
	 * @return 指定されたリクエストがこのフィルタの対象外である場合は <code>true</code>、そうでない場合は
	 *         <code>false</code>
	 */
	private boolean isIgnore(final HttpServletRequest request) {
		final String servletPath = request.getServletPath();
		for (final Pattern ignorePattern : ignorePathPatterns) {
			final Matcher matcher = ignorePattern.matcher(servletPath);
			if (matcher.matches()) {
				return true;
			}
		}
		return false;
	}

}
