package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

public class RangeLengthValidator extends BaseValidator {
	private final int min;
	private final int max;

	public RangeLengthValidator(final int min, final int max) {
		this.min = min;
		this.max = max;
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtil.isEmpty(str)) {
				return null;
			}

			int length = str.length();
			if (length >= min && length <= max) {
				return null;
			}
		}else if(value == null){
			return null;
		}
		return getMessage("valid.rangeLength", getPropertyMessage(ctx.getName()), min, max);
	}
}