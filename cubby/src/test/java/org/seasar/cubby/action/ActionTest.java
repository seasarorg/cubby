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
package org.seasar.cubby.action;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertSame;
import static org.junit.Assert.assertTrue;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.junit.Test;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;

public class ActionTest {

	private final ActionImpl action = new ActionImpl();

	@Test
	public void errors() {
		ActionErrors errors = new ActionErrorsImpl();
		action.setErrors(errors);
		assertSame(errors, action.errors);
		assertSame(errors, action.getErrors());
	}

	@Test
	public void flash() {
		Map<String, Object> flash = new HashMap<String, Object>();
		action.setFlash(flash);
		assertSame(flash, action.flash);
		assertSame(flash, action.getFlash());
	}

	@Test
	public void noAnnotationInititalize() throws Exception {
		Method method = ActionImpl.class.getMethod("noannotation");
		action.invokeInitializeMethod(method);
		assertTrue(action.initialized1);
		assertFalse(action.initialized2);
		assertFalse(action.prerendered1);
		assertFalse(action.prerendered2);
		assertFalse(action.postrendered1);
		assertFalse(action.postrendered2);
	}

	@Test
	public void annotationInititalize() throws Exception {
		Method method = ActionImpl.class.getMethod("annotation");
		action.invokeInitializeMethod(method);
		assertFalse(action.initialized1);
		assertTrue(action.initialized2);
		assertFalse(action.prerendered1);
		assertFalse(action.prerendered2);
		assertFalse(action.postrendered1);
		assertFalse(action.postrendered2);
	}

	@Test
	public void noAnnotationPrerender() throws Exception {
		Method method = ActionImpl.class.getMethod("noannotation");
		action.invokePreRenderMethod(method);
		assertFalse(action.initialized1);
		assertFalse(action.initialized2);
		assertTrue(action.prerendered1);
		assertFalse(action.prerendered2);
		assertFalse(action.postrendered1);
		assertFalse(action.postrendered2);
	}

	@Test
	public void annotationPrerender() throws Exception {
		Method method = ActionImpl.class.getMethod("annotation");
		action.invokePreRenderMethod(method);
		assertFalse(action.initialized1);
		assertFalse(action.initialized2);
		assertFalse(action.prerendered1);
		assertTrue(action.prerendered2);
		assertFalse(action.postrendered1);
		assertFalse(action.postrendered2);
	}

	@Test
	public void noAnnotationPostrender() throws Exception {
		Method method = ActionImpl.class.getMethod("noannotation");
		action.invokePostRenderMethod(method);
		assertFalse(action.initialized1);
		assertFalse(action.initialized2);
		assertFalse(action.prerendered1);
		assertFalse(action.prerendered2);
		assertTrue(action.postrendered1);
		assertFalse(action.postrendered2);
	}

	@Test
	public void annotationPostrender() throws Exception {
		Method method = ActionImpl.class.getMethod("annotation");
		action.invokePostRenderMethod(method);
		assertFalse(action.initialized1);
		assertFalse(action.initialized2);
		assertFalse(action.prerendered1);
		assertFalse(action.prerendered2);
		assertFalse(action.postrendered1);
		assertTrue(action.postrendered2);
	}

	public static class ActionImpl extends Action {

		boolean initialized1 = false;
		boolean initialized2 = false;
		boolean prerendered1 = false;
		boolean prerendered2 = false;
		boolean postrendered1 = false;
		boolean postrendered2 = false;

		public ActionResult noannotation() {
			return null;
		}

		@InitializeMethod("initialize2")
		@PreRenderMethod("prerender2")
		@PostRenderMethod("postrender2")
		public ActionResult annotation() {
			return null;
		}

		@Override
		protected void initialize() {
			super.initialize();
			initialized1 = true;
		}

		public void initialize2() {
			initialized2 = true;
		}

		@Override
		protected void prerender() {
			super.prerender();
			prerendered1 = true;
		}

		public void prerender2() {
			prerendered2 = true;
		}

		@Override
		protected void postrender() {
			super.postrender();
			postrendered1 = true;
		}

		public void postrender2() {
			postrendered2 = true;
		}

	}

}
