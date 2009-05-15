/*
 * Copyright 2004-2009 the Seasar Foundation and the Others.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND,
 * either express or implied. See the License for the specific language
 * governing permissions and limitations under the License.
 */
package org.seasar.cubby.validator.validators;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.fileupload.FileItem;
import org.seasar.cubby.validator.MessageInfo;
import org.seasar.cubby.validator.ScalarFieldValidator;
import org.seasar.cubby.validator.ValidationContext;

/**
 * アップロードされたファイル({@link FileItem})のファイル名が指定された正規表現にマッチするか検証します。
 * <p>
 * <table>
 * <caption>検証エラー時に設定するエラーメッセージ</caption> <tbody>
 * <tr>
 * <th scope="row">デフォルトのキー</th>
 * <td>valid.fileRegexp</td>
 * </tr>
 * <tr>
 * <th scope="row">置換文字列</th>
 * <td>
 * <ol start="0">
 * <li>フィールド名</li>
 * </ol></td>
 * </tr>
 * </tbody>
 * </table>
 * </p>
 * 
 * @see Pattern
 * @author baba
 */
public class FileRegexpValidator implements ScalarFieldValidator {

	/**
	 * メッセージキー。
	 */
	private final String messageKey;

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
	 * @param pattern
	 *            正規表現パターン
	 */
	public FileRegexpValidator(final Pattern pattern) {
		this(pattern, "valid.fileRegexp");
	}

	/**
	 * エラーメッセージキーを指定するコンストラクタ
	 * 
	 * @param pattern
	 *            正規表現パターン
	 * @param messageKey
	 *            エラーメッセージキー
	 */
	public FileRegexpValidator(final Pattern pattern, final String messageKey) {
		this.pattern = pattern;
		this.messageKey = messageKey;
	}

	/**
	 * {@inheritDoc}
	 */
	public void validate(final ValidationContext context, final Object value) {
		if (value instanceof FileItem) {
			final FileItem fileItem = (FileItem) value;
			final Matcher matcher = pattern.matcher(fileItem.getName());
			if (matcher.matches()) {
				return;
			}

			final MessageInfo messageInfo = new MessageInfo();
			messageInfo.setKey(this.messageKey);
			messageInfo.setArguments(new Object[] { value });
			context.addMessageInfo(messageInfo);
		}
	}
}
