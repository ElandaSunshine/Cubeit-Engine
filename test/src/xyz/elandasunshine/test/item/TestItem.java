package xyz.elandasunshine.test.item;

import xyz.elandasunshine.capi.item.GameItem;
import xyz.elandasunshine.capi.script.GameScript;
import xyz.elandasunshine.capi.script.cgs.CgsRegister;

@GameScript(CgsRegister.class)
public class TestItem extends GameItem
{
	//==================================================================================================================
	public TestItem()
	{
		super("test_item");
	}
}
