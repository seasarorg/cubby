package org.seasar.cubby.cubbitter.action;

import java.io.IOException;
import java.util.Map;


import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.CheckResultDto;
import org.seasar.cubby.cubbitter.entity.Member;
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
import org.seasar.framework.util.StringUtil;

public class RegisterAction extends Action {

	// ----------------------------------------------[DI Filed]

	public MemberDao memberDao;
	public Map<String, Object> sessionScope;

	// ----------------------------------------------[Attribute]

	public String regMemberName;
	public String regPassword;
	public String regEmail;

	// ----------------------------------------------[Action Method]

	/**  トップ画面 */
	public ActionResult index() {
		return new Forward("index.jsp");
	}

	/** コメントの登録 */
	@Validation(rules = "registValidation", errorPage = "index.jsp")
	public ActionResult process() {
		Member member = new Member();
		member.setMemberName(regMemberName);
		member.setFullName(regMemberName);
		member.setPassword(regPassword);
		member.setEmail(regEmail);
		member.setLocale("ja");
		member.setOpen(true);
		memberDao.insertMember(member);

		// セッションに入れる
		sessionScope.put("user", member);

		return new Forward("result.jsp");
	}

	/** MemberNameが使用できるかどうかチェック(Ajax用) */
	@Path("checkName/{regMemberName,[0-9a-zA-Z_]+}")
	public ActionResult checkName() throws IOException {

		Member user = memberDao.getMemberByName(regMemberName);

		CheckResultDto dto = new CheckResultDto();
		if (user == null) {
			dto.isError = false;
		} else {
			dto.isError = true;
			dto.errorMessage = Messages.getText("checkMemberName.msg.error",
					regMemberName);
		}

		return new Json(dto);
	}

	// ----------------------------------------------[Validation]

	/** 登録時のバリデーション */
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

	/** memberNameの重複チェック用バリデータ */
	public class RegistMemberNameValidator implements ScalarFieldValidator {
		private final MessageHelper messageHelper;

		public RegistMemberNameValidator() {
			this.messageHelper = new MessageHelper("valid.existMemberName");
		}

		public void validate(final ValidationContext context, final Object value) {
			if (value == null || !(value instanceof String)) {
				return;
			}

			final String str = (String) value;
			if (StringUtil.isEmpty(str)) {
				return;
			}
			Member user = memberDao.getMemberByName(str);
			if (user != null) {
				context.addMessageInfo(this.messageHelper
						.createMessageInfo(str));
			}
		}
	}
}
