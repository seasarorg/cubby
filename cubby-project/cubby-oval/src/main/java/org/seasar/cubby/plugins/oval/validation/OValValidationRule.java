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
package org.seasar.cubby.plugins.oval.validation;

import java.util.List;
import java.util.Map;

import net.sf.oval.ConstraintViolation;
import net.sf.oval.Validator;
import net.sf.oval.context.ClassContext;
import net.sf.oval.context.ConstructorParameterContext;
import net.sf.oval.context.FieldContext;
import net.sf.oval.context.MethodEntryContext;
import net.sf.oval.context.MethodExitContext;
import net.sf.oval.context.MethodParameterContext;
import net.sf.oval.context.MethodReturnValueContext;
import net.sf.oval.context.OValContext;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;

/**
 * OVal によって入力を検証する {@link ValidationRule} です。
 * 
 * @author baba
 */
public class OValValidationRule implements ValidationRule {

	/** リソースのキープレフィックス。 */
	private final String resourceKeyPrefix;

	/**
	 * キーのプレフィックスなしでインスタンス化します。
	 */
	public OValValidationRule() {
		this(null);
	}

	/**
	 * 指定されたプレフィックスをフィールド名のキーのプレフィックスとしてインスタンス化します。
	 * 
	 * @param resourceKeyPrefix
	 *            リソースのキープレフィックス
	 */
	public OValValidationRule(final String resourceKeyPrefix) {
		this.resourceKeyPrefix = resourceKeyPrefix;
	}

	/**
	 * {@inheritDoc}
	 */
	public void apply(final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) throws ValidationException {
		final OValValidationContext context = OValValidationContext.get();
		context.setResourceKeyPrefix(resourceKeyPrefix);
		try {
			final Validator validator = buildValidator();
			final List<ConstraintViolation> violations = validator
					.validate(form);
			processViolations(violations, errors);
		} finally {
			OValValidationContext.remove();
		}
	}

	/**
	 * バリデータを構築します。
	 * 
	 * @return バリデータ
	 */
	protected Validator buildValidator() {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		try {
			return container.lookup(Validator.class);
		} catch (final LookupException e) {
			return new Validator();
		}
	}

	/**
	 * 入力検証で検出した制約違反を処理します。
	 * <p>
	 * <code>errors</code> に制約違反から抽出したメッセージを設定します。
	 * </p>
	 * 
	 * @param violations
	 *            制約違反のリスト
	 * @param errors
	 *            メッセージを設定するオブジェクト
	 */
	protected void processViolations(
			final List<ConstraintViolation> violations,
			final ActionErrors errors) {
		for (final ConstraintViolation violation : violations) {
			final String message = violation.getMessage();
			final FieldInfo fieldInfo = createFieldInfo(violation.getContext());
			if (fieldInfo != null) {
				errors.add(message, fieldInfo);
			} else {
				errors.add(message);
			}
		}
	}

	/**
	 * <code>ovalContext</code> から {@link FieldInfo} を生成します。
	 * 
	 * @param ovalContext
	 *            OVal のコンテキスト
	 * @return <code>ovalContext</code> から生成された {@link FieldInfo}
	 */
	protected FieldInfo createFieldInfo(final OValContext ovalContext) {
		final FieldInfo fieldInfo;
		if (ovalContext instanceof ClassContext) {
			fieldInfo = null;
		} else if (ovalContext instanceof FieldContext) {
			final FieldContext ctx = (FieldContext) ovalContext;
			fieldInfo = new FieldInfo(ctx.getField().getName());
		} else if (ovalContext instanceof ConstructorParameterContext) {
			final ConstructorParameterContext ctx = (ConstructorParameterContext) ovalContext;
			fieldInfo = new FieldInfo(ctx.getParameterName());
		} else if (ovalContext instanceof MethodParameterContext) {
			final MethodParameterContext ctx = (MethodParameterContext) ovalContext;
			fieldInfo = new FieldInfo(ctx.getParameterName());
		} else if (ovalContext instanceof MethodEntryContext) {
			final MethodEntryContext ctx = (MethodEntryContext) ovalContext;
			fieldInfo = new FieldInfo(ctx.getMethod().getName());
		} else if (ovalContext instanceof MethodExitContext) {
			final MethodExitContext ctx = (MethodExitContext) ovalContext;
			fieldInfo = new FieldInfo(ctx.getMethod().getName());
		} else if (ovalContext instanceof MethodReturnValueContext) {
			final MethodReturnValueContext ctx = (MethodReturnValueContext) ovalContext;
			fieldInfo = new FieldInfo(ctx.getMethod().getName());
		} else {
			fieldInfo = null;
		}

		return fieldInfo;
	}

}
