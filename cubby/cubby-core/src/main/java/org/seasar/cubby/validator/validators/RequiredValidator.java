package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class RequiredValidator extends BaseValidator {
	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (!StringUtils.isEmpty(str)) {
				return null;
			}
		} else if (value != null) {
			return null;
		}
		return getMessage("valid.required", getPropertyMessage(ctx
				.getName()));
	}
}
