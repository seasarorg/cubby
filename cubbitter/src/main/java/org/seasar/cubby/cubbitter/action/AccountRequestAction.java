package org.seasar.cubby.cubbitter.action;

/** 
 * 承認リクエスト用Actionクラス
 */

import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/request")
public class AccountRequestAction extends AbstractAccountAction {

	@RequestParameter
	public Account requestAccount;

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		return new Forward("/account/request/index.jsp");
	}

	@Path("accept/{requestAccount,[0-9a-zA-Z_]+}")
	@Validation(rules = "validationRules")
	public ActionResult accept() {
		if (requestAccount == null) {
			return NOT_FOUND;
		}

		account.getRequests().remove(requestAccount);
		account.getFollowings().add(requestAccount);
		account.getFollowers().add(requestAccount);
		requestAccount.getFollowings().add(account);
		requestAccount.getFollowers().add(account);

		return acceptDenyRedirect();
	}

	@Path("deny/{requestAccount,[0-9a-zA-Z_]+}")
	@Validation(rules = "validationRules")
	public ActionResult deny() {
		if (requestAccount == null) {
			return NOT_FOUND;
		}
		account.getRequests().remove(requestAccount);

		return acceptDenyRedirect();
	}

	private ActionResult acceptDenyRedirect() {
		if (account.getRequests().size() > 0) {
			return restore();
		} else {
			return new Redirect(AccountAction.class, "index").param("account",
					account.getName());
		}
	}

}
