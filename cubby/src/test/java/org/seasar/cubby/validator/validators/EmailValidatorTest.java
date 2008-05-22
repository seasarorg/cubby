/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
