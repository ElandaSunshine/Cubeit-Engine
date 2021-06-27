package xyz.elandasunshine.test.block;

import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemBlock;
import xyz.elandasunshine.capi.block.GameBlock;
import xyz.elandasunshine.capi.registry.Register;

@Register("test_block")
public class TestBlock extends GameBlock
{
	//==================================================================================================================
	public TestBlock()
	{
		super(Material.CACTUS);
		this.setUnlocalizedName("test_block");
		this.setCreativeTab(CreativeTabs.BUILDING_BLOCKS);
	}
	
	//==================================================================================================================
	@Override
	public ItemBlock getItemBlock()
	{
		return new ItemBlock(this);
	}
}
