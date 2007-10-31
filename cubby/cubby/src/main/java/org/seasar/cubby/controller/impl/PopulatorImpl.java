package org.seasar.cubby.controller.impl;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import org.seasar.cubby.controller.Populator;
import org.seasar.cubby.dxo.HttpRequestDxo;
import org.seasar.framework.beans.BeanDesc;
import org.seasar.framework.beans.PropertyDesc;
import org.seasar.framework.beans.PropertyNotFoundRuntimeException;
import org.seasar.framework.beans.factory.BeanDescFactory;
import org.seasar.framework.util.StringUtil;

public class PopulatorImpl implements Populator {

	private HttpRequestDxo httpRequestDxo;

	public void setHttpRequestDxo(final HttpRequestDxo httpRequestDxo) {
		this.httpRequestDxo = httpRequestDxo;
	}

	public void populate(final Map<String, Object[]> src, final Object dest) {
		if (src == null) {
			return;
		}

		final Map<String, Object> normalized = normalize(src, dest);

		httpRequestDxo.convert(normalized, dest);
	}

	private Map<String, Object> normalize(final Map<String, Object[]> src,
			final Object dest) {
		final Map<String, Object> normalized = new HashMap<String, Object>();
		final BeanDesc beanDesc = BeanDescFactory.getBeanDesc(dest.getClass());
		for (final Entry<String, Object[]> entry : src.entrySet()) {
			try {
				final String name = entry.getKey();
				final PropertyDesc propertyDesc = beanDesc
						.getPropertyDesc(name);
				if (propertyDesc.isWritable()) {
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
			} catch (final PropertyNotFoundRuntimeException e) {

			}
		}
		return normalized;
	}

	public Map<String, String> describe(final Object src) {
		final Map<String, String> dest = new HashMap<String, String>();
		if (src == null) {
			return dest;
		}
		httpRequestDxo.convert(src, dest);
		return dest;
	}

}
