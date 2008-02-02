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
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.exception.FileUploadRuntimeException;
import org.seasar.framework.container.S2Container;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.StringUtil;

/**
 * 
 * @author baba
 * 
 */
public class MultipartRequestParserImpl implements RequestParser {

	private final S2Container container;

	public MultipartRequestParserImpl(final S2Container container) {
		this.container = container;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object[]> getParameterMap(
			final HttpServletRequest request) {
		final Map<String, Object[]> parameterMap = new HashMap<String, Object[]>(
				request.getParameterMap());
		if (ServletFileUpload.isMultipartContent(request)) {
			final S2Container root = container.getRoot();
			final FileUpload fileUpload = (FileUpload) root
					.getComponent(FileUpload.class);
			final RequestContext requestContext = (RequestContext) root
					.getComponent(RequestContext.class);
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
			throw new FileUploadRuntimeException(e);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
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
				if (StringUtil.isEmpty(item.getName()) || item.getSize() == 0) {
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

		final Map<String, Object[]> parameterMap = fromValueListToValueArray(valueListParameterMap);
		return parameterMap;
	}

	Map<String, Object[]> fromValueListToValueArray(
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

}