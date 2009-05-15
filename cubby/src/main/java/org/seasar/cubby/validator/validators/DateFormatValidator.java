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

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.action.MessageInfo;
import org.seasar.cubby.controller.FormatPattern;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 日付パターンに適合するかを検証します。
 * <p>
 * 日付パターンを指定しない場合、「app-cubby.dicon」で指定した日付パターンが使用されます。
 * </p>
 * <p>
 * <table>
 * <caption>検証エラー時に設定するエラーメッセージ</caption> <tbody>
 * <tr>
 * <th scope="row">デフォルトのキー</th>
 * <td>valid.dateFormat</td>
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
 * @see SimpleDateFormat
 */
public class DateFormatValidator implements ScalarFieldValidator {

	/**
	 * メッセージキー。
	 */
	private final String messageKey;

	/**
	 * 日付パターン
	 */
	private final String pattern;

	/**
	 * 日付パターンを指定しないコンストラクタ
	 */
	public DateFormatValidator() {
		this(null);
	}

	/**
	 * 日付パターンを指定するコンストラクタ
	 * 
	 * @param pattern
	 *            日付パターン（例："yyyy/MM/dd"）
	 */
	public DateFormatValidator(final String pattern) {
		this(pattern, "valid.dateFormat");
	}

	/**
	 * 日付パターンとエラーメッセージキーを指定したコンストラクタ
	 * 
	 * @param pattern
	 *            日付パターン（例："yyyy/MM/dd"）
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public DateFormatValidator(final String pattern, final String messageKey) {
		this.pattern = pattern;
		this.messageKey = messageKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value == null) {
			return;
		}
		if (value instanceof String) {
			final String stringValue = (String) value;
			if (StringUtils.isEmpty((String) value)) {
				return;
			}
			try {
				final DateFormat dateFormat = createDateFormat(context, value);
				final ParsePosition parsePosition = new ParsePosition(0);
				final Date date = dateFormat.parse(stringValue, parsePosition);
				if (date != null
						&& parsePosition.getIndex() == stringValue.length()) {
					return;
				}
			} catch (final Exception e) {
			}
		}

		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(this.messageKey);
		context.addMessageInfo(messageInfo);
	}

	/**
	 * {@link DateFormat} のインスタンスを生成します。
	 * 
	 * @param context
	 *            コンテキスト
	 * @param value
	 *            値
	 * @return {@link DateFormat} のインスタンス
	 */
	private DateFormat createDateFormat(final ValidationContext context,
			final Object value) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat();
		final String pattern;
		if (StringUtils.isEmpty(this.pattern)) {
			final Container container = ProviderFactory.get(
					ContainerProvider.class).getContainer();
			final FormatPattern formatPattern = container
					.lookup(FormatPattern.class);
			if (formatPattern == null) {
				throw new IllegalStateException(format("ECUB0301", this, value));
			}
			pattern = formatPattern.getDatePattern();
		} else {
			pattern = this.pattern;
		}
		dateFormat.applyPattern(pattern);
		dateFormat.setLenient(false);
		return dateFormat;
	}

}