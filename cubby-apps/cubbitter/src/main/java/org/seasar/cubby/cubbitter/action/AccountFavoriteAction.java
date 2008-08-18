package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import java.util.List;

import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Path;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.cubbitter.Constants;
import org.seasar.cubby.cubbitter.entity.Entry;
import org.seasar.cubby.cubbitter.util.Pager;
import org.seasar.cubby.validator.DefaultValidationRules;
import org.seasar.cubby.validator.ValidationRules;

@Path("{account,[0-9a-zA-Z_]+}/favorite")
public class AccountFavoriteAction extends AbstractAccountAction {

	@RequestParameter
	public Entry entry;

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
		List<Entry> entries = account.getFavorites();
		pager = new Pager<Entry>(entries, pageNo, Constants.ENTRIES_MAX_RESULT);
		this.entries = pager.subList();
		return new Forward("/account/favorite/index.jsp");
	}

	@Path("add/{entry,[0-9]+}")
	@Validation(rules = "validationRules")
	public ActionResult add() {
		if (entry == null) {
			return NOT_FOUND;
		}
		account.getFavorites().add(entry);
		return restore();
	}

	@Path("remove/{entry,[0-9]+}")
	@Validation(rules = "validationRules")
	public ActionResult remove() {
		if (entry == null) {
			return NOT_FOUND;
		}
		account.getFavorites().remove(entry);
		return restore();
	}

}
