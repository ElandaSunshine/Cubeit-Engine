package xyz.elandasunshine.capi.item;

import net.minecraft.item.Item;
import xyz.elandasunshine.capi.registry.IRegisterable;

public abstract class GameItem extends Item implements IRegisterable
{
	//==================================================================================================================
	private String registryName;

	//==================================================================================================================
	public GameItem(final String registryName)
	{
		this.registryName = registryName;
		this.setUnlocalizedName(registryName);
	}
	
	//==================================================================================================================
	public int[] getModelVariants() { return null; }
	
	//==================================================================================================================
	@Override
	public String getRegistryName()
	{
		return registryName;
	}
}
