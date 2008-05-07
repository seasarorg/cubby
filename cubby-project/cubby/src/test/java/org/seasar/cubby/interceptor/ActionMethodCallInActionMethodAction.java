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
package org.seasar.cubby.interceptor;

import java.util.Map;

import org.junit.Assert;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

@Path("bar")
public class ActionMethodCallInActionMethodAction extends Action {

	public int count = 0;

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		public void initialize() {
			add(new ValidationRule() {

				public void apply(Map<String, Object[]> params, Object form,
						ActionErrors errors) {
					Assert.assertEquals(0, count);
				}

			});
		}

	};

	@Validation(rules = "validationRules", errorPage = "error.jsp")
	public ActionResult entry() {
		return top();
	}

	public ActionResult top() {
		return new Forward("top.jsp");
	}

}
