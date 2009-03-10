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
package org.seasar.cubby.validator;

import static org.seasar.cubby.CubbyConstants.ATTR_CONVERSION_ERRORS;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.action.FieldInfo;
import org.seasar.cubby.internal.controller.ThreadContext;
import org.seasar.cubby.internal.util.RequestUtils;
import org.seasar.cubby.util.Messages;

/**
 * リクエストパラメータをフォームオブジェクトへのバインドする時の型変換エラーを検証する入力検証ルールです。
 * 
 * @author baba
 * @since 2.0.0
 */
public class ConversionValidationRule implements ValidationRule {

	/** デフォルトのメッセージを置換するメッセージ情報。 */
	private final Map<FieldInfo, MessageInfo> messages = new HashMap<FieldInfo, MessageInfo>();

	/**
	 * {@inheritDoc}
	 */
	public void apply(final Map<String, Object[]> params, final Object form,
			final ActionErrors errors) throws ValidationException {
		final HttpServletRequest request = ThreadContext.getRequest();
		final ActionErrors conversionErrors = RequestUtils.getAttribute(
				request, ATTR_CONVERSION_ERRORS);
		if (conversionErrors != null) {
			merge(conversionErrors, errors);
		}
	}

	/**
	 * デフォルトのメッセージを置換するメッセージ情報。
	 * 
	 * @param fieldName
	 *            フィールド名
	 * @param messageKey
	 *            {@link Messages}からメッセージを取得するためのキー
	 * @param arguments
	 *            メッセージの置換パターンを置き換えるオブジェクトからなる配列
	 * @return このオブジェクト
	 */
	public ConversionValidationRule add(final String fieldName,
			final String messageKey, final Object... arguments) {
		final FieldInfo fieldInfo = new FieldInfo(fieldName);
		putToMessages(fieldInfo, messageKey, arguments);
		return this;
	}

	/**
	 * デフォルトのメッセージを置換するメッセージ情報。
	 * 
	 * @param fieldName
	 *            フィールド名
	 * @param index
	 *            フィールドのインデックス
	 * @param messageKey
	 *            {@link Messages}からメッセージを取得するためのキー
	 * @param arguments
	 *            メッセージの置換パターンを置き換えるオブジェクトからなる配列
	 * @return このオブジェクト
	 */
	public ConversionValidationRule add(final String fieldName,
			final int index, final String messageKey, final Object... arguments) {
		final FieldInfo fieldInfo = new FieldInfo(fieldName, index);
		putToMessages(fieldInfo, messageKey, arguments);
		return this;
	}

	private void putToMessages(final FieldInfo fieldInfo,
			final String messageKey, final Object... arguments) {
		final MessageInfo messageInfo = new MessageInfo();
		messageInfo.setKey(messageKey);
		messageInfo.setArguments(arguments);
		this.messages.put(fieldInfo, messageInfo);
	}

	private void merge(final ActionErrors from, final ActionErrors to) {
		for (final Entry<String, Map<Integer, List<String>>> indexedField : from
				.getIndexedFields().entrySet()) {
			final String fieldName = indexedField.getKey();
			for (final Entry<Integer, List<String>> field : indexedField
					.getValue().entrySet()) {
				final Integer index = field.getKey();
				for (final String message : field.getValue()) {
					final FieldInfo fieldInfo;
					if (index == null) {
						fieldInfo = new FieldInfo(fieldName);
					} else {
						fieldInfo = new FieldInfo(fieldName, index);
					}
					to.add(message(message, fieldInfo), fieldInfo);
				}
			}
		}
	}

	private String message(final String message, final FieldInfo fieldInfo) {
		if (messages.containsKey(fieldInfo)) {
			final MessageInfo messageInfo = messages.get(fieldInfo);
			return messageInfo.builder().fieldNameKey(fieldInfo.getName())
					.toString();
		} else {
			return message;
		}
	}

}
