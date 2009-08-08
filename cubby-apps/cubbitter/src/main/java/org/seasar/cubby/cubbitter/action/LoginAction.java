package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dto.AjaxDto;
import org.seasar.cubby.cubbitter.service.AuthenticationException;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class LoginAction extends AbstractAction {

	@RequestParameter
	public String loginName;

	@RequestParameter
	public String loginPassword;

	public ValidationRules loginValidationRules = new AbstractValidationRules(
			"login.") {
		@Override
		public void initialize() {
			add("loginName", new RequiredValidator());
			add("loginPassword", new RequiredValidator());
		}
	};

	@Validation(rules = "loginValidationRules", errorPage = "/loginError.jsp")
	public ActionResult index() {
		try {
			accountService.login(loginName, loginPassword);
		} catch (AuthenticationException e) {
			throw new ValidationException(Messages.getText("login.msg.error"),
					"userId", "password");
		}
		return new Redirect("/" + loginName + "/");
	}

	public ValidationRules ajaxLoginValidation = new AbstractValidationRules(
			"login.") {
		@Override
		public void initialize() {
			addAll(loginValidationRules);
		}

		public ActionResult fail(String errorPage) {
			AjaxDto dto = new AjaxDto();
			dto.error = true;
			dto.messages = getErrors().getAll();
			return new Json(dto);
		}
	};

	@Validation(rules = "ajaxLoginValidation")
	public ActionResult check() {
		AjaxDto dto = new AjaxDto();
		if (accountService.isLoginable(loginName, loginPassword)) {
			dto.error = false;
		} else {
			dto.error = true;
			dto.messages.add(Messages.getText("login.msg.error"));
		}

		return new Json(dto);
	}

}
