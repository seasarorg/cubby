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
package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * アクションメソッドから直接レスポンスを返すことを示す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定すると、後続の処理はレスポンスに何も出力しません。
 * アクションメソッド中でレスポンスを出力してください。
 * </p>
 * 
 * @author baba
 * @since 1.0.0
 */
public class Direct implements ActionResult {

	/**
	 * インスタンスを生成します。
	 */
	public Direct() {
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final ActionContext actionContext,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
	}

}
