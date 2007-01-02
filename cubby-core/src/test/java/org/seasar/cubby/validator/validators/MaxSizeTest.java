package org.seasar.cubby.validator.validators;

import java.util.HashMap;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.Validator;

public class MaxSizeTest extends TestCase {

	public void testValidation() {
		Validator validator = new MaxSize(3);
		ValidContext context = new ValidContext("field", new HashMap());

		assertNull(validator.validate(context, null));
		assertNull(validator.validate(context, new Object[]{"1","2"}));
		assertNull(validator.validate(context, new Object[]{"1","2","3"}));
		assertNotNull(validator.validate(context, new Object[]{"1","2","3","4"}));
	}
}
