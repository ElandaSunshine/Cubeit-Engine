package xyz.elandasunshine.capi.registry;

import java.util.ArrayList;
import java.util.Iterator;

import com.google.common.collect.Lists;

public final class Registry<T> implements Iterable<RegistryEntry<T>>
{
	//==================================================================================================================
	private class RegistryIterator implements Iterator<RegistryEntry<T>>
	{
		private final Registry<T> registry;
		private       int         index = 0;
		
		//==============================================================================================================
		public RegistryIterator(final Registry<T> parRegistry) { registry = parRegistry; }
		
		//==============================================================================================================
		@Override
		public boolean hasNext()
		{
			return index < registry.size();
		}

		@Override
		public RegistryEntry<T> next()
		{
			return registry.getEntryAt(index++);
		}
		
	}
	
	//==================================================================================================================
	private ArrayList<RegistryEntry<T>> elements;
	
	//==================================================================================================================
	public Registry()                   { elements = Lists.newArrayList(); }
	public Registry(final int capacity) { elements = Lists.newArrayListWithCapacity(capacity); }
	
	@SafeVarargs
	public Registry(final RegistryEntry<T> ...parElements)
	{
		elements = Lists.newArrayList(parElements);
	}
	
	//==================================================================================================================
	public boolean register(final String id, final T element)
	{
		if (contains(id) || contains(element))
		{
			return false;
		}
		
		elements.add(new RegistryEntry<T>(id, element));
		return true;
	}
	
	//==================================================================================================================
	public T                getElementAt(final int index) { return elements.get(index).getValue(); }
	public String           getIdAt     (final int index) { return elements.get(index).getKey(); }
	public RegistryEntry<T> getEntryAt  (final int index) { return elements.get(index); }
	
	public T get(final String id)
	{
		for (final RegistryEntry<T> entry : elements)
		{
			if (entry.getKey().equals(id))
			{
				return entry.getValue();
			}
		}
		
		return null;
	}
	
	public String get(final T element)
	{
		for (final RegistryEntry<T> entry : elements)
		{
			if (entry.getValue().equals(element))
			{
				return entry.getKey();
			}
		}
		
		return null;
	}
	
	//==================================================================================================================
	public void clear() { elements.clear(); }
	
	//==================================================================================================================
	public int size() { return elements.size(); }
	
	//==================================================================================================================
	public int indexOf(final String id)
	{
		for (int i = 0; i < elements.size(); ++i)
		{			
			if (elements.get(i).getKey().equals(id))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	public int indexOf(final T element)
	{
		for (int i = 0; i < elements.size(); ++i)
		{			
			if (elements.get(i).getValue().equals(element))
			{
				return i;
			}
		}
		
		return -1;
	}
	
	//==================================================================================================================
	public boolean contains(final String id) { return indexOf(id)      >= 0; }
	public boolean contains(final T element) { return indexOf(element) >= 0; }
	
	//==================================================================================================================
	public boolean isEmpty() { return elements.isEmpty(); }
	
	//==================================================================================================================
	@Override
	public Iterator<RegistryEntry<T>> iterator()
	{
		return new RegistryIterator(this);
	}
}
