/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
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

import org.seasar.cubby.action.MessageInfo;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 文字列の最大長を検証します。
 * <p>
 * {@link String#length()} メソッドで文字列の長さを求めます。文字列のバイト数でないこと、半角全角も 1
 * 文字としてカウントされることに注意してください。
 * </p>
 * <p>
 * <table>
 * <caption>検証エラー時に設定するエラーメッセージ</caption> <tbody>
 * <tr>
 * <th scope="row">デフォルトのキー</th>
 * <td>valid.maxLength</td>
 * </tr>
 * <tr>
 * <th scope="row">置換文字列</th>
 * <td>
 * <ol start="0">
 * <li>フィールド名</li>
 * <li>このオブジェクトに設定された文字列の最大長</li>
 * </ol></td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @author agata
 * @author baba
 * @see String#length()
 */
public class MaxLengthValidator implements ScalarFieldValidator {

	/**
	 * メッセージキー。
	 */
	private final String messageKey;

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
		this.messageKey = messageKey;
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

		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		messageInfo.setArguments(max);
		context.addMessageInfo(messageInfo);
	}
}