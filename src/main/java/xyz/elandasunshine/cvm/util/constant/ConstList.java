package xyz.elandasunshine.cvm.util.constant;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.stream.Stream;

public class ConstList<T> implements Iterable<T>
{
	//==================================================================================================================
	private final List<T> list;
	
	//==================================================================================================================
	public ConstList(List<T> parList)
	{
		this.list = parList;
	}
	
	//==================================================================================================================
	public List<T> getMutableList()
	{
		return list;
	}
	
	//==================================================================================================================
	public int size()
	{
		return list.size();
	}
	
	//==================================================================================================================
	public Object[] toArray()
    {
    	return list.toArray();
    }

	public <U> U[] toArray(U[] a)
    {
    	return list.toArray(a);
    }

    public List<T> subList(int fromIndex, int toIndex)
    {
    	return list.subList(fromIndex, toIndex);
    }
	
	//==================================================================================================================
  	public boolean isEmpty()
  	{
  		return list.isEmpty();
  	}

  	public boolean contains(Object o)
  	{
  		return list.contains(o);
  	}
  	
	public boolean containsAll(Collection<?> c)
    {
    	return list.containsAll(c);
    }
	
	//==================================================================================================================
	@Override
	public boolean equals(Object o)
    {
    	return list.equals(o);
    }

	@Override
	public int hashCode()
    {
    	return list.hashCode();
    }

	//==================================================================================================================
    public T get(int index)
    {
    	return list.get(index);
    }

	//==================================================================================================================
    public int indexOf(Object o)
    {
    	return list.indexOf(o);
    }

    public int lastIndexOf(Object o)
    {
    	return list.lastIndexOf(o);
    }
    
	//==================================================================================================================
    @Override
	public Iterator<T> iterator()
    {
    	return list.iterator();
    }
    
    @Override
    public Spliterator<T> spliterator()
    {
    	return list.spliterator();
    }
    
    public ListIterator<T> listIterator()
    {
    	return list.listIterator();
    }

    public ListIterator<T> listIterator(int index)
    {
    	return list.listIterator(index);
    }
    
    //==================================================================================================================
    public Stream<T> stream()
    {
        return list.stream();
    }

    public Stream<T> parallelStream()
    {
        return list.parallelStream();
    }
}
