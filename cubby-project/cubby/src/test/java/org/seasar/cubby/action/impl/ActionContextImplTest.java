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
package org.seasar.cubby.action.impl;

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
import org.seasar.cubby.action.impl.ActionContextImpl;
import org.seasar.cubby.action.impl.ActionErrorsImpl;
import org.seasar.cubby.plugin.PluginRegistry;
import org.seasar.cubby.plugins.BinderPlugin;
import org.seasar.cubby.spi.BeanDescProvider;
import org.seasar.cubby.spi.beans.impl.DefaultBeanDescProvider;

public class ActionContextImplTest {

	private final PluginRegistry pluginRegistry = PluginRegistry.getInstance();

	@Before
	public void setupProvider() {
		final BinderPlugin binderPlugin = new BinderPlugin();
		binderPlugin.bind(BeanDescProvider.class).toInstance(
				new DefaultBeanDescProvider());
		pluginRegistry.register(binderPlugin);
	}

	@After
	public void teardownProvider() {
		pluginRegistry.clear();
	}

	@Test
	public void constructWithNormalAction() throws Exception {
		final Action action = new NormalAction();
		final Class<?> actionClass = action.getClass();
		final Method method = action.getClass().getMethod("method1");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);
		assertSame(action, actionContext.getAction());
		assertEquals(actionClass, actionContext.getActionClass());
		assertEquals(method, actionContext.getActionMethod());
		assertSame(actionErrors, actionContext.getActionErrors());
		assertEquals(flashMap, actionContext.getFlashMap());

		// assertSame(action.getErrors(), actionContext.getActionErrors());
		// assertSame(action.getFlash(), actionContext.getFlashMap());

		System.out.println(actionContext);
	}

	@Test
	public void invokeWithNormalAction() throws Exception {
		final NormalAction action = new NormalAction();
		final Class<?> actionClass = action.getClass();
		final Method method = action.getClass().getMethod("method1");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

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
		final PojoAction action = new PojoAction();
		final Class<?> actionClass = action.getClass();
		final Method method = action.getClass().getMethod("method1");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);
		assertSame(action, actionContext.getAction());
		assertEquals(actionClass, actionContext.getActionClass());
		assertEquals(method, actionContext.getActionMethod());
		assertSame(actionErrors, actionContext.getActionErrors());
		assertEquals(flashMap, actionContext.getFlashMap());

		System.out.println(actionContext);
	}

	@Test
	public void invokeWithPojoAction() throws Exception {
		final PojoAction action = new PojoAction();
		final Class<?> actionClass = action.getClass();
		final Method method = action.getClass().getMethod("method2");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

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
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class.getMethod("noAnnotate");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		assertSame(action, actionContext.getFormBean());
		assertFalse(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateValidFormName() throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateValidFormName");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		assertSame(action.getMyForm(), actionContext.getFormBean());
		assertTrue(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateAllPropertiesBindingType() throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateAllPropertiesBindingType");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		assertSame(action, actionContext.getFormBean());
		assertTrue(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateOnlySpecifiedPropertiesBindingType()
			throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateOnlySpecifiedPropertiesBindingType");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		assertSame(action.getMyForm(), actionContext.getFormBean());
		assertFalse(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void getForm_annotateNoneBindingType() throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateNoneBindingType");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		assertNull(actionContext.getFormBean());
		try {
			actionContext.isBindRequestParameterToAllProperties();
			fail();
		} catch (final IllegalStateException e) {
			// ok
		}

	}

	@Test
	public void getForm_annotateNullFormName() throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateNullFormName");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		try {
			assertNull(actionContext.getFormBean());
			fail();
		} catch (final ActionException e) {
			// ok
		}
	}

	@Test
	public void getForm_annotateNotExistFormName() throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateNotExistFormName");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		try {
			assertNull(actionContext.getFormBean());
			fail();
		} catch (final ActionException e) {
			// ok
		}
	}

	@Test
	public void getForm_annotateThisFormName() throws Exception {
		final FormAction action = new FormAction();
		final Class<?> actionClass = action.getClass();
		final Method method = FormAction.class
				.getMethod("annotateThisFormName");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		assertSame(action, actionContext.getFormBean());
		assertTrue(actionContext.isBindRequestParameterToAllProperties());
	}

	@Test
	public void clearFlash() throws Exception {
		final Object action = new NormalAction();
		final Class<?> actionClass = action.getClass();
		final Method method = action.getClass().getMethod("method1");
		final ActionErrors actionErrors = new ActionErrorsImpl();
		final Map<String, Object> flashMap = new HashMap<String, Object>();
		final ActionContext actionContext = new ActionContextImpl();
		actionContext.initialize(action, actionClass, method, actionErrors,
				flashMap);

		flashMap.put("key", "value");
		assertFalse(actionContext.getFlashMap().isEmpty());
		assertFalse(flashMap.isEmpty());
		actionContext.clearFlash();
		assertTrue(actionContext.getFlashMap().isEmpty());
		assertTrue(flashMap.isEmpty());
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

		private final Object myForm = new Object();

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
