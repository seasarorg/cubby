package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.ScalarFieldValidator;

public class EmailValidatorTest extends AbstractScalarFieldValidatorTestCase {

	public void testValidation() {
		ScalarFieldValidator validator = new EmailValidator();
		assertSuccess(validator, null, "", "testuser@test.jp",
				"testuser@192.168.192.168", "testuser@192.168.192");
		assertFail(validator, "testuser", "testuser@", "testuser@test",
				"testuser@test.", "testuser@192.168.192.256", "ｔｅｓｔ@ｔｅｓｔ.ｊｐ",
				"testuser@jp", "testuser@test.a", "testuser@test.aaaaa",
				"test\\user@test.jp", "test[u]ser@test.jp");
	}
}
