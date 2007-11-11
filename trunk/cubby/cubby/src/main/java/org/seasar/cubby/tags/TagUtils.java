package org.seasar.cubby.tags;

import static java.lang.Boolean.TRUE;
import static javax.servlet.jsp.PageContext.REQUEST_SCOPE;
import static org.seasar.cubby.CubbyConstants.ATTR_OUTPUT_VALUES;
import static org.seasar.cubby.CubbyConstants.ATTR_PARAMS;
import static org.seasar.cubby.CubbyConstants.ATTR_VALIDATION_FAIL;

import java.util.Collection;
import java.util.Map;
import java.util.Map.Entry;

import javax.servlet.jsp.JspContext;
import javax.servlet.jsp.PageContext;

import org.seasar.cubby.action.ActionErrors;
import org.seasar.cubby.util.CubbyFunctions;
import org.seasar.framework.util.StringUtil;

/**
 * 
 * @author baba
 * 
 */
class TagUtils {

	public static ActionErrors errors(final JspContext context) {
		return (ActionErrors) context.getAttribute("errors", REQUEST_SCOPE);
	}

	@SuppressWarnings("unchecked")
	private static Object[] paramValues(final JspContext context,
			final String name) {
		final Map<String, Object[]> valuesMap = (Map<String, Object[]>) context
				.getAttribute(ATTR_PARAMS, REQUEST_SCOPE);
		final Object[] values;
		if (valuesMap == null || !valuesMap.containsKey(name)) {
			values = new Object[0];
		} else {
			values = valuesMap.get(name);
		}
		return values;
	}

	@SuppressWarnings("unchecked")
	private static Object[] formValues(final Map<String, String[]> valuesMap,
			final String name) {
		final Object[] values;
		if (valuesMap == null || !valuesMap.containsKey(name)) {
			values = new Object[0];
		} else {
			values = valuesMap.get(name);
		}
		return values;
	}

	public static Object[] multipleFormValues(final JspContext context,
			final Map<String, String[]> outputValuesMap, final String name) {
		return multipleFormValues(context, outputValuesMap, name, null);
	}

	public static Object[] multipleFormValues(final JspContext context,
			final Map<String, String[]> outputValuesMap, final String name,
			final String checkedValue) {
		final Object[] values;
		if (isValidationFail(context)) {
			values = paramValues(context, name);
		} else {
			if (checkedValue != null) {
				values = new Object[] { checkedValue };
			} else {
				values = formValues(outputValuesMap, name);
			}
		}
		return values;
	}

	public static Object formValue(final JspContext context,
			final Map<String, String[]> outputValuesMap, final String name,
			final Integer index, final Object specifiedValue) {
		final Object value;

		if (isValidationFail(context)) {
			final Object[] values = paramValues(context, name);
			value = value(values, index);
		} else {
			if (specifiedValue == null) {
				final Object[] values = formValues(outputValuesMap, name);
				value = value(values, index);
			} else {
				value = specifiedValue;
			}
		}

		return value;
	}

	private static Object value(final Object[] values, final Integer index) {
		final Object value;
		if (values == null) {
			value = "";
		} else {
			if (index == null) {
				value = getElement(values, 0);
			} else {
				value = getElement(values, index);
			}
		}
		return value;
	}

	private static Object getElement(final Object[] values, final Integer index) {
		final Object value;
		if (values.length <= index) {
			value = "";
		} else {
			value = values[index];
		}
		return value;
	}

	private static boolean isValidationFail(final JspContext context) {
		return TRUE.equals(context.getAttribute(ATTR_VALIDATION_FAIL,
				REQUEST_SCOPE));
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String[]> outputValues(final JspContext context) {
		final Map<String, String[]> outputValues = (Map<String, String[]>) context
				.getAttribute(ATTR_OUTPUT_VALUES, PageContext.PAGE_SCOPE);
		return outputValues;
	}

	public static String toAttr(final Map<String, Object> map) {
		final StringBuilder sb = new StringBuilder();
		for (final Entry<String, Object> entry : map.entrySet()) {
			final String key = entry.getKey();
			if ("value".equals(key) || "checkedValue".equals(key)) {
				continue;
			}
			sb.append(key);
			sb.append("=\"");
			sb.append(CubbyFunctions.escapeHtml(entry.getValue()));
			sb.append("\" ");
		}
		return sb.toString();
	}

	public static boolean isChecked(final String value, final Object values) {
		if (values instanceof Collection) {
			return ((Collection<?>) values).contains(value);
		} else if (values.getClass().isArray()) {
			for (final Object v : (Object[]) values) {
				if (equalsValue(v, value)) {
					return true;
				}
			}
			return false;
		} else {
			return equalsValue(values, value);
		}
	}

	private static boolean equalsValue(final Object values, final Object value) {
		if (values == value) {
			return true;
		} else if (values == null) {
			return false;
		} else {
			return values.toString().equals(value.toString());
		}
	}

	@SuppressWarnings("unchecked")
	public static void addClassName(final Map dyn, final String className) {
		String classValue = (String) dyn.get("class");
		if (StringUtil.isEmpty(classValue)) {
			classValue = className;
		} else {
			classValue = classValue + " " + className;
		}
		dyn.put("class", classValue);
	}

}
