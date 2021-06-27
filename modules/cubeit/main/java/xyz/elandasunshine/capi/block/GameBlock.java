package xyz.elandasunshine.capi.block;

import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.item.ItemBlock;

public abstract class GameBlock extends Block
{
	//==================================================================================================================
	private String registryName;

	//==================================================================================================================
	protected GameBlock(final Material material, final MapColor mapColor)
	{
		super(material, mapColor);
		this.setUnlocalizedName(registryName);
	}
	
	public GameBlock(final Material material)
	{
		this(material, material.getMaterialMapColor());
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
}
