package org.seasar.cubby.controller.impl;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionDef;
import org.seasar.cubby.controller.CubbyConfiguration;
import org.seasar.cubby.controller.ParameterBinder;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.container.ComponentDef;
import org.seasar.framework.log.Logger;

/**
 * 
 * @author baba
 *
 */
public class ActionContextImpl implements ActionContext {

	private static final Logger logger = Logger
			.getLogger(ActionContextImpl.class);

	private static final Object[] EMPTY_ARGS = new Object[0];

	private CubbyConfiguration configuration;

	private ParameterBinder parameterBinder;

	private Action action;

	private ActionDef actionDef;

	public void initialize(final ActionDef actionDef) {
		this.actionDef = actionDef;
	}

	public boolean isInitialized() {
		return this.actionDef != null;
	}

	public CubbyConfiguration getConfiguration() {
		return configuration;
	}

	public void setConfiguration(final CubbyConfiguration configuration) {
		this.configuration = configuration;
	}

	public ParameterBinder getParameterBinder() {
		return parameterBinder;
	}

	public void setParameterBinder(final ParameterBinder parameterBinder) {
		this.parameterBinder = parameterBinder;
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

	public Validation getValidation() {
		return actionDef.getMethod().getAnnotation(Validation.class);
	}

	public ActionResult invoke() throws Throwable {
		try {
			final ActionResult result = (ActionResult) actionDef.getMethod().invoke(getAction(),
					EMPTY_ARGS);
			return result;
		} catch (final InvocationTargetException ex) {
			logger.log(ex);
			throw ex.getCause();
		}
	}

	public Object getFormBean() {
		final Form form = actionDef.getMethod().getAnnotation(Form.class);
		if (form == null) {
			return null;
		}
		final Action action = getAction();
		final String formName = form.value();
		if (Form.THIS.equals(formName)) {
			return action;
		} else {
			final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action.getClass());
			final PropertyDesc propertyDesc = beanDesc.getPropertyDesc(formName);
			final Object value = propertyDesc.getValue(action);
			return value;
		}
	}

}
