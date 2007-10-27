package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.validator.BaseValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * ファイル名が指定された正規表現にマッチするか検証します。
 * @see Pattern
 * @see Matcher
 */
public class FileRegexpValidator extends BaseValidator {
	
	/**
	 * 正規表現パターン
	 */
	private final Pattern pattern;

	/**
	 * コンストラクタ
	 * @param regex 正規表現（例：".+\\.(png|jpg)"）
	 */
	public FileRegexpValidator(final String regex) {
		this(regex, "valid.fileRegexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * @param regex 正規表現（例：".+\\.(png|jpg)"）
	 * @param messageKey エラーメッセージキー
	 */
	public FileRegexpValidator(final String regex, final String messageKey) {
		this.pattern = Pattern.compile(regex);
		this.setMessageKey(messageKey);
	}

	public String validate(final ValidationContext ctx) {
		final Object value = ctx.getValue();
		if (value instanceof FileItem) {
			Matcher matcher = pattern.matcher(((FileItem) value).getName());
			if (matcher.matches()) {
				return null;
			}
			return getMessage(getPropertyMessage(ctx.getName()));
		}
		return null;
	}
}
