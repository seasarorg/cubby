package org.seasar.cubby.util;

import static java.lang.Boolean.TRUE;
import static org.seasar.cubby.CubbyConstants.ATTR_CONTROLLER;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.lang.reflect.Method;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.cubby.controller.Controller;
import org.seasar.cubby.convert.ValueConverter;


public class CubbyFunction {
	
	private static final Class[] EMPTY_PARAM_TYPES = new Class[0];
	private static final Object[] EMPTY_ARGS = new Object[0];
	public static ValueConverter valueConverter;
	
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
	
	public static Boolean contains(Object c, Object value) {
		if (c instanceof Collection) {
			return _contains((Collection)c, value);
		} else if (c != null && c.getClass().isArray()) {
			return _contains(Arrays.asList((Object[])c), value);
		} else {
			return false;
		}
	}

	public static Boolean _contains(Collection c, Object value) {
		return c.contains(value);
	}

	public static Boolean containsKey(Map m, Object value) {
		return m.containsKey(value);
	}

	public static Boolean containsValue(Map m, Object value) {
		return m.containsValue(value);
	}

	public static String toAttr(Map<String, Object> map) {
		StringBuilder sb = new StringBuilder();
		for(String key : map.keySet()) {
			sb.append(key);
			sb.append("=\"");
			sb.append(map.get(key));
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
		return values.toString().equals(value.toString());
	}

	public static String odd(Integer index, String classnames) {
		String[] c = classnames.split(",");
		return c[index % c.length];
	}

	public static String out(Object value) {
		return value == null ? "" : value.toString();
	}

	public static String convertFieldValue(Object source, Object form, String propertyName) {
		if (form == null || propertyName == null) {
			return out(source);
		} else {
			Method setter = ClassUtils.getSetter(form.getClass(), propertyName);
			String converted = (String) valueConverter.convert(source, form, setter, String.class);
			return out(converted);
		}
	}
	
	public static String dateFormat(Object date, String pattern) {
		if (date instanceof Date) {
			SimpleDateFormat format = new SimpleDateFormat(pattern);
			return format.format(date);
		} else {
			return "";
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
		String classValue = dyn.get("class") + " " + className;
		dyn.put("class", classValue);
	}
	
	public static Object formValue(Object value, Object form, String name, HttpServletRequest request) {
		if (TRUE.equals(request.getAttribute(ATTR_VALIDATION_FAIL))) {
			if (value == null && !StringUtils.isEmpty(name)) {
				Controller controller = (Controller) request.getAttribute(ATTR_CONTROLLER);
				return controller.getParams().get(name);
			} else {
				return value;
			}
		} else {
			if (value == null && form != null && !StringUtils.isEmpty(name)) {
				return property(form, name);
			} else {
				return value;
			}
		}
	}
}
