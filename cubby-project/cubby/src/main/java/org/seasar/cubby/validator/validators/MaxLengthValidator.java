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

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 最大文字数を検証します。
 * <p>
 * String#length()メソッドで文字列の長さを求めます。文字列のバイト数でないこと、半角全角も1文字としてカウントされることに注意してください。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.maxLength
 * </p>
 * 
 * @author agata
 * @author baba
 * @see String#length()
 * @since 1.0.0
 */
public class MaxLengthValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 最大文字数
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * 
	 * @param max
	 *            最大文字数
	 */
	public MaxLengthValidator(final int max) {
		this(max, "valid.maxLength");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param max
	 *            最大文字数
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public MaxLengthValidator(final int max, final String messageKey) {
		this.max = max;
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (StringUtils.isEmpty((String) value)) {
				return;
			}
			if (str.length() <= max) {
				return;
			}
		} else if (value == null) {
			return;
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo(max));
	}
}