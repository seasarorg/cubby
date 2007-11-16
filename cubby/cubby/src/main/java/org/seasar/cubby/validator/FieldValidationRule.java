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

	abstract class AbstractValidationContext implements ValidationContext {

		protected final ActionErrors errors;

		public AbstractValidationContext(final ActionErrors errors) {
			this.errors = errors;
		}

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
			final ArrayFieldValidationContext context = new ArrayFieldValidationContext(
					errors);
			this.validator.validate(context, values);
		}

		class ArrayFieldValidationContext extends AbstractValidationContext {

			public ArrayFieldValidationContext(final ActionErrors errors) {
				super(errors);
			}

			public void addMessageInfo(final MessageInfo messageInfo) {
				final String message = messageInfo.builder().fieldNameKey(
						fieldNameKey).toString();
				final FieldInfo fieldInfo = new FieldInfo(fieldName);
				this.errors.add(message, fieldInfo);
			}

		}

	}

	class ScalarFieldValidationInvoker implements ValidationInvoker {

		private final ScalarFieldValidator validator;

		public ScalarFieldValidationInvoker(final ScalarFieldValidator validator) {
			this.validator = validator;
		}

		public void invoke(final Object[] values, final ActionErrors errors) {
			final ScalarFieldValidationContext context = new ScalarFieldValidationContext(
					errors);
			for (int i = 0; i < values.length; i++) {
				context.setIndex(i);
				this.validator.validate(context, values[i]);
			}
		}

		class ScalarFieldValidationContext extends AbstractValidationContext {

			private int index;

			public ScalarFieldValidationContext(final ActionErrors errors) {
				super(errors);
			}

			public void setIndex(final int index) {
				this.index = index;
			}

			public void addMessageInfo(final MessageInfo messageInfo) {
				final String message = messageInfo.builder().fieldNameKey(
						fieldNameKey).toString();
				final FieldInfo fieldInfo = new FieldInfo(fieldName, index);
				this.errors.add(message, fieldInfo);
			}

		}

	}

}
