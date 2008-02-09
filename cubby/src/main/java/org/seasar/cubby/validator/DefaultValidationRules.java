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
package org.seasar.cubby.validator;

import java.util.ArrayList;
import java.util.List;

/**
 * 入力検証を保持するクラスです。
 * 
 * @author agata
 */
public class DefaultValidationRules implements ValidationRules {

	/** 入力検証ルールのリスト。 */
	public final List<ValidationRule> rules = new ArrayList<ValidationRule>();

	/** メッセージキーのプリフィックス。 */
	private final String resourceKeyPrefix;

	/**
	 * メッセージキーのプリフィックスなしのコンストラクタ。
	 */
	public DefaultValidationRules() {
		this(null);
	}

	/**
	 * メッセージキーのプリフィックス付きのコンストラクタ。
	 * 
	 * @param resourceKeyPrefix
	 *            メッセージキーのプリフィックス
	 */
	public DefaultValidationRules(final String resourceKeyPrefix) {
		this.resourceKeyPrefix = resourceKeyPrefix;
		initialize();
	}

	/**
	 * 初期化メソッド。
	 * <p>
	 * このメソッドをサブクラスでオーバーライドして各項目の入力検証ルールを追加します。
	 * </p>
	 */
	public void initialize() {
	}

	/**
	 * 入力検証ルールを追加します。
	 * 
	 * @param rule
	 *            入力検証ルール
	 */
	protected void add(final ValidationRule rule) {
		this.rules.add(rule);
	}

	/**
	 * 入力検証ルールを追加します。
	 * <p>
	 * 項目名のメッセージキーとしてパラメータ名が使用されます。
	 * </p>
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param validators
	 *            入力検証ルールリスト
	 */
	public void add(final String paramName, final Validator... validators) {
		this.add(paramName, paramName, validators);
	}

	/**
	 * 項目名のメッセージキーを指定して入力検証ルールを追加します。
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param paramNameMessageKey
	 *            項目名のメッセージキー
	 * @param validators
	 *            入力検証ルールリスト
	 */
	public void add(final String paramName, final String paramNameMessageKey,
			final Validator... validators) {
		this.add(new FieldValidationRule(paramName,
				makePropertyNameKey(paramNameMessageKey), validators));
	}

	/**
	 * メッセージキーを作成します。
	 * <p>
	 * キーのプリフィックスが指定されていた場合、メッセージキーに付加します。
	 * </p>
	 * 
	 * @param messageKey
	 *            メッセージキー
	 * @return 作成後のメッセージキー
	 */
	private String makePropertyNameKey(final String messageKey) {
		if (this.resourceKeyPrefix == null) {
			return messageKey;
		} else {
			return this.resourceKeyPrefix + messageKey;
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public List<ValidationRule> getRules() {
		return rules;
	}

}
