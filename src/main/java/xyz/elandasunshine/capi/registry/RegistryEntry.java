package xyz.elandasunshine.capi.registry;

import java.util.AbstractMap;
import java.util.Map.Entry;

public class RegistryEntry<T> extends AbstractMap.SimpleEntry<String, T>
{
	private static final long serialVersionUID = -8822874729588141389L;
	
	protected RegistryEntry(Entry<? extends String, ? extends T> entry) { super(entry); }
	protected RegistryEntry(String id, T element) { super(id, element); }
}