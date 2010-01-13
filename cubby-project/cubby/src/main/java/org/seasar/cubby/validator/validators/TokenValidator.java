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
package org.seasar.cubby.validator.validators;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.cubby.action.MessageInfo;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.TokenHelper;
import org.seasar.cubby.tags.TokenTag;
import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 2 重サブミットの検証をします。
 * <p>
 * ポストする画面で {@link TokenTag} を使用し、アクションクラスでこのクラスで検証することで 2 重サブミットを防止します。
 * </p>
 * <p>
 * <table>
 * <caption>検証エラー時に設定するエラーメッセージ</caption> <tbody>
 * <tr>
 * <th scope="row">デフォルトのキー</th>
 * <td>valid.token</td>
 * </tr>
 * <tr>
 * <th scope="row">置換文字列</th>
 * <td>
 * <ol start="0">
 * <li>フィールド名</li>
 * </ol>
 * </td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class TokenValidator implements ArrayFieldValidator {

	/**
	 * メッセージキー。
	 */
	private final String messageKey;

	/**
	 * コンストラクタ。
	 */
	public TokenValidator() {
		this("valid.token");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public TokenValidator(final String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object[] values) {
		if (values == null) {
			return;
		}

		if (values.length == 1) {
			final String token = (String) values[0];
			final ThreadContext currentContext = ThreadContext
					.getCurrentContext();
			final HttpServletRequest request = currentContext.getRequest();
			final HttpSession session = request.getSession(false);
			if (session == null) {
				return;
			}
			if (TokenHelper.validateToken(session, token)) {
				return;
			}
		}

		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		context.addMessageInfo(messageInfo);
	}
}
