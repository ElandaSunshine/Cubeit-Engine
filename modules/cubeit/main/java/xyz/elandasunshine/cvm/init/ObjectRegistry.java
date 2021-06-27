package xyz.elandasunshine.cvm.init;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import com.google.common.collect.Lists;
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
	public static final class EntityDef
	{
		public static final class EggColor
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
	public final Registry<SoundEvent>  soundEvents  = new Registry<>(SoundEvent .class);
	public final Registry<GameBlock>   blocks       = new Registry<>(GameBlock  .class);
	public final Registry<Potion>      potions      = new Registry<>(Potion     .class);
	public final Registry<Enchantment> enchantments = new Registry<>(Enchantment.class);
	public final Registry<GameItem>    items        = new Registry<>(GameItem   .class);
	public final Registry<PotionType>  potionTypes  = new Registry<>(PotionType .class);
	public final Registry<EntityDef>   entities     = new Registry<>(EntityDef  .class);
	public final Registry<Biome>       biomes       = new Registry<>(Biome      .class);
	
	//==================================================================================================================
	private final List<Registry<?>> registries = Lists.newArrayList(
		soundEvents, blocks, potions, enchantments, items, potionTypes, entities, biomes
    );
	
	//==================================================================================================================
	public <T> boolean hasRegistryFor(final Class<T> registryClass)
	{
		return registries.stream().anyMatch(x -> x.getRegistryType().isAssignableFrom(registryClass));
	}
	
	@SuppressWarnings("unchecked")
	public <T> Registry<T> getRegistryFor(final Class<T> registryClass)
	{
		final Optional<?> optional = registries.stream().filter(x -> x.getRegistryType()
																	  .isAssignableFrom(registryClass)).findFirst();
		return ((Registry<T>) optional.orElse(null));
	}
	
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
