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
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.unit.S2TigerTestCase;

/**
 * CubbyのActionクラスの単体テスト用のクラスです。
 * <p>
 * このクラスを継承して、それぞれのActionクラス用の単体テストを作成します。 詳細はCubbyドキュメントの「アクションのテスト」を参照下さい。
 * 
 * <pre>
 * public class HelloActionTest extends CubbyTestCase {
 * 	private HelloAction action;
 * 
 * 	protected void setUp() throws Exception {
 * 		include(&quot;app.dicon&quot;);
 * 	}
 * 
 * 	public void testIndex() throws Exception {
 * 		ActionResult result = processAction(&quot;/hello/&quot;);
 * 		assertPathEquals(Forward.class, &quot;input.jsp&quot;, result);
 * 	}
 * 
 * 	public void testMessage() throws Exception {
 * 		getRequest().addParameter(&quot;name&quot;, &quot;name1&quot;);
 * 		ActionResult result = processAction(&quot;/hello/message&quot;);
 * 		assertPathEquals(Forward.class, &quot;result.jsp&quot;, result);
 * 		assertEquals(&quot;name1&quot;, action.name);
 * 	}
 * }
 * </pre>
 * 
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
		this.setRequest(internalForwardRequest);
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
