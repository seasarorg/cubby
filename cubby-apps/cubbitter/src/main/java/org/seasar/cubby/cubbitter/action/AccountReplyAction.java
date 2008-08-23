package org.seasar.cubby.cubbitter.action;

import java.util.Collection;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.Pager;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/reply")
public class AccountReplyAction extends AbstractAccountAction {

	public EntryService entryService;

	@RequestParameter
	public int pageNo = 1;

	public Collection<Entry> entries;

	public Pager pager;

	public ValidationRules validationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			addAll(accountValidationRules);
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		long count = entryService.getRepliesCountByAccount(account);
		pager = new Pager(count, pageNo, Constants.ENTRIES_MAX_RESULT);
		entries = entryService.findRepliesByAccount(account, pager
				.getFirstResult(), pager.getMaxResults());
		return new Forward("/account/reply/index.jsp");
	}
}
