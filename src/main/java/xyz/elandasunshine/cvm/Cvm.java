/**
    ===============================================================
    This program is free software: you can redistribute it and/or modify
    it under the terms of the GNU General Public License as published by
    the Free Software Foundation, either version 3 of the License, or
    (at your option) any internal version.
    This program is distributed in the hope that it will be useful,
    but WITHOUT ANY WARRANTY; without even the implied warranty of
    MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
    GNU General Public License for more details.
    You should have received a copy of the GNU General Public License
    along with this program. If not, see <https://www.gnu.org/licenses/>.
    
    Copyright (c) 2021 ElandaSunshine
    ===============================================================
    
    @author Elanda
    @file   Cvm.java
    @date   13, June 2021
    
    ===============================================================
 */

package xyz.elandasunshine.cvm;

import org.apache.logging.log4j.Logger;

import net.minecraft.client.Minecraft;
import xyz.elandasunshine.cvm.init.ObjectRegistry;
import xyz.elandasunshine.cvm.target.TargetManager;

public class Cvm
{
	//==================================================================================================================
	private Minecraft      minecraft;
	private TargetManager manager;
	private Logger         logger;
	
	//==================================================================================================================
	public Cvm(TargetManager parManager, Minecraft parMinecraft)
	{
		minecraft = parMinecraft;
		manager   = parManager;
		logger    = parManager.getLogger();
	}
	
	//==================================================================================================================
	/** Gets the VM's current target manager. */
	public TargetManager getTargetManager() { return manager; }
	
	//==================================================================================================================	
	/** Setup resources and registers before the actual VM initialisation. */
	public void setup(ObjectRegistry registry)
	{
		manager.setupVm(registry);
	}
	
	/** Initialise the VM and do everything after the registration. */
	public void init()
	{
		manager.initResources();
	}
	
	/** Anything that happens after VM initialisation can be done here. */
	public void load()
	{
		manager.loadResources();
	}
}
