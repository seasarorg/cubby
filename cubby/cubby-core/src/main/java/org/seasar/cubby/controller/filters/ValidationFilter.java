package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Map;

import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.annotation.Validation;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.ActionFilter;
import org.seasar.cubby.controller.ActionFilterChain;
import org.seasar.cubby.controller.ActionResult;
import org.seasar.cubby.controller.results.Forward;
import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.validator.ActionValidator;

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
		boolean success = actionValidator.processValidation(action
				.getValidation(), action.getController(), action.getFormBean(),
				action.getValidators());
		if (success) {
			setupForm(action);
			return chain.doFilter(action);
		} else {
			action.getRequest().setAttribute(ATTR_VALIDATION_FAIL, true);
			Validation valid = action.getValidation();
			setupForm(action);
			return new Forward(valid.errorPage());
		}
	}

	@SuppressWarnings( { "unchecked", "deprecation" })
	private void setupForm(ActionContext action) {
		Map<String, Object> params = action.getController().getParams()
				.getOriginalParameter();
		Object formBean = action.getFormBean();
		if (formBean != null) {
			populater.populate(formBean, params);
		}
	}
}
