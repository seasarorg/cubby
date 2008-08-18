package org.seasar.cubby.cubbitter.action;

import java.util.Collection;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/reply")
public class AccountReplyAction extends AbstractAccountAction {

	public EntryService entryService;

	public Collection<Entry> entries;

	public ValidationRules validationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}

	};

	@Validation(rules = "validationRules")
	public ActionResult index() {
		entries = account.getReplies();
		return new Forward("/account/reply/index.jsp");
	}

}
