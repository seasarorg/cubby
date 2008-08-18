package org.seasar.cubby.cubbitter.action;

import java.io.IOException;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dto.AjaxDto;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.EmailValidator;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RangeLengthValidator;
import org.seasar.cubby.validator.validators.RegexpValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class RegisterAction extends AbstractAction {

	public ValidationRules registValidation = new DefaultValidationRules(
			"register.") {
		@Override
		public void initialize() {
			add("regMemberName", new RequiredValidator(),
					new MaxLengthValidator(15), new RegexpValidator(
							"^[0-9a-zA-Z_]+$"), new RegistMemberNameValidator());

			add("regPassword", new RequiredValidator(),
					new RangeLengthValidator(6, 20));

			add("regEmail", new RequiredValidator(), new EmailValidator());
		}
	};

	@RequestParameter
	public String regMemberName;

	@RequestParameter
	public String regPassword;

	@RequestParameter
	public String regEmail;

	public ActionResult index() {
		return new Forward("index.jsp");
	}

	@Validation(rules = "registValidation", errorPage = "index.jsp")
	public ActionResult process() {
		Account account = new Account();
		account.setName(regMemberName);
		account.setFullName(regMemberName);
		account.setPassword(regPassword);
		account.setMail(regEmail);
		account.setOpen(true);
		accountService.persist(account);
		accountService.login(account);

		return new Forward("result.jsp");
	}

	/** MemberNameが使用できるかどうかチェック(Ajax用) */
	@Path("checkName/{regMemberName,[0-9a-zA-Z_]+}")
	public ActionResult checkName() throws IOException {
		AjaxDto dto = new AjaxDto();
		if (accountService.isDuplicate(regMemberName)) {
			dto.isError = true;
			dto.errorMessage = Messages.getText("checkMemberName.msg.error",
					regMemberName);
		} else {
			dto.isError = false;
		}
		return new Json(dto);
	}

	/** memberNameの重複チェック用バリデータ */
	public class RegistMemberNameValidator implements ScalarFieldValidator {
		private final MessageHelper messageHelper;

		public RegistMemberNameValidator() {
			this.messageHelper = new MessageHelper("valid.existMemberName");
		}

		public void validate(final ValidationContext context, final Object value) {
			String name = (String) value;
			if (accountService.isDuplicate(name)) {
				context.addMessageInfo(this.messageHelper
						.createMessageInfo(name));
			}
		}
	}
}
