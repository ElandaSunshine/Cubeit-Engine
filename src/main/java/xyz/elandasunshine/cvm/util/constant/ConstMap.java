package xyz.elandasunshine.cvm.util.constant;

import java.util.Collection;
import java.util.Map;
import java.util.Set;
import java.util.function.BiConsumer;

public class ConstMap<K, V>
{
	//==================================================================================================================
	interface Entry<K, V> extends Map.Entry<K, V> {}
	
	//==================================================================================================================
	private final Map<K, V> map;
	
	//==================================================================================================================
	public ConstMap(Map<K, V> parMap)
	{
		this.map = parMap;
	}
	
	//==================================================================================================================
	public int size()
	{
		return map.size();
	}

	//==================================================================================================================
	public boolean isEmpty()
	{
		return map.isEmpty();
	}

    public boolean containsKey(Object key)
    {
    	return map.containsKey(key);
    }

    public boolean containsValue(Object value)
    {
    	return map.containsValue(value);
    }

	//==================================================================================================================
    public V get(Object key)
    {
    	return map.get(key);
    }
    
    public V getOrDefault(Object key, V defaultValue)
    {
        return map.getOrDefault(key, defaultValue);
    }
    
	//==================================================================================================================
    public Set<K> keySet()
    {
    	return map.keySet();
    }
    
    public Collection<V> values()
    {
    	return map.values();
    }

    public Set<Map.Entry<K, V>> entrySet()
    {
    	return map.entrySet();
    }
    
	//==================================================================================================================
    @Override
    public boolean equals(Object o)
    {
    	return map.equals(o);
    }
    
    @Override
    public int hashCode()
    {
    	return map.hashCode();
    }    
    
	//==================================================================================================================
    public void forEach(BiConsumer<? super K, ? super V> action)
    {
        map.forEach(action);
    }
}
