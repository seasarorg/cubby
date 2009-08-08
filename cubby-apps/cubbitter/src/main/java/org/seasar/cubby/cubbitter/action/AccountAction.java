package org.seasar.cubby.cubbitter.action;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Redirect;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}")
public class AccountAction extends AbstractAccountAction {

	public EntryService entryService;

	@RequestParameter
	public int pageNo = 1;

	public List<Entry> entries;

	public ValidationRules validationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			addAll(accountValidationRules);
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		if (account.equals(loginAccount)) {
			return new Redirect(AccountFriendAction.class).param("account",
					account.getName());
		} else {
			return new Redirect(AccountEntryAction.class).param("account",
					account.getName());
		}
	}

}
