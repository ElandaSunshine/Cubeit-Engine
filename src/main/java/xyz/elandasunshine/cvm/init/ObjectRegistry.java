package xyz.elandasunshine.cvm.init;

import java.util.Set;

import com.google.common.collect.Sets;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionType;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.biome.Biome;
import xyz.elandasunshine.capi.block.GameBlock;
import xyz.elandasunshine.capi.item.GameItem;
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
			public EggColor(final int parPrimaryEggColor, final int parSecondaryEggColor)
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
		public EntityDef(final Class<? extends Entity> parEntityClass, final String parEntityName,
						 final EggColor eggColor)
		{
			entityClass    = parEntityClass;
			entityName     = parEntityName;
			entityEggColor = eggColor;
		}
		
		public EntityDef(final Class<? extends Entity> parEntityClass, final String parEntityName)
		{
			this(parEntityClass, parEntityName, null);
		}
	}
	
	//==================================================================================================================
	public static final int cubeitBlockStartId       = 454;
	public static final int cubeitPotionStartId      = 30;
	public static final int cubeitEnchantmentStartId = 100;
	public static final int cubeitItemStartId        = 2268;
	public static final int cubeitEntityStartId      = 300;
	public static final int cubeitBiomeStartId       = 300;
	
	//==================================================================================================================
	public final Set<String> registryNames = Sets.newHashSet();
	
	//==================================================================================================================
	public final Registry<SoundEvent>  soundEvents  = new Registry<>();
	public final Registry<GameBlock>   blocks       = new Registry<>();
	public final Registry<Potion>      potions      = new Registry<>();
	public final Registry<Enchantment> enchantments = new Registry<>();
	public final Registry<GameItem>    items        = new Registry<>();
	public final Registry<PotionType>  potionTypes  = new Registry<>();
	public final Registry<EntityDef>   entities     = new Registry<>();
	public final Registry<Biome>       biomes       = new Registry<>();
	
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
