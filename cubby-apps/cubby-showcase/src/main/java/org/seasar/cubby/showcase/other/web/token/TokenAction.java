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
package org.seasar.cubby.showcase.other.web.token;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.TokenValidator;

public class TokenAction extends Action {


	// ----------------------------------------------[Validation]

	public ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("cubby.token", new TokenValidator());
		}
	};

	@RequestParameter
	public String name;
	
	public ActionResult index() {
		return new Forward("token.jsp");
	}

	@Validation(errorPage="token.jsp", rules = "validation")
	public ActionResult message() {
		return new Forward("result.jsp");
	}
	
}
