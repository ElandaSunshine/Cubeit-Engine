package xyz.elandasunshine.cvm.init;

import java.lang.annotation.Annotation;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import xyz.elandasunshine.capi.misc.ExcludeLoad;
import xyz.elandasunshine.capi.registry.Register;
import xyz.elandasunshine.capi.script.GameScript;
import xyz.elandasunshine.capi.script.IScript;
import xyz.elandasunshine.cvm.util.constant.ConstList;
import xyz.elandasunshine.cvm.util.constant.ConstMap;

public class ActionRegistry
{
	//==================================================================================================================
	private final Map<Class<? extends IScript>, List<Class<?>>> scripts = Maps .newHashMap();
	private final List<Class<?>>                                objects = Lists.newArrayList();
	
	//==================================================================================================================
	public void findAndRegisterClassActions(final List<Class<?>> classList) throws Exception
	{
		final Map<Integer, List<Class<?>>> priorityMap = Maps.newTreeMap();
		
		for (final Class<?> clazz : classList)
		{
			final ExcludeLoad annExclude = clazz.getAnnotation(ExcludeLoad.class);
			
			// Game scripts
			if (!isExcluded(GameScript.class, annExclude))
			{
				final GameScript annScript = clazz.getAnnotation(GameScript.class);
				
				if (annScript != null && annScript.value().length > 0)
				{
					for (final Class<? extends IScript> script : annScript.value())
					{
						List<Class<?>> scriptOwners = scripts.get(script);
						
						if (scriptOwners == null)
						{
							scriptOwners = Lists.newArrayList();
							scripts.put(script, scriptOwners);
						}
						
						scriptOwners.add(clazz);
					}
				}
			}
			
			// Register objects
			if (!isExcluded(Register.class, annExclude))
			{
				final Register annRegister = clazz.getAnnotation(Register.class);
				
				if (annRegister != null)
				{
					final int      priority   = Math.max(1, annRegister.priority());
					List<Class<?>> objectList = priorityMap.get(priority);
					
					if (objectList == null)
					{
						objectList = Lists.newArrayList();
						priorityMap.put(priority, objectList);
					}
					
					objectList.add(clazz);
				}
			}
		}
		
		for (final Map.Entry<Integer, List<Class<?>>> entry : priorityMap.entrySet())
		{
			objects.addAll(entry.getValue());
		}
	}
	
	//==================================================================================================================
	public ConstMap<Class<? extends IScript>, List<Class<?>>> getAllScripts()
	{
		return new ConstMap<>(scripts);
	}
	
	public ConstList<Class<?>> getClassesForScript(final Class<? extends IScript> scriptType)
	{
		return new ConstList<>(scripts.get(scriptType));
	}
	
	//==================================================================================================================
	public ConstList<Class<?>> getRegistryObjectList()
	{
		return new ConstList<>(objects);
	}
	
	//==================================================================================================================
	private static boolean isExcluded(final Class<? extends Annotation> annotationInQuestion,
								      final ExcludeLoad                 annotationExclude)
	{
		if (annotationExclude == null)
		{
			return false;
		}
		
		final Stream<Class<?>> stream = Stream.of(annotationExclude.value());
		return annotationExclude.value().length == 0 || stream.anyMatch(x -> x.isAssignableFrom(annotationInQuestion));
	}
}
