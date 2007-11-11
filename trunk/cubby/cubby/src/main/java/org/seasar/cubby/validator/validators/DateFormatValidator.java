package org.seasar.cubby.validator.validators;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.action.FormatPattern;
import org.seasar.cubby.validator.BaseScalarValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.exception.SRuntimeException;
import org.seasar.framework.util.StringUtil;

/**
 * 日付に対する検証を行います。
 * <p>
 * 日付パターンを指定しない場合、「app-cubby.dicon」で指定した日付パターンが使用されます。
 * </p>
 * <p>
 * デフォルトエラーメッセージキー:valid.dateFormat
 * </p>
 * @author agata
 * @author baba
 * @see SimpleDateFormat
 */
public class DateFormatValidator extends BaseScalarValidator {

	/**
	 * 日付パターン
	 */
	private final String pattern;

	/**
	 * 日付パターンを指定しないコンストラクタ
	 */
	public DateFormatValidator() {
		this(null);
	}

	/**
	 * 日付パターンを指定するコンストラクタ
	 * 
	 * @param pattern
	 *            日付パターン（例："yyyy/MM/dd"）
	 */
	public DateFormatValidator(final String pattern) {
		this(pattern, "valid.dateFormat");
	}

	/**
	 * 日付パターンとエラーメッセージキーを指定したコンストラクタ
	 * 
	 * @param pattern
	 *            日付パターン（例："yyyy/MM/dd"）
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public DateFormatValidator(final String pattern, final String messageKey) {
		this.pattern = pattern;
		this.setMessageKey(messageKey);
	}

	@Override
	protected void validate(final Object value, final ValidationContext ctx) {
		if (value == null) {
			return;
		}
		if (value instanceof String) {
			final String stringValue = (String) value;
			if (StringUtil.isEmpty((String) value)) {
				return;
			}
			try {
				final DateFormat dateFormat = createDateFormat(ctx);
				final ParsePosition parsePosition = new ParsePosition(0);
				final Date date = dateFormat.parse(stringValue, parsePosition);
				if (date != null
						&& parsePosition.getIndex() == stringValue.length()) {
					return;
				}
			} catch (final Exception e) {
			}
		}
		ctx.addMessage(getMessage(getPropertyMessage(ctx.getName())));
	}

	private DateFormat createDateFormat(final ValidationContext context) {
		final SimpleDateFormat dateFormat = new SimpleDateFormat();
		final String pattern;
		if (StringUtil.isEmpty(this.pattern)) {
			FormatPattern formatPattern = context.getFormatPattern();
			if (formatPattern == null) {
				throw new SRuntimeException("ECUB0301", new Object[] { this,
						context.getValues() });
			}
			pattern = formatPattern.getDatePattern();
		} else {
			pattern = this.pattern;
		}
		dateFormat.applyPattern(pattern);
		dateFormat.setLenient(false);
		return dateFormat;
	}
}