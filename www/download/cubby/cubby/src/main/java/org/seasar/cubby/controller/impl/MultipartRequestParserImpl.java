package org.seasar.cubby.controller.impl;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUpload;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.RequestContext;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.cubby.controller.RequestParser;
import org.seasar.cubby.exception.FileUploadRuntimeException;
import org.seasar.framework.container.SingletonS2Container;
import org.seasar.framework.exception.IORuntimeException;
import org.seasar.framework.util.StringUtil;

public class MultipartRequestParserImpl implements RequestParser {

	@SuppressWarnings("unchecked")
	public Map<String, Object> getParameterMap(final HttpServletRequest request) {
		Map<String, Object> parameterMap;
		if (ServletFileUpload.isMultipartContent(request)) {
			final FileUpload fileUpload = SingletonS2Container
					.getComponent(FileUpload.class);
			final RequestContext requestContext = SingletonS2Container
					.getComponent(RequestContext.class);
			parameterMap = this.getMultipartParameterMap(fileUpload,
					requestContext);
		} else {
			parameterMap = request.getParameterMap();
		}
		return parameterMap;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getMultipartParameterMap(
			final FileUpload fileUpload, final RequestContext requestContext) {
		try {
			final String encoding = requestContext.getCharacterEncoding();
			fileUpload.setHeaderEncoding(encoding);
			final List<FileItem> items = fileUpload
					.parseRequest(requestContext);

			// Fieldごとにパラメータを集める
			final Map<String, Object> parameterMap = toParameterMap(encoding,
					items);

			return parameterMap;
		} catch (final FileUploadException e) {
			throw new FileUploadRuntimeException(e);
		} catch (final IOException e) {
			throw new IORuntimeException(e);
		}
	}

	private Map<String, Object> toParameterMap(final String encoding,
			final List<FileItem> items) throws UnsupportedEncodingException {
		final Map<String, List<Object>> valueListParameterMap = new LinkedHashMap<String, List<Object>>();
		for (final FileItem item : items) {
			Object value = null;
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
			List<Object> values = null;
			if (valueListParameterMap.containsKey(item.getFieldName())) {
				values = valueListParameterMap.get(item.getFieldName());
			} else {
				values = new ArrayList<Object>();
				valueListParameterMap.put(item.getFieldName(), values);
			}
			values.add(value);
		}

		final Map<String, Object> parameterMap = fromValueListToValueArray(valueListParameterMap);
		return parameterMap;
	}

	private Map<String, Object> fromValueListToValueArray(
			final Map<String, List<Object>> collectParameterMap) {
		// 配列でパラメータMapを構築
		final Map<String, Object> parameterMap = new HashMap<String, Object>();
		for (final String key : collectParameterMap.keySet()) {
			final List<?> values = collectParameterMap.get(key);
			Object[] valueArray = null;
			if (values.get(0) instanceof String) {
				valueArray = new String[values.size()];
			} else {
				valueArray = new FileItem[values.size()];
			}
			parameterMap.put(key, values.toArray(valueArray));
		}
		return parameterMap;
	}

}
