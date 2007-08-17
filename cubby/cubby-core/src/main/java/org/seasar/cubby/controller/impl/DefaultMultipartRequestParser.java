package org.seasar.cubby.controller.impl;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.FileUploadBase.SizeLimitExceededException;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.seasar.cubby.controller.MultipartRequestParser;
import org.seasar.cubby.util.Messages;
import org.seasar.framework.log.Logger;
import org.seasar.framework.util.StringUtil;

public class DefaultMultipartRequestParser implements MultipartRequestParser {

	private final Logger logger = Logger.getLogger(this.getClass());

	private FileItemFactory fileItemFactory;

	private long sizeMax;

	public DefaultMultipartRequestParser(FileItemFactory fileItemFactory) {
		this.fileItemFactory = fileItemFactory;
	}

	public void setSizeMax(long sizeMax) {
		this.sizeMax = sizeMax;
	}

	@SuppressWarnings("unchecked")
	public Map<String, Object> getMultipartParameterMap(
			HttpServletRequest request) {
		ServletFileUpload upload = new ServletFileUpload(fileItemFactory);
		upload.setSizeMax(sizeMax);

		try {
			String encoding = request.getCharacterEncoding();
			upload.setHeaderEncoding(encoding);
			List<FileItem> items = upload.parseRequest(request);

			// Fieldごとにパラメータを集める
			Map<String, List<Object>> collectParameterMap = collectParameter(
					encoding, items);

			// 配列でパラメータMapを構築
			Map<String, Object> parameterMap = new HashMap<String, Object>();
			for (String key : collectParameterMap.keySet()) {
				List values = collectParameterMap.get(key);
				Object[] valueArray = null;
				if (values.get(0) instanceof String) {
					valueArray = new String[values.size()];
				} else {
					valueArray = new FileItem[values.size()];
				}
				parameterMap.put(key, values.toArray(valueArray));
			}
			return parameterMap;
		} catch (SizeLimitExceededException e) {
			throw new RuntimeException(Messages.getString(
					"valid.multipartRequsetParser.sizeLimitExceeded", e
							.getPermittedSize(), e.getActualSize()), e);
		} catch (FileUploadException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			logger.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private Map<String, List<Object>> collectParameter(String encoding,
			List<FileItem> items) throws UnsupportedEncodingException {
		Map<String, List<Object>> parameterMap = new LinkedHashMap<String, List<Object>>();
		for (FileItem item : items) {
			Object value = null;
			if (item.getName() == null) {
				value = new String(item.getString().getBytes("iso-8859-1"),
						encoding);
			} else {
				if (StringUtil.isEmpty(item.getName()) || item.getSize() == 0) {
					// ファイル名無し、あるいは０バイトのファイル
					value = null;
				} else {
					value = item;
				}
			}
			List<Object> values = null;
			if (parameterMap.containsKey(item.getFieldName())) {
				values = parameterMap.get(item.getFieldName());
			} else {
				values = new ArrayList<Object>();
				parameterMap.put(item.getFieldName(), values);
			}
			values.add(value);
		}
		return parameterMap;
	}

	public boolean isMultipart(HttpServletRequest request) {
		return ServletFileUpload.isMultipartContent(request);
	}

}
