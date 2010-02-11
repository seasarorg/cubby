/*
 * Copyright 2004-2010 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */

package org.seasar.cubby.validator.validators;

import static org.seasar.cubby.validator.validators.ArrayFieldValidatorAssert.assertFail;
import static org.seasar.cubby.validator.validators.ArrayFieldValidatorAssert.assertSuccess;

import org.junit.Test;
import org.seasar.cubby.validator.ArrayFieldValidator;

public class ArrayMinSizeTest {

	@Test
	public void testValidation() {
		ArrayFieldValidator validator = new ArrayMinSizeValidator(3);
		assertSuccess(validator, null, new Object[] { "1", "2", "3" },
				new Object[] { "1", "2", "3", "4" });
		assertFail(validator, new Object[] { "1", "2" });
	}

}
