package xyz.elandasunshine.cvm;

import java.io.File;
import java.lang.reflect.Field;
import java.util.jar.Attributes;
import java.util.jar.Manifest;

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
import xyz.elandasunshine.capi.block.GameBlock;
import xyz.elandasunshine.capi.block.IFlammable;
import xyz.elandasunshine.capi.game.GameInfo;
import xyz.elandasunshine.capi.game.GameManager;
import xyz.elandasunshine.capi.game.exception.InvalidGameManifestException;
import xyz.elandasunshine.capi.item.GameItem;
import xyz.elandasunshine.capi.misc.IDispensable;
import xyz.elandasunshine.capi.registry.Register;
import xyz.elandasunshine.capi.registry.Registry;
import xyz.elandasunshine.capi.registry.RegistryEntry;
import xyz.elandasunshine.capi.registry.exception.InvalidRegistryNameException;
import xyz.elandasunshine.capi.registry.exception.RegistryObjectConstructionException;
import xyz.elandasunshine.capi.target.TargetSide;
import xyz.elandasunshine.capi.target.VmTarget;
import xyz.elandasunshine.cvm.client.CubeitResourceFolder;
import xyz.elandasunshine.cvm.game.GameClassList;
import xyz.elandasunshine.cvm.init.ObjectRegistry;
import xyz.elandasunshine.cvm.init.ActionRegistry;
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
	private final ActionRegistry actionRegistry = new ActionRegistry();
	
	//==================================================================================================================
	public Cvm(final TargetManager parManager, final File parDirGameRoot)
	{
		this.manager = parManager;
		this.logger  = parManager.getLogger();
		
		this.dirGameRoot = parDirGameRoot;
		this.dirAssets   = new File(this.dirGameRoot, "assets");
		this.dirBin      = new File(this.dirGameRoot, "bin");
		
		this.classList = new GameClassList(new File(dirBin, "data.jar"));
	}
	
	//==================================================================================================================	
	public void initialiseVm()
	{
		logger.info("Setting up CubeIt VM");
		
		try
		{
			this.classList     .findClasses();
			this.actionRegistry.findAndRegisterClassActions(this.classList.getClasses());
			
			final GameInfo info = GameInfo.createFromManifest(this.classList.getManifest());
			GameManager.createManager(dirGameRoot, info);

			logger.info("Loading game: " + info.gameName + " (" + info.gameId + "); Version: " + info.gameVersion);
		}
		catch (final RuntimeException ex)
		{
			throw ex;
		}
		catch (final Exception ex)
		{
			throw new RuntimeException(ex);
		}
	}
	
	//==================================================================================================================	
	public void setup()
	{
		manager.preInit(registry);
		init();
	}
	
	public void init()
	{
		manager.init(registry);
		logger.info("Registering game objects...");
		
		try
		{
			registerObjects();
		}
		catch (final InvalidRegistryNameException | RegistryObjectConstructionException ex)
		{
			logger.catching(ex);
		}
		
		logger.info("Injecting game objects into the Minecraft kernel...");
		injectObjects();
	}
	
	public void load()
	{
		manager.load(registry);
		registry.cleanRegistries();
	}
	
	//==================================================================================================================
	@TargetSide(VmTarget.CLIENT)
	public IResourcePack getResourceFolder()
	{
		return new CubeitResourceFolder(dirAssets);
	}
	
	//==================================================================================================================
	@SuppressWarnings("unchecked")
	private void registerObjects() throws InvalidRegistryNameException, RegistryObjectConstructionException
	{		
		final ConstList<Class<?>> registryList = this.actionRegistry.getRegistryObjectList();
		
		if (!registryList.isEmpty())
		{
			for (final Class<?> clazz : registryList)
			{
				@SuppressWarnings("rawtypes")
				final Registry thisRegistry = registry.getRegistryFor(clazz);
				
				if (thisRegistry == null)
				{
					continue;
				}
				
				final Register annRegister = clazz.getAnnotation(Register.class);
				final String   objectId    = annRegister.value();
				Object         toRegister;
				
				if (objectId.isEmpty())
				{
					throw InvalidRegistryNameException.createNullNameException(annRegister);
				}
				
				if (annRegister.instancePath().isEmpty())
				{
					try
					{
						toRegister = clazz.newInstance();
					}
					catch (final InstantiationException | IllegalAccessException e)
					{
						throw new RegistryObjectConstructionException(e);
					}
				}
				else
				{
					final String[] instancePath = annRegister.instancePath().split(":");
					
					if (instancePath.length != 2)
					{
						throw new RegistryObjectConstructionException("The instance path for '" + objectId + "' was "
																	  + "invalidly formatted.");
					}
					
					try
					{
						final Class<?> instanceClass = classList.forName(instancePath[0]);
						final Field    objectField   = instanceClass.getDeclaredField(instancePath[1]);
						final Object   obj           = objectField.get(null);
						
						if (registry.getRegistryFor(obj.getClass()) != thisRegistry)
						{
							throw new RegistryObjectConstructionException("Field '" + instancePath[1] + "' of class '"
									  + instancePath[0] + "' is not the same type as the class to be registered.");
						}
						
						toRegister = obj;
					}
					catch (final ClassNotFoundException ex)
					{
						throw new RegistryObjectConstructionException("Class '" + instancePath[0]
																	  + "' could not be located.");
					}
					catch (final NoSuchFieldException ex)
					{
						throw new RegistryObjectConstructionException("There is no field '" + instancePath[1]
																	  + "' in the class: '" + instancePath[0]);
					}
					catch (final SecurityException ex)
					{
						throw new RegistryObjectConstructionException(ex);
					}
					catch (final IllegalArgumentException ex)
					{
						throw new RegistryObjectConstructionException("Field '" + instancePath[1] + "' of class '"
																	  + instancePath[0] + "' must be static or it " 
								                                      + "can't be accessed.");
					}
					catch (final IllegalAccessException ex)
					{
						throw new RegistryObjectConstructionException("Field '" + instancePath[1] + "' of class '"
																	  + instancePath[0] + "' must be public or it " 
																	  + "can't be accessed.");
					}
				}
								
				if (thisRegistry != null)
				{
					if (thisRegistry.contains(objectId))
					{
						throw InvalidRegistryNameException.createDuplicateNameException(annRegister);
					}
					
					thisRegistry.register(objectId, toRegister);
				}
			}
		}
	}
	
	private void injectObjects()
	{
		SoundEvent.registerCubeitSounds(registry.soundEvents);
        Block     .registerCubeitBlocks(registry.blocks);
        
        for (final RegistryEntry<GameBlock> blockEntry : registry.blocks)
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
        
        for (final RegistryEntry<GameItem> entry : registry.items)
        {
        	final GameItem item = entry.getValue();

        	if (item instanceof IDispensable)
        	{
        		final IDispensable dispensable = (IDispensable) item;
        		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(item, dispensable.getBehaviour());
        	}
        }
        
        for (final RegistryEntry<GameBlock> entry : registry.blocks)
        {
        	final GameBlock block = entry.getValue();
        	
        	if (block.getItemBlock() != null && block instanceof IDispensable)
        	{
        		final IDispensable dispensable = (IDispensable) block;
        		final Item         itemBlock   = Item.getItemFromBlock(block);
        		BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(itemBlock, dispensable.getBehaviour());
        	}
        }
	}
}
