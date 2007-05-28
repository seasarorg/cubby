package org.seasar.cubby.validator.validators;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

public class DateFormatValidator extends BaseValidator {
	private final SimpleDateFormat dateFormat;

	public DateFormatValidator(final String dateFormatPattern) {
		this.dateFormat = new SimpleDateFormat();
		this.dateFormat.setLenient(false);
		this.dateFormat.applyPattern(dateFormatPattern);
	}
	

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof String) {
			String str = (String)value;
			if (StringUtils.isEmpty((String)value)) {
				return null;
			}
			try {
				Date date = dateFormat.parse(str);
				if (date != null) {
					return null;
				}
			} catch (Exception e) {}
		}else if(value == null){
			return null;
		}
		return getMessage("valid.dateFormat", getPropertyMessage(ctx.getName()));
	}
}