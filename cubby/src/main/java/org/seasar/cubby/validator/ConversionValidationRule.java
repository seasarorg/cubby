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
package org.seasar.cubby.validator;

import static org.seasar.cubby.CubbyConstants.ATTR_CONVERSION_FAILURES;

import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.action.MessageInfo;
import org.seasar.cubby.internal.controller.ConversionFailure;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.RequestUtils;

/**
 * 要求パラメータをフォームオブジェクトへのバインドする時の型変換エラーを検証する入力検証ルールです。
 * 
 * @author baba
 */
public class ConversionValidationRule implements ValidationRule {

	/** リソースのキープレフィックス。 */
	private final String resourceKeyPrefix;

	/**
	 * キーのプレフィックスなしでインスタンス化します。
	 */
	public ConversionValidationRule() {
		this(null);
	}

	/**
	 * 指定されたプレフィックスをフィールド名のキーのプレフィックスとしてインスタンス化します。
	 * 
	 * @param resourceKeyPrefix
	 *            リソースのキープレフィックス
	 */
	public ConversionValidationRule(final String resourceKeyPrefix) {
		this.resourceKeyPrefix = resourceKeyPrefix;
	}

	/**
	 * {@inheritDoc}
	 */
	public void apply(final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) throws ValidationException {
		final ThreadContext currentContext = ThreadContext.getCurrentContext();
		final HttpServletRequest request = currentContext.getRequest();
		final List<ConversionFailure> conversionFailures = RequestUtils
				.getAttribute(request, ATTR_CONVERSION_FAILURES);
		if (conversionFailures != null && !conversionFailures.isEmpty()) {
			for (final ConversionFailure conversionFailure : conversionFailures) {
				final MessageInfo messageInfo = conversionFailure
						.getMessageInfo();
				final String fieldNameKey;
				if (resourceKeyPrefix == null) {
					fieldNameKey = conversionFailure.getFieldName();
				} else {
					fieldNameKey = resourceKeyPrefix
							+ conversionFailure.getFieldName();
				}
				final String message = messageInfo.toMessage(fieldNameKey);
				final FieldInfo[] fieldInfos = conversionFailure
						.getFieldInfos();
				errors.add(message, fieldInfos);
			}
		}
	}

}
