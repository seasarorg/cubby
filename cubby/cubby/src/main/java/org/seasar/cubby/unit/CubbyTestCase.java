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

import java.io.IOException;
import java.lang.reflect.Field;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.controller.ActionProcessor;
import org.seasar.cubby.filter.RequestRoutingFilter;
import org.seasar.cubby.routing.InternalForwardInfo;
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
 *  // 対象のアクション
 * 	private HelloAction action;
 *  
 *  // 初期化処理
 * 	protected void setUp() throws Exception {
 *      // diconファイルの読み込み
 * 		include(&quot;app.dicon&quot;);
 * 	}
 * 
 * 	public void testIndex() throws Exception {
 *      // アクションの実行
 * 		ActionResult result = processAction(&quot;/hello/&quot;);
 *      // 結果のチェック
 * 		assertPathEquals(Forward.class, &quot;input.jsp&quot;, result);
 * 	}
 * 
 * 	public void testMessage() throws Exception {
 *      // リクエストパラメータのセット
 * 		getRequest().addParameter(&quot;name&quot;, &quot;name1&quot;);
 *      // アクションの実行
 * 		ActionResult result = processAction(&quot;/hello/message&quot;);
 *      // 結果のチェック
 * 		assertPathEquals(Forward.class, &quot;result.jsp&quot;, result);
 *      // 実行後のアクションの状態を確認
 * 		assertEquals(&quot;name1&quot;, action.name);
 * 	}
 * }
 * </pre>
 * 
 * <pre>
 * public class TodoActionTest extends CubbyTestCase {
 *   private TodoAction action;
 *   
 *   protected void setUp() throws Exception {
 *     include("app.dicon");
 *     RunDdlServletRequestListener listener = new RunDdlServletRequestListener();
 *     listener.requestInitialized(null);
 *   }
 *   
 *   @Override
 *   protected void setUpAfterBindFields() throws Throwable {
 *     super.setUpAfterBindFields();
 *     getRequest().addParameter("userId", "test");
 *     getRequest().addParameter("password", "test");
 *     // 後続のテストを実行するためにログインアクションを実行
 *     assertPathEquals(Redirect.class, "/todo/", processAction("/todo/login/process"));
 *   }
 *   
 *   public void testShow() throws Exception {
 *     this.readXlsAllReplaceDb("TodoActionTest_PREPARE.xls");
 *     // CoolURIの場合のテスト
 *     ActionResult result = processAction("/todo/1");
 *     assertPathEquals(Forward.class, "show.jsp", result);
 *     assertEquals(new Integer(1), action.id);
 *     assertEquals("todo1", action.text);
 *     assertEquals("todo1 memo", action.memo);
 *     assertEquals(new Integer(1), action.todoType.getId());
 *     assertEquals("type1", action.todoType.getName());
 *     assertEquals("2008-01-01", action.limitDate);
 *    }
 *  }
 * </pre>
 * </p>
 * 
 * @author agata
 * @author baba
 */
public abstract class CubbyTestCase extends S2TigerTestCase {

	/** ルーティング */
	private final RequestRoutingFilter.Router router = new RequestRoutingFilter.Router();

	/** フィルターチェイン */
	private final MockFilterChain filterChain = new MockFilterChain();

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
		return actionProcessor
				.process(getRequest(), getResponse(), filterChain);
	}

	/**
	 * CubbyFilterで行っているルーティングをエミュレートして、内部フォワードパスをリクエストにセットします。
	 * 
	 * @param orginalPath
	 *            オリジナルパス
	 * @return 内部フォワードパス
	 */
	@SuppressWarnings({ "unchecked", "deprecation" })
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
		Beans.copy(internalForwardRequest, getRequest()).execute();
		Field servletPathField = ClassUtil.getDeclaredField(getRequest().getClass(), "servletPath");
		servletPathField.setAccessible(true);
		try {
			servletPathField.set(getRequest(), internalForwardRequest.getServletPath());
		} catch (Exception ex) {
			throw new RuntimeException(ex);
		}
		
		if (StringUtil.isNotBlank(internalForwardRequest.getQueryString())) {
			getRequest().getParameterMap().putAll(javax.servlet.http.HttpUtils.parseQueryString(getRequest().getQueryString()));
		}
		return internalForwardPath;
	}

	/**
	 * モックのFilterChain。
	 * 
	 * @author agata
	 */
	private static class MockFilterChain implements FilterChain {
		/**
		 * {@inheritDoc}
		 */
		public void doFilter(final ServletRequest request,
				final ServletResponse response) throws IOException,
				ServletException {
		}
	}

}
