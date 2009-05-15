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

import java.util.regex.Pattern;

import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.validator.MessageInfo;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 数値であるかを検証します。
 * <p>
 * <table>
 * <caption>検証エラー時に設定するエラーメッセージ</caption> <tbody>
 * <tr>
 * <th scope="row">デフォルトのキー</th>
 * <td>valid.number</td>
 * </tr>
 * <tr>
 * <th scope="row">置換文字列</th>
 * <td>
 * <ol start="0">
 * <li>フィールド名</li>
 * </ol></td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class NumberValidator implements ScalarFieldValidator {

	private static final Pattern NUMBER_PATTERN = Pattern
			.compile("^[-+]?[0-9]+[.]?[0-9]*$");

	/**
	 * メッセージキー。
	 */
	private final String messageKey;

	/**
	 * コンストラクタ
	 */
	public NumberValidator() {
		this("valid.number");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public NumberValidator(final String messageKey) {
		this.messageKey = messageKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (StringUtils.isEmpty(str)) {
				return;
			}
			if (NUMBER_PATTERN.matcher(str).find()) {
				return;
			}
		} else if (value == null) {
			return;
		}

		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		context.addMessageInfo(messageInfo);
	}

}
