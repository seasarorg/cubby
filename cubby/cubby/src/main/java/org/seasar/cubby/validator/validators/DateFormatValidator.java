package org.seasar.cubby.validator.validators;

import java.text.DateFormat;
import java.text.ParsePosition;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

public class DateFormatValidator extends BaseValidator {

	private final String pattern;

	public DateFormatValidator(final String pattern) {
		this.pattern = pattern;
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			final String stringValue = (String) value;
			if (StringUtil.isEmpty((String) value)) {
				return null;
			}
			try {
				final DateFormat dateFormat = createDateFormat();
				final ParsePosition parsePosition = new ParsePosition(0);
				final Date date = dateFormat.parse(stringValue, parsePosition);
				if (date != null && parsePosition.getIndex() == stringValue.length()) {
					return null;
				}
			} catch (final Exception e) {
			}
		}
		return getMessage("valid.dateFormat", getPropertyMessage(ctx.getName()));
	}

	private DateFormat createDateFormat() {
		final SimpleDateFormat dateFormat = new SimpleDateFormat();
		dateFormat.setLenient(false);
		dateFormat.applyPattern(this.pattern);
		return dateFormat;
	}
}