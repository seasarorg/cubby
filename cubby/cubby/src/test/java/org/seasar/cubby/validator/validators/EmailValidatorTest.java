package org.seasar.cubby.validator.validators;

import java.util.Collections;
import java.util.Map;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.Validator;

public class EmailValidatorTest extends TestCase {

	final static Map<String, Object[]> emptyMap = Collections.emptyMap();

	public void testValidation() {
		Validator validator = new EmailValidator();
		assertNull(validator.validate(new ValidationContext("field", null,
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field", "",
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field", "testuser@test.jp",
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field", "testuser@192.168.192.168",
				emptyMap, null)));
		assertNull(validator.validate(new ValidationContext("field", "testuser@192.168.192",
				emptyMap, null)));
		
		assertNotNull(validator.validate(new ValidationContext("field", "testuser",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@test",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@test.",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@192.168.192.256",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "ｔｅｓｔ@ｔｅｓｔ.ｊｐ",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@jp",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@test.a",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "testuser@test.aaaaa",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "test\\user@test.jp",
				emptyMap, null)));
		assertNotNull(validator.validate(new ValidationContext("field", "test[u]ser@test.jp",
				emptyMap, null)));
	}
}
