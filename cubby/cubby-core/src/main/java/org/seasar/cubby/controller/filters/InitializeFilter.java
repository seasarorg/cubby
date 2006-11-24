package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_ALL_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTROLLER;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FIELD_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.seasar.cubby.CubbyConstants;
import org.seasar.cubby.annotation.Session;
import org.seasar.cubby.controller.ActionContext;
import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.controller.impl.ActionErrorsImpl;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.FlashHashMap;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.cubby.util.ParameterMap;
import org.seasar.cubby.util.RequestMap;
import org.seasar.cubby.util.ResourceBundleUtils;
import org.seasar.cubby.util.SessionMap;
import org.seasar.cubby.util.StringUtils;

public class InitializeFilter extends AroundFilter {

	private static final Log LOG = LogFactory.getLog(InitializeFilter.class);
	
	@Override
	protected void doBeforeFilter(ActionContext action) {
		setupErrors(action);
		setupLocale(action);
		setupImplicitVariable(action);
		setupParams(action);
		setupRequest(action);
		setupSession(action);
		setupFlash(action);
		action.getController().initalize();
	}

	private void setupErrors(ActionContext action) {
		action.getController().setErrors(new ActionErrorsImpl());
	}

	private void setupLocale(ActionContext action) {
		HttpServletRequest req = action.getRequest();
		LocaleHolder.setLocale(req.getLocale());
	}

	private void setupImplicitVariable(ActionContext action) {
		HttpServletRequest req = action.getRequest();
		req.setAttribute("contextPath", req.getContextPath());
		ResourceBundle resource = ResourceBundle.getBundle(RES_MESSAGES, LocaleHolder.getLocale());
		Map messagesMap = ResourceBundleUtils.toMap(resource);
		req.setAttribute("messages", messagesMap);
	}

	@Override
	protected void doAfterFilter(ActionContext action) {
		bindAttributes(action);
	}

	private void setupParams(ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		ParameterMap params = new ParameterMap(getMultipartSupportParameterMap(request));
		controller.setParams(params);
		request.setAttribute(ATTR_PARAMS, controller.getParams());
	}

	private void setupRequest(ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		RequestMap requestMap = new RequestMap(request);
		controller.setRequest(requestMap);
		setupRequestScopeFields(action);
	}

	private void setupRequestScopeFields(ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		for (Field f : controller.getClass().getFields()) {
			if (isSessionScope(f)) {
				Object value = request.getAttribute(f.getName());
				if (value != null) {
					ClassUtils.setFieldValue(f, controller, value);
				}
			}
		}
	}

	private void setupSession(ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		SessionMap sessionMap = new SessionMap(request.getSession());
		controller.setSession(sessionMap);
		setupSessionScopeFields(action);
	}

	private void setupSessionScopeFields(ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		HttpSession session = request.getSession();
		for (Field f : controller.getClass().getFields()) {
			if (isSessionScope(f)) {
				Object value = session.getAttribute(f.getName());
				if (value != null) {
					ClassUtils.setFieldValue(f, controller, value);
				}
			}
		}
	}

	@SuppressWarnings("unchecked")
	private void setupFlash(ActionContext action) {
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		HttpSession session = request.getSession();
		Map<String, Object> flash = (Map<String, Object>) session
				.getAttribute(ATTR_FLASH);
		if (flash == null) {
			flash = new FlashHashMap();
			session.setAttribute(CubbyConstants.ATTR_FLASH, flash);
		}
		controller.setFlash(flash);
	}

	private void bindAttributes(ActionContext action) {
		// set controller
		Controller controller = action.getController();
		HttpServletRequest request = action.getRequest();
		request.setAttribute(ATTR_CONTROLLER, controller);

		// set actioneErrors
		request.setAttribute(ATTR_ERRORS, controller.getErrors());
		request.setAttribute(ATTR_ALL_ERRORS, controller.getErrors().getAllErrors());
		request.setAttribute(ATTR_ACTION_ERRORS, controller.getErrors().getActionErrors());
		request.setAttribute(ATTR_FIELD_ERRORS, controller.getErrors().getFieldErrors());

		// set field value
		HttpSession session = request.getSession();
		for (Field f : controller.getClass().getFields()) {
			Object value = ClassUtils.getFieldValue(f, controller);
			if (isSessionScope(f)) {
				session.setAttribute(f.getName(), value);
			} else {
				request.setAttribute(f.getName(), value);
			}
		}
	}

	private boolean isSessionScope(Field f) {
		return f.getAnnotation(Session.class) != null;
	}
	
	@SuppressWarnings({ "unchecked", "deprecation" })
	private Map<String,Object> getMultipartSupportParameterMap(HttpServletRequest request) {
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);
		if (isMultipart) {
			return getMultipartParameterMap(request);
		} else {
			return request.getParameterMap();
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String,Object> getMultipartParameterMap(HttpServletRequest request) {
		FileItemFactory factory = new DiskFileItemFactory();
		ServletFileUpload upload = new ServletFileUpload(factory);
		try {
			String encoding = request.getCharacterEncoding();
			upload.setHeaderEncoding(encoding);
			List<FileItem> items = upload.parseRequest(request);
			
			// Fieldごとにパラメータを集める
			Map<String, List<Object>> collectParameterMap = collectParameter(encoding, items);
			
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
		} catch (FileUploadException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		} catch (UnsupportedEncodingException e) {
			LOG.error(e.getMessage(), e);
			throw new RuntimeException(e);
		}
	}

	private Map<String, List<Object>> collectParameter(String encoding, List<FileItem> items) throws UnsupportedEncodingException {
		Map<String,List<Object>> parameterMap = new LinkedHashMap<String, List<Object>>();
		for (FileItem item : items) {
			Object value = null;
			if (item.getName() == null) {
				value = new String(item.getString().getBytes("iso-8859-1"), encoding);
			} else {
				if (StringUtils.isEmpty(item.getName()) || item.getSize() == 0) {
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
}
