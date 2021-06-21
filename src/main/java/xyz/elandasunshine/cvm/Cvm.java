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

import java.io.File;

import org.apache.logging.log4j.Logger;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.client.resources.IResourcePack;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityList;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import xyz.elandasunshine.capi.block.IFlammable;
import xyz.elandasunshine.capi.block.IItemBlockProvider;
import xyz.elandasunshine.capi.misc.IDispensable;
import xyz.elandasunshine.capi.registry.RegistryEntry;
import xyz.elandasunshine.capi.script.cgs.CgsRegister;
import xyz.elandasunshine.capi.target.TargetSide;
import xyz.elandasunshine.capi.target.VmTarget;
import xyz.elandasunshine.cvm.client.CubeitResourceFolder;
import xyz.elandasunshine.cvm.game.GameClassList;
import xyz.elandasunshine.cvm.init.ObjectRegistry;
import xyz.elandasunshine.cvm.init.ScriptRegistry;
import xyz.elandasunshine.cvm.target.TargetManager;
import xyz.elandasunshine.cvm.util.constant.ConstList;

public class Cvm
{
	
	//==================================================================================================================
	private final TargetManager  manager;
	private final Logger         logger;
	private final ObjectRegistry registry = new ObjectRegistry();

	private final File dirGameRoot;
	private final File dirAssets;
	private final File dirBin;
	
	private final GameClassList  classList;
	private final ScriptRegistry scriptRegistry = new ScriptRegistry();
	
	//==================================================================================================================
	public Cvm(TargetManager parManager, File parDirGameRoot)
	{
		this.manager   = parManager;
		this.logger    = parManager.getLogger();
		
		this.dirGameRoot = parDirGameRoot;
		this.dirAssets   = new File(this.dirGameRoot, "assets");
		this.dirBin      = new File(this.dirGameRoot, "bin");
		
		this.classList = new GameClassList(new File(dirBin, "data.jar"));
	}
	
	//==================================================================================================================	
	public void setup()
	{
		try
		{
			this.classList.findClasses();
			this.scriptRegistry.findAndRegisterScripts(this.classList.getClasses().getMutableList());
		}
		catch (final Exception ex)
		{
			throw new RuntimeException(ex);
		}
		
		manager.setupVm();
	}
	
	public void init()
	{
		registerObjects();
		injectObjects();
		manager.initResources();
	}
	
	public void load()
	{
		manager.loadResources();
	}
	
	
	//==================================================================================================================
	@TargetSide(VmTarget.CLIENT)
	public IResourcePack getResourceFolder()
	{
		return new CubeitResourceFolder(dirAssets);
	}
	
	//==================================================================================================================
	private void registerObjects()
	{
		final ConstList<Class<?>> registryList = this.scriptRegistry.getClassesForScript(CgsRegister.class);
		
		if (!registryList.isEmpty())
		{
			final CgsRegister registryScript = new CgsRegister(registry);
			
			for (final Class<?> clazz : registryList)
			{
				registryScript.execute(clazz);
			}
		}
	}
	
	private void injectObjects()
	{
		SoundEvent.registerCubeitSounds(registry.soundEvents);
        Block     .registerCubeitBlocks(registry.blocks);
        
        for (final RegistryEntry<Block> blockEntry : registry.blocks)
        {
        	final Block block = blockEntry.getValue();
        	
        	if (block instanceof IFlammable)
        	{
        		final IFlammable flammable = (IFlammable) block;
        		Blocks.FIRE.setFireInfo(block, flammable.getEncouragement(), flammable.getFlammability());
        	}
        }
        
        Potion     .registerCubeitPotions(registry.potions);
        Enchantment.registerCubeitEnchantments(registry.enchantments);
        Item       .registerCubeitItems(registry);
        PotionType .registerCubeitPotionTypes(registry.potionTypes);
        EntityList .initForCubeit(registry.entities);
        Biome      .registerCubeitBiomes(registry.biomes);
        
        for (final RegistryEntry<Item> entry : registry.items)
        {
        	final Item item = entry.getValue();

        	if (item instanceof IDispensable)
        	{
        		final IDispensable dispensable = (IDispensable) item;
        		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, dispensable.getBehaviour());
        	}
        }
        
        for (final RegistryEntry<Block> entry : registry.blocks)
        {
        	final Block block = entry.getValue();
        	
        	if (block instanceof IItemBlockProvider && block instanceof IDispensable)
        	{
        		final IDispensable dispensable = (IDispensable) block;
        		final Item         itemBlock   = Item.getItemFromBlock(block);
        		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemBlock, dispensable.getBehaviour());
        	}
        }
	}
}
