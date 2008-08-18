package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/following")
public class AccountFollowingAction extends AbstractAccountAction {

	@RequestParameter
	public Account following;

	@RequestParameter
	public int pageNo = 1;

	public ValidationRules indexValidationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new FollowerOnlyValidationRule());
		}

	};

	@Validation(rules = "indexValidationRules")
	public ActionResult index() {
		return new Forward("/account/following/index.jsp");
	}

	public ValidationRules actionValidationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}

	};

	@Path("add/{following,[0-9a-zA-Z_]+}")
	@Validation(rules = "actionValidationRules")
	public ActionResult add() {
		if (following == null) {
			return NOT_FOUND;
		}
		if (following.isOpen()) {
			account.getFollowings().add(following);
			account.getFollowers().add(following);
			following.getFollowings().add(account);
			following.getFollowers().add(account);
		} else {
			following.getRequests().add(account);
		}
		return restore();
	}

	@Path("remove/{following,[0-9a-zA-Z_]+}")
	@Validation(rules = "actionValidationRules")
	public ActionResult remove() {
		if (following == null) {
			return NOT_FOUND;
		}
		account.getFollowings().remove(following);
		following.getFollowers().remove(account);
		following.getRequests().remove(account);
		return restore();
	}

}
