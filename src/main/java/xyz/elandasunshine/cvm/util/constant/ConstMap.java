package xyz.elandasunshine.cvm.util.constant;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ConstMap<K, V> implements Map<K, V>
{
	//==================================================================================================================
	private static void notSupported() { throw new UnsupportedOperationException("Const collections are immutable."); }
			
	//==================================================================================================================
	private final Map<K, V> map;
	
	//==================================================================================================================
	public ConstMap(Map<K, V> parMap) { this.map = parMap; }
	
	//==================================================================================================================
	@Override public int size() { return map.size(); }

	//==================================================================================================================
	@Override public boolean isEmpty()                   { return map.isEmpty(); }
	@Override public boolean containsKey(Object key)     { return map.containsKey(key); }
    @Override public boolean containsValue(Object value) { return map.containsValue(value); }

	//==================================================================================================================
    @Override public V get(Object key)                          { return map.get(key); }
    @Override public V getOrDefault(Object key, V defaultValue) { return map.getOrDefault(key, defaultValue); }
    
	//==================================================================================================================
    @Override public Set<K> keySet()                 { return map.keySet(); }
    @Override public Collection<V> values()          { return map.values(); }
    @Override public Set<Map.Entry<K, V>> entrySet() { return map.entrySet(); }
    
	//==================================================================================================================
    @Override public boolean equals(Object o) { return map.equals(o); }
    @Override public int     hashCode()       { return map.hashCode(); }    
    
	//==================================================================================================================
    @Override public void forEach(BiConsumer<? super K, ? super V> action) { map.forEach(action); }

	//==================================================================================================================
	@Override public V    put(K key, V value)                     { notSupported(); return null; }
	@Override public V    remove(Object key)                      { notSupported(); return null; }
	@Override public void putAll(Map<? extends K, ? extends V> m) { notSupported(); }
	@Override public void clear()                                 { notSupported(); }
}
