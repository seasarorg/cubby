package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_OUTPUT_VALUES;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.ActionResult;
import org.seasar.cubby.action.Form;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;

/**
 * 入力検証とフォームオブジェクトへの値のバインディングを行います。<br>
 * 入力検証が成功した場合：<br>
 * フォームオブジェクトへのバインドを行い、後のフィルターを実行します。<br>
 * 入力検証が失敗した場合：<br>
 * フォームオブジェクトへのバインドを行い、アクションメソッドに設定された{@link Validation#errorPage()}へフォワードします。
 * またリクエスト中の入力検証エラーフラグがONになります。
 * 
 * @see CubbyConstants#ATTR_VALIDATION_FAIL 入力検証エラーフラグの属性名
 * @author agata
 * @since 1.0
 */
public class ValidationFilter implements ActionFilter {

	public static final ValidationRules NULL_VALIDATION_RULES = new ValidationRules() {
		@SuppressWarnings("unchecked")
		public List<ValidationRule> getRules() {
			return Collections.EMPTY_LIST;
		}
	};

	private ActionValidator actionValidator;

	private final Populater populater;

	public ValidationFilter(ActionValidator actionValidator, Populater populater) {
		this.actionValidator = actionValidator;
		this.populater = populater;
	}

	@SuppressWarnings("unchecked")
	public ActionResult doFilter(ActionContext context, ActionFilterChain chain)
			throws Throwable {
		final ActionResult result;

		final HttpServletRequest request = context.getRequest();
		final Action controller = context.getAction();
		final Validation validation = context.getActionMethod().getValidation();
		final ValidationRules rules = getValidationRules(context);
		final Map<String, Object> params = (Map<String, Object>) request
				.getAttribute(ATTR_PARAMS);

		boolean success = actionValidator.processValidation(validation,
				controller, params, getFormBean(controller, context
						.getActionMethod().getForm()), rules);
		if (success) {
			setupForm(context);
			result = chain.doFilter(context);
		} else {
			request.setAttribute(ATTR_VALIDATION_FAIL, true);
			setupForm(context);
			String path = validation.errorPage();
			result = new Forward(path);
		}

		Map<String, String> outputValues = populater.describe(getFormBean(
				controller, context.getActionMethod().getForm()));
		request.setAttribute(ATTR_OUTPUT_VALUES, outputValues);

		return result;
	}

	@SuppressWarnings("unchecked")
	private void setupForm(ActionContext context) {
		Object formBean = getFormBean(context.getAction(), context
				.getActionMethod().getForm());
		if (formBean != null) {
			Map<String, Object> params = (Map<String, Object>) context
					.getRequest().getAttribute(ATTR_PARAMS);
			populater.populate(params, formBean);
		}
	}

	private ValidationRules getValidationRules(ActionContext context) {
		Validation validation = context.getActionMethod().getValidation();
		if (validation != null) {
			return (ValidationRules) ClassUtils.getFieldValue(
					context.getAction(), validation.rulesField());
		}
		return NULL_VALIDATION_RULES;
	}

	private Object getFormBean(Action controller, Form form) {
		if (form == null) {
			return null;
		}
		String formFieldName = form.value();
		if (Form.THIS.equals(formFieldName)) {
			return controller;
		} else {
			return ClassUtils.getFieldValue(controller, formFieldName);
		}
	}
}
