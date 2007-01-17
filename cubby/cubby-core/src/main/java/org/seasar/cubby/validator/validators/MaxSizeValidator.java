package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class MaxSizeValidator extends BaseValidator {
	private int max;

	public MaxSizeValidator(int max) {
		this.max = max;
	}

	public String validate(ValidContext context, Object value) {
		if (value == null) {
			return null; 
		} 
		int size = CubbyUtils.getObjectSize(value);
		if (size <= max) {
			return null;
		} else {
			return getMessage("valid.maxSize", getPropertyMessage(context
					.getName()), (Integer) max);
		}
	}
}