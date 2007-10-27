package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;
import org.seasar.framework.exception.EmptyRuntimeException;

/**
 * 配列のパラメータに対して入力検証を行います。
 * @author agata
 */
public class ArrayValidator implements Validator {

	private final Validator[] validators;

	/**
	 * 配列に適用するバリデーション一覧を指定します。
	 * @param validators バリデーション
	 */
	public ArrayValidator(final Validator... validators) {
		if (validators == null) {
			throw new EmptyRuntimeException("validators");
		}
		this.validators = validators.clone();
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null || !value.getClass().isArray()) {
			return validateAll(ctx);
		}
		Object[] values = (Object[])value;
		for (Object currentValue : values) {
			ValidationContext currentCtx = new ValidationContext(ctx.getName(),
					currentValue, ctx.getParams(), ctx.getFormatPattern());
			String error = validateAll(currentCtx);
			if (error != null) {
				return error;
			}
		}
		return null;
	}

	private String validateAll(final ValidationContext ctx) {
		for (Validator v : validators) {
			String error = v.validate(ctx);
			if (error != null) {
				return error;
			}
		}
		return null;
	}
}
