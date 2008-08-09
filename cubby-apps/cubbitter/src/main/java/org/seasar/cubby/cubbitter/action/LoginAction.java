package org.seasar.cubby.cubbitter.action;

/** 
 * ログイン・ログアウト処理用Actionクラス 
 */

import java.util.Map;


import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Json;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.dao.MemberDao;
import org.seasar.cubby.cubbitter.dto.CheckResultDto;
import org.seasar.cubby.cubbitter.entity.Member;
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.framework.aop.annotation.InvalidateSession;

public class LoginAction extends Action {

	// ----------------------------------------------[DI Filed]

	public MemberDao memberDao;
	public Map<String, Object> sessionScope;
	
	// ----------------------------------------------[Attribute]
	
	public String memberName;
	public String password;

	// ----------------------------------------------[Action Method]

	/** ログイン */
	@Validation(rules = "loginValidation", errorPage = "/top/loginError.jsp")
	public ActionResult index() {
		return new Redirect("/home/");
	}

	/** Ajax用チェック処理 */
	public ActionResult check() {
		CheckResultDto dto = new CheckResultDto();
		if (getLoginMember() == null) {
			dto.isError = true;
			dto.errorMessage = Messages.getText("login.msg.error");
		} else {
			dto.isError = false;
		}

		return new Json(dto);
	}

	/** ログアウト */
	@InvalidateSession
	public ActionResult logout() {
		return new Redirect("/top/");
	}

	// ----------------------------------------------[Private Method]

	private Member getLoginMember() {
		if (memberName == null || password == null) {
			return null;
		}
		Member user = memberDao.getMemberByName(memberName);
		if (user == null || !user.getPassword().equals(password)) {
			return null;
		}
		return user;
	}

	// ----------------------------------------------[Validation]

	public ValidationRules loginValidation = new DefaultValidationRules(
			"login.") {
		@Override
		public void initialize() {
			add("memberName", new RequiredValidator());
			add("password", new RequiredValidator());
			add(new UserValidationRule());
		}
	};

	/** ログインチェック処理 */
	private class UserValidationRule implements ValidationRule {
		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) {
			Member user = getLoginMember();
			if (user == null) {
				errors.add(Messages.getText("login.msg.error"), "userId",
						"password");
			} else {
				sessionScope.put("user", user);
			}
		}
	}
}
