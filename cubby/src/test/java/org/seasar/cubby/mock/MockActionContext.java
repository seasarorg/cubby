package org.seasar.cubby.mock;

import static org.seasar.cubby.action.RequestParameterBindingType.NONE;
import static org.seasar.cubby.internal.util.LogMessages.format;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionContext;
import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.ActionException;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.RequestParameterBindingType;
import org.seasar.cubby.internal.action.impl.ActionErrorsImpl;
import org.seasar.cubby.internal.beans.BeanDesc;
import org.seasar.cubby.internal.beans.BeanDescFactory;
import org.seasar.cubby.internal.beans.PropertyDesc;
import org.seasar.cubby.internal.controller.ActionResultWrapper;

public class MockActionContext implements ActionContext {

	private final Action action;

	private final Class<? extends Action> actionClass;

	private final Method actionMethod;

	private final ActionErrors actionErrors = new ActionErrorsImpl();

	private final Map<String, Object> flashMap = new HashMap<String, Object>();

	public MockActionContext(Action action,
			Class<? extends Action> actionClass, Method actionMethod) {
		this.action = action;
		this.actionClass = actionClass;
		this.actionMethod = actionMethod;
	}

	public Action getAction() {
		return action;
	}

	public Class<? extends Action> getActionClass() {
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
		final PropertyDesc propertyDesc = beanDesc
				.getPropertyDesc(propertyName);
		final Object formBean = propertyDesc.getValue(action);
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
