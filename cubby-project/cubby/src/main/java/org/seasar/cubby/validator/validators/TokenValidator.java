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
package org.seasar.cubby.validator.validators;

import static org.seasar.cubby.internal.util.LogMessages.format;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.TokenHelper;
import org.seasar.cubby.tags.TokenTag;
import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 2重サブミットの検証をします。
 * <p>
 * ポストする画面で{@link TokenTag}を使用して、Actionクラスで TokenValidatorを使用することで、
 * ２重サブミットを防止します。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.token
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class TokenValidator implements ArrayFieldValidator {

	private final MessageHelper messageHelper;

	/**
	 * コンストラクタ
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
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object[] values) {
		if (values != null && values.length != 1) {
			context.addMessageInfo(this.messageHelper.createMessageInfo());
		} else {
			final String token = (String) values[0];
			final HttpServletRequest request = ThreadContext.getRequest();
			if (request == null) {
				throw new IllegalStateException(format("ECUB0401"));
			}
			final HttpSession session = request.getSession();
			if (!TokenHelper.validateToken(session, token)) {
				context.addMessageInfo(this.messageHelper.createMessageInfo());
			}
		}
	}
}
