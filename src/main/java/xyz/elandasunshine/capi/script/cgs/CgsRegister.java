package xyz.elandasunshine.capi.script.cgs;

import xyz.elandasunshine.capi.block.GameBlock;
import xyz.elandasunshine.capi.registry.IRegisterable;
import xyz.elandasunshine.capi.registry.exception.InvalidRegistryNameException;
import xyz.elandasunshine.capi.registry.exception.RegistryObjectConstructionException;
import xyz.elandasunshine.capi.script.IScript;
import xyz.elandasunshine.cvm.init.ObjectRegistry;

/**
 *  Tells the CubeIt VM that the annotated type should be recorded into
 *  the game object registry. (e.g. blocks or items)
 *  Only specific types of objects are accepted, otherwise this will be ignored.
 *  
 *  @author elanda
 */
public class CgsRegister implements IScript
{
	//==================================================================================================================
	final ObjectRegistry registry;
	
	//==================================================================================================================
	public CgsRegister(ObjectRegistry parRegistry)
	{
		this.registry = parRegistry;
	}
	
	//==================================================================================================================
	@Override
	public void execute(Class<?> sender)
	{
		if (IRegisterable.class.isAssignableFrom(sender))
		{
			IRegisterable registerable;
			
			try
			{
				registerable = (IRegisterable) sender.newInstance();
			}
			catch (Exception ex)
			{
				throw new RegistryObjectConstructionException(ex);
			}
			
			if (registerable.getRegistryName() == null)
			{
				throw InvalidRegistryNameException.createNullNameException(registerable);
			}
			
			if (!registry.registryNames.add(registerable.getRegistryName()))
			{
				throw InvalidRegistryNameException.createDuplicateNameException(registerable);
			}
			
			if (registerable instanceof GameBlock)
			{
				final GameBlock block = (GameBlock) registerable;
				registry.blocks.register(block.getRegistryName(), block);
			}
			
			// TODO register other objects
		}
	}
}