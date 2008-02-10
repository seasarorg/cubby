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

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 指定された正規表現にマッチするか検証します。
 * <p>
 * 正規表現についての詳細は {@link Pattern}を参照してください。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.regexp
 * </p>
 * 
 * @author baba
 * @see Pattern
 * @see Matcher
 * @since 1.0.0
 */
public class RegexpValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 正規表現パターン
	 */
	private final Pattern pattern;

	/**
	 * コンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(?i)(png|jpg)"）・・・「(?i)」は大文字小文字を区別しないフラグ
	 */
	public RegexpValidator(final String regex) {
		this(regex, "valid.regexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(?i)(png|jpg)"）・・・「(?i)」は大文字小文字を区別しないフラグ
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RegexpValidator(final String regex, final String messageKey) {
		this(Pattern.compile(regex), messageKey);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param pattern
	 *            正規表現パターン
	 */
	public RegexpValidator(final Pattern pattern) {
		this(pattern, "valid.regexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param pattern
	 *            正規表現パターン
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RegexpValidator(final Pattern pattern, final String messageKey) {
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
			if (StringUtil.isEmpty(stringValue)) {
				return;
			}
			final Matcher matcher = pattern.matcher(stringValue);
			if (matcher.matches()) {
				return;
			}
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo());
	}

}
