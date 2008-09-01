package org.seasar.cubby.cubbitter.action;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.OnSubmit;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.EmailValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RegexpValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.framework.aop.annotation.InvalidateSession;
import org.seasar.framework.beans.util.Beans;

@Path("setting/profile")
public class SettingProfileAction extends AbstractAction {

	@RequestParameter
	public String name;

	@RequestParameter
	public String fullName;

	@RequestParameter
	public boolean open;

	@RequestParameter
	public String mail;

	@RequestParameter
	public String locale;

	@RequestParameter
	public String web;

	@RequestParameter
	public String biography;

	@RequestParameter
	public String location;

	public ActionResult index() {
		Beans.copy(loginAccount, this).execute();
		return new Forward("index.jsp");
	}

	public ValidationRules validationRules = new AbstractValidationRules(
			"setting.") {

		@Override
		public void initialize() {
			add("name", new RequiredValidator(), new MaxLengthValidator(15),
					new RegexpValidator("^[0-9a-zA-Z_]+$"),
					new AccountNameValidationRule());
			add("mail", new RequiredValidator(), new MaxLengthValidator(50),
					new EmailValidator());
			add("fullName", new RequiredValidator(), new MaxLengthValidator(40));
			add("web", new MaxLengthValidator(100), new RegexpValidator(
					"(https?://[-_.!~*'()a-zA-Z0-9;/?:@&=+$,%#]+)?"));
			add("biography", new MaxLengthValidator(200));
			add("location", new MaxLengthValidator(100));
		}

	};

	class AccountNameValidationRule implements ScalarFieldValidator {
		private final MessageHelper messageHelper;

		public AccountNameValidationRule() {
			this.messageHelper = new MessageHelper("valid.existMemberName");
		}

		public void validate(final ValidationContext context, final Object value) {
			final String name = (String) value;
			Account account = accountService.findByName(name);
			if (account != null && !account.equals(loginAccount)) {
				context.addMessageInfo(this.messageHelper
						.createMessageInfo(name));
			}
		}
	}

	@Validation(rules = "validationRules", errorPage = "index.jsp")
	public ActionResult update() {
		Beans.copy(this, loginAccount).excludesNull().excludesWhitespace()
				.execute();
		notice(Messages.getText("setting.msg.updateSuccess"));
		return new Redirect(SettingProfileAction.class);
	}

	@Path("update")
	@OnSubmit("delete")
	@InvalidateSession
	public ActionResult delete() {
		accountService.remove(loginAccount);
		return new Forward("delete.jsp");
	}

}
