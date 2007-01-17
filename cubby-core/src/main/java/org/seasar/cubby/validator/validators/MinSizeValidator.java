package org.seasar.cubby.validator.validators;

import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class MinSizeValidator extends BaseValidator {
	private int min;

	public MinSizeValidator(int min) {
		this.min = min;
	}

	public String validate(ValidContext context, Object value) {
		if (value == null) {
			return null; 
		} 
		int size = CubbyUtils.getObjectSize(value);
		if (size >= min) {
			return null;
		} else {
			return getMessage("valid.minSize", getPropertyMessage(context
					.getName()), (Integer) min);
		}
	}
}