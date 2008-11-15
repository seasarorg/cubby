/*
 * Copyright 2004-2008 the Seasar Foundation and the Others.
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
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.cubby.controller.RequestParseException;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.internal.container.Container;
import org.seasar.cubby.internal.container.ContainerFactory;
import org.seasar.cubby.internal.util.ServiceFactory;
import org.seasar.cubby.internal.util.StringUtils;

/**
 * contentType が multipart/form-data のリクエストに対応したリクエスト解析器です。
 * <p>
 * リクエストの解析には <a href="http://commons.apache.org/fileupload/">Commons
 * FileUpload</a> を使用します。
 * </p>
 * 
 * @author baba
 * @see <a href="http://commons.apache.org/fileupload/">Commons FileUpload</a>
 * @since 1.0.0
 */
public class MultipartRequestParserImpl implements RequestParser {

	/** デフォルトの優先順位。 */
	static final int DEFAULT_PRIORITY = DefaultRequestParserImpl.DEFAULT_PRIORITY - 1;

	/** 優先順位。 */
	private int priority = DEFAULT_PRIORITY;

	/**
	 * {@inheritDoc}
	 * <p>
	 * 指定されたリクエストがマルチパートのリクエスト(contentType が multipart/form-data)であれば、コンテナに登録された
	 * {@link FileUpload} と {@link RequestContext} を使用してリクエストを解析します。
	 * <p>
	 * リクエストパラメータを戻り値の {@link Map} に格納する際には以下のように変換します。
	 * <ul>
	 * <li>フォームのフィールド
	 * <p>
	 * 文字列に変換
	 * </p>
	 * </li>
	 * <li>フォームのフィールド以外(アップロードされたファイル)
	 * <p>
	 * {@link FileItem}に変換
	 * </p>
	 * </li>
	 * </ul>
	 * </p>
	 * </p>
	 * <p>
	 * 指定されたリクエストが通常のリクエストであれば、{@link HttpServletRequest#getParameterMap()}
	 * の結果をそのまま返します。
	 * </p>
	 * 
	 * @see FileUpload
	 */
	@SuppressWarnings("unchecked")
	public Map<String, Object[]> getParameterMap(
			final HttpServletRequest request) {
		final Map<String, Object[]> parameterMap = new HashMap<String, Object[]>(
				request.getParameterMap());
		if (ServletFileUpload.isMultipartContent(request)) {
			final Container container = ContainerFactory.getContainer();
			final FileUpload fileUpload = container.lookup(FileUpload.class);
			final RequestContext requestContext = container
					.lookup(RequestContext.class);
			parameterMap.putAll(this.getMultipartParameterMap(fileUpload,
					requestContext));
		}
		return parameterMap;
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
			final Map<String, List<Object>> collectParameterMap) {
		// 配列でパラメータMapを構築
		final Map<String, Object[]> parameterMap = new HashMap<String, Object[]>();
		for (final Entry<String, List<Object>> entry : collectParameterMap
				.entrySet()) {
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
	 */
	public boolean isParsable(final HttpServletRequest request) {
		final RequestContext requestContext = ServiceFactory
				.getProvider(RequestContext.class);
		if (requestContext == null) {
			return false;
		}
		// final Container container = ContainerFactory.getContainer();
		// if (container.has(RequestContext.class)) {
		// final RequestContext requestContext = container
		// .lookup(RequestContext.class);
		return FileUpload.isMultipartContent(requestContext);
		// }
		// return false;
	}

	/**
	 * {@inheritDoc}
	 * <p>
	 * デフォルトの優先順位は {@link DefaultRequestParserImpl#DEFAULT_PRIORITY} - 1 です。
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
