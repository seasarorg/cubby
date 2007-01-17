package org.seasar.cubby.validator.validators;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.seasar.cubby.util.StringUtils;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

public class DateFormatValidator extends BaseValidator {
	private SimpleDateFormat dateFormat;

	public DateFormatValidator(String dateFormatPattern) {
		this.dateFormat = new SimpleDateFormat();
		this.dateFormat.setLenient(false);
		this.dateFormat.applyPattern(dateFormatPattern);
	}
	

	public String validate(ValidContext context, Object value) {		
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
		return getMessage("valid.dateFormat", getPropertyMessage(context.getName()));
	}
}