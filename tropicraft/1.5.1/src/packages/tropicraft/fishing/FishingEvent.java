package tropicraft.fishing;

import net.minecraft.entity.player.EntityPlayer;

public abstract class FishingEvent {
	
	public EntityHook theHook;
	public EntityPlayer thePlayer;
	
	 public int totalTicks = 200;
	 public int currentTick = 0;
	
	public FishingEvent(EntityHook h, EntityPlayer p){
		theHook = h;
		thePlayer = p;
	}
	
	public abstract void onUpdate();
}
