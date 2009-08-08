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
package org.seasar.cubby.showcase.other.web.converter;

import java.util.Arrays;
import java.util.List;

import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationPhase;

abstract class AbstractValidationRules extends DefaultValidationRules {

	public static final ValidationPhase RESOURCE = new ValidationPhase();

	private static final List<ValidationPhase> VALIDATION_PHASES = Arrays
			.asList(new ValidationPhase[] { RESOURCE, DATA_TYPE,
					DATA_CONSTRAINT });

	@Override
	public List<ValidationPhase> getValidationPhases() {
		return VALIDATION_PHASES;
	}

}
