package CoroAI.bt.actions;

import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.Vec3;
import CoroAI.bt.Behavior;
import CoroAI.bt.EnumBehaviorState;
import CoroAI.bt.selector.Selector;
import CoroAI.componentAI.ICoroAI;

public class MoveToCoords extends Selector {

	public ICoroAI ent;
	public ChunkCoordinates[] coordsRef;
	public int closeDist;
	public boolean ignoreY = false;
	
	public boolean helpMonitor = false;
	public boolean noMoveReset = true;
	public int noMoveTicks = 0;
	public int noMoveTicksMax = 80;
	public double noMoveTicksThreshold = 0.1D;
	public Vec3 noMoveTicksLastPos;
	
	public MoveToCoords(Behavior parParent, ICoroAI parEnt, ChunkCoordinates[] parCoordsRef, int parCloseDist, boolean parIgnoreY, boolean parHelpMonitor) {
		this(parParent, parEnt, parCoordsRef, parCloseDist);
		ignoreY = parIgnoreY;
		helpMonitor = parHelpMonitor;
	}
	
	public MoveToCoords(Behavior parParent, ICoroAI parEnt, ChunkCoordinates[] parCoordsRef, int parCloseDist) {
		super(parParent);
		ent = parEnt;
		coordsRef = parCoordsRef;
		closeDist = parCloseDist;
	}
	
	@Override
	public EnumBehaviorState tick() {
		if (coordsRef[0] != null) {
			
			double dist;
			
			EntityLiving entL = ((EntityLiving)ent);
			
			if (ignoreY) {
				dist = ((EntityLiving)ent).getDistance(coordsRef[0].posX, ((EntityLiving)ent).posY, coordsRef[0].posZ);
			} else {
				dist = ((EntityLiving)ent).getDistance(coordsRef[0].posX, coordsRef[0].posY, coordsRef[0].posZ);
			}
			
			//closeDist = 10;
			
			//dbg("moveto dist: " + dist);
			if (dist < closeDist) {
				noMoveTicks = 0;
				//keep in mind, having this set to clear really broke the ai when job hunt is firing....
				//((EntityLiving)ent).getNavigator().clearPathEntity();
				return children.get(1).tick();
			} else {
				if (((EntityLiving)ent).getNavigator().noPath() && ((EntityLiving)ent).worldObj.getWorldTime() % 20 == 0) {
					//dbg("moveto trying to set path, cur dist: " + dist);
					//dbg("moveto: " + coordsRef[0].posX + ", " + coordsRef[0].posY + ", " + coordsRef[0].posZ + " - " + (int)dist);
					ent.getAIAgent().moveTo(coordsRef[0]);
					noMoveTicks = 0;
				}
				//timeout check go here maybe?
				
				if (helpMonitor) {
					if (noMoveTicksLastPos != null) {
						double posDiff = entL.getDistance(noMoveTicksLastPos.xCoord, entL.posY, noMoveTicksLastPos.zCoord);
						noMoveTicksThreshold = 0.01D;
						if (posDiff < noMoveTicksThreshold) {
							noMoveTicks++;
							//dbg("noMoveTicks: " + noMoveTicks + " posDiff: " + posDiff);
							if (noMoveTicks > noMoveTicksMax) {
								noMoveTicks = 0;
								dbg("no move ticks max triggered, nulling coords");
								coordsRef[0] = null;
								return children.get(0).tick();
							}
						}
					}
					noMoveTicksLastPos = Vec3.createVectorHelper(entL.posX, entL.posY, entL.posZ);
				}
				
				return EnumBehaviorState.RUNNING;
			}
		} else {
			//dbg("null moveto coords");
			return children.get(0).tick();
		}
	}
	
}
