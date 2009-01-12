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
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.internal.controller.RequestProcessor;
import org.seasar.cubby.internal.controller.RequestProcessor.CommandFactory;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.internal.controller.impl.ActionProcessorImpl;
import org.seasar.cubby.internal.controller.impl.RequestProcessorImpl;
import org.seasar.cubby.internal.routing.Router;
import org.seasar.cubby.internal.routing.impl.RouterImpl;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.routing.PathInfo;
import org.seasar.cubby.routing.Routing;

/**
 * Cubby 用のフィルター。
 * <p>
 * 要求を解析し、対応するアクションが登録されている場合はアクションを実行します。
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

	/** 要求を処理します。 */
	private final RequestProcessor requestProcessor = new RequestProcessorImpl();

	/** コマンドのファクトリ。 */
	private final CommandFactory<Void> commandFactory = new CubbyFilterCommandFactory();

	/** アクションを処理します。 */
	private final ActionProcessor actionProcessor = new ActionProcessorImpl();

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
	 * 要求された URI に対応する情報が {@link Router} から取得できた場合は、 {@link RequestProcessor}
	 * によってリクエストを処理します。URI に対応する情報が取得できなかった場合はフィルタチェインで次のフィルタに処理を委譲します。
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
	 * @see RequestProcessor#process(HttpServletRequest, HttpServletResponse,
	 *      PathInfo, CommandFactory)
	 */
	public void doFilter(final ServletRequest req, final ServletResponse res,
			final FilterChain chain) throws IOException, ServletException {
		final HttpServletRequest request = (HttpServletRequest) req;
		final HttpServletResponse response = (HttpServletResponse) res;
		final PathInfo pathInfo = router.routing(request, response,
				ignorePathPatterns);
		if (pathInfo != null) {
			try {
				requestProcessor.process(request, response, pathInfo,
						commandFactory);
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
	 * アクションを実行するコマンドです。
	 * 
	 * @author baba
	 */
	class CubbyFilterCommandFactory implements CommandFactory<Void> {

		/**
		 * {@inheritDoc}
		 * <p>
		 * {@link ActionProcessor} でアクションを実行後、その結果を実行します。
		 * {@link ActionResultWrapper#execute(HttpServletRequest, HttpServletResponse)}
		 * </p>
		 */
		public Command<Void> create(final Routing routing) {
			return new Command<Void>() {

				/**
				 * {@inheritDoc}
				 */
				public Void execute(final HttpServletRequest request,
						final HttpServletResponse response) throws Exception {
					final ActionResultWrapper actionResultWrapper = actionProcessor
							.process(request, response, routing);
					actionResultWrapper.execute(request, response);
					return null;
				}

			};
		}

	}

}
