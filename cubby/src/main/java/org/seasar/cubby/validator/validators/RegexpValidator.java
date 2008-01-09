package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 指定された正規表現にマッチするか検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.regexp
 * </p>
 * 
 * @author baba
 * @see Pattern
 * @see Matcher
 */
public class RegexpValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 正規表現パターン
	 */
	private final Pattern pattern;

	/**
	 * コンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(png|jpg)"）
	 */
	public RegexpValidator(final String regex) {
		this(regex, "valid.regexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(png|jpg)"）
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RegexpValidator(final String regex, final String messageKey) {
		this.pattern = Pattern.compile(regex);
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object value) {
		if (value == null) {
			return;
		}
		if (value instanceof String) {
			final String stringValue = (String) value;
			if (StringUtil.isEmpty(stringValue)) {
				return;
			}
			final Matcher matcher = pattern.matcher(stringValue);
			if (matcher.matches()) {
				return;
			}
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo());
	}

}