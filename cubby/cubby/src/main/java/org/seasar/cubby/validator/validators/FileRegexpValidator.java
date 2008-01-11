package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.validator.MessageHelper;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * ファイルアップロードのファイル名が指定された正規表現にマッチするか検証します。
* <p>
 * 正規表現についての詳細は {@link Pattern}を参照してください。
 * </p>
  * <p>
 * デフォルトエラーメッセージキー:valid.fileRegexp
 * </p>
 * 
 * @see Pattern
 * @see Matcher
 * @author baba
 */
public class FileRegexpValidator implements ScalarFieldValidator {

	/**
	 * メッセージヘルパ。
	 */
	private final MessageHelper messageHelper;

	/**
	 * 正規表現パターン
	 */
	private final Pattern pattern;

	/**
	 * コンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(?i)(png|jpg)"）・・・「(?i)」は大文字小文字を区別しないフラグ
	 */
	public FileRegexpValidator(final String regex) {
		this(regex, "valid.fileRegexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param regex
	 *            正規表現（例：".+\\.(?i)(png|jpg)"）・・・「(?i)」は大文字小文字を区別しないフラグ
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public FileRegexpValidator(final String regex, final String messageKey) {
		this(Pattern.compile(regex), messageKey);
	}

	/**
	 * コンストラクタ
	 * 
	 * @param pattern 正規表現パターン
	 */
	public FileRegexpValidator(Pattern pattern) {
		this(pattern, "valid.fileRegexp");
	}
	
	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param pattern 正規表現パターン
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public FileRegexpValidator(Pattern pattern, String messageKey) {
		this.pattern = pattern;
		this.messageHelper = new MessageHelper(messageKey);
	}

	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof FileItem) {
			final FileItem fileItem = (FileItem) value;
			final Matcher matcher = pattern.matcher(fileItem.getName());
			if (!matcher.matches()) {
				context.addMessageInfo(this.messageHelper.createMessageInfo());
			}
		}
	}
}
