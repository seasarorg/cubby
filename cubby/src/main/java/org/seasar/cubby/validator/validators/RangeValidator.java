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

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 数値の範囲を指定して検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.range
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class RangeValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 最小値
	 */
	private final long min;

	/**
	 * 最大値
	 */
	private final long max;

	/**
	 * コンストラクタ
	 * 
	 * @param min
	 *            最小値
	 * @param max
	 *            最大値
	 */
	public RangeValidator(final long min, final long max) {
		this(min, max, "valid.range");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param min
	 *            最小値
	 * @param max
	 *            最大値
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RangeValidator(final long min, final long max,
			final String messageKey) {
		this.min = min;
		this.max = max;
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (StringUtil.isEmpty(str)) {
				return;
			}
			try {
				final long longValue = Long.parseLong(str);
				if (longValue >= min && longValue <= max) {
					return;
				}
			} catch (final NumberFormatException e) {
			}
		} else if (value == null) {
			return;
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo(min, max));
	}
}