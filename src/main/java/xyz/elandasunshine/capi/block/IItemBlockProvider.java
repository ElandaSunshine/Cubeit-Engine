package xyz.elandasunshine.capi.block;

import net.minecraft.item.ItemBlock;

public interface IItemBlockProvider
{
	default ItemBlock getItemBlockForBlock() { return null; }
}
