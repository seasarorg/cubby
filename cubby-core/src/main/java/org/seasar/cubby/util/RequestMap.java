package org.seasar.cubby.util;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

public class RequestMap implements Map<String, Object> {
	private HttpServletRequest request;
	
	public RequestMap(HttpServletRequest request) {
		this.request = request;
	}

	public void clear() {
	}

	public boolean containsKey(Object key) {
		return keySet().contains(key);
	}

	public boolean containsValue(Object value) {
		return values().contains(value);
	}

	public Set<java.util.Map.Entry<String, Object>> entrySet() {
		return null;
	}

	public Object get(Object key) {
		return request.getAttribute((String)key);
	}

	public boolean isEmpty() {
		return !request.getAttributeNames().hasMoreElements();
	}

	@SuppressWarnings("unchecked")
	public Set<String> keySet() {
		Set<String> keySet = new HashSet<String>();
		for (Enumeration<String> enu = request.getAttributeNames(); enu.hasMoreElements(); ) {
			keySet.add(enu.nextElement());
		}
		return Collections.unmodifiableSet(keySet);
	}

	public Object put(String key, Object value) {
		request.setAttribute(key, value);
		return value;
	}

	public void putAll(Map<? extends String, ? extends Object> map) {
		for (String key : map.keySet()) {
			put(key, map.get(key));
		}
	}

	public Object remove(Object key) {
		Object value = get(key);
		put((String)key, null);
		return value;
	}

	@SuppressWarnings("unchecked")
	public int size() {
		int size = 0;
		for (Enumeration<String> enu = request.getAttributeNames(); enu.hasMoreElements(); ) {
			size++;
		}
		return size;
	}

	@SuppressWarnings("unchecked")
	public Collection<Object> values() {
		Collection<Object> values = new ArrayList<Object>();
		for (Enumeration<String> enu = request.getAttributeNames(); enu.hasMoreElements(); ) {
			values.add(request.getAttribute(enu.nextElement()));
		}
		return Collections.unmodifiableCollection(values);
	}
}
