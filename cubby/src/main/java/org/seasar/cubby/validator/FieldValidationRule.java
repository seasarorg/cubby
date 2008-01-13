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

public class FieldValidationRule implements ValidationRule {

	private static final Object[] EMPTY_VALUES = new Object[] { "" };

	private final String fieldName;

	private final String fieldNameKey;

	private final List<ValidationInvoker> invokers = new ArrayList<ValidationInvoker>();

	public FieldValidationRule(final String fieldName,
			final Validator... validators) {
		this(fieldName, fieldName, validators);
	}

	public FieldValidationRule(final String fieldName,
			final String fieldNameKey, final Validator... validators) {
		this.fieldName = fieldName;
		this.fieldNameKey = fieldNameKey;
		for (final Validator validator : validators) {
			final ValidationInvoker invoker = createInvoker(validator);
			this.invokers.add(invoker);
		}
	}

	public void apply(final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) {
		final Object[] values = getValues(params, this.fieldName);
		for (final ValidationInvoker invoker : this.invokers) {
			invoker.invoke(values, errors);
		}
	}

	private Object[] getValues(final Map<String, Object[]> params,
			final String fieldName) {
		final Object[] values = params.get(fieldName);
		if (values != null) {
			return values;
		}
		return EMPTY_VALUES;
	}

	public String getFieldName() {
		return fieldName;
	}

	public String getFieldNameKey() {
		return fieldNameKey;
	}

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

	interface ValidationInvoker {

		void invoke(Object[] values, ActionErrors errors);

	}

	class ArrayFieldValidationInvoker implements ValidationInvoker {

		private final ArrayFieldValidator validator;

		public ArrayFieldValidationInvoker(final ArrayFieldValidator validator) {
			this.validator = validator;
		}

		public void invoke(final Object[] values, final ActionErrors errors) {
			final ValidationContext context = new ValidationContext();
			final FieldInfo fieldInfo = new FieldInfo(fieldName);
			this.validator.validate(context, values);
			for (MessageInfo message : context.getMessageInfos()) {
				errors.add(message.builder().fieldNameKey(fieldNameKey)
						.toString(), fieldInfo);
			}
		}

	}

	class ScalarFieldValidationInvoker implements ValidationInvoker {

		private final ScalarFieldValidator validator;

		public ScalarFieldValidationInvoker(final ScalarFieldValidator validator) {
			this.validator = validator;
		}

		public void invoke(final Object[] values, final ActionErrors errors) {
			for (int i = 0; i < values.length; i++) {
				final ValidationContext context = new ValidationContext();
				final FieldInfo fieldInfo = new FieldInfo(fieldName, i);
				this.validator.validate(context, values[i]);
				for (final MessageInfo messageInfo : context.getMessageInfos()) {
					final String message = messageInfo.builder().fieldNameKey(
							fieldNameKey).toString();
					errors.add(message, fieldInfo);
				}
			}
		}

	}

}
