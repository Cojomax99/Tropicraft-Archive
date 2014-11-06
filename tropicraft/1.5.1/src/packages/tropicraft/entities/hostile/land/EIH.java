package tropicraft.entities.hostile.land;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import tropicraft.blocks.TropicraftBlocks;
import tropicraft.entities.ai.jobs.v3.JobSleep;
import tropicraft.entities.hostile.EntityLand;
import CoroAI.componentAI.jobSystem.JobHunt;
import CoroAI.diplomacy.TeamTypes;

public class EIH extends EntityLand {

	JobSleep jobSleep;
	
	public EIH(World par1World) {
		super(par1World);
		setSize(1.1F, 3.3F);
		agent.jobMan.clearJobs();
		agent.jobMan.addPrimaryJob(jobSleep = new JobSleep(agent.jobMan));
		agent.jobMan.addJob(new JobHunt(agent.jobMan));
		
		agent.moveLeadTicks = 0;
		agent.dipl_info = TeamTypes.getType("neutral");
		agent.shouldAvoid = false;
		
		texture = "/mods/TropicraftMod/textures/entities/headtext.png";
		
		agent.collideResistClose = agent.collideResistFormation = agent.collideResistPathing = entityCollisionReduction = 1F;
	}
	
	public boolean getCanSpawnHere()
    {
        int i = MathHelper.floor_double(this.posX);
        int j = MathHelper.floor_double(this.boundingBox.minY);
        int k = MathHelper.floor_double(this.posZ);
        return this.worldObj.getBlockId(i, j - 1, k) != 0 && this.worldObj.getFullBlockLightValue(i, j, k) > 8 && super.getCanSpawnHere();
    }
	
	@Override
	public void entityInit() {
		super.entityInit();
		getDataWatcher().addObject(16, (byte)0); //anger state
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		if (worldObj.isRemote) {
			texture = "/mods/TropicraftMod/textures/entities/head" + (getDataWatcher().getWatchableObjectByte(16) == 0 ? "" : "angry") + "text.png";
		} else {
			getDataWatcher().updateObject(16, (byte) (jobSleep.sleeping ? 0 : 1));
		}
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (!worldObj.isRemote) {
			if (jobSleep.hookHit(par1DamageSource, par2)) return super.attackEntityFrom(par1DamageSource, par2);;
		} else {
			return super.attackEntityFrom(par1DamageSource, par2);
		}
		return false;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound par1nbtTagCompound) {
		super.readEntityFromNBT(par1nbtTagCompound);
		jobSleep.sleeping = par1nbtTagCompound.getBoolean("sleeping");
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound par1nbtTagCompound) {
		super.writeEntityToNBT(par1nbtTagCompound);
		par1nbtTagCompound.setBoolean("sleeping", jobSleep.sleeping);
	}
	
	@Override
	public void attackMelee(Entity ent, float dist) {
		super.attackMelee(ent, dist);
		worldObj.playSoundAtEntity(ent, "headattack", 1.0f, 1.0f);
	}
	
	@Override
	protected String getLivingSound() {
		return worldObj.rand.nextInt(10) == 0 ? "headmed" : "";
	}

	@Override
	protected void dropFewItems(boolean par1, int par2)
    {
        int j = this.rand.nextInt(2) + this.rand.nextInt(1 + par2);
        int k;

        for (k = 0; k < j; ++k)
        {
            this.dropItem(TropicraftBlocks.chunkOHead.blockID, 1);
        }

    }
	
	@Override
	protected String getHurtSound() {
		return "headpain";
	}
	
	@Override
	protected String getDeathSound() {
		return "headdeath";
	}
}
