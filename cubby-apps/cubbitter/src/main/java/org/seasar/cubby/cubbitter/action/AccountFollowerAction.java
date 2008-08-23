package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/follower")
public class AccountFollowerAction extends AbstractAccountAction {

	@RequestParameter
	public Account follower;

	@RequestParameter
	public int pageNo = 1;

	public ValidationRules validationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			addAll(accountValidationRules);
			add(DATA_CONSTRAINT, new FollowerOnlyValidationRule());
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		return new Forward("/account/follower/index.jsp");
	}

	@Path("{follower,[0-9a-zA-Z_]+}/add")
	@Validation(rules = "validationRules")
	public ActionResult add() {
		if (follower == null) {
			return NOT_FOUND;
		}
		follower.getRequests().remove(account);
		account.getFollowings().add(follower);
		account.getFollowers().add(follower);
		follower.getFollowings().add(account);
		follower.getFollowers().add(account);
		return restore();
	}

}
