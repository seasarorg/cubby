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
package org.seasar.cubby.unit;

import static org.seasar.cubby.CubbyConstants.ATTR_ROUTINGS;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.controller.ActionResultWrapper;
import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.routing.InternalForwardInfo;
import org.seasar.cubby.routing.Router;
import org.seasar.framework.beans.util.Beans;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.unit.S2TigerTestCase;
import org.seasar.framework.util.ClassUtil;
import org.seasar.framework.util.StringUtil;

/**
 * CubbyのActionクラスの単体テスト用のクラスです。
 * <p>
 * このクラスを継承して、それぞれのActionクラス用の単体テストを作成します。 詳細はCubbyドキュメントの「アクションのテスト」を参照下さい。
 * 
 * <pre>
 * public class HelloActionTest extends CubbyTestCase {
 * 	// 対象のアクション
 * 	private HelloAction action;
 * 
 * 	// 初期化処理
 * 	protected void setUp() throws Exception {
 * 		// diconファイルの読み込み
 * 		include(&quot;app.dicon&quot;);
 * 	}
 * 
 * 	public void testIndex() throws Exception {
 * 		// アクションの実行
 * 		ActionResult result = processAction(&quot;/hello/&quot;);
 * 		// 結果のチェック
 * 		assertPathEquals(Forward.class, &quot;input.jsp&quot;, result);
 * 	}
 * 
 * 	public void setUpMessage() {
 * 		// リクエストパラメータのセット
 * 		getRequest().addParameter(&quot;name&quot;, &quot;name1&quot;);
 * 	}
 * 
 * 	public void testMessage() throws Exception {
 * 		// アクションの実行
 * 		ActionResult result = processAction(&quot;/hello/message&quot;);
 * 		// 結果のチェック
 * 		assertPathEquals(Forward.class, &quot;result.jsp&quot;, result);
 * 		// 実行後のアクションの状態を確認
 * 		assertEquals(&quot;name1&quot;, action.name);
 * 	}
 * }
 * </pre>
 * 
 * <pre>
 * public class TodoActionTest extends CubbyTestCase {
 * 
 * 	private TodoAction action;
 * 
 * 	public void setUpShow() throws Exception {
 * 		emulateLogin();
 * 	}
 * 
 * 	private void emulateLogin() throws Exception {
 * 		User user = new User();
 * 		user.setId(&quot;mock&quot;);
 * 		user.setName(&quot;mock&quot;);
 * 		user.setPassword(&quot;mock&quot;);
 * 		getRequest().getSession().setAttribute(&quot;user&quot;, user);
 * 	}
 * 
 * 	public void testShow() throws Exception {
 * 		this.readXlsAllReplaceDb(&quot;TodoActionTest_PREPARE.xls&quot;);
 * 		// CoolURIの場合のテスト
 * 		ActionResult result = processAction(&quot;/todo/1&quot;);
 * 		assertPathEquals(Forward.class, &quot;show.jsp&quot;, result);
 * 		assertEquals(new Integer(1), action.id);
 * 		assertEquals(&quot;todo1&quot;, action.text);
 * 		assertEquals(&quot;todo1 memo&quot;, action.memo);
 * 		assertEquals(new Integer(1), action.todoType.getId());
 * 		assertEquals(&quot;type1&quot;, action.todoType.getName());
 * 		assertEquals(&quot;2008-01-01&quot;, action.limitDate);
 * 	}
 * }
 * </pre>
 * 
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public abstract class CubbyTestCase extends S2TigerTestCase {

	/** ルーティング */
	private Router router;

	/** ActionProcessor */
	private ActionProcessor actionProcessor;

	/**
	 * ActionResultの型とパスをチェックします。
	 * 
	 * @param resultClass
	 *            ActionResultの型
	 * @param expectedPath
	 *            期待されるパス
	 * @param actualResult
	 *            チェックするActionResult
	 */
	public static void assertPathEquals(
			final Class<? extends ActionResult> resultClass,
			final String expectedPath, final ActionResult actualResult) {
		assertEquals("ActionResultの型をチェック", resultClass, actualResult
				.getClass());
		if (actualResult instanceof Forward) {
			assertEquals("パスのチェック", expectedPath, ((Forward) actualResult)
					.getPath());
		} else if (actualResult instanceof Redirect) {
			assertEquals("パスのチェック", expectedPath, ((Redirect) actualResult)
					.getPath());
		}
	}

	/**
	 * アクションメソッドを実行します。
	 * 
	 * @param originalPath
	 *            オリジナルパス
	 * @return アクションメソッドの実行結果。アクションメソッドが見つからなかったり結果がない場合は <code>null</code>
	 * @throws Exception
	 *             アクションメソッドの実行時に例外が発生した場合
	 */
	protected ActionResult processAction(final String originalPath)
			throws Exception {
		final MockHttpServletRequest request = getRequest();
		setServletPath(request, originalPath);
		final MockHttpServletResponse response = getResponse();
		routing(request, response);
		setupThreadContext();
		final ActionResultWrapper actionResultWrapper = actionProcessor
				.process(request, response);
		if (actionResultWrapper == null) {
			return null;
		}
		return actionResultWrapper.getActionResult();
	}

	/**
	 * CubbyFilterで行っているルーティングをエミュレートして、内部フォワードパスをリクエストにセットします。
	 * 
	 * @param request
	 *            リクエスト
	 * @param response
	 *            レスポンス
	 * @return 内部フォワードパス
	 * @since 1.0.5
	 */
	protected String routing(final MockHttpServletRequest request,
			final MockHttpServletResponse response) {
		final InternalForwardInfo internalForwardInfo = router.routing(request,
				response);
		if (internalForwardInfo == null) {
			fail(request.getServletPath() + " could not mapping to action");
		}
		final String internalForwardPath = internalForwardInfo
				.getInternalForwardPath();
		final MockHttpServletRequest internalForwardRequest = this
				.getServletContext().createRequest(internalForwardPath);
		request.setAttribute(ATTR_ROUTINGS, internalForwardInfo
				.getOnSubmitRoutings());
		request.setAttribute("javax.servlet.forward.request_uri", request
				.getRequestURI());
		request.setAttribute("javax.servlet.forward.context_path", request
				.getContextPath());
		request.setAttribute("javax.servlet.forward.servlet_path", request
				.getServletPath());
		request.setAttribute("javax.servlet.forward.path_info", request
				.getPathInfo());
		request.setAttribute("javax.servlet.forward.query_string", request
				.getQueryString());
		final String servletPath = internalForwardRequest.getServletPath();
		setServletPath(request, servletPath);
		request.setQueryString(internalForwardRequest.getQueryString());
		if (StringUtil.isNotBlank(internalForwardRequest.getQueryString())) {
			final Map<String, List<String>> pathParameters = parseQueryString(internalForwardRequest
					.getQueryString());
			for (final Entry<String, List<String>> entry : pathParameters
					.entrySet()) {
				final String name = entry.getKey();
				for (final String value : entry.getValue()) {
					request.addParameter(name, value);
				}
			}
		}
		return internalForwardPath;
	}

	/**
	 * 指定されたモックリクエストのサーブレットパスを設定します。
	 * 
	 * @param request
	 *            リクエスト
	 * @param servletPath
	 *            サーブレットパス
	 */
	private static void setServletPath(final MockHttpServletRequest request,
			final String servletPath) {
		final Field servletPathField = ClassUtil.getDeclaredField(request
				.getClass(), "servletPath");
		servletPathField.setAccessible(true);
		try {
			servletPathField.set(request, servletPath);
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
	}

	/**
	 * {@link ThreadContext}にリクエストをセットします。
	 */
	protected void setupThreadContext() {
		ThreadContext.setRequest(getRequest());
	}

	/**
	 * クエリ文字列をパースして {@link Map} へ変換します。
	 * 
	 * @param queryString
	 *            クエリ文字列
	 * @return クエリ文字列をパースした {@link Map}
	 */
	private Map<String, List<String>> parseQueryString(final String queryString) {
		final Map<String, List<String>> params = new HashMap<String, List<String>>();
		final String[] tokens = queryString.split("&");
		for (final String token : tokens) {
			final String[] param = token.split("=");
			final String name = param[0];
			final String value = param[1];
			final List<String> values;
			if (params.containsKey(name)) {
				values = params.get(name);
			} else {
				values = new ArrayList<String>();
				params.put(name, values);
			}
			values.add(value);
		}
		return params;
	}

	/**
	 * CubbyFilterで行っているルーティングをエミュレートして、内部フォワードパスをリクエストにセットします。
	 * 
	 * @param orginalPath
	 *            オリジナルパス
	 * @return 内部フォワードパス
	 */
	@Deprecated
	@SuppressWarnings( { "unchecked" })
	protected String routing(final String orginalPath) {
		final MockHttpServletRequest originalRequest = this.getServletContext()
				.createRequest(orginalPath);
		final MockHttpServletResponse response = this.getResponse();
		final InternalForwardInfo internalForwardInfo = router.routing(
				originalRequest, response);
		if (internalForwardInfo == null) {
			fail(orginalPath + " could not mapping to action");
		}
		final String internalForwardPath = internalForwardInfo
				.getInternalForwardPath();
		final MockHttpServletRequest internalForwardRequest = this
				.getServletContext().createRequest(internalForwardPath);
		final MockHttpServletRequest request = getRequest();
		request.setAttribute(ATTR_ROUTINGS, internalForwardInfo
				.getOnSubmitRoutings());
		Beans.copy(internalForwardRequest, request).execute();
		final Field servletPathField = ClassUtil.getDeclaredField(request
				.getClass(), "servletPath");
		servletPathField.setAccessible(true);
		try {
			servletPathField.set(request, internalForwardRequest
					.getServletPath());
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}
		request.setQueryString(internalForwardRequest.getQueryString());
		if (StringUtil.isNotBlank(internalForwardRequest.getQueryString())) {
			request.getParameterMap().putAll(
					javax.servlet.http.HttpUtils.parseQueryString(request
							.getQueryString()));
		}
		return internalForwardPath;
	}

}
