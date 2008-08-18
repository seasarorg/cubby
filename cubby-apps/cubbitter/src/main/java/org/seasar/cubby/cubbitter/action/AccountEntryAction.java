package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import java.util.Date;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.service.EntryService;
import org.seasar.cubby.cubbitter.util.Pager;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.MaxLengthValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

@Path("{account,[0-9a-zA-Z_]+}/entry")
public class AccountEntryAction extends AbstractAccountAction {

	public EntryService entryService;

	@RequestParameter
	public String text;

	@RequestParameter
	public Entry entry;

	@RequestParameter
	public int pageNo = 1;

	public List<Entry> entries;

	public Pager<Entry> pager;

	public ValidationRules indexValidationRules = new DefaultValidationRules() {

		@Override
		protected void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new FollowerOnlyValidationRule());
		}

	};

	@Validation(rules = "indexValidationRules")
	public ActionResult index() {
		List<Entry> entries = account.getEntries();
		pager = new Pager<Entry>(entries, pageNo, Constants.ENTRIES_MAX_RESULT);
		this.entries = pager.subList();
		return new Forward("/account/entry/index.jsp");
	}

	public ValidationRules entryValidation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add("text", new RequiredValidator(), new MaxLengthValidator(140));
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}
	};

	@Validation(rules = "entryValidation", errorPage = "index.jsp")
	public ActionResult add() {
		text = text.trim();
		if (text.length() > 0) {
			Entry entry = new Entry();
			entry.setAccount(account);
			entry.setText(this.text);
			entry.setPost(new Date());
			entryService.persist(entry);

			reply(entry);
		}
		return restore();
	}

	private void reply(Entry entry) {
		Pattern pattern = Pattern.compile("^@([_0-9a-zA-Z]+)");
		Matcher matcher = pattern.matcher(text);
		if (matcher.find()) {
			String replyAccountName = matcher.group(1);
			Account replyaAccount = accountService
					.findByName(replyAccountName);
			if (replyaAccount != null) {
				replyaAccount.getReplies().add(entry);
			}
		}
	}

	public ValidationRules removeValidation = new DefaultValidationRules() {
		@Override
		public void initialize() {
			add(DATA_CONSTRAINT, new ExistAccountValidationRule());
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}
	};

	@Path("remove/{entry,[0-9]+}")
	@Validation(rules = "removeValidation")
	public ActionResult remove() {
		if (entry == null) {
			return NOT_FOUND;
		}
		List<Entry> entries = account.getEntries();
		entries.remove(entry);
		entryService.remove(entry);
		return restore();
	}

}
