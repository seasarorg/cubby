package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidContext;

/**
 * 指定された正規表現にマッチしない場合にエラーとします。
 * @see Pattern
 * @see Matcher
 */
public class FileRegexpValidator extends BaseValidator {
	private final Pattern pattern;

	public FileRegexpValidator(final String regex) {
		this.pattern = Pattern.compile(regex);
	}

	public String validate(ValidContext context, Object value) {
		if (value instanceof FileItem) {
			Matcher matcher = pattern.matcher(((FileItem) value).getName());
			if (matcher.matches()) {
				return null;
			}
			return getMessage("valid.fileRegexp", getPropertyMessage(context.getName()));
		}
		return null;
	}
}
