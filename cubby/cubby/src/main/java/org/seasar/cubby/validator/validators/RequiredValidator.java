package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

public class RequiredValidator extends BaseValidator {
	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (!StringUtil.isEmpty(str)) {
				return null;
			}
		} else if (value != null) {
			return null;
		}
		return getMessage("valid.required", getPropertyMessage(ctx
				.getName()));
	}
}
