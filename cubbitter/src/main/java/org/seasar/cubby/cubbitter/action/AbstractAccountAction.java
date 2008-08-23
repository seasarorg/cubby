package org.seasar.cubby.cubbitter.action;

import static org.seasar.cubby.cubbitter.util.SendErrors.FORBIDDEN;
import static org.seasar.cubby.cubbitter.util.SendErrors.NOT_FOUND;

import java.util.Map;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.RequestParameter;
import org.seasar.cubby.cubbitter.entity.Account;
import org.seasar.cubby.validator.ValidationException;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

public class AbstractAccountAction extends AbstractAction {

	@RequestParameter
	public Account account;

	private class ExistAccountValidationRule implements ValidationRule {

		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) throws ValidationException {
			if (account == null) {
				throw new ValidationException(NOT_FOUND);
			}
		}

	}

	protected class LoginAccountOnlyValidationRule implements ValidationRule {

		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) throws ValidationException {
			if (loginAccount == null || !loginAccount.equals(account)) {
				throw new ValidationException(FORBIDDEN);
			}
		}

	}

	protected class FollowerOnlyValidationRule implements ValidationRule {

		public void apply(Map<String, Object[]> params, Object form,
				ActionErrors errors) throws ValidationException {
			if (!account.equals(loginAccount) && !account.isOpen()) {
				if (!account.getFollowers().contains(loginAccount)) {
					throw new ValidationException(FORBIDDEN);
				}
			}
		}

	}

	protected ValidationRules accountValidationRules = new AbstractValidationRules() {

		@Override
		protected void initialize() {
			add(RESOURCE, new ExistAccountValidationRule());
		}
		
	};
}
