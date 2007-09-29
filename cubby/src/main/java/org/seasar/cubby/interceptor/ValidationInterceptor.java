package org.seasar.cubby.interceptor;

import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collections;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.action.Action;
import org.seasar.cubby.action.Forward;
import org.seasar.cubby.action.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.util.RequestHolder;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.ValidationRule;
import org.seasar.cubby.validator.ValidationRules;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;

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
public class ValidationInterceptor implements MethodInterceptor {

	private static final ValidationRules NULL_VALIDATION_RULES = new ValidationRules() {
		public List<ValidationRule> getRules() {
			return Collections.emptyList();
		}
	};

	private ActionValidator validator;

	private ActionContext context;

	public ValidationInterceptor() {
	}

	public void setActionContext(final ActionContext context) {
		this.context = context;
	}

	public void setActionValidatior(final ActionValidator actionValidator) {
		this.validator = actionValidator;
	}

	public Object invoke(final MethodInvocation invocation) throws Throwable {
		final HttpServletRequest request = RequestHolder.getRequest();

		final Action controller = context.getAction();
		final Validation validation = context.getValidation();
		final ValidationRules rules = getValidationRules(context);
		final Map<String, Object> params = getParams(request);

		final boolean success = validator.processValidation(validation,
				controller, params, context.getFormBean(), rules);

		final Object result;
		if (success) {
			result = invocation.proceed();
		} else {
			request.setAttribute(ATTR_VALIDATION_FAIL, true);
			String path = validation.errorPage();
			result = new Forward(path);
		}

		return result;
	}

	private ValidationRules getValidationRules(ActionContext context) {
		final Validation validation = context.getValidation();
		final ValidationRules validationRules;
		if (validation == null) {
			validationRules = NULL_VALIDATION_RULES;
		} else {
			final Action action = context.getAction();
			BeanDesc beanDesc = BeanDescFactory.getBeanDesc(action.getClass());
			PropertyDesc propertyDesc = beanDesc.getPropertyDesc(validation
					.rulesField());
			validationRules = (ValidationRules) propertyDesc.getValue(action);
		}
		return validationRules;
	}

	@SuppressWarnings("unchecked")
	private static Map<String, Object> getParams(
			final HttpServletRequest request) {
		return (Map<String, Object>) request.getAttribute(ATTR_PARAMS);
	}

}
