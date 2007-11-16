package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ArrayFieldValidator;

public class ArrayMaxSizeTest extends AbstractArrayFieldValidatorTestCase {

	public void testValidation() {
		ArrayFieldValidator validator = new ArrayMaxSizeValidator(3);
		assertSuccess(validator, null, new Object[] { "1", "2" }, new Object[] {
				"1", "2", "3" });
		assertFail(validator, new Object[] { "1", "2", "3", "4" });
	}

}
