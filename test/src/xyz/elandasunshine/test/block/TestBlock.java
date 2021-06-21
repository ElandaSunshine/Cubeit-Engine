package xyz.elandasunshine.test.block;

import net.minecraft.block.material.Material;
import xyz.elandasunshine.capi.block.GameBlock;
import xyz.elandasunshine.capi.block.IItemBlockProvider;
import xyz.elandasunshine.capi.script.GameScript;
import xyz.elandasunshine.capi.script.cgs.CgsRegister;

@GameScript(CgsRegister.class)
public class TestBlock extends GameBlock implements IItemBlockProvider
{
	public TestBlock()
	{
		super("test_block", Material.CACTUS);
	}
}
