package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.FORBIDDEN;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.Pager;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}")
public class AccountAction extends AbstractAccountAction {

	public EntryService entryService;

	@RequestParameter
	public int pageNo = 1;

	public List<Entry> entries;

	public Pager<Entry> pager;

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		if (account.equals(loginAccount)) {
			return new Redirect(AccountFriendAction.class, "index").param(
					"account", account.getName());
		} else {
			if (account.isOpen()
					|| (loginAccount != null && loginAccount.getFollowings()
							.contains(account))) {
				return new Redirect(AccountEntryAction.class, "index").param(
						"account", account.getName());
			}
			return FORBIDDEN;
		}
	}

}
