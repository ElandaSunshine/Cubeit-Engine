package xyz.elandasunshine.cvm.init;

import java.util.List;
import java.util.Map;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;

import xyz.elandasunshine.capi.script.GameScript;
import xyz.elandasunshine.capi.script.IScript;
import xyz.elandasunshine.cvm.util.constant.ConstList;
import xyz.elandasunshine.cvm.util.constant.ConstMap;

public class ScriptRegistry
{
	//==================================================================================================================
	private final Map<Class<? extends IScript>, List<Class<?>>> scripts = Maps.newHashMap();
	
	//==================================================================================================================
	public void findAndRegisterScripts(List<Class<?>> classList) throws Exception
	{
		for (final Class<?> clazz : classList)
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
	}
	
	//==================================================================================================================
	public ConstMap<Class<? extends IScript>, List<Class<?>>> getAllScripts()
	{
		return new ConstMap<>(scripts);
	}
	
	public ConstList<Class<?>> getClassesForScript(Class<? extends IScript> scriptType)
	{
		return new ConstList<>(scripts.get(scriptType));
	}
}
