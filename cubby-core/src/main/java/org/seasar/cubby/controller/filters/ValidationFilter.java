package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.*;

import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.validator.ActionValidator;
import org.seasar.cubby.validator.Validators;

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

	private ActionValidator actionValidator;

	private final Populater populater;

	public ValidationFilter(ActionValidator actionValidator, Populater populater) {
		this.actionValidator = actionValidator;
		this.populater = populater;
	}

	public ActionResult doFilter(ActionContext action, ActionFilterChain chain)
			throws Throwable {

		final ActionResult result;

		final HttpServletRequest request = action.getRequest();
		final Controller controller = action.getController();
		final Validation validation = action.getValidation();
		final Validators validators = action.getValidators();

		boolean success = actionValidator.processValidation(validation,
				controller, action.getFormBean(), validators);
		if (success) {
			setupForm(action);
			result = chain.doFilter(action);
		} else {
			request.setAttribute(ATTR_VALIDATION_FAIL, true);
			setupForm(action);
			result = new Forward(validation.errorPage());
		}

		Map<String, String> outputValues = populater.describe(action.getFormBean());
		request.setAttribute(ATTR_OUTPUT_VALUES, outputValues);

		return result;
	}

	@SuppressWarnings("unchecked")
	private void setupForm(ActionContext action) {
		Object formBean = action.getFormBean();
		if (formBean != null) {
			Map<String, Object> params = action.getController().getParams()
					.getOriginalParameter();
			populater.populate(params, formBean);
		}
	}
}
