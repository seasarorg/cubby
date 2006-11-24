package org.seasar.cubby.validator.validators;

import junit.framework.TestCase;

import org.seasar.cubby.validator.ValidContext;
import org.seasar.cubby.validator.Validator;

public class MinSizeTest extends TestCase {

	public void testValidation() {
		Validator validator = new MinSize(3);
		ValidContext context = new ValidContext();
		context.setName("field");

		assertNull(validator.validate(context, null));
		assertNotNull(validator.validate(context, new Object[]{"1","2"}));
		assertNull(validator.validate(context, new Object[]{"1","2","3"}));
		assertNull(validator.validate(context, new Object[]{"1","2","3","4"}));
	}
}
