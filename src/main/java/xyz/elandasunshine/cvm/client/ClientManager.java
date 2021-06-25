package xyz.elandasunshine.cvm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import xyz.elandasunshine.capi.target.TargetSide;
import xyz.elandasunshine.capi.target.VmTarget;
import xyz.elandasunshine.cvm.init.ObjectRegistry;
import xyz.elandasunshine.cvm.target.TargetManager;

@TargetSide(VmTarget.CLIENT)
public class ClientManager extends TargetManager
{
	//==================================================================================================================
	private final Logger    logger = LogManager.getLogger("CVM");
	private final Minecraft minecraft;
	
	//==================================================================================================================
	public ClientManager(final Minecraft parMinecraft)
	{
		this.minecraft = parMinecraft;
		init(this);
	}
	
	//==================================================================================================================
	@Override
	public VmTarget getVmTarget() { return VmTarget.CLIENT; }

	@Override
	public Logger getLogger() { return logger; }
	
	//==================================================================================================================
	@Override
	public void preInit(final ObjectRegistry registry)
	{
		
	}

	@Override
	public void init(final ObjectRegistry registry)
	{
	}

	@Override
	public void load(final ObjectRegistry registry)
	{
		minecraft.getRenderItem().registerCubeitItems(registry);
	}
}
