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
import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;

/**
 * 入力フォームのフィールドに対する入力検証のルールです。
 * 
 * @author baba
 * @since 1.0.0
 */
public class FieldValidationRule implements ValidationRule {

	/** 空のオブジェクト配列。 */
	private static final Object[] EMPTY_VALUES = new Object[] { "" };

	/** この入力検証ルールが対応する入力フォームのフィールド名。 */
	private final String fieldName;

	/** リソースバンドルからフィールド名を取得するためのキー。 */
	private final String fieldNameKey;

	/** 入力検証を実行するクラスのリスト。 */
	private final List<ValidationInvoker> invokers = new ArrayList<ValidationInvoker>();

	/**
	 * 指定されたフィールド名に対する入力検証ルールを生成します。
	 * 
	 * @param fieldName
	 *            フィールド名
	 * @param validators
	 *            入力検証
	 */
	public FieldValidationRule(final String fieldName,
			final Validator... validators) {
		this(fieldName, fieldName, validators);
	}

	/**
	 * 指定されたフィールド名に対する入力検証ルールを生成します。
	 * 
	 * @param fieldName
	 *            フィールド名
	 * @param fieldNameKey
	 *            リソースバンドルからフィールド名を取得するためのキー
	 * @param validators
	 *            入力検証
	 */
	public FieldValidationRule(final String fieldName,
			final String fieldNameKey, final Validator... validators) {
		this.fieldName = fieldName;
		this.fieldNameKey = fieldNameKey;
		for (final Validator validator : validators) {
			final ValidationInvoker invoker = createInvoker(validator);
			this.invokers.add(invoker);
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 対応するフィールドに対してこのオブジェクトが保持する入力検証を順次実行します。
	 * </p>
	 */
	public void apply(final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		final Object[] values = getValues(params, this.fieldName);
		for (final ValidationInvoker invoker : this.invokers) {
			invoker.invoke(this, values, errors);
		}
	}

	/**
	 * リクエストパラメータの{@link Map}から指定されたフィールド名に対する値を取得します。
	 * 
	 * @param params
	 *            リクエストパラメータの{@link Map}
	 * @param fieldName
	 *            フィールド名
	 * @return フィールド名に対する値
	 */
	private Object[] getValues(final Map<String, Object[]> params,
			final String fieldName) {
		final Object[] values = params.get(fieldName);
		if (values != null) {
			return values;
		}
		return EMPTY_VALUES;
	}

	/**
	 * この入力検証ルールが対応する入力フォームのフィールド名を取得します。
	 * 
	 * @return この入力検証ルールが対応する入力フォームのフィールド名
	 */
	public String getFieldName() {
		return fieldName;
	}

	/**
	 * リソースバンドルからフィールド名を取得するためのキーを取得します。
	 * 
	 * @return リソースバンドルからフィールド名を取得するためのキー
	 */
	public String getFieldNameKey() {
		return fieldNameKey;
	}

	/**
	 * 入力検証を呼び出すクラスのインスタンスを生成します。
	 * 
	 * @param validator
	 *            入力検証
	 * @return 入力検証を呼び出すクラスのインスタンス
	 */
	private ValidationInvoker createInvoker(final Validator validator) {
		final ValidationInvoker invoker;
		if (validator instanceof ArrayFieldValidator) {
			invoker = new ArrayFieldValidationInvoker(
					(ArrayFieldValidator) validator);
		} else if (validator instanceof ScalarFieldValidator) {
			invoker = new ScalarFieldValidationInvoker(
					(ScalarFieldValidator) validator);
		} else {
			throw new UnsupportedOperationException();
		}
		return invoker;
	}

	/**
	 * 入力検証を呼び出すためのクラスです。
	 * 
	 * @author baba
	 * @since 1.0.0
	 */
	private interface ValidationInvoker {

		/**
		 * 入力検証を呼び出します。
		 * 
		 * @param validationRule
		 *            入力検証ルール
		 * @param values
		 *            入力検証を行う値
		 * @param errors
		 *            アクションで発生したエラー
		 */
		void invoke(FieldValidationRule validationRule, Object[] values,
				ActionErrors errors);

	}

	/**
	 * {@link ArrayFieldValidator}の入力検証を呼び出すためのクラスです。
	 * 
	 * @author baba
	 * @since 1.0.0
	 */
	private static class ArrayFieldValidationInvoker implements
			ValidationInvoker {

		/** {@link #invoke(FieldValidationRule, Object[], ActionErrors)}で呼び出す入力検証。 */
		private final ArrayFieldValidator validator;

		/**
		 * インスタンス化します。
		 * 
		 * @param validator
		 *            入力検証
		 */
		public ArrayFieldValidationInvoker(final ArrayFieldValidator validator) {
			this.validator = validator;
		}

		/**
		 * {@inheritDoc}
		 */
		public void invoke(final FieldValidationRule validationRule,
				final Object[] values, final ActionErrors errors) {
			final ValidationContext context = new ValidationContext();
			final FieldInfo fieldInfo = new FieldInfo(validationRule
					.getFieldName());
			this.validator.validate(context, values);
			for (final MessageInfo message : context.getMessageInfos()) {
				errors
						.add(message.builder().fieldNameKey(
								validationRule.getFieldNameKey()).toString(),
								fieldInfo);
			}
		}

	}

	/**
	 * {@link ScalarFieldValidator}の入力検証を呼び出すためのクラスです。
	 * 
	 * @author baba
	 * @since 1.0.0
	 */
	private static class ScalarFieldValidationInvoker implements
			ValidationInvoker {

		/** {@link #invoke(FieldValidationRule, Object[], ActionErrors)}で呼び出す入力検証。 */
		private final ScalarFieldValidator validator;

		/**
		 * インスタンス化します。
		 * 
		 * @param validator
		 *            入力検証
		 */
		public ScalarFieldValidationInvoker(final ScalarFieldValidator validator) {
			this.validator = validator;
		}

		/**
		 * {@inheritDoc}
		 */
		public void invoke(final FieldValidationRule validationRule,
				final Object[] values, final ActionErrors errors) {
			for (int i = 0; i < values.length; i++) {
				final ValidationContext context = new ValidationContext();
				final FieldInfo fieldInfo = new FieldInfo(validationRule
						.getFieldName(), i);
				this.validator.validate(context, values[i]);
				for (final MessageInfo messageInfo : context.getMessageInfos()) {
					final String message = messageInfo.builder().fieldNameKey(
							validationRule.getFieldNameKey()).toString();
					errors.add(message, fieldInfo);
				}
			}
		}

	}

}
