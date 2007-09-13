package org.seasar.cubby.util;

import static java.lang.Boolean.TRUE;
import static org.seasar.cubby.CubbyConstants.ATTR_OUTPUT_VALUES;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collection;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;

public class CubbyHelperFunctions {

	public static String joinPropertyValue(Object beans, String propertyName,
			String delim) {
		return _joinPropertyValue(toArray(beans), propertyName, delim);
	}

	private static Object[] toArray(Object value) {
		if (value.getClass().isArray()) {
			return (Object[]) value;
		} else if (value instanceof Collection) {
			return ((Collection<?>) value).toArray();
		}
		throw new IllegalArgumentException("not array or collection : " + value);
	}

	private static String _joinPropertyValue(Object[] beans,
			String propertyName, String delim) {
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
		for (String key : map.keySet()) {
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
			return ((Collection<?>) values).contains(value);
		} else if (values.getClass().isArray()) {
			for (Object v : (Object[]) values) {
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

	@SuppressWarnings("unchecked")
	public static String convertFieldValue(Object source, Object form,
			HttpServletRequest request, String propertyName) {
		if (form == null || propertyName == null) {
			return CubbyFunctions.out(source);
		} else {
			final Map<String, String> outputValues = (Map<String, String>) request
					.getAttribute(ATTR_OUTPUT_VALUES);
			final String converted;
			if (outputValues != null) {
				final String outputValue = outputValues.get(propertyName);
				if (outputValue == null && source != null) {
					converted = source.toString();
				} else {
					converted = outputValues.get(propertyName);
				}
			} else if (source == null) {
				converted = "";
			} else {
				converted = source.toString();
			}
			return CubbyFunctions.out(converted);
		}
	}

	public static Object property(Object bean, String property) {
		if (StringUtil.isEmpty(property)) {
			return bean;
		}
		BeanDesc beanDesc = BeanDescFactory.getBeanDesc(bean.getClass());
		PropertyDesc propertyDesc = beanDesc.getPropertyDesc(property);
		return propertyDesc.getValue(bean);
	}

	@SuppressWarnings("unchecked")
	public static void addClassName(Map dyn, String className) {
		String classValue = (String) dyn.get("class");
		if (StringUtil.isEmpty(classValue)) {
			classValue = className;
		} else {
			classValue = classValue + " " + className;
		}
		dyn.put("class", classValue);
	}

	@SuppressWarnings("unchecked")
	public static Object formValue(Map dyn, Object form,
			HttpServletRequest request, String valueParamName) {
		Object value = dyn.get(valueParamName);
		String name = (String) dyn.get("name");
		if (TRUE.equals(request.getAttribute(ATTR_VALIDATION_FAIL))) {
			if (dyn.containsKey(valueParamName)) {
				return value;
			} else {
				Map<String, Object> params = (Map<String, Object>) request
						.getAttribute(ATTR_PARAMS);
				return CubbyUtils.getParamsValue(params, name);
			}
		} else {
			if (dyn.containsKey(valueParamName) || form == null
					|| StringUtil.isEmpty(name)) {
				return value;
			} else {
				return property(form, name);
			}
		}
	}
}
