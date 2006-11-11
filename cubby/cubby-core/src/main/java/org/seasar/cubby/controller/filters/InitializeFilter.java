package org.seasar.cubby.controller.filters;

import static org.seasar.cubby.CubbyConstants.ATTR_ACTION_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTROLLER;
import static org.seasar.cubby.CubbyConstants.ATTR_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FIELD_ERRORS;
import static org.seasar.cubby.CubbyConstants.ATTR_FLASH;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.RES_MESSAGES;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Field;
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
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.FlashHashMap;
import org.seasar.cubby.util.LocaleHolder;
import org.seasar.cubby.util.ParameterMap;
import org.seasar.cubby.util.RequestMap;
import org.seasar.cubby.util.ResourceBundleUtils;
import org.seasar.cubby.util.SessionMap;

public class InitializeFilter extends AroundFilter {

	private static final Log LOG = LogFactory.getLog(InitializeFilter.class);
	
	@Override
	protected void doBeforeFilter(ActionContext action) {
		setupLocale(action);
		setupImplicitVariable(action);
		setupParams(action);
		setupRequest(action);
		setupSession(action);
		setupFlash(action);
		action.getController().initalize();
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
		List<String> actionErrors = controller.getErrors().getErrors();
		request.setAttribute(ATTR_ACTION_ERRORS, actionErrors);
		request.setAttribute(ATTR_ERRORS, controller.getErrors());
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
			Map<String,Object> parameterMap = new LinkedHashMap<String, Object>();
			String encoding = request.getCharacterEncoding();
			upload.setHeaderEncoding(encoding);
			List<FileItem> items = upload.parseRequest(request);
			for (FileItem item : items) {
				if (item.getName() == null) {
					String value = new String(item.getString().getBytes("iso-8859-1"), encoding);
					parameterMap.put(item.getFieldName(), new String[] {value});
				} else {
					parameterMap.put(item.getFieldName(), new FileItem[] { item });
				}
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
}
