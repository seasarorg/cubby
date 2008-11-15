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

import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 必須検証します。
 * <p>
 * 文字列の長さが0の場合、検証エラーとなります。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.required
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class RequiredValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * コンストラクタ
	 */
	public RequiredValidator() {
		this("valid.required");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RequiredValidator(final String messageKey) {
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (!StringUtils.isEmpty(str)) {
				return;
			}
		} else if (value != null) {
			return;
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo());
	}

}
