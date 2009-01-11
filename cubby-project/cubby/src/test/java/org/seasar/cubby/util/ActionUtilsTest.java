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
package org.seasar.cubby.util;

import static org.easymock.EasyMock.createMock;
import static org.easymock.EasyMock.expect;
import static org.easymock.EasyMock.replay;
import static org.easymock.EasyMock.verify;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.controller.ThreadContext.Command;

/**
 * 
 * @author baba
 */
public class ActionUtilsTest {

	@Test
	public void actionContextFromThredLocal() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andStubReturn(actionContext);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				assertSame(actionContext, ActionUtils.actionContext());
				return null;
			}

		});

		verify(actionContext, request, response);
	}

	@Test
	public void actionContextFromRequest() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andStubReturn(actionContext);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, request, response);

		assertSame(actionContext, ActionUtils.actionContext(request));

		verify(actionContext, request, response);
	}

	@Test
	public void actionContextThrowsException() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, request, response);

		try {
			ActionUtils.actionContext();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

		verify(actionContext, request, response);
	}

	@Test
	public void errorsFromThrealLocal() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final ActionErrors actionErrors = createMock(ActionErrors.class);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, actionErrors, request, response);

		try {
			ActionUtils.errors();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

		verify(actionContext, actionErrors, request, response);
	}

	@Test
	public void errorsFromRequest() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final ActionErrors actionErrors = createMock(ActionErrors.class);
		expect(actionContext.getActionErrors()).andReturn(actionErrors);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andReturn(actionContext);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, actionErrors, request, response);

		assertSame(actionErrors, ActionUtils.errors(request));

		verify(actionContext, actionErrors, request, response);
	}

	@Test
	public void errorsThrowsException() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final ActionErrors actionErrors = createMock(ActionErrors.class);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, actionErrors, request, response);

		try {
			ActionUtils.errors();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

		verify(actionContext, actionErrors, request, response);
	}

	@Test
	public void flashFromThreadLocal() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		expect(actionContext.getFlashMap()).andReturn(flashMap);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andReturn(actionContext);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, request, response);

		ThreadContext.runInContext(request, response, new Command<Void>() {

			public Void execute(final HttpServletRequest request,
					final HttpServletResponse response) throws Exception {
				assertSame(flashMap, ActionUtils.flash());
				return null;
			}
		});

		verify(actionContext, request, response);
	}

	@Test
	public void flashFromRequest() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		expect(actionContext.getFlashMap()).andReturn(flashMap);
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andReturn(actionContext);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, request, response);

		assertSame(flashMap, ActionUtils.flash(request));

		verify(actionContext, request, response);
	}

	@Test
	public void flashThrowsException() throws Exception {
		final ActionContext actionContext = createMock(ActionContext.class);
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final HttpServletRequest request = createMock(HttpServletRequest.class);
		final HttpServletResponse response = createMock(HttpServletResponse.class);
		replay(actionContext, request, response);

		try {
			ActionUtils.flash();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

		verify(actionContext, request, response);
	}

	@Test
	public void isActionMethod() throws Exception {
		assertTrue("親クラスのアクションメソッド", ActionUtils
				.isActionMethod(ChildAction.class.getMethod("m1")));
		assertTrue("オーバーライドした親クラスのアクションメソッド", ActionUtils
				.isActionMethod(ChildAction.class.getMethod("m2")));
		assertTrue("子クラスのアクションメソッド", ActionUtils
				.isActionMethod(ChildAction.class.getMethod("m3")));
		assertFalse("メソッドの引数が不正", ActionUtils.isActionMethod(ChildAction.class
				.getMethod("m4", int.class)));
		assertFalse("メソッドの戻り値が不正", ActionUtils.isActionMethod(ChildAction.class
				.getMethod("m5")));
	}

	public abstract class ParentAction extends Action {
		public ActionResult m1() {
			return null;
		}

		public abstract ActionResult m2();
	}

	public class ChildAction extends ParentAction {
		@Override
		public ActionResult m2() {
			return null;
		}

		public ActionResult m3() {
			return null;
		}

		public ActionResult m4(final int value) {
			return null;
		}

		public Object m5() {
			return null;
		}
	}

}
