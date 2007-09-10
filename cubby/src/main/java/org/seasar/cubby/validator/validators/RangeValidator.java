package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

public class RangeValidator extends BaseValidator {
	private final long min;
	private final long max;

	public RangeValidator(final long min, final long max) {
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
			try {
				long longValue = Long.parseLong(str);
				if (longValue >= min && longValue <= max) {
					return null;
				}
			} catch (NumberFormatException e) {}
		}else if(value == null){
			return null;
		}
		return getMessage("valid.range", getPropertyMessage(ctx
				.getName()), (Long) min, (Long) max);
	}
}