package org.seasar.cubby.controller.impl;

import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.seasar.cubby.annotation.Form;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionHolder;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.CubbyUtils;
import org.seasar.cubby.validator.FormValidator;
import org.seasar.cubby.validator.Validatable;
import org.seasar.cubby.validator.Validators;

public class ActionContextImpl implements ActionContext {
	public static final Validators NULL_VALIDATORS = new Validators();
	
	private final Controller controller;
	private final Class<? extends Controller> controllerClass;

	private final ActionHolder holder;

	private final HttpServletRequest request;

	private final HttpServletResponse response;

	private ActionFilter currentFilter;

	@SuppressWarnings("unchecked")
	public ActionContextImpl(HttpServletRequest request,
			HttpServletResponse response, Controller controller, ActionHolder holder) {
		this.request = request;
		this.response = response;
		this.controller = controller;
		this.holder = holder;
		this.controllerClass = (Class<? extends Controller>) holder.getActionMethod().getDeclaringClass();
	}

	public Controller getController() {
		return controller;
	}

	public Method getMethod() {
		return holder.getActionMethod();
	}

	public HttpServletRequest getRequest() {
		return request;
	}

	public HttpServletResponse getResponse() {
		return response;
	}

	public Form getForm() {
		Form formInfo = (Form) holder.getActionMethod().getAnnotation(Form.class);
		if (formInfo != null) {
			return formInfo;
		}
		formInfo = (Form) controllerClass.getAnnotation(Form.class);
		return formInfo;
	}

	public Validation getValidation() {
		return holder.getActionMethod().getAnnotation(Validation.class);
	}

	public Validators getValidators() {
		Validation validation = getValidation();
		if (validation != null) {
			Class<? extends Validatable> validatorsClass = validation.validator();
			if (validatorsClass == FormValidator.class) {
				if (getFormBean() instanceof Validatable) {
					return ((Validatable) getFormBean()).getValidators();
				}
			} else if (validatorsClass != null) {
				return ClassUtils.newInstance(validatorsClass).getValidators();
			} else {
				throw new RuntimeException("Can't find validators.");
			}
		}
		return NULL_VALIDATORS;
	}

	public Object getFormBean() {
		Form form = getForm();
		if (form == null) {
			return null;
		}
		String formFieldName = form.value();
		Object formBean = ClassUtils.getField(controller, formFieldName);
		return formBean;
	}

	public String getControllerName() {
		return CubbyUtils.getControllerName(controllerClass);
	}

	public ActionHolder getActionHolder() {
		return holder;
	}

	public ActionFilter getCurrentFilter() {
		return currentFilter;
	}

	public void setCurrentFilter(ActionFilter currentFilter) {
		this.currentFilter = currentFilter;
	}
}
