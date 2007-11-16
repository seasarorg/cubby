package org.seasar.cubby.validator.validators;

import javax.servlet.http.HttpSession;

import org.seasar.cubby.controller.ThreadContext;
import org.seasar.cubby.tags.TokenTag;
import org.seasar.cubby.util.TokenHelper;
import org.seasar.cubby.validator.ArrayFieldValidator;
import org.seasar.cubby.validator.MessageHelper;
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
 * @author baba
 */
public class TokenValidator implements ArrayFieldValidator {

	private final MessageHelper messageHelper;

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
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object[] values) {
		if (values != null && values.length != 1) {
			context.addMessageInfo(this.messageHelper.createMessageInfo());
		} else {
			final String token = (String) values[0];
			final HttpSession session = ThreadContext.getRequest().getSession();
			if (!TokenHelper.validateToken(session, token)) {
				context.addMessageInfo(this.messageHelper.createMessageInfo());
			}
		}
	}
}
