package xyz.elandasunshine.cvm.init;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.block.Block;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import xyz.elandasunshine.capi.registry.Registry;

public class ObjectRegistry
{
	//==================================================================================================================
	public final class EntityDef
	{
		public final class EggColor
		{
			public final int primaryEggColor;
			public final int secondaryEggColor;
			
			//==========================================================================================================
			public EggColor(int parPrimaryEggColor, int parSecondaryEggColor)
			{
				primaryEggColor   = parPrimaryEggColor;
				secondaryEggColor = parSecondaryEggColor;
			}
		}
		
		//==============================================================================================================
		public final Class<? extends Entity> entityClass;
		public final String                  entityName;
		public final EggColor                entityEggColor;
		
		//==============================================================================================================
		public EntityDef(Class<? extends Entity> parEntityClass, String parEntityName, EggColor eggColor)
		{
			entityClass    = parEntityClass;
			entityName     = parEntityName;
			entityEggColor = eggColor;
		}
		
		public EntityDef(Class<? extends Entity> parEntityClass, String parEntityName)
		{
			this(parEntityClass, parEntityName, null);
		}
	}
	
	//==================================================================================================================
	public static final int cubeitBlockStartId       = 300;
	public static final int cubeitPotionStartId      = 30;
	public static final int cubeitEnchantmentStartId = 100;
	public static final int cubeitItemBlockStartId   = 3000;
	public static final int cubeitItemStartId        = 4000;
	public static final int cubeitEntityStartId      = 300;
	public static final int cubeitBiomeStartId       = 300;
	
	//==================================================================================================================
	public final Set<String> registryNames = Sets.newHashSet();
	
	//==================================================================================================================
	public final Registry<SoundEvent>  soundEvents  = new Registry<SoundEvent>();
	public final Registry<Block>       blocks       = new Registry<Block>();
	public final Registry<Potion>      potions      = new Registry<Potion>();
	public final Registry<Enchantment> enchantments = new Registry<Enchantment>();
	public final Registry<Item>        items        = new Registry<Item>();
	public final Registry<PotionType>  potionTypes  = new Registry<PotionType>();
	public final Registry<EntityDef>   entities     = new Registry<EntityDef>();
	public final Registry<Biome>       biomes       = new Registry<Biome>();
	
	//==================================================================================================================
	public void cleanRegistries()
	{
		registryNames.clear();
		
		soundEvents .clear();
		blocks      .clear();
		potions     .clear();
		enchantments.clear();
		items       .clear();
		potionTypes .clear();
		entities    .clear();
		biomes      .clear();
	}
}
