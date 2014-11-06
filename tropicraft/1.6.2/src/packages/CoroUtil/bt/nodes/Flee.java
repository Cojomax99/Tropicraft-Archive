package CoroUtil.bt.nodes;

import java.util.List;
import java.util.Random;

import org.apache.commons.io.filefilter.AgeFileFilter;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import CoroUtil.bt.Behavior;
import CoroUtil.bt.BlackboardBase;
import CoroUtil.bt.EnumBehaviorState;
import CoroUtil.bt.IBTAgent;
import CoroUtil.bt.selector.Selector;

public class Flee extends Selector {

	//0 = nothing to attack, 1 = attacking, 2 = sanity check says no
	
	public IBTAgent entInt;
	public EntityLiving ent;
	public BlackboardBase blackboard;
	
	public Flee(Behavior parParent, IBTAgent parEnt, BlackboardBase parBB) {
		super(parParent);
		blackboard = parBB;
		entInt = parEnt;
		ent = (EntityLiving)parEnt;
	}

	@Override
	public EnumBehaviorState tick() {
		
		float fleeRangeOverride = 6;
		float distToFleePref = 32;
		
		boolean fleeFromTarget = false;
		
		if (blackboard.lastFleeTarget != null) {
		
			if (blackboard.fleeToCoords != null) {
				if (ent.onGround && ent.getDistance(blackboard.fleeToCoords.posX, blackboard.fleeToCoords.posY, blackboard.fleeToCoords.posZ) > distToFleePref) {
					
				} else {
					fleeFromTarget = true;
				}
			} else {
				fleeFromTarget = true;
			}
		
		
			if (ent.getDistanceToEntity(blackboard.lastFleeTarget) < fleeRangeOverride || fleeFromTarget) {//ent.getDistanceToEntity(blackboard.lastFleeTarget) < fleeRangeOverride) {
				//System.out.println("flee target");
				Vec3 vec = getTargetVector(blackboard.lastFleeTarget);
				double dist = 3D;
				//if (!ent.onGround) dist = 3D;
				Vec3 dest = Vec3.createVectorHelper(ent.posX - vec.xCoord*dist, ent.posY/* + vec.xCoord*dist*/, ent.posZ - vec.zCoord*dist);
				//blackboard.setMoveTo(dest);
				blackboard.setMoveAndPathTo(dest); //clears out old path
				entInt.getAIBTAgent().setMoveTo(dest.xCoord, dest.yCoord, dest.zCoord);
				
				//air help
				if (!ent.onGround) {
					ent.motionX += -vec.xCoord * 0.05F;
					ent.motionZ += -vec.zCoord * 0.05F;
				}
			} else {//if (ent.getDistanceToEntity(blackboard.lastFleeTarget) > fleeRange) {
				//System.out.println("flee home");
				//if (ent.worldObj.getTotalWorldTime() % 20 == 0) {
					if (blackboard.fleeToCoords != null) {
						blackboard.setMoveTo(Vec3.createVectorHelper(blackboard.fleeToCoords.posX, blackboard.fleeToCoords.posY, blackboard.fleeToCoords.posZ));
						//entInt.getAIBTAgent().setMoveTo(blackboard.fleeToCoords.posX, blackboard.fleeToCoords.posY, blackboard.fleeToCoords.posZ);
					}
				//}
			}
		}
		
		return super.tick();
	}
	
	public Vec3 getTargetVector(Entity target) {
    	double vecX = target.posX - ent.posX;
    	double vecY = target.posY - ent.posY;
    	double vecZ = target.posZ - ent.posZ;
    	double dist = Math.sqrt(vecX * vecX + vecY * vecY + vecZ * vecZ);
    	Vec3 vec3 = Vec3.createVectorHelper(vecX / dist, vecY / dist, vecZ / dist);
    	return vec3;
    }
	
}
