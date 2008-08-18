package org.seasar.cubby.cubbitter.action;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class SearchAction extends AbstractAction {

	@RequestParameter
	public String keyword;

	@RequestParameter
	public int pageNo = 1;

	public List<Account> accounts;

	public ValidationRules searchValidation = new DefaultValidationRules(
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
				accounts = accountService.findByKeyword(keyword, pageNo);
			}
		}

		return new Forward("index.jsp");
	}

}
