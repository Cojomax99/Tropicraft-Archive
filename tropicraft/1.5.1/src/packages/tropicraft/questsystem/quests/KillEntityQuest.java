package tropicraft.questsystem.quests;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import tropicraft.questsystem.EnumQuestState;
import tropicraft.questsystem.PlayerQuests;

public class KillEntityQuest extends ActiveQuest {
	
	public Class neededMob;
	public int neededKillCount;
	public boolean returnToQuestGiver;
	
	public int curKillCount;

	public KillEntityQuest(PlayerQuests parPlQuests, int parID, Class mob, int count, boolean parReturnToQuestGiver) {
		super(parPlQuests, parID);
		neededMob = mob;
		neededKillCount = count;
		returnToQuestGiver = parReturnToQuestGiver;
	}

	@Override
	public void tick() {
		super.tick();
		
		if (curState == EnumQuestState.ASSIGNED) {
			
			if (curKillCount >= neededKillCount) {
				if (returnToQuestGiver) {
					setState(EnumQuestState.CONCLUDING);
				} else {
					setState(EnumQuestState.COMPLETE);
					System.out.println("quest kill complete");
				}
			}
		} else if (curState == EnumQuestState.CONCLUDING) {
			//logic that determines they have talked to the quest giver to complete the quest, should this be here or in the koa?
		}
	}
	
	public void deathEvent(LivingDeathEvent event) {
		Entity source = event.source.getEntity();
		if (source != null && source.equals(playerQuests.playerInstance) && event.entityLiving.getClass().equals(neededMob)) {
			curKillCount++;
			saveAndSync();
			System.out.println("quest kill inc");
		}
	}
	
	public void load(NBTTagCompound parNBT) {
		super.load(parNBT);
		curKillCount = parNBT.getInteger("curKillCount");
	}
	
	public void save(NBTTagCompound parNBT) {
		super.save(parNBT);
		parNBT.setInteger("curKillCount", curKillCount);
	}
}
