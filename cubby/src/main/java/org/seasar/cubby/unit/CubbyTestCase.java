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
import org.seasar.framework.unit.S2TigerTestCase;
import org.seasar.framework.util.FieldUtil;

/**
 * CubbyのActionクラスの単体テスト用のクラスです。
 * <p>
 * このクラスを継承して、それぞれのActionクラス用の単体テストを作成します。
 * 親クラスに
 * <pre>
 * public class HelloActionTest extends CubbyTestCase<HelloAction> {
 *   private HelloAction action;
 *   protected void setUp() throws Exception {
 *     include("app.dicon");
 *   }
 *   public void testIndex() throws Exception {
 *     ActionResult result = processAction("/hello/");
 *     assertPathEquals(Forward.class, "input.jsp", result);
 *   }
 *   public void testMessage() throws Exception {
 *     getRequest().addParameter("name", "name1");
 *     ActionResult result = processAction("/hello/message");
 *     assertPathEquals(Forward.class, "result.jsp", result);
 *     assertEquals("name1", action.name);
 *   }	
 * }
 * </pre>
 * </p>
 * @author agata
 */
public class CubbyTestCase extends S2TigerTestCase {
	
	/** ルーティング */
	private RequestRoutingFilter.Router router = new RequestRoutingFilter.Router();

	private MockFilterChain filterChain = new MockFilterChain();
	
	/** ActionProcessor */
	private ActionProcessor actionProcessor;
	
	/**
	 * ActionResultの型とパスをチェックします。
	 * @param <T>
	 * @param resultClass ActionResultの型
	 * @param expectedPath 期待されるパス
	 * @param actualResult チェックするActionResult
	 */
	public static void assertPathEquals(Class<? extends ActionResult> resultClass, String expectedPath,
			ActionResult actualResult) {
		assertEquals("ActionResultの型をチェック", resultClass, actualResult.getClass());
		if (actualResult instanceof Forward) {
			assertEquals("パスのチェック", expectedPath, ((Forward)actualResult).getPath());
		} else if (actualResult instanceof Redirect) {
			assertEquals("パスのチェック", expectedPath, ((Redirect)actualResult).getPath());
		}
	}
	
	/**
	 * アクションメソッドを実行します。
	 * @param string 
	 * @param string 
	 * @return アクションメソッドの実行結果。アクションメソッドが見つからなかったり結果がない場合、null
	 * @throws Exception 
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	protected ActionResult processAction(String orginalPath) throws Exception {
		routing(orginalPath);
		return actionProcessor.process(getRequest(), getResponse(), filterChain);
	}
	
	/**
	 * CubbyFilterで行っているルーティングをエミュレートして、内部フォワードパスをリクエストにセットします。
	 * @param orginalPath オリジナルパス
	 * @return 内部フォワードパス
	 * @throws NoSuchFieldException
	 */
	protected String routing(String orginalPath) throws NoSuchFieldException {
		Field field = getRequest().getClass().getDeclaredField("servletPath");
		field.setAccessible(true);
		FieldUtil.set(field, getRequest(), orginalPath);
		String forwardPath = router.routing(getRequest(), getResponse());
		FieldUtil.set(field, getRequest(), forwardPath);
		return forwardPath;
	}

	/**
	 * モックのFilterChain
	 * @author agata
	 */
	private static class MockFilterChain implements FilterChain {
		public void doFilter(ServletRequest request,
				ServletResponse response) throws IOException,
				ServletException {
		}
	}

}
