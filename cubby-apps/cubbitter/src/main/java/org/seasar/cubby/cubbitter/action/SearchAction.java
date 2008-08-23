package org.seasar.cubby.cubbitter.action;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.cubbitter.util.Pager;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class SearchAction extends AbstractAction {

	@RequestParameter
	public String keyword;

	@RequestParameter
	public int pageNo = 1;

	public List<Account> accounts;

	public Pager pager;

	public ValidationRules searchValidation = new AbstractValidationRules(
			"search.") {
		@Override
		public void initialize() {
			add("keyword", new RequiredValidator());
		}
	};

	@Validation(rules = "searchValidation", errorPage = "index.jsp")
	public ActionResult index() {
		if (keyword != null) {
			keyword = keyword.trim();
			if (keyword.length() > 0) {
				long count = accountService.getCountByKeyword(keyword);
				pager = new Pager(count, pageNo, Constants.ACCOUNTS_MAX_RESULT);
				accounts = accountService.findByKeyword(keyword, pager
						.getFirstResult(), pager.getMaxResults());
			}
		}

		return new Forward("index.jsp");
	}

}
