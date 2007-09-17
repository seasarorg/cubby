package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;
import org.seasar.framework.util.StringUtil;

/**
 * 指定された正規表現にマッチしない場合にエラーとします。
 * @see Pattern
 * @see Matcher
 */
public class RegexpValidator extends BaseValidator {

	private final Pattern pattern;

	public RegexpValidator(final String regex) {
		this(regex, "valid.regexp");
	}

	public RegexpValidator(final String regex, final String messageKey) {
		this.pattern = Pattern.compile(regex);
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value == null) {
			return null;
		}
		if (value instanceof String) {
			String stringValue = (String) value;
			if (StringUtil.isEmpty(stringValue)) {
				return null;
			}
			Matcher matcher = pattern.matcher(stringValue);
			if (matcher.matches()) {
				return null;
			}
		}
		return getMessage(getPropertyMessage(ctx.getName()));
	}

}
