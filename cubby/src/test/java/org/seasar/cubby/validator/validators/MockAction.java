package org.seasar.cubby.validator.validators;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.impl.ActionErrorsImpl;

public class MockAction extends Action {

	public MockAction() {
		this.setErrors(new ActionErrorsImpl());
	}
}
