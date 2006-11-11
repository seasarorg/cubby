package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

/**
 * 指定された正規表現にマッチしない場合にエラーとします。
 * @see Pattern
 * @see Matcher
 */
public class RegexpValidator extends BaseValidator {
	private final Pattern pattern;

	public RegexpValidator(final String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public String validate(ValidContext context, Object value) {
		if (value instanceof String) {
			Matcher matcher = pattern.matcher((String) value);
			if (matcher.matches()) {
				return null;
			}
		}
		return getMessage("valid.regexp", getPropertyMessage(context.getName()));
	}
}
