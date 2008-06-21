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

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.controller.ActionProcessor;
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
 * 	public void testMessage() throws Exception {
 * 		// リクエストパラメータのセット
 * 		getRequest().addParameter(&quot;name&quot;, &quot;name1&quot;);
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
 * 	private TodoAction action;
 * 
 * 	protected void setUp() throws Exception {
 * 		include(&quot;app.dicon&quot;);
 * 		RunDdlServletRequestListener listener = new RunDdlServletRequestListener();
 * 		listener.requestInitialized(null);
 * 	}
 * 
 * 	&#064;Override
 * 	protected void setUpAfterBindFields() throws Throwable {
 * 		super.setUpAfterBindFields();
 * 		getRequest().addParameter(&quot;userId&quot;, &quot;test&quot;);
 * 		getRequest().addParameter(&quot;password&quot;, &quot;test&quot;);
 * 		// 後続のテストを実行するためにログインアクションを実行
 * 		assertPathEquals(Redirect.class, &quot;/todo/&quot;,
 * 				processAction(&quot;/todo/login/process&quot;));
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
	 * @param orginalPath
	 *            パス
	 * @return アクションメソッドの実行結果。アクションメソッドが見つからなかったり結果がない場合、null
	 * @throws Exception
	 */
	protected ActionResult processAction(final String orginalPath)
			throws Exception {
		routing(orginalPath);
		setupThreadContext();
		return actionProcessor
				.process(getRequest(), getResponse()).getActionResult();
	}

	/**
	 * {@link ThreadContext}にリクエストをセットします。
	 */
	protected void setupThreadContext() {
		ThreadContext.setRequest(getRequest());
	}

	/**
	 * CubbyFilterで行っているルーティングをエミュレートして、内部フォワードパスをリクエストにセットします。
	 * 
	 * @param orginalPath
	 *            オリジナルパス
	 * @return 内部フォワードパス
	 */
	@SuppressWarnings( { "unchecked", "deprecation" })
	protected String routing(final String orginalPath) {
		final MockHttpServletRequest request = this.getServletContext()
				.createRequest(orginalPath);
		final MockHttpServletResponse response = this.getResponse();
		final InternalForwardInfo internalForwardInfo = router.routing(request,
				response);
		if (internalForwardInfo == null) {
			fail(orginalPath + " could not mapping to action");
		}
		final String internalForwardPath = internalForwardInfo
				.getInternalForwardPath();
		final MockHttpServletRequest internalForwardRequest = this
				.getServletContext().createRequest(internalForwardPath);
		getRequest().setAttribute(ATTR_ROUTINGS, internalForwardInfo.getOnSubmitRoutings());
		Beans.copy(internalForwardRequest, getRequest()).execute();
		final Field servletPathField = ClassUtil.getDeclaredField(getRequest()
				.getClass(), "servletPath");
		servletPathField.setAccessible(true);
		try {
			servletPathField.set(getRequest(), internalForwardRequest
					.getServletPath());
		} catch (final Exception ex) {
			throw new RuntimeException(ex);
		}

		if (StringUtil.isNotBlank(internalForwardRequest.getQueryString())) {
			getRequest().getParameterMap().putAll(
					javax.servlet.http.HttpUtils.parseQueryString(getRequest()
							.getQueryString()));
		}
		return internalForwardPath;
	}
}
