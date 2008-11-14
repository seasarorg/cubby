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

import static org.seasar.cubby.util.LogMessages.format;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.util.ServiceFactory;
import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 日付に対する検証を行います。
 * <p>
 * 日付パターンを指定しない場合、「app-cubby.dicon」で指定した日付パターンが使用されます。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.dateFormat
 * </p>
 * 
 * @author agata
 * @author baba
 * @see SimpleDateFormat
 * @since 1.0.0
 */
public class DateFormatValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

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
		this.messageHelper = new MessageHelper(messageKey);
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

		context.addMessageInfo(this.messageHelper.createMessageInfo());
	}

	private DateFormat createDateFormat(final ValidationContext context,
			final Object value) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat();
		final String pattern;
		if (StringUtils.isEmpty(this.pattern)) {
			// final CubbyConfiguration configuration = ThreadContext
			// .getConfiguration();
			// final FormatPattern formatPattern = configuration
			// .getFormatPattern();
			final FormatPattern formatPattern = ServiceFactory
					.getProvider(FormatPattern.class);
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