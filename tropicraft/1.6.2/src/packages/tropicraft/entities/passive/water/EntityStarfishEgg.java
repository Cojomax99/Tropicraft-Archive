package tropicraft.entities.passive.water;

import tropicraft.ModInfo;
import tropicraft.entities.EntityEchinoderm;
import tropicraft.entities.EntityEchinodermEgg;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class EntityStarfishEgg extends EntityEchinodermEgg {
	private StarfishType starfishType;

	public EntityStarfishEgg(World par1World) {
		super(par1World);
		starfishType = StarfishType.values()[rand.nextInt(StarfishType.values().length)];
	}
	
	public EntityStarfishEgg(World world, StarfishType starfishType) {
		this(world);
		this.starfishType = starfishType;
	}

	@Override
	public EntityEchinoderm createBaby() {
		EntityStarfish baby = new EntityStarfish(worldObj, true);
		baby.setStarfishType(starfishType);
		return baby;
	}
}
