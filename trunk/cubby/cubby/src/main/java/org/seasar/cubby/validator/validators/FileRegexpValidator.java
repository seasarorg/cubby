package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.validator.BaseScalarValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * ファイルアップロードのファイル名が指定された正規表現にマッチするか検証します。
 * <p>
 * デフォルトエラーメッセージキー:valid.fileRegexp
 * </p>
 * @see Pattern
 * @see Matcher
 * @author baba
 */
public class FileRegexpValidator extends BaseScalarValidator {

	/**
	 * 正規表現パターン
	 */
	private final Pattern pattern;

	/**
	 * コンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(png|jpg)"）
	 */
	public FileRegexpValidator(final String regex) {
		this(regex, "valid.fileRegexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(png|jpg)"）
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public FileRegexpValidator(final String regex, final String messageKey) {
		this.pattern = Pattern.compile(regex);
		this.setMessageKey(messageKey);
	}

	@Override
	protected void validate(final Object value, final ValidationContext context) {
		if (value instanceof FileItem) {
			final FileItem fileItem = (FileItem) value;
			final Matcher matcher = pattern.matcher(fileItem.getName());
			if (!matcher.matches()) {
				context.addMessage(getMessage(getPropertyMessage(context
						.getName())));
			}
		}
	}
}
