package xyz.elandasunshine.cvm.client;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import xyz.elandasunshine.capi.target.TargetSide;
import xyz.elandasunshine.capi.target.VmTarget;
import xyz.elandasunshine.cvm.target.TargetManager;

@TargetSide(VmTarget.CLIENT)
public class ClientManager extends TargetManager
{
	//==================================================================================================================
	private final Logger    logger = LogManager.getLogger("CVM");
	private final Minecraft minecraft;
	
	//==================================================================================================================
	public ClientManager(Minecraft parMinecraft)
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
	public void setupVm()
	{
		
	}

	@Override
	public void initResources()
	{
		
	}

	@Override
	public void loadResources()
	{
		
	}
}
