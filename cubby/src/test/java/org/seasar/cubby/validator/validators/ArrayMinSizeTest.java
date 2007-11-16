package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ArrayFieldValidator;

public class ArrayMinSizeTest extends AbstractArrayFieldValidatorTestCase {

	public void testValidation() {
		ArrayFieldValidator validator = new ArrayMinSizeValidator(3);
		assertSuccess(validator, null, new Object[] { "1", "2", "3" },
				new Object[] { "1", "2", "3", "4" });
		assertFail(validator, new Object[] { "1", "2" });
	}

}
