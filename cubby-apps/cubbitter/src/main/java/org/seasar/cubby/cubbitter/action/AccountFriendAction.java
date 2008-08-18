package org.seasar.cubby.cubbitter.action;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.Pager;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/friend")
public class AccountFriendAction extends AbstractAccountAction {

	public EntryService entryService;

	@RequestParameter
	public int pageNo = 1;

	public List<Entry> entries;

	public Pager<Entry> pager;

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		List<Entry> entries = entryService.getFollowingEntries(account);
		pager = new Pager<Entry>(entries, pageNo, Constants.ENTRIES_MAX_RESULT);
		this.entries = pager.subList();
		return new Forward("/account/friends/index.jsp");
	}

}
