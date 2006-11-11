package org.seasar.cubby.convert.impl;

import java.lang.reflect.Method;
import java.util.Map;

import org.seasar.cubby.convert.Populater;
import org.seasar.cubby.convert.ValueConverter;
import org.seasar.cubby.util.ClassUtils;
import org.seasar.cubby.util.StringUtils;

public class PopulaterImpl implements Populater {

	private ValueConverter valueConverter;
	public PopulaterImpl(ValueConverter valueConverter) {
		this.valueConverter = valueConverter;
	}

	public void populate(Object target, Map<String, Object> params) {
		for (String name : params.keySet()) {
			Method setter = ClassUtils.getSetter(target.getClass(), name);
			if (setter != null) {
				Object values = params.get(name);
				Class<?> destClass = setter.getParameterTypes()[0];
				Object converted = convert(values, target, setter, destClass);
				Object[] args = new Object[] { converted };
				ClassUtils.invoke(target, setter, args);
			}
		}
	}

	public Object convert(Object source, Object target, Method setter,
			Class destClass) {
		if (destClass.isArray()) {
			if (source.getClass().isArray()) {
				return valueConverter.convert(StringUtils.removeEmpty((Object[])source),
						target, setter, destClass);
			} else {
				return valueConverter.convert(StringUtils.removeEmpty(new Object[] {source}),
						target, setter, destClass);
			}
		} else {
			if (source.getClass().isArray()) {
				return valueConverter.convert(((Object[])source)[0], target, setter, destClass);
			} else {
				return valueConverter.convert(source, target, setter, destClass);
			}
		}
	}
}
