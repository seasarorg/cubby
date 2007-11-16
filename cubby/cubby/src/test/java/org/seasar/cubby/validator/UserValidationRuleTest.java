package org.seasar.cubby.validator;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.extension.unit.S2TestCase;

public class UserValidationRuleTest extends S2TestCase {

	public MockAction action;

	public ValidationProcessor validationProcessor;

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testUserValidation() {
		ActionErrors errors = action.getErrors();
		Object form = action;
		ValidationRules rules = action.rules;
		Map<String, Object[]> params = new HashMap<String, Object[]>();

		validationProcessor.process(errors, params, form, rules);
		assertTrue(errors.isEmpty());

		action.value2 = "ng";
		validationProcessor.process(errors, params, form, rules);
		assertFalse(errors.isEmpty());
	}

	public static class MockAction extends Action {

		public String value1;

		public String value2;

		public ValidationRules rules = new DefaultValidationRules() {
			@Override
			public void initialize() {
				add(new UserValidationRule());
			}
		};

		class UserValidationRule implements ValidationRule {

			public void apply(Map<String, Object[]> params, Object form,
					ActionErrors errors, CubbyConfiguration cubbyConfiguration) {
				if ("ng".equals(value1) || "ng".equals(value2)) {
					errors.add("validation fail", "value1", "value2");
				}
			}

		}
	}
}
