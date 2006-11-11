package org.seasar.cubby.util;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * 
 * @author agata
 */
public class FlashHashMap<K,V> implements Map<K,V> {

	private static final long serialVersionUID = 8417458542243888272L;

	private Map<K,V> map;
	
	public FlashHashMap() {
		map = new HashMap<K,V>();
	}
	
	public V get(Object key) {
		return map.remove(key);
	}
	
	public void clear() {
		map.clear();
	}

	public boolean containsKey(Object key) {
		return map.containsKey(key);
	}

	public boolean containsValue(Object key) {
		return map.containsValue(key);
	}

	@SuppressWarnings("unchecked")
	public Set entrySet() {
		return map.entrySet();
	}

	public boolean isEmpty() {
		return map.isEmpty();
	}

	public Set<K> keySet() {
		return map.keySet();
	}

	public V put(K key, V value) {
		return map.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map map) {
		map.putAll(map);
	}

	public V remove(Object key) {
		return map.remove(key);
	}

	public int size() {
		return map.size();
	}

	public Collection<V> values() {
		return map.values();
	}
}
