package org.seasar.cubby.cubbitter.action;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RangeLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Path("setting/password")
public class SettingPasswordAction extends AbstractAction {

	@RequestParameter
	public String password;

	@RequestParameter
	public String password2;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	public ValidationRules validationRules = new DefaultValidationRules(
			"setting.") {

		@Override
		public void initialize() {
			add("password", new RequiredValidator(), new RangeLengthValidator(
					6, 20));
			add(new ValidationRule() {

				public void apply(Map<String, Object[]> params, Object form,
						ActionErrors errors) {
					if (!password.equals(password2)) {
						errors.add(Messages
								.getText("setting.msg.verifyPasswordError"));
					}
				}

			});
		}
	};

	@Validation(rules = "validationRules", errorPage = "index.jsp")
	public ActionResult update() {
		loginAccount.setPassword(password);

		flash.put("notice", Messages.getText("setting.msg.updateSuccess"));
		return new Redirect(SettingPasswordAction.class, "index");
	}

}
