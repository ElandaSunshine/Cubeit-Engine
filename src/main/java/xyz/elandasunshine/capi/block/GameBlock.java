package xyz.elandasunshine.capi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;
import xyz.elandasunshine.capi.registry.IRegisterable;

public abstract class GameBlock extends Block implements IRegisterable
{
	//==================================================================================================================
	private String registryName;

	//==================================================================================================================
	protected GameBlock(final String registryName, final Material material, final MapColor mapColor)
	{
		super(material, mapColor);
		this.registryName = registryName;
		this.setUnlocalizedName(registryName);
	}
	
	public GameBlock(final String registryName, final Material material)
	{
		this(registryName, material, material.getMaterialMapColor());
	}
	
	//==================================================================================================================
	public int[] getModelVariants()
	{
		return null;
	}
	
	public ItemBlock getItemBlock()
	{
		return null;
	}
	
	//==================================================================================================================
	@Override
	public String getRegistryName()
	{
		return registryName;
	}	
}
