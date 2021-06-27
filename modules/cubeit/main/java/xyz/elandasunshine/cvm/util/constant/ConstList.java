package xyz.elandasunshine.cvm.util.constant;

import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Spliterator;
import java.util.stream.Stream;

public class ConstList<T> implements List<T>
{
    //==================================================================================================================
	private static void notSupported() { throw new UnsupportedOperationException("Const collections are immutable."); }
	
	//==================================================================================================================
	private final List<T> list;
	
	//==================================================================================================================
	public ConstList(List<T> parList) { this.list = parList; }
	
	//==================================================================================================================
	@Override public int size() { return list.size(); }
	
	//==================================================================================================================
	@Override public Object[] toArray()                           { return list.toArray(); }
	@Override public <U> U[]  toArray(U[] a)                      { return list.toArray(a); }
	@Override public List<T>  subList(int fromIndex, int toIndex) { return list.subList(fromIndex, toIndex); }
	
	//==================================================================================================================
    @Override public boolean isEmpty()                    { return list.isEmpty(); }
    @Override public boolean contains(Object o)           { return list.contains(o); }
    @Override public boolean containsAll(Collection<?> c) { return list.containsAll(c); }
	
	//==================================================================================================================
	@Override public boolean equals(Object o) { return list.equals(o); }
	@Override public int     hashCode()       { return list.hashCode(); }

	//==================================================================================================================
	@Override public T get(int index) { return list.get(index); }

	//==================================================================================================================
    @Override public int indexOf(Object o)     { return list.indexOf(o); }
    @Override public int lastIndexOf(Object o) { return list.lastIndexOf(o); }
    
	//==================================================================================================================
    @Override public Iterator<T>     iterator()              { return list.iterator(); }
    @Override public Spliterator<T>  spliterator()           { return list.spliterator(); }
    @Override public ListIterator<T> listIterator()          { return list.listIterator(); }
    @Override public ListIterator<T> listIterator(int index) { return list.listIterator(index); }
    
    //==================================================================================================================
    @Override public Stream<T> stream()         { return list.stream(); }
    @Override public Stream<T> parallelStream() { return list.parallelStream(); }

    //==================================================================================================================
	@Override public boolean add(T e)                                     { notSupported(); return false; }
	@Override public boolean remove(Object o)                             { notSupported(); return false; }
	@Override public boolean addAll(Collection<? extends T> c)            { notSupported(); return false; }
	@Override public boolean addAll(int index, Collection<? extends T> c) { notSupported(); return false; }
    @Override public boolean removeAll(Collection<?> c)                   { notSupported(); return false; }
	@Override public boolean retainAll(Collection<?> c)                   { notSupported(); return false; }
	@Override public void    clear()                                      { notSupported(); }
	@Override public T       set(int index, T element)                    { notSupported(); return null; }
	@Override public void    add(int index, T element)                    { notSupported(); }
	@Override public T       remove(int index)                            { notSupported(); return null; }
}
