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
package org.seasar.cubby.mock;

import static org.seasar.cubby.action.RequestParameterBindingType.NONE;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.internal.controller.ActionResultWrapper;
import org.seasar.cubby.spi.beans.Attribute;
import org.seasar.cubby.spi.beans.BeanDesc;
import org.seasar.cubby.spi.beans.BeanDescFactory;

public class MockActionContext implements ActionContext {

	private Object action;

	private Class<?> actionClass;

	private Method actionMethod;

	private ActionErrors actionErrors = new MockActionErrors();

	private Map<String, Object> flashMap = new HashMap<String, Object>();

	public MockActionContext(Object action, Class<?> actionClass,
			Method actionMethod) {
		this.initialize(action, actionClass, actionMethod,
				new MockActionErrors(), new HashMap<String, Object>());
	}

	public MockActionContext() {
	}

	public void initialize(Object action, Class<?> actionClass,
			Method actionMethod, ActionErrors actionErrors,
			Map<String, Object> flashMap) {
		this.action = action;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
		this.actionErrors = actionErrors;
		this.flashMap = flashMap;
	}

	public Object getAction() {
		return action;
	}

	public Class<?> getActionClass() {
		return actionClass;
	}

	public Method getActionMethod() {
		return actionMethod;
	}

	/**
	 * {@inheritDoc}
	 */
	public Object getFormBean() {
		final Form form = getForm();
		if (form == null) {
			return action;
		}
		if (form.bindingType() == NONE) {
			return null;
		}
		if (Form.THIS.equals(form.value())) {
			return action;
		}

		final String propertyName = form.value();
		final BeanDesc beanDesc = BeanDescFactory
				.getBeanDesc(action.getClass());
		final Attribute attribute = beanDesc.getPropertyAttribute(propertyName);
		final Object formBean = attribute.getValue(action);
		if (formBean == null) {
			throw new ActionException(format("ECUB0102", propertyName));
		}
		return formBean;
	}

	private Form getForm() {
		final Form form;
		if (actionMethod.isAnnotationPresent(Form.class)) {
			form = actionMethod.getAnnotation(Form.class);
		} else {
			form = actionClass.getAnnotation(Form.class);
		}
		return form;
	}

	public boolean isBindRequestParameterToAllProperties() {
		final Form form = this.getForm();
		if (form == null) {
			return false;
		}

		final RequestParameterBindingType type = form.bindingType();
		switch (type) {
		case ALL_PROPERTIES:
			return true;
		case ONLY_SPECIFIED_PROPERTIES:
			return false;
		default:
			throw new IllegalStateException(type.toString());
		}
	}

	/**
	 * {@inheritDoc}
	 */
	public ActionResultWrapper invoke() throws Exception {
		System.out.println("invoke");
		return null;
	}

	public void invokeInitializeMethod() {
		this.initialized = true;
	}

	public void invokePreRenderMethod() {
		this.prerendered = true;
	}

	public void invokePostRenderMethod() {
		this.postrendered = true;
	}

	public void clearFlash() {
		flashMap.clear();
	}

	private boolean initialized = false;
	private boolean prerendered = false;
	private boolean postrendered = false;

	public boolean isInitialized() {
		return initialized;
	}

	public boolean isPrerendered() {
		return prerendered;
	}

	public boolean isPostrendered() {
		return postrendered;
	}

	public ActionErrors getActionErrors() {
		return actionErrors;
	}

	public Map<String, Object> getFlashMap() {
		return flashMap;
	}

}
