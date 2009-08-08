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
package org.seasar.cubby.plugins.s2.unit;

import java.lang.reflect.Field;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.unit.CubbyAssert;
import org.seasar.cubby.unit.CubbyRunner;
import org.seasar.framework.mock.servlet.MockHttpServletRequest;
import org.seasar.framework.mock.servlet.MockHttpServletResponse;
import org.seasar.framework.unit.S2TigerTestCase;

/**
 * CubbyのActionクラスの単体テスト用のクラスです。
 * <p>
 * このクラスを継承して、それぞれのActionクラス用の単体テストを作成します。詳細はドキュメントの「アクションのテスト」を参照下さい。
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
 */
public abstract class CubbyTestCase extends S2TigerTestCase {

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
		CubbyAssert.assertPathEquals(resultClass, expectedPath, actualResult);
	}

	/**
	 * ActionResultの型とパスをチェックします。
	 * 
	 * @param resultClass
	 *            ActionResultの型
	 * @param expectedPath
	 *            期待されるパス
	 * @param actualResult
	 *            チェックするActionResult
	 * @param characterEncoding
	 *            URI のエンコーディング
	 */
	public static void assertPathEquals(
			final Class<? extends ActionResult> resultClass,
			final String expectedPath, final ActionResult actualResult,
			final String characterEncoding) {
		CubbyAssert.assertPathEquals(resultClass, expectedPath, actualResult,
				characterEncoding);
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
		return processAction(request, response);
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
		try {
			final Field servletPathField = request.getClass().getDeclaredField(
					"servletPath");
			servletPathField.setAccessible(true);
			servletPathField.set(request, servletPath);
		} catch (final Exception ex) {
			throw new IllegalStateException(ex);
		}
	}

	/**
	 * アクションメソッドを実行します。
	 * 
	 * @param request
	 *            要求
	 * @param response
	 *            応答
	 * @return アクションメソッドの実行結果。アクションメソッドが見つからなかったり結果がない場合は <code>null</code>
	 * @throws Exception
	 *             アクションメソッドの実行時に例外が発生した場合
	 */
	protected ActionResult processAction(final HttpServletRequest request,
			final HttpServletResponse response) throws Exception {
		return CubbyRunner.processAction(request, response);
	}

}
