/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
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
package org.seasar.cubby.internal.action.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNull;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.InitializeMethod;
import org.seasar.cubby.action.PostRenderMethod;
import org.seasar.cubby.action.PreRenderMethod;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.beans.PropertyNotFoundException;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;

public class ActionContextImplTest {

	@Before
	public void setupProvider() {
		ProviderFactory.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
	}

	@After
	public void teardownProvider() {
		ProviderFactory.clear();
	}

	@Test
	public void constructWithNormalAction() throws Exception {
		Action action = new NormalAction();
		Class<?> actionClass = action.getClass();
		Method method = action.getClass().getMethod("method1");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);
		assertSame(action, actionContext.getAction());
		assertEquals(actionClass, actionContext.getActionClass());
		assertEquals(method, actionContext.getActionMethod());
		assertSame(actionErrors, actionContext.getActionErrors());
		assertEquals(flashMap, actionContext.getFlashMap());

		assertSame(action.getErrors(), actionContext.getActionErrors());
		assertSame(action.getFlash(), actionContext.getFlashMap());

		System.out.println(actionContext);
	}

	@Test
	public void invokeWithNormalAction() throws Exception {
		NormalAction action = new NormalAction();
		Class<?> actionClass = action.getClass();
		Method method = action.getClass().getMethod("method1");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertFalse(action.isInitialized());
		assertFalse(action.isPrerendered());
		assertFalse(action.isPostrendered());

		actionContext.invokeInitializeMethod();
		assertTrue(action.isInitialized());
		assertFalse(action.isPrerendered());
		assertFalse(action.isPostrendered());

		actionContext.invokePreRenderMethod();
		assertTrue(action.isInitialized());
		assertTrue(action.isPrerendered());
		assertFalse(action.isPostrendered());

		actionContext.invokePostRenderMethod();
		assertTrue(action.isInitialized());
		assertTrue(action.isPrerendered());
		assertTrue(action.isPostrendered());
	}

	@Test
	public void constructWithPojoAction() throws Exception {
		PojoAction action = new PojoAction();
		Class<?> actionClass = action.getClass();
		Method method = action.getClass().getMethod("method1");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);
		assertSame(action, actionContext.getAction());
		assertEquals(actionClass, actionContext.getActionClass());
		assertEquals(method, actionContext.getActionMethod());
		assertSame(actionErrors, actionContext.getActionErrors());
		assertEquals(flashMap, actionContext.getFlashMap());

		System.out.println(actionContext);
	}

	@Test
	public void invokeWithPojoAction() throws Exception {
		PojoAction action = new PojoAction();
		Class<?> actionClass = action.getClass();
		Method method = action.getClass().getMethod("method2");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertFalse(action.isInitialized());
		assertFalse(action.isPrerendered());
		assertFalse(action.isPostrendered());

		actionContext.invokeInitializeMethod();
		assertTrue(action.isInitialized());
		assertFalse(action.isPrerendered());
		assertFalse(action.isPostrendered());

		actionContext.invokePreRenderMethod();
		assertTrue(action.isInitialized());
		assertTrue(action.isPrerendered());
		assertFalse(action.isPostrendered());

		actionContext.invokePostRenderMethod();
		assertTrue(action.isInitialized());
		assertTrue(action.isPrerendered());
		assertTrue(action.isPostrendered());
	}

	@Test
	public void getForm_noAnnotateMethod() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class.getMethod("noAnnotate");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertSame(action, actionContext.getFormBean());
		assertFalse(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateValidFormName() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class.getMethod("annotateValidFormName");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertSame(action.getMyForm(), actionContext.getFormBean());
		assertTrue(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateAllPropertiesBindingType() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class
				.getMethod("annotateAllPropertiesBindingType");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertSame(action, actionContext.getFormBean());
		assertTrue(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateOnlySpecifiedPropertiesBindingType()
			throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class
				.getMethod("annotateOnlySpecifiedPropertiesBindingType");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertSame(action.getMyForm(), actionContext.getFormBean());
		assertFalse(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateNoneBindingType() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class.getMethod("annotateNoneBindingType");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertNull(actionContext.getFormBean());
		try {
			actionContext.isBindRequestParameterToAllProperties();
			fail();
		} catch (IllegalStateException e) {
			// ok
		}

	}

	@Test
	public void getForm_annotateNullFormName() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class.getMethod("annotateNullFormName");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		try {
			assertNull(actionContext.getFormBean());
			fail();
		} catch (ActionException e) {
			// ok
		}
	}

	@Test
	public void getForm_annotateNotExistFormName() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class.getMethod("annotateNotExistFormName");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		try {
			assertNull(actionContext.getFormBean());
			fail();
		} catch (PropertyNotFoundException e) {
			// ok
		}
	}

	@Test
	public void getForm_annotateThisFormName() throws Exception {
		FormAction action = new FormAction();
		Class<?> actionClass = action.getClass();
		Method method = FormAction.class.getMethod("annotateThisFormName");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		assertSame(action, actionContext.getFormBean());
		assertTrue(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void clearFlash() throws Exception {
		Action action = new NormalAction();
		Class<?> actionClass = action.getClass();
		Method method = action.getClass().getMethod("method1");
		ActionErrors actionErrors = new ActionErrorsImpl();
		Map<String, Object> flashMap = new HashMap<String, Object>();
		ActionContext actionContext = new ActionContextImpl(action,
				actionClass, method, actionErrors, flashMap);

		action.getFlash().put("key", "value");
		assertFalse(actionContext.getFlashMap().isEmpty());
		assertFalse(action.getFlash().isEmpty());
		actionContext.clearFlash();
		assertTrue(actionContext.getFlashMap().isEmpty());
		assertTrue(action.getFlash().isEmpty());
	}

	public static class PojoAction {
		public ActionResult method1() {
			return null;
		}

		@InitializeMethod("initialize")
		@PreRenderMethod("prerender")
		@PostRenderMethod("postrender")
		public ActionResult method2() {
			return null;
		}

		private boolean initialized = false;

		private boolean prerendered = false;

		private boolean postrendered = false;

		public void initialize() {
			initialized = true;
		}

		public void prerender() {
			prerendered = true;
		}

		public void postrender() {
			postrendered = true;
		}

		public boolean isInitialized() {
			return initialized;
		}

		public boolean isPrerendered() {
			return prerendered;
		}

		public boolean isPostrendered() {
			return postrendered;
		}

	}

	public static class NormalAction extends Action {
		public ActionResult method1() {
			return null;
		}

		private boolean initialized = false;

		private boolean prerendered = false;

		private boolean postrendered = false;

		@Override
		public void initialize() {
			initialized = true;
		}

		@Override
		public void prerender() {
			prerendered = true;
		}

		@Override
		public void postrender() {
			postrendered = true;
		}

		public boolean isInitialized() {
			return initialized;
		}

		public boolean isPrerendered() {
			return prerendered;
		}

		public boolean isPostrendered() {
			return postrendered;
		}

	}

	public static class FormAction {

		private Object myForm = new Object();

		public Object getMyForm() {
			return myForm;
		}

		public Object getNullForm() {
			return null;
		}

		public ActionResult noAnnotate() {
			return null;
		}

		@Form("myForm")
		public ActionResult annotateValidFormName() {
			return null;
		}

		@Form(bindingType = RequestParameterBindingType.ALL_PROPERTIES)
		public ActionResult annotateAllPropertiesBindingType() {
			return null;
		}

		@Form(value = "myForm", bindingType = RequestParameterBindingType.ONLY_SPECIFIED_PROPERTIES)
		public ActionResult annotateOnlySpecifiedPropertiesBindingType() {
			return null;
		}

		@Form(bindingType = RequestParameterBindingType.NONE)
		public ActionResult annotateNoneBindingType() {
			return null;
		}

		@Form("nullForm")
		public ActionResult annotateNullFormName() {
			return null;
		}

		@Form("illegal")
		public ActionResult annotateNotExistFormName() {
			return null;
		}

		@Form("this")
		public ActionResult annotateThisFormName() {
			return null;
		}

	}

}
