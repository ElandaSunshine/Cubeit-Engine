package xyz.elandasunshine.cvm;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import xyz.elandasunshine.capi.target.VmTarget;
import xyz.elandasunshine.cvm.init.ObjectRegistry;
import xyz.elandasunshine.cvm.target.TargetManager;

public class ClientManager extends TargetManager
{
	//==================================================================================================================
	private final Logger logger = LogManager.getLogger("CVM");
	
	//==================================================================================================================
	public ClientManager()
	{
		init(this);
	}
	
	//==================================================================================================================
	@Override
	public VmTarget getVmTarget() { return VmTarget.CLIENT; }

	@Override
	public Logger getLogger() { return logger; }
	
	//==================================================================================================================
	@Override
	public void setupVm(ObjectRegistry register)
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