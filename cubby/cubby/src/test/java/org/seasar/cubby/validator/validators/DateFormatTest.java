package org.seasar.cubby.validator.validators;

public class DateFormatTest extends AbstractScalarFieldValidatorTestCase {

	public void testValidate() {
		DateFormatValidator validator = new DateFormatValidator("yyyy-MM-dd");
		assertSuccess(validator, "2006-01-01");
		assertFail(validator, "2006-02-29", "06-02-29", "2006-2-2a");
	}

}
