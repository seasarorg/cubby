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

import static org.seasar.cubby.validator.validators.ScalarFieldValidatorAssert.assertFail;
import static org.seasar.cubby.validator.validators.ScalarFieldValidatorAssert.assertSuccess;

import org.junit.Test;
import org.seasar.cubby.validator.ScalarFieldValidator;

public class NumberValidatorTest {

	@Test
	public void validate() {
		ScalarFieldValidator validator = new NumberValidator();
		assertSuccess(validator, "1", "-1", "1.1", "-1.1");
		assertFail(validator, "A", "123,456", "1.23.5");
		// FIXED https://www.seasar.org/issues/browse/CUBBY-222
		assertFail(validator, "１", "Ⅰ");
	}

}
