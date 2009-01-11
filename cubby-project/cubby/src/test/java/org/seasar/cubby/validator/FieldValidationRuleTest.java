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

import static org.easymock.EasyMock.*;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.controller.MessagesBehaviour;
import org.seasar.cubby.controller.impl.DefaultMessagesBehaviour;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;
import org.seasar.cubby.mock.MockContainerProvider;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.validator.validators.ArrayMaxSizeValidator;
import org.seasar.cubby.validator.validators.RequiredValidator;

public class FieldValidationRuleTest {

	private ActionErrors errors = new ActionErrorsImpl();

	@Before
	public void setup() {
		ProviderFactory.bind(ContainerProvider.class).toInstance(
				new MockContainerProvider(new Container() {

					public <T> T lookup(Class<T> type) {
						if (type.equals(MessagesBehaviour.class)) {
							return type.cast(new DefaultMessagesBehaviour());
						}
						return null;
					}

				}));
	}

	@After
	public void teardown() {
		ProviderFactory.clear();
	}

	@Test
	public void apply1() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				Map<String, Object[]> params = new HashMap<String, Object[]>();
				params.put("name", new Object[] { "aa" });

				ValidationRule rule = new FieldValidationRule("name",
						new RequiredValidator(), new ArrayMaxSizeValidator(1));
				rule.apply(params, null, errors);
				assertTrue(errors.isEmpty());
				return null;
			}

		});
	}

	@Test
	public void apply2() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(null);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {

				Map<String, Object[]> params = new HashMap<String, Object[]>();
				params.put("name", new Object[] { "aa", "bb" });

				ValidationRule rule = new FieldValidationRule("name",
						new RequiredValidator(), new ArrayMaxSizeValidator(1));
				rule.apply(params, null, errors);
				assertFalse(errors.isEmpty());
				assertFalse(errors.getFields().get("name").isEmpty());
				assertTrue(errors.getIndexedFields().get("name").get(0)
						.isEmpty());
				return null;
			}
		});
	}

	@Test
	public void apply3() throws Exception {
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getLocale()).andStubReturn(null);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				Map<String, Object[]> params = new HashMap<String, Object[]>();

				ValidationRule rule = new FieldValidationRule("name",
						new RequiredValidator(), new ArrayMaxSizeValidator(1));
				rule.apply(params, null, errors);
				assertFalse(errors.isEmpty());
				assertFalse(errors.getFields().get("name").isEmpty());
				assertEquals(1, errors.getIndexedFields().get("name").get(0)
						.size());
				return null;
			}
		});
	}

	public static class MockAction extends Action {
		public String name;
	}

}
