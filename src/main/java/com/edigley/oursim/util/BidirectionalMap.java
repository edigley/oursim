package com.edigley.oursim.util;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.collections.BidiMap;
import org.apache.commons.collections.bidimap.TreeBidiMap;

public class BidirectionalMap<K, V> implements Map<K, V> {

	private BidiMap bidiMap;

	public BidirectionalMap() {
		this.bidiMap = new TreeBidiMap();
	}

	public void clear() {
		bidiMap.clear();
	}

	public boolean containsKey(Object key) {
		return bidiMap.containsKey(key);
	}

	public boolean containsValue(Object value) {
		return bidiMap.containsValue(value);
	}

	@SuppressWarnings("unchecked")
	public Set<java.util.Map.Entry<K, V>> entrySet() {
		return bidiMap.entrySet();
	}

	@SuppressWarnings("unchecked")
	public K getKey(V v) {
		return (K) bidiMap.getKey(v);
	}

	@SuppressWarnings("unchecked")
	public V get(Object key) {
		return (V) bidiMap.get(key);
	}

	public boolean isEmpty() {
		return bidiMap.isEmpty();
	}

	@SuppressWarnings("unchecked")
	public Set<K> keySet() {
		return bidiMap.keySet();
	}

	@SuppressWarnings("unchecked")
	public V put(K key, V value) {
		assert key != null && value != null;
		assert !bidiMap.containsKey(key) : key;
		assert !bidiMap.containsValue(value) : value;
		return (V) bidiMap.put(key, value);
	}

	@SuppressWarnings("unchecked")
	public void putAll(Map<? extends K, ? extends V> m) {
		bidiMap.putAll(m);
	}

	@SuppressWarnings("unchecked")
	public V remove(Object key) {
		assert bidiMap.containsKey(key);
		V removed = (V) bidiMap.remove(key);
		assert !bidiMap.containsValue(removed);
		assert !bidiMap.containsKey(key);
		return removed;
	}

	public int size() {
		return bidiMap.size();
	}

	@SuppressWarnings("unchecked")
	public Collection<V> values() {
		return bidiMap.values();
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		String sep = "";
		for (Entry<K, V> entry : entrySet()) {
			sb.append(sep).append("   " + entry.getKey() + " -> " + entry.getValue());
			sep = "\n";
		}
		return sb.toString();
	}

}
