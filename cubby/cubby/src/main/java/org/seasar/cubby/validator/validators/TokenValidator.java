package org.seasar.cubby.validator.validators;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.tags.TokenTag;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * 2重サブミットの検証をします。
 * <p>
 * ポストする画面で{@link TokenTag}を使用して、Actionクラスで TokenValidatorを使用することで、
 * ２重サブミットを防止します。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.token
 * </p>
 * 
 * @author agata
 * 
 */
public class TokenValidator extends BaseValidator {

	/**
	 * コンストラクタ
	 */
	public TokenValidator() {
		this("valid.token");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public TokenValidator(final String messageKey) {
		this.setMessageKey(messageKey);
	}

	@SuppressWarnings("unchecked")
	public void validate(ValidationContext context) {
		final Object[] values = context.getValues();
		if (values != null && values.length != 1) {
			context.addMessage(getMessage());
		} else {
			String token = (String) values[0];
			HttpSession session = ThreadContext.getRequest().getSession();
			if (!TokenHelper.validateToken(session, token)) {
				context.addMessage(getMessage());
			}
		}
	}
}
