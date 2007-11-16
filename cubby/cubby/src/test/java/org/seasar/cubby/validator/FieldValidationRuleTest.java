package org.seasar.cubby.validator;

import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.validator.validators.ArrayMaxSizeValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;
import org.seasar.extension.unit.S2TestCase;

public class FieldValidationRuleTest extends S2TestCase {

	public ActionErrors errors = new ActionErrorsImpl();

	@Override
	protected void setUp() throws Exception {
		include(this.getClass().getName().replaceAll("\\.", "/") + ".dicon");
	}

	public void testApply1() throws Exception {
		Map<String, Object[]> params = new HashMap<String, Object[]>();
		params.put("name", new Object[] { "aa" });

		ValidationRule rule = new FieldValidationRule("name",
				new RequiredValidator(), new ArrayMaxSizeValidator(1));
		rule.apply(params, null, errors);
		assertTrue(errors.isEmpty());
	}

	public void testApply2() throws Exception {
		Map<String, Object[]> params = new HashMap<String, Object[]>();
		params.put("name", new Object[] { "aa", "bb" });

		ValidationRule rule = new FieldValidationRule("name",
				new RequiredValidator(), new ArrayMaxSizeValidator(1));
		rule.apply(params, null, errors);
		assertFalse(errors.isEmpty());
		assertFalse(errors.getFields().get("name").isEmpty());
		assertTrue(errors.getIndexedFields().get("name").get(0).isEmpty());
	}

	public void testApply3() throws Exception {
		Map<String, Object[]> params = new HashMap<String, Object[]>();

		ValidationRule rule = new FieldValidationRule("name",
				new RequiredValidator(), new ArrayMaxSizeValidator(1));
		rule.apply(params, null, errors);
		assertFalse(errors.isEmpty());
		assertFalse(errors.getFields().get("name").isEmpty());
		assertEquals(1, errors.getIndexedFields().get("name").get(0).size());
	}

	public static class MockAction extends Action {
		public String name;
	}

}
