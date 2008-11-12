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
package org.seasar.cubby.examples.other.web.hello;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.plugins.s2.unit.CubbyTestCase;

public class HelloActionTest extends CubbyTestCase {
	// 対象のアクション
	private HelloAction action;
	
	// 初期化処理
    protected void setUp() throws Exception {
    	// diconファイルの読み込み
        include("app.dicon");
    }

	public void testIndex() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/hello/");
		// 結果のチェック
		assertPathEquals(Forward.class, "input.jsp", result);
	}

	public void setUpMessage() {
	    // リクエストパラメータのセット
		getRequest().addParameter("name", "name1");
	}

	public void testMessage() throws Exception {
		// アクションの実行
		ActionResult result = processAction("/hello/message");
		// 結果のチェック
		assertPathEquals(Forward.class, "result.jsp", result);
		// 実行後のアクションの状態を確認
		assertEquals("name1", action.name);
	}	
}
