package org.seasar.cubby.validator.validators;

import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 数値の範囲を指定して検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.range
 * </p>
 * 
 * @author agata
 * @author baba
 */
public class RangeValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 最小値
	 */
	private final long min;

	/**
	 * 最大値
	 */
	private final long max;

	/**
	 * コンストラクタ
	 * 
	 * @param min
	 *            最小値
	 * @param max
	 *            最大値
	 */
	public RangeValidator(final long min, final long max) {
		this(min, max, "valid.range");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param min
	 *            最小値
	 * @param max
	 *            最大値
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public RangeValidator(final long min, final long max,
			final String messageKey) {
		this.min = min;
		this.max = max;
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof String) {
			final String str = (String) value;
			if (StringUtil.isEmpty(str)) {
				return;
			}
			try {
				final long longValue = Long.parseLong(str);
				if (longValue >= min && longValue <= max) {
					return;
				}
			} catch (final NumberFormatException e) {
			}
		} else if (value == null) {
			return;
		}
		context.addMessageInfo(this.messageHelper.createMessageInfo(min, max));
	}
}