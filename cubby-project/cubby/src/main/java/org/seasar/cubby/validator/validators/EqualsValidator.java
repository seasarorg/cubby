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

import org.seasar.cubby.validator.MessageInfo;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 指定された文字列と等しいかどうかを検証します。
 * <p>
 * <table>
 * <caption>検証エラー時に設定するエラーメッセージ</caption> <tbody>
 * <tr>
 * <th scope="row">デフォルトのキー</th>
 * <td>valid.equals</td>
 * </tr>
 * <tr>
 * <th scope="row">置換文字列</th>
 * <td>
 * <ol start="0">
 * <li>フィールド名</li>
 * <li>このオブジェクトに設定された比較対象の文字列</li>
 * </ol></td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @see String#equals(Object)
 * @author agata
 * @author baba
 */
public class EqualsValidator implements ScalarFieldValidator {

	/**
	 * メッセージキー。
	 */
	private final String messageKey;

	/**
	 * 対象文字列
	 */
	private final String value;

	/**
	 * コンストラクタ
	 * 
	 * @param value
	 *            比較対象の文字列
	 */
	public EqualsValidator(final String value) {
		this(value, "valid.equals");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param value
	 * @param messageKey
	 */
	public EqualsValidator(final String value, final String messageKey) {
		this.value = value;
		this.messageKey = messageKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (this.value.equals(value)) {
			return;
		}

		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		messageInfo.setArguments(new Object[] { this.value });
		context.addMessageInfo(messageInfo);
	}

}
