package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ScalarFieldValidator;

public class RegexpValidatorTest extends AbstractScalarFieldValidatorTestCase {

	public void testValidation1() {
		ScalarFieldValidator validator = new RegexpValidator("a.*34");
		assertSuccess(validator, null, "", "a5634");
		assertFail(validator, "b5634");
	}

}
