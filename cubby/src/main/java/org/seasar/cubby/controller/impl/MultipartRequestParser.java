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
package org.seasar.cubby.controller.impl;

import static org.seasar.cubby.internal.util.LogMessages.format;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadBase;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.seasar.cubby.controller.RequestParseException;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.util.StringUtils;
import org.seasar.cubby.spi.ContainerProvider;
import org.seasar.cubby.spi.ProviderFactory;
import org.seasar.cubby.spi.container.Container;
import org.seasar.cubby.spi.container.LookupException;

/**
 * マルチパートの要求に対応した解析器です。
 * <p>
 * 要求の解析には <a href="http://commons.apache.org/fileupload/">Commons
 * FileUpload</a> を使用します。
 * </p>
 * 
 * @author baba
 * @see <a href="http://commons.apache.org/fileupload/">Commons FileUpload</a>
 * @since 1.0.0
 */
public class MultipartRequestParser implements RequestParser {

	/** デフォルトの優先順位。 */
	static final int DEFAULT_PRIORITY = DefaultRequestParser.DEFAULT_PRIORITY - 1;

	/** 優先順位。 */
	private int priority = DEFAULT_PRIORITY;

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定された要求がマルチパートの要求 (contentType が "multipart/" で始まる) であれば、コンテナに登録された
	 * {@link FileUpload} と {@link RequestContext} を使用して要求を解析します。
	 * <p>
	 * 要求パラメータを戻り値の {@link Map} に格納する際には以下のように変換します。
	 * <ul>
	 * <li>フォームのフィールド
	 * <p>
	 * 文字列に変換
	 * </p>
	 * </li>
	 * <li>フォームのフィールド以外(アップロードされたファイル)
	 * <p>
	 * {@link FileItem} に変換
	 * </p>
	 * </li>
	 * </ul>
	 * </p>
	 * </p>
	 * 
	 * @throws RequestParseException
	 *             指定された要求がマルチパートではなかった場合
	 * @see FileUpload#parseRequest(RequestContext)
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object[]> getParameterMap(
			final HttpServletRequest request) {
		final Map<String, Object[]> parameterMap = new HashMap<String, Object[]>(
				request.getParameterMap());
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		try {
			final RequestContext requestContext = container
					.lookup(RequestContext.class);
			final FileUpload fileUpload = container.lookup(FileUpload.class);
			final Map<String, Object[]> multipartParameterMap = getMultipartParameterMap(
					fileUpload, requestContext);
			parameterMap.putAll(multipartParameterMap);
			return parameterMap;
		} catch (final LookupException e) {
			throw new IllegalStateException(e);
		}
	}

	@SuppressWarnings("unchecked")
	Map<String, Object[]> getMultipartParameterMap(final FileUpload fileUpload,
			final RequestContext requestContext) {
		try {
			final String encoding = requestContext.getCharacterEncoding();
			fileUpload.setHeaderEncoding(encoding);
			final List<FileItem> items = fileUpload
					.parseRequest(requestContext);

			// Fieldごとにパラメータを集める
			final Map<String, Object[]> parameterMap = toParameterMap(encoding,
					items);

			return parameterMap;
		} catch (final FileUploadException e) {
			final String messageCode;
			final Object[] args;
			if (e instanceof SizeLimitExceededException) {
				final SizeLimitExceededException sle = (SizeLimitExceededException) e;
				messageCode = "ECUB0202";
				args = new Object[] { sle.getPermittedSize(),
						sle.getActualSize() };
			} else {
				messageCode = "ECUB0201";
				args = new Object[] { e };
			}
			throw new RequestParseException(format(messageCode, args), e);
		} catch (final IOException e) {
			throw new RequestParseException(e);
		}
	}

	Map<String, Object[]> toParameterMap(final String encoding,
			final List<FileItem> items) throws UnsupportedEncodingException {
		final Map<String, List<Object>> valueListParameterMap = new LinkedHashMap<String, List<Object>>();
		for (final FileItem item : items) {
			final Object value;
			if (item.isFormField()) {
				value = item.getString(encoding);
			} else {
				if (StringUtils.isEmpty(item.getName()) || item.getSize() == 0) {
					// ファイル名無し、あるいは０バイトのファイル
					value = null;
				} else {
					value = item;
				}
			}
			final List<Object> values;
			if (valueListParameterMap.containsKey(item.getFieldName())) {
				values = valueListParameterMap.get(item.getFieldName());
			} else {
				values = new ArrayList<Object>();
				valueListParameterMap.put(item.getFieldName(), values);
			}
			values.add(value);
		}

		final Map<String, Object[]> parameterMap = fromValueListMapToValueArrayMap(valueListParameterMap);
		return parameterMap;
	}

	Map<String, Object[]> fromValueListMapToValueArrayMap(
			final Map<String, List<Object>> valueListMap) {
		// 配列でパラメータMapを構築
		final Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		for (final Entry<String, List<Object>> entry : valueListMap.entrySet()) {
			final List<Object> values = entry.getValue();
			final Object[] valueArray;
			if (values.get(0) instanceof String) {
				valueArray = new String[values.size()];
			} else {
				valueArray = new FileItem[values.size()];
			}
			parameterMap.put(entry.getKey(), values.toArray(valueArray));
		}
		return parameterMap;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定された要求がマルチパートの要求 (contentType が "multipart/" で始まる) の場合に
	 * <code>true</code> を返します。
	 * </p>
	 * 
	 * @see FileUpload#isMultipartContent(RequestContext)
	 */
	public boolean isParsable(final HttpServletRequest request) {
		final Container container = ProviderFactory
				.get(ContainerProvider.class).getContainer();
		try {
			final RequestContext requestContext = container
					.lookup(RequestContext.class);
			return FileUploadBase.isMultipartContent(requestContext);
		} catch (final LookupException e) {
			return false;
		}
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * デフォルトの優先順位は {@link DefaultRequestParser#DEFAULT_PRIORITY} - 1 です。
	 * </p>
	 */
	public int getPriority() {
		return priority;
	}

	/**
	 * 優先順位を設定します。
	 * 
	 * @param priority
	 *            優先順位
	 */
	public void setPriority(final int priority) {
		this.priority = priority;
	}

}
