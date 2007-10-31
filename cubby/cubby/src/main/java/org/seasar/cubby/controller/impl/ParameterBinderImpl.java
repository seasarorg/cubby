package org.seasar.cubby.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.cubby.controller.ParameterBinder;
import org.seasar.cubby.dxo.RequestParameterDxo;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;

public class ParameterBinderImpl implements ParameterBinder {

	private RequestParameterDxo requestParameterDxo;

	public void setRequestParameterDxo(
			final RequestParameterDxo requestParameterDxo) {
		this.requestParameterDxo = requestParameterDxo;
	}

	private Map<String, Object> normalize(final Map<String, Object[]> parameters,
			final Class<?> formType) {
		final Map<String, Object> normalized = new HashMap<String, Object>();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(formType);
		for (final Entry<String, Object[]> entry : parameters.entrySet()) {
			final String name = entry.getKey();
			if (beanDesc.hasPropertyDesc(name)) {
				final PropertyDesc propertyDesc = beanDesc
						.getPropertyDesc(name);
				if (propertyDesc.isReadable() && propertyDesc.isWritable()) {
					final Object[] values = entry.getValue();
					final Class<?> propertyType = propertyDesc
							.getPropertyType();
					if (propertyType.isArray()) {
						normalized.put(name, values);
					} else if (Collection.class.isAssignableFrom(propertyType)) {
						normalized.put(name, values);
					} else if (String.class.isAssignableFrom(propertyType)) {
						final String value = (String) values[0];
						if (!StringUtil.isEmpty(value)) {
							normalized.put(name, value);
						} else {
							normalized.put(name, null);
						}
					} else {
						normalized.put(name, values[0]);
					}
				}
			}
		}
		return normalized;
	}

	public void bindParamsToForm(final Map<String, Object[]> src, final Object dest) {
		if (src == null) {
			return;
		}

		final Map<String, Object> normalized = normalize(src, dest.getClass());

		requestParameterDxo.convert(normalized, dest);
	}

	public Map<String, String[]> bindFormToOutputValues(final Object form) {
		final Map<String, String[]> dest = new HashMap<String, String[]>();
		if (form == null) {
			return dest;
		}

		requestParameterDxo.convert(form, dest);
		return dest;
	}

}
