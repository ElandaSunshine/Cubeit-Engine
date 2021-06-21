package xyz.elandasunshine.capi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import xyz.elandasunshine.capi.registry.IRegisterable;

public abstract class GameBlock extends Block implements IRegisterable
{
	//==================================================================================================================
	private String registryName;

	//==================================================================================================================
	public GameBlock(String registryName, Material material)
	{
		super(material);
		this.registryName = registryName;
	}
	
	protected GameBlock(String registryName, Material material, MapColor mapColor)
	{
		super(material, mapColor);
		this.registryName = registryName;
	}
	
	//==================================================================================================================
	@Override
	public String getRegistryName()
	{
		return registryName;
	}
}
