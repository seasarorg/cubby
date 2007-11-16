package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ScalarFieldValidator;

public class NumberValidatorTest extends AbstractScalarFieldValidatorTestCase {

	public void testValidation() {
		ScalarFieldValidator validator = new NumberValidator();
		assertSuccess(validator, "1", "-1", "1.1", "-1.1");
		assertFail(validator, "A", "123,456", "1.23.5");
	}

}
