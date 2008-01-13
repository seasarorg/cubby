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
package org.seasar.cubby.validator.impl;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.validator.ValidationProcessor;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

public class ValidationProcessorImpl implements ValidationProcessor {

	public boolean process(final ActionErrors errors,
			final Map<String, Object[]> params, final Object form,
			final ValidationRules rules) {
		for (final ValidationRule rule : rules.getRules()) {
			rule.apply(params, form, errors);
		}
		return errors.isEmpty();
	}

}
