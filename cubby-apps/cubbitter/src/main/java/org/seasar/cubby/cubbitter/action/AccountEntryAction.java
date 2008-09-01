package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.action.ActionErrors;
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
import org.seasar.cubby.util.Messages;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;
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

	public Pager pager;

	public ValidationRules indexValidationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			addAll(accountValidationRules);
			add(RESOURCE, new ValidationRule() {

				public void apply(Map<String, Object[]> params, Object form,
						ActionErrors errors) throws ValidationException {
					if (account.isOpen()) {
						return;
					}
					if (loginAccount != null) {
						if (account.equals(loginAccount)) {
							return;
						}
						if (loginAccount.getFollowings().contains(account)) {
							return;
						}
					}
					errors.add(Messages.getText("member.msg.noFollowing"));
					throw new ValidationException();
				}

			});
		}

	};

	@Validation(rules = "indexValidationRules", errorPage = "/account/entry/index.jsp")
	public ActionResult index() {
		long count = entryService.getCountByAccount(account, loginAccount);
		pager = new Pager(count, pageNo, Constants.ENTRIES_MAX_RESULT);
		entries = entryService.findByAccount(account, loginAccount, pager
				.getFirstResult(), pager.getMaxResults());
		return new Forward("/account/entry/index.jsp");
	}

	public ValidationRules addValidation = new AbstractValidationRules() {
		@Override
		public void initialize() {
			addAll(accountValidationRules);
			add("text", new RequiredValidator(), new MaxLengthValidator(140));
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}
	};

	@Validation(rules = "addValidation", errorPage = "index.jsp")
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
			Account replyaAccount = accountService.findByName(replyAccountName);
			if (replyaAccount != null) {
				replyaAccount.getReplies().add(entry);
			}
		}
	}

	public ValidationRules removeValidation = new AbstractValidationRules() {
		@Override
		public void initialize() {
			addAll(accountValidationRules);
			add(DATA_CONSTRAINT, new LoginAccountOnlyValidationRule());
		}
	};

	@Path("remove/{entry,[0-9]+}")
	@Validation(rules = "removeValidation")
	public ActionResult remove() {
		if (entry == null) {
			return NOT_FOUND;
		}
		account.getEntries().remove(entry);
		entryService.remove(entry);
		return restore();
	}

}
