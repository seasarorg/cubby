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

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.junit.Test;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.internal.controller.ThreadContext;

/**
 * 
 * @author baba
 */
public class ActionUtilsTest {

	@Test
	public void actionContext() {
		ActionContext actionContext = createMock(ActionContext.class);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andReturn(actionContext);
		replay(actionContext, request);

		ThreadContext.newContext(request);
		assertSame(actionContext, ActionUtils.actionContext());

		verify(request, actionContext);
	}

	@Test
	public void errors() {
		ActionContext actionContext = createMock(ActionContext.class);
		ActionErrors actionErrors = createMock(ActionErrors.class);
		expect(actionContext.getActionErrors()).andReturn(actionErrors);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andReturn(actionContext);
		replay(actionContext, actionErrors, request);

		ThreadContext.newContext(request);
		assertSame(actionErrors, ActionUtils.errors());

		verify(actionContext, actionErrors, request);
	}

	@Test
	public void flash() {
		ActionContext actionContext = createMock(ActionContext.class);
		Map<String, Object> flashMap = new HashMap<String, Object>();
		expect(actionContext.getFlashMap()).andReturn(flashMap);
		HttpServletRequest request = createMock(HttpServletRequest.class);
		expect(request.getAttribute(CubbyConstants.ATTR_ACTION_CONTEXT))
				.andReturn(actionContext);
		replay(actionContext, request);

		ThreadContext.newContext(request);
		assertSame(flashMap, ActionUtils.flash());

		verify(actionContext, request);
	}

	@Test
	public void isActionClass() throws Exception {
		assertTrue("アクションクラスであればtrue", ActionUtils
				.isActionClass(ChildAction.class));
		assertTrue("Actionを継承していないクラスでもアクションクラスである", ActionUtils
				.isActionClass(Object.class));
		assertFalse("抽象クラスはアクションクラスではない", ActionUtils
				.isActionClass(ParentAction.class));
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

		public ActionResult m4(int value) {
			return null;
		}

		public Object m5() {
			return null;
		}
	}

}
