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
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.exception.ActionRuntimeException;
import org.seasar.framework.util.StringUtil;

/**
 * 入力検証を保持するクラスです。
 * 
 * @author agata
 * @author baba
 * @since 1.0.0
 */
public abstract class DefaultValidationRules implements ValidationRules {

	/** データ型を検証するフェーズ。 */
	public static final ValidationPhase DATA_TYPE = new ValidationPhase();

	/** データ上の制約を検証するフェーズ。 */
	public static final ValidationPhase DATA_CONSTRAINT = new ValidationPhase();

	/** 入力検証のフェーズとそれに対応する入力検証ルールのリスト。 */
	private final Map<ValidationPhase, List<ValidationRule>> phaseValidationRulesMap = new HashMap<ValidationPhase, List<ValidationRule>>();

	/** メッセージキーのプリフィックス。 */
	private final String resourceKeyPrefix;

	/** 入力検証のフェーズ。 */
	private static final List<ValidationPhase> VALIDATION_PHASES = Arrays
			.asList(new ValidationPhase[] { DATA_TYPE, DATA_CONSTRAINT });

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
	protected abstract void initialize();

	/**
	 * 入力検証ルールを追加します。
	 * 
	 * @param validationPhase
	 *            指定された入力検証ルールを実行するフェーズ
	 * @param validationRule
	 *            入力検証ルール
	 */
	protected void add(final ValidationPhase validationPhase,
			final ValidationRule validationRule) {
		if (!this.phaseValidationRulesMap.containsKey(validationPhase)) {
			this.phaseValidationRulesMap.put(validationPhase,
					new ArrayList<ValidationRule>());
		}
		final List<ValidationRule> validationRules = this.phaseValidationRulesMap
				.get(validationPhase);
		validationRules.add(validationRule);
	}

	/**
	 * 最初のフェーズに入力検証ルールを追加します。
	 * 
	 * @param validationRule
	 *            入力検証ルール
	 */
	protected void add(final ValidationRule validationRule) {
		this.add(getValidationPhases().get(0), validationRule);
	}

	/**
	 * 最初のフェーズに入力検証を追加します。
	 * <p>
	 * 項目名のメッセージキーとしてパラメータ名が使用されます。
	 * </p>
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param validators
	 *            入力検証
	 */
	protected void add(final String paramName, final Validator... validators) {
		this.add(paramName, paramName, validators);
	}

	/**
	 * 項目名のメッセージキーを指定して、最初のフェーズに入力検証を追加します。
	 * 
	 * @param paramName
	 *            パラメータ名
	 * @param paramNameMessageKey
	 *            項目名のメッセージキー
	 * @param validators
	 *            入力検証
	 */
	protected void add(final String paramName,
			final String paramNameMessageKey, final Validator... validators) {
		this.add(getValidationPhases().get(0),
				new FieldValidationRule(paramName,
						makePropertyNameKey(paramNameMessageKey), validators));
	}

	/**
	 * 指定された {@link ValidationRules} に定義された入力検証ルールをすべて追加します。
	 * 
	 * @param validationRules
	 *            追加する入力検証ルールの集合
	 */
	protected void addAll(final ValidationRules validationRules) {
		for (final ValidationPhase validationPhase : validationRules
				.getValidationPhases()) {
			final Collection<ValidationRule> phaseValidationRules = validationRules
					.getPhaseValidationRules(validationPhase);
			for (final ValidationRule validationRule : phaseValidationRules) {
				this.add(validationPhase, validationRule);
			}
		}
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
	 * <p>
	 * 指定されたエラーページへ遷移する {@link Forward} を返します。
	 * </p>
	 */
	public ActionResult fail(final String errorPage) {
		if (StringUtil.isEmpty(errorPage)) {
			throw new ActionRuntimeException("ECUB0106");
		}
		return new Forward(errorPage);
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * デフォルトでは以下の順序です。
	 * <ul>
	 * <li>{@link #DATA_TYPE}</li>
	 * <li>{@link #DATA_CONSTRAINT}</li>
	 * </ul>
	 * これを変更してフェーズの追加などをしたい場合はこのメソッドをオーバーライドしてください。
	 * </p>
	 */
	public List<ValidationPhase> getValidationPhases() {
		return VALIDATION_PHASES;
	}

	/**
	 * {@inheritDoc}
	 */
	public Collection<ValidationRule> getPhaseValidationRules(
			final ValidationPhase validationPhase) {
		final Collection<ValidationRule> phaseValidationRules;
		if (this.phaseValidationRulesMap.containsKey(validationPhase)) {
			phaseValidationRules = this.phaseValidationRulesMap
					.get(validationPhase);
		} else {
			phaseValidationRules = Collections.emptyList();
		}
		return phaseValidationRules;
	}

	/**
	 * {@inheritDoc}
	 * 
	 * @deprecated {@link #addAll(ValidationRules)} を使用してください。
	 */
	@Deprecated
	public List<ValidationRule> getRules() {
		return phaseValidationRulesMap.get(getValidationPhases().get(0));
	}

	public boolean isFail(String name) {
		return false;
	}

	public boolean isFail(ValidationRule rule) {
		return false;
	}

}
