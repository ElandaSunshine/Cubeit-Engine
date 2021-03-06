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
    @file   ICvmManager.java
    @date   13, June 2021
    
    ===============================================================
 */

package xyz.elandasunshine.cvm.target;

import org.apache.logging.log4j.Logger;

import xyz.elandasunshine.capi.target.VmTarget;
import xyz.elandasunshine.cvm.init.ObjectRegistry;

/**
 *  The ICvmManager interface is the base
 *  @author elanda
 */
public abstract class TargetManager
{	
	//==================================================================================================================
	 /** The name of this VM version */
	public static final String PROJECT_NAME = "Cubeit VM Alpha";
	
	/** The version of this project */
	public static final String PROJECT_VERSION = "0.1.0";
	
	/** The vendor of this project */
	public static final String PROJECT_VENDOR = "ElandaSunshine";
	
	private static TargetManager INSTANCE = null;
	
	//==================================================================================================================
	protected void init(final TargetManager managerType)
	{
		if (INSTANCE == null)
		{
			INSTANCE = managerType;
		}
	}
	
	//==================================================================================================================
	public static TargetManager getInstance() { return INSTANCE; }
	
	//==================================================================================================================
	/** 
	 * Gets the target of this VM instance, this can only be one of the following: VmTarget.SERVER or VmTarget.CLIENT.
	 * @return The target type
	 */
	public abstract VmTarget getVmTarget();
	
	/** The logger for this VM instance. */
	public abstract Logger getLogger();
	
	//==================================================================================================================
	/** Setup resources and registers before the actual VM initialisation. */
	public abstract void preInit(final ObjectRegistry registry);
	
	/** Initialise the VM and register all the things needed to run it. */
	public abstract void init(final ObjectRegistry registry);
	
	/** Anything that happens after VM initialisation can be done here. */
	public abstract void load(final ObjectRegistry registry);	
}