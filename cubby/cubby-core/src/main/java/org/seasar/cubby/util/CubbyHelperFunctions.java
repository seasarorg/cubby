package org.seasar.cubby.util;

import static java.lang.Boolean.TRUE;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTROLLER;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.convert.ValueConverter;


public class CubbyHelperFunctions {

	private static final Class[] EMPTY_PARAM_TYPES = new Class[0];

	private static final Object[] EMPTY_ARGS = new Object[0];

//	private static ThreadLocal<ValueConverter> VALUE_CONVERTER = new ThreadLocal<ValueConverter>();
	private static ValueConverter VALUE_CONVERTER;

	public static void setValueConverter(final ValueConverter valueConverter) {
		VALUE_CONVERTER = valueConverter;
	}

	public static String joinPropertyValue(Object beans, String propertyName, String delim) {
		return _joinPropertyValue(toArray(beans), propertyName, delim);
	}

	private static Object[] toArray(Object value) {
		if (value.getClass().isArray()) {
			return (Object[])value;
		} else if (value instanceof Collection) {
			return ((Collection)value).toArray();
		}
		throw new IllegalArgumentException("not array or collection : " + value);
	}

	private static String _joinPropertyValue(Object[] beans, String propertyName, String delim) {
		StringBuilder sb = new StringBuilder();
		for (int i = 0; i < beans.length; i++) {
			Object bean = beans[i];
			Object value = property(bean, propertyName);
			sb.append(value);
			if (i < beans.length - 1) {
				sb.append(delim);
			}
		}
		return sb.toString();
	}
	
	public static String toAttr(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		for(String key : map.keySet()) {
			if (key.equals("value") || key.equals("checkedValue")) {
				continue;
			}
			sb.append(key);
			sb.append("=\"");
			sb.append(CubbyFunctions.escapeHtml(map.get(key)));
			sb.append("\" ");
		}
		return sb.toString();
	}
	
	public static String checked(String value, Object values) {
		if (value == null || values == null) {
			return "";
		}
		if (isChecked(value, values)) {
			return "checked=\"true\"";
		} else {
			return "";
		}
	}

	public static String selected(String value, Object values) {
		if (value == null || values == null) {
			return "";
		}
		if (isChecked(value, values)) {
			return "selected=\"true\"";
		} else {
			return "";
		}
	}

	public static boolean isChecked(String value, Object values) {
		if (values instanceof Collection) {
			return ((Collection)values).contains(value);
		} else if (values.getClass().isArray()) {
			for (Object v : (Object[])values) {
				if (equalsValue(v, value)) {
					return true;
				}
			}
			return false;
		} else {
			return equalsValue(values, value);
		}
	}
	
	private static boolean equalsValue(Object values, Object value) {
		if (values == value) { 
			return true; 
		} else if (values == null) { 
			return false; 
		} else {
			return values.toString().equals(value.toString());
		}
	}

	public static String convertFieldValue(Object source, Object form, String propertyName) {
		if (form == null || propertyName == null) {
			return CubbyFunctions.out(source);
		} else {
			String converted = null;
			if (source != null) {
				converted = (String) VALUE_CONVERTER.convert(source);
			}
			return CubbyFunctions.out(converted);
		}
	}
	
	public static Object property(Object bean, String property) {
		if (StringUtils.isEmpty(property)) {
			return bean;
		}
		String methodName = "get" + StringUtils.toFirstUpper(property);
		return ClassUtils.invoke(bean, methodName, EMPTY_PARAM_TYPES, EMPTY_ARGS);
	}
	
	@SuppressWarnings("unchecked")
	public static void addClassName(Map dyn, String className) {
		String classValue = (String) dyn.get("class");
		if (StringUtils.isEmpty(classValue)) {
			classValue = className;
		} else {
			classValue = classValue + " " + className;
		}
		dyn.put("class", classValue);
	}
	
	public static Object formValue(Map dyn, Object form, HttpServletRequest request, String valueParamName) {
		Object value = dyn.get(valueParamName);
		String name = (String) dyn.get("name");
		if (TRUE.equals(request.getAttribute(ATTR_VALIDATION_FAIL))) {
			if (dyn.containsKey(valueParamName)) {
				return value;
			} else {
				Controller controller = (Controller) request.getAttribute(ATTR_CONTROLLER);
				return controller.getParams().getValue(name);
			}
		} else {
			if (dyn.containsKey(valueParamName) || form == null || StringUtils.isEmpty(name)) {
				return value;
			} else {
				return property(form, name);
			}
		}
	}
}
