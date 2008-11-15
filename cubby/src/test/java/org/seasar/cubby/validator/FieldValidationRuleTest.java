/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.validator;

import java.util.HashMap;
import java.util.Map;

import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.controller.MessagesBehaviour;
import org.seasar.cubby.internal.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.validator.validators.ArrayMaxSizeValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;
import static org.junit.Assert.*;

public class FieldValidationRuleTest {

	private ActionErrors errors = new ActionErrorsImpl();

	@Before
	public void setupContainer() {
		MockContainerProvider.setContainer(new Container() {

			public <T> T lookup(Class<T> type) {
				if (type.equals(MessagesBehaviour.class)) {
					return type.cast(new DefaultMessagesBehaviour());
				}
				return null;
			}

		});
	}

	@Test
	public void apply1() throws Exception {
		Map<String, Object[]> params = new HashMap<String, Object[]>();
		params.put("name", new Object[] { "aa" });

		ValidationRule rule = new FieldValidationRule("name",
				new RequiredValidator(), new ArrayMaxSizeValidator(1));
		rule.apply(params, null, errors);
		assertTrue(errors.isEmpty());
	}

	@Test
	public void apply2() throws Exception {
		Map<String, Object[]> params = new HashMap<String, Object[]>();
		params.put("name", new Object[] { "aa", "bb" });

		ValidationRule rule = new FieldValidationRule("name",
				new RequiredValidator(), new ArrayMaxSizeValidator(1));
		rule.apply(params, null, errors);
		assertFalse(errors.isEmpty());
		assertFalse(errors.getFields().get("name").isEmpty());
		assertTrue(errors.getIndexedFields().get("name").get(0).isEmpty());
	}

	@Test
	public void apply3() throws Exception {
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
