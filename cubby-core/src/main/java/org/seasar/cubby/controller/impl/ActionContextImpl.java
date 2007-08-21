package org.seasar.cubby.controller.impl;

import java.io.Serializable;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.log.Logger;

public class ActionContextImpl implements ActionContext, Serializable {

	private static final long serialVersionUID = 2416038583405864226L;

	private static final Object[] EMPTY_ARGS = new Object[0];

	private final Logger logger = Logger.getLogger(this.getClass());

	private ActionDef actionDef;

	private Action action;

	public void setActionDef(ActionDef actionDef) {
		this.actionDef = actionDef;
	}

	public ComponentDef getComponentDef() {
		return actionDef.getComponentDef();
	}

	public Method getMethod() {
		return actionDef.getMethod();
	}

	public Action getAction() {
		if (action == null) {
			action = (Action) actionDef.getComponentDef().getComponent();
		}
		return action;
	}

	public Form getForm() {
		return actionDef.getMethod().getAnnotation(Form.class);
	}

	public Validation getValidation() {
		return actionDef.getMethod().getAnnotation(Validation.class);
	}

	public ActionResult invoke() throws Throwable {
		try {
			ActionResult result = (ActionResult) actionDef.getMethod().invoke(getAction(),
					EMPTY_ARGS);
			return result;
		} catch (InvocationTargetException ex) {
			logger.error(ex.getMessage(), ex);
			throw ex.getCause();
		}
	}

	public Object getFormBean() {
		Form form = getForm();
		if (form == null) {
			return null;
		}
		Action action = getAction();
		String formName = form.value();
		if (Form.THIS.equals(formName)) {
			return action;
		} else {
			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action.getClass());
			PropertyDesc propertyDesc = beanDesc.getPropertyDesc(formName);
			Object value = propertyDesc.getValue(action);
			return value;
		}
	}

}
