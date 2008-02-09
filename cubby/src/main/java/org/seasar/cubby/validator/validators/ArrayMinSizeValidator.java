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
 * 配列の最小サイズを検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.arrayMinSize
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class ArrayMinSizeValidator implements ArrayFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 配列の最小サイズ
	 */
	private final int min;

	/**
	 * コンストラクタ
	 * 
	 * @param min
	 *            配列の最小サイズ
	 */
	public ArrayMinSizeValidator(final int min) {
		this(min, "valid.minSize");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param min
	 *            配列の最小サイズ
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public ArrayMinSizeValidator(final int min, final String messageKey) {
		this.min = min;
		this.messageHelper = new MessageHelper(messageKey);
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object[] values) {
		if (values == null) {
			return;
		}
		if (values.length < min) {
			context.addMessageInfo(this.messageHelper.createMessageInfo(min));
		}
	}
}