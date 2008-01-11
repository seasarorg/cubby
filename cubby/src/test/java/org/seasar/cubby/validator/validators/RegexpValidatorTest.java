package org.seasar.cubby.validator.validators;

import java.util.regex.Pattern;

import org.seasar.cubby.validator.ScalarFieldValidator;

public class RegexpValidatorTest extends AbstractScalarFieldValidatorTestCase {

	public void testValidation1() {
		ScalarFieldValidator validator = new RegexpValidator("a.*34");
		assertSuccess(validator, null, "", "a5634");
		assertFail(validator, "b5634");
	}

	public void testValidation2() {
		ScalarFieldValidator validator = new RegexpValidator(Pattern.compile("(?i)a.*34"));
		assertSuccess(validator, null, "", "a5634");
		assertSuccess(validator, null, "", "A5634");
		assertFail(validator, "b5634");
	}
}
