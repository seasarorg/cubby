/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package ${package}.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import ${package}.service.HelloService;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.context.WebApplicationContext;

@Scope(WebApplicationContext.SCOPE_REQUEST)
public class HelloAction extends Action {

	private ValidationRules validation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("name", new RequiredValidator());
		}
	};

	@Autowired
	private HelloService helloService;

	private String name;

	private String message;

	public ValidationRules getValidation() {
		return validation;
	}

	public String getName() {
		return name;
	}

	@RequestParameter
	public void setName(String name) {
		this.name = name;
	}

	public String getMessage() {
		return message;
	}

	public void setMessage(String message) {
		this.message = message;
	}

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Validation(rules = "validation", errorPage = "index.jsp")
	public ActionResult message() {
		this.message = this.name + " " + helloService.getMessage();
		return new Forward("hello.jsp");
	}

	public ActionResult back() {
		flash.put("notice", "Redirect OK!(this message is flash message)");
		return new Redirect("/hello/");
	}
}
