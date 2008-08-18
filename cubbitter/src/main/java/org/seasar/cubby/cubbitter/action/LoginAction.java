package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dto.AjaxDto;
import org.seasar.cubby.cubbitter.service.AccountService;
import org.seasar.cubby.cubbitter.service.AuthenticationException;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class LoginAction extends Action {

	public ValidationRules loginValidation = new DefaultValidationRules(
			"login.") {
		@Override
		public void initialize() {
			add("loginName", new RequiredValidator());
			add("loginPassword", new RequiredValidator());
		}
	};

	public AccountService accountService;

	@RequestParameter
	public String loginName;

	@RequestParameter
	public String loginPassword;

	@Validation(rules = "loginValidation", errorPage = "/loginError.jsp")
	public ActionResult index() {
		try {
			accountService.login(loginName, loginPassword);
		} catch (AuthenticationException e) {
			throw new ValidationException(Messages.getText("login.msg.error"),
					"userId", "password");
		}
		return new Redirect("/" + loginName + "/");
	}

	public ActionResult check() {
		AjaxDto dto = new AjaxDto();
		if (accountService.isLoginable(loginName, loginPassword)) {
			dto.isError = false;
		} else {
			dto.isError = true;
			dto.errorMessage = Messages.getText("login.msg.error");
		}

		return new Json(dto);
	}

}
