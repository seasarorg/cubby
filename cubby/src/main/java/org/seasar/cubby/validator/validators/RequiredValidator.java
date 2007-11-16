package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 必須検証します。
 * <p>
 * 文字列の長さが0の場合、検証エラーとなります。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.required
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class RequiredValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * コンストラクタ
	 */
	public RequiredValidator() {
		this("valid.required");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RequiredValidator(final String messageKey) {
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (!StringUtil.isEmpty(str)) {
				return;
			}
		} else if (value != null) {
			return;
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo());
	}

}
