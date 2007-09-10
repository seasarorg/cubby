package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

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

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			Matcher matcher = pattern.matcher((String) value);
			if (matcher.matches()) {
				return null;
			}
		}
		return getMessage("valid.regexp", getPropertyMessage(ctx.getName()));
	}
}
