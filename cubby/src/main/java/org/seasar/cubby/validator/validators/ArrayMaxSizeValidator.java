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

import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 配列の最大サイズを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.arrayMaxSize
 * </p>
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public class ArrayMaxSizeValidator implements ArrayFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 配列の最大サイズ
	 */
	private final int max;

	/**
	 * コンストラクタ
	 * 
	 * @param max
	 *            配列の最大サイズ
	 */
	public ArrayMaxSizeValidator(final int max) {
		this(max, "valid.maxSize");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param max
	 *            配列の最大サイズ
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public ArrayMaxSizeValidator(final int max, final String messageKey) {
		this.max = max;
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object[] values) {
		if (values == null) {
			return;
		}
		if (values.length > max) {
			context.addMessageInfo(this.messageHelper.createMessageInfo(max));
		}
	}
}