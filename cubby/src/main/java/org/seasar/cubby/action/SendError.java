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
package org.seasar.cubby.action;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.controller.ActionContext;

/**
 * エラーのレスポンスを返すことを示す {@link ActionResult} です。
 * <p>
 * アクションメソッドの戻り値としてこのインスタンスを指定すると、エラーのレスポンスを返します。
 * </p>
 * 
 * @author baba
 * @since 1.1.0
 */
public class SendError implements ActionResult {

	/** ステータスコード。 */
	private final int statusCode;

	/** メッセージ。 */
	private final String message;

	/**
	 * インスタンスを生成します。
	 * 
	 * @param statusCode
	 *            ステータスコード
	 * @see HttpServletResponse#sendError(int)
	 */
	public SendError(final int statusCode) {
		this(statusCode, null);
	}

	/**
	 * インスタンスを生成します。
	 * 
	 * @param statusCode
	 *            ステータスコード
	 * @param message
	 *            メッセージ
	 * @see HttpServletResponse#sendError(int, String)
	 */
	public SendError(final int statusCode, String message) {
		this.statusCode = statusCode;
		this.message = message;
	}

	/**
	 * {@inheritDoc}
	 */
	public void execute(final ActionContext actionContext,
			final HttpServletRequest request, final HttpServletResponse response)
			throws Exception {
		if (message == null) {
			response.sendError(statusCode);
		} else {
			response.sendError(statusCode, message);
		}
	}

}
