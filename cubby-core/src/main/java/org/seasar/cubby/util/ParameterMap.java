package org.seasar.cubby.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;

public class ParameterMap implements Map<String, Object> {

	private final Map parameter;

	public ParameterMap(Map parameter) {
		this.parameter = parameter;
	}
	
	public Map getOriginalParameter() {
		return parameter;
	}

	public int toInt(String key) {
		Object value = parameter.get(key);
		if (value.getClass().isArray()) {
			String[] values = (String[])value;
			return Integer.parseInt(values[0]);
		} else {
			String valueStr = (String)value;
			return Integer.parseInt(valueStr);
		}
	}

	public Object get(Object key) {
		Object value = parameter.get(key);
		if (value == null) {
			System.out.println("key '" + key + "' is null.");
			return null;
		}
		if (value.getClass().isArray()) {
			Object[] values = (Object[])value;
			return values[0];
		} else {
			return value;
		}
	}
	
	public String toStr(Object key) {
		Object value = parameter.get(key);
		if (value == null) {
			System.out.println("key '" + key + "' is null.");
			return null;
		}
		if (value.getClass().isArray()) {
			String[] values = (String[])value;
			return values[0];
		} else {
			String valueStr = (String)value;
			return valueStr;
		}
	}

	public void clear() {
		parameter.clear();
	}

	public boolean containsKey(Object key) {
		return parameter.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return parameter.containsValue(value);
	}

	@SuppressWarnings("unchecked")
	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return parameter.entrySet();
	}

	public boolean isEmpty() {
		return parameter.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public Set keySet() {
		return parameter.keySet();
	}

	@SuppressWarnings("unchecked")
	public Object put(String key, Object value) {
		return parameter.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map<? extends String, ? extends Object> map) {
		parameter.putAll(map);
	}

	public Object remove(Object key) {
		return parameter.remove(key);
	}

	public int size() {
		return parameter.size();
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> values() {
		return parameter.values();
	}

	public Object getValue(String key) {
		Object value = parameter.get(key);
		if (value == null) {
			return null;
		}
		if (value.getClass().isArray()) {
			Object[] values = (Object[])value;
			if (values.length > 1) {
				return values;
			} else {
				return values[0];
			}
		} else {
			return value;
		}
	}
}
