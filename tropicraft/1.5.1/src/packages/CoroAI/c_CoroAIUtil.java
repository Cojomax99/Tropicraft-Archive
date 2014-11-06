package CoroAI;

import java.lang.reflect.Field;
import java.util.HashMap;

import net.minecraft.block.Block;
import net.minecraft.block.BlockChest;
import net.minecraft.block.BlockFence;
import net.minecraft.block.BlockFenceGate;
import net.minecraft.block.BlockWall;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.ChunkCoordinates;
import net.minecraft.util.FoodStats;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;
import CoroAI.componentAI.AIFakePlayer;
import CoroAI.componentAI.ICoroAI;
import CoroAI.entity.c_EnhAI;
import CoroAI.entity.c_PlayerProxy;
import cpw.mods.fml.common.ObfuscationReflectionHelper;

public class c_CoroAIUtil {
	
	public static String refl_mcp_Item_maxStackSize = "maxStackSize";
	public static String refl_c_Item_maxStackSize = "cq";
    public static String refl_s_Item_maxStackSize = "cq";
	public static String refl_mcp_Item_moveSpeed = "moveSpeed";
	public static String refl_obf_Item_moveSpeed = "bI";
	
	public static String refl_mcp_EntityPlayer_itemInUse = "itemInUse";
	public static String refl_c_EntityPlayer_itemInUse = "f";
	public static String refl_s_EntityPlayer_itemInUse = "f";
	public static String refl_mcp_EntityPlayer_itemInUseCount = "itemInUseCount";
	public static String refl_c_EntityPlayer_itemInUseCount = "g";
	public static String refl_s_EntityPlayer_itemInUseCount = "g";
	public static String refl_mcp_FoodStats_foodLevel = "foodLevel";
	public static String refl_c_FoodStats_foodLevel = "a";
	public static String refl_s_FoodStats_foodLevel = "a";
	
	public static String refl_thrower_mcp = "thrower";
	public static String refl_thrower_obf = "g";
	
	public static String refl_loadedChunks_mcp = "loadedChunks";
	public static String refl_loadedChunks_obf = "g";
	
	public static String refl_curBlockDamageMP_mcp = "curBlockDamageMP";
	public static String refl_curBlockDamageMP_obf = "g";
	
	
	
	public static boolean checkforMCP = true;
	public static boolean runningMCP = true;
	
	public static HashMap<String, c_EntInterface> playerToAILookup = new HashMap();
	public static HashMap<String, ICoroAI> playerToCompAILookup = new HashMap();
	
	//Tropicraft reflection
	public static boolean hasTropicraft = true; //try reflection once
	public static String tcE = "tropicraft.entities.";
	//public static String[] koaEnemyWhitelist = {"EntityVMonkey", "EntityTropicalFish", "EntityEIH", "EntityTropicraftWaterMob", "EntityTropiCreeper", "EntityAmphibian"};
	public static String[] koaEnemyWhitelist = {""};
	public static Item fishingRodTropical;
	public static Item dagger;
	public static Item leafBall;
	
	
	public c_CoroAIUtil() {
		//wut
	}
	
    public static boolean koaEnemy(Entity ent) {
    	try {
    		if (hasTropicraft) {
	    		/*for (String entStr : koaEnemyWhitelist) {
	    			if (Class.forName(tcE + entStr).isInstance(ent)) {
	    				return false;
	    			}
	    		}*/
    		}
    	} catch (Exception ex) {
    		hasTropicraft = false;
    		ex.printStackTrace();
    	}
    	return true;
    }
    
    public static boolean isEnemy(c_PlayerProxy ent, Entity enemy) {
    	if (enemy instanceof EntityLiving && !(enemy == ent)
    			&& !(enemy instanceof EntityCreeper
    			|| enemy instanceof EntityWaterMob
    			|| enemy instanceof EntityEnderman
    			|| enemy instanceof EntityPlayer
    			|| !koaEnemy(enemy))) {
			return true;
		}
    	return false;
    }
	
	public static boolean isNoPathBlock(Entity ent, int id, int meta) {
		if (ent instanceof EntityPlayer) {
			//barricades
			if (id >= 192 && id <= 197) {
				return true;
			}
			
			Block block = Block.blocksList[id];
			
			if (block != null && block instanceof BlockFence) {
				return true;
			}
			
			if (block != null && block instanceof BlockWall) {
				return true;
			}
			
			if (block != null && block instanceof BlockFenceGate) {
				return !BlockFenceGate.isFenceGateOpen(meta);
			}
			
			
		}
		//if (false) {
		/*if (id == ZCBlocks.barrier.blockID) {
			return true;
		}*/
		return false;
	}
	
	public static Field s_getItemInUse() {
		return tryGetField(EntityPlayer.class, refl_s_EntityPlayer_itemInUse, refl_mcp_EntityPlayer_itemInUse);
	}
	
	public static Field s_getItemInUseCount() {
		return tryGetField(EntityPlayer.class, refl_s_EntityPlayer_itemInUseCount, refl_mcp_EntityPlayer_itemInUseCount);
	}
	
	public static Field s_getFoodLevel() {
		return tryGetField(FoodStats.class, refl_s_FoodStats_foodLevel, refl_mcp_FoodStats_foodLevel);
	}
	
	public static Field tryGetField(Class theClass, String obf, String mcp) {
		Field field = null;
		try {
			field = theClass.getDeclaredField(ObfuscationReflectionHelper.remapFieldNames(theClass.getName(), new String[] { obf })[0]);
			field.setAccessible(true);
		} catch (Exception ex) {
			try {
				field = theClass.getDeclaredField(mcp);
				field.setAccessible(true);
			} catch (Exception ex2) { ex2.printStackTrace(); }
		}
		return field;
	}
	
	public static void check() {
		checkforMCP = false;
		try {
			//runningMCP = Class.forName("net.minecraft.world.World") != null;
			runningMCP = getPrivateValue(Vec3.class, Vec3.fakePool, "fakePool") != null;
		} catch (Exception e) {
			runningMCP = false;
			System.out.println("CoroAI: 'fakePool' field not found, mcp mode disabled");
		}
	}
	
	public static void setPrivateValueBoth(Class var0, Object var1, String obf, String mcp, Object var3) {
		if (checkforMCP) check();
    	try {
    		
    		if (!runningMCP) {
                //setPrivateValue(var0, var1, obf, var3);
            	ObfuscationReflectionHelper.setPrivateValue(var0, var1, obf, var3);
    		} else {
    			setPrivateValue(var0, var1, mcp, var3);
    		}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }
	
	public static Object getPrivateValueSRGMCP(Class var0, Object var1, String srg, String mcp) {
    	if (checkforMCP) check();
    	try {
    		
    		if (!runningMCP) {
                //setPrivateValue(var0, var1, obf, var3);
    			return getPrivateValue(var0, var1, srg);
            	//ObfuscationReflectionHelper.setPrivateValue(var0, var1, obf, var3);
    		} else {
    			return getPrivateValue(var0, var1, mcp);
    			//setPrivateValue(var0, var1, mcp, var3);
    		}
            /*try {
                return getPrivateValue(var0, var1, obf);
            } catch (NoSuchFieldException ex) {
                return getPrivateValue(var0, var1, mcp);
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
	
	public static void setPrivateValueSRGMCP(Class var0, Object var1, String obf, String mcp, Object var3) {
		if (checkforMCP) check();
		try {
    		
    		if (!runningMCP) {
    			setPrivateValue(var0, var1, obf, var3);
    		} else {
    			setPrivateValue(var0, var1, mcp, var3);
    		}
        } catch (Exception ex) {
            ex.printStackTrace();
        }
	}
    
    public static Object getPrivateValueBoth(Class var0, Object var1, String obf, String mcp) {
    	if (checkforMCP) check();
    	try {
    		
    		if (!runningMCP) {
                //setPrivateValue(var0, var1, obf, var3);
    			return ObfuscationReflectionHelper.getPrivateValue(var0, var1, obf);
            	//ObfuscationReflectionHelper.setPrivateValue(var0, var1, obf, var3);
    		} else {
    			return getPrivateValue(var0, var1, mcp);
    			//setPrivateValue(var0, var1, mcp, var3);
    		}
            /*try {
                return getPrivateValue(var0, var1, obf);
            } catch (NoSuchFieldException ex) {
                return getPrivateValue(var0, var1, mcp);
            }*/
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return null;
    }
    
    public static Object getPrivateValue(Class var0, Object var1, String var2) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field var3 = var0.getDeclaredField(var2);
            var3.setAccessible(true);
            return var3.get(var1);
        }
        catch (IllegalAccessException var4)
        {
            ModLoader.throwException("An impossible error has occured!", var4);
            return null;
        }
    }
    
    static Field field_modifiers = null;
    
    public static void setPrivateValue(Class var0, Object var1, int var2, Object var3) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
            Field var4 = var0.getDeclaredFields()[var2];
            var4.setAccessible(true);
            int var5 = field_modifiers.getInt(var4);

            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }

            var4.set(var1, var3);
        }
        catch (IllegalAccessException var6)
        {
            //logger.throwing("ModLoader", "setPrivateValue", var6);
            //throwException("An impossible error has occured!", var6);
        }
    }
    
    public static void setPrivateValue(Class var0, Object var1, String var2, Object var3) throws IllegalArgumentException, SecurityException, NoSuchFieldException
    {
        try
        {
        	if (field_modifiers == null) {
        		field_modifiers = Field.class.getDeclaredField("modifiers");
                field_modifiers.setAccessible(true);
        	}
        	
            Field var4 = var0.getDeclaredField(var2);
            int var5 = field_modifiers.getInt(var4);

            if ((var5 & 16) != 0)
            {
                field_modifiers.setInt(var4, var5 & -17);
            }

            var4.setAccessible(true);
            var4.set(var1, var3);
        }
        catch (IllegalAccessException var6)
        {
            //logger.throwing("ModLoader", "setPrivateValue", var6);
            //throwException("An impossible error has occured!", var6);
        }
    }

	/*public void setBlocksExplodable(boolean var1)
    {
        try
        {
            int var2;

            if (!var1)
            {
                blockResistance = new float[Block.blocksList.length];

                for (var2 = 0; var2 < Block.blocksList.length; ++var2)
                {
                    if (Block.blocksList[var2] != null)
                    {
                        blockResistance[var2] = Block.blocksList[var2].blockResistance;

                        if (var2 != Block.glass.blockID || !mod_SdkGuns.bulletsDestroyGlass)
                        {
                            Block.blocksList[var2].blockResistance = 6000000.0F;
                        }
                    }
                }
            }
            else if (blockResistance != null)
            {
                for (var2 = 0; var2 < Block.blocksList.length; ++var2)
                {
                    if (Block.blocksList[var2] != null)
                    {
                        Block.blocksList[var2].blockResistance = blockResistance[var2];
                    }
                }
            }

            areBlocksExplodable = var1;
        }
        catch (Exception var3)
        {
            ModLoader.getLogger().throwing("mod_SdkFps", "setBlocksExplodable", var3);
            SdkTools.ThrowException(String.format("Error setting blocks explodable: %b.", new Object[] {Boolean.valueOf(var1)}), var3);
        }
    }*/
    
    public static boolean AIRightClickHook(c_PlayerProxy ent, ItemStack itemToUse) {
    	/*if (itemToUse.getItem() instanceof ZCSdkItemGun) {
			if (ZCSdkTools.useItemInInventory(ent.fakePlayer, ((ZCSdkItemGun)itemToUse.getItem()).requiredBullet.itemID) <= 0) {
				return false;
			}
		}*/
    	return true;
    }
    
    public static void setPrjOwnerHook(c_PlayerProxy ent, Entity lastEnt) {
        
    	/*if (ent instanceof ZCSdkEntityBullet) {
			((ZCSdkEntityBullet) ent).owner = this;
		}*/
    }
    
    public static void setItems_JobHunt(c_PlayerProxy ent) {
    	//System.out.println("setItems_JobHunt broken");
    	//Melee slot
    	getTropiItemRefl("dagger", dagger);
    	if (dagger != null) ent.inventory.addItemStackToInventory(new ItemStack(dagger, 1));
		//Ranged slot
    	getTropiItemRefl("leafBall", leafBall);
    	if (leafBall != null) ent.inventory.addItemStackToInventory(new ItemStack(leafBall, 1));
		
		ent.wantedItems.add(Item.fishRaw.itemID);
		ent.wantedItems.add(Item.fishCooked.itemID);
		ent.wantedItems.add(Item.porkRaw.itemID);
		ent.wantedItems.add(Item.porkCooked.itemID);
		ent.wantedItems.add(Item.chickenRaw.itemID);
		ent.wantedItems.add(Item.chickenCooked.itemID);
    }
    
    public static void setItems_JobTrade(c_PlayerProxy ent) {
    	//System.out.println("setItems_JobHunt broken");
    	//Melee slot
    	getTropiItemRefl("dagger", dagger);
    	if (dagger != null) ent.inventory.addItemStackToInventory(new ItemStack(dagger, 1));
		//Ranged slot
    	getTropiItemRefl("leafBall", leafBall);
    	if (leafBall != null) ent.inventory.addItemStackToInventory(new ItemStack(leafBall, 1));
		
		ent.wantedItems.add(Item.fishRaw.itemID);
		ent.wantedItems.add(Item.fishCooked.itemID);
		ent.wantedItems.add(Item.porkRaw.itemID);
		ent.wantedItems.add(Item.porkCooked.itemID);
		ent.wantedItems.add(Item.chickenRaw.itemID);
		ent.wantedItems.add(Item.chickenCooked.itemID);
    }
    
    public static void setItems_JobHunt(AIFakePlayer ent) {
    	//System.out.println("setItems_JobHunt broken");
    	//Melee slot
    	getTropiItemRefl("swordZircon", dagger);
    	if (dagger != null) ent.inventory.addItemStackToInventory(new ItemStack(dagger, 1));
		//Ranged slot
    	getTropiItemRefl("leafBall", leafBall);
    	if (leafBall != null) ent.inventory.addItemStackToInventory(new ItemStack(leafBall, 1));
		
		ent.wantedItems.add(Item.fishRaw.itemID);
		ent.wantedItems.add(Item.fishCooked.itemID);
		ent.wantedItems.add(Item.porkRaw.itemID);
		ent.wantedItems.add(Item.porkCooked.itemID);
		ent.wantedItems.add(Item.chickenRaw.itemID);
		ent.wantedItems.add(Item.chickenCooked.itemID);
    }
    
    public static Item getTropiItemRefl(String fieldName, Item cache) {
		//Item item = null;
    	try {
    		if (hasTropicraft) {
    			if (cache == null) {
	    			Class clazz = Class.forName("tropicraft.items.TropicraftItems");
	    			
	    			if (clazz != null) {
	    				cache = (Item)getPrivateValue(clazz, clazz, fieldName);
	    				if (cache == null) {
	    					hasTropicraft = false;
	    				} else {
	    					setPrivateValue(c_CoroAIUtil.class, c_CoroAIUtil.class, fieldName, cache);
	    				}
	    			} else {
	    				hasTropicraft = false;    				
	    			}
    			} else {
    				
    			}
    		}
    	} catch (Exception ex) {
    		hasTropicraft = false;
    		System.out.println("this really shouldnt ever happen unless fishing job is used outside tropicraft");
    		//ex.printStackTrace();
    	}
    	return cache;
    	//ent.setCurrentItem(TropicraftMod.fishingRodTropical.itemID);
    }
    
    public static void equipFishingRod(c_EnhAI ent) {
    	
    	getTropiItemRefl("fishingRodTropical", fishingRodTropical);
    	if (fishingRodTropical != null) ent.setCurrentItem(fishingRodTropical.itemID);
    	
    	/*try {
    		if (hasTropicraft) {
    			if (fishingRodTropical == null) {
	    			Class clazz = Class.forName("net.tropicraft.mods.TropicraftMod");
	    			
	    			if (clazz != null) {
	    				fishingRodTropical = (Item)getPrivateValue(clazz, clazz, "fishingRodTropical");
	    				if (fishingRodTropical == null) {
	    					hasTropicraft = false;
	    					return;
	    				} else {
	    					ent.setCurrentItem(fishingRodTropical.itemID);
	    				}
	    			} else {
	    				hasTropicraft = false;    				
	    			}
    			} else {
    				ent.setCurrentItem(fishingRodTropical.itemID);
    			}
    		}
    	} catch (Exception ex) {
    		hasTropicraft = false;
    		System.out.println("this really shouldnt ever happen unless fishing job is used outside tropicraft");
    		//ex.printStackTrace();
    	}*/
    	//ent.setCurrentItem(TropicraftMod.fishingRodTropical.itemID);
    }
    
    public static boolean tryTransferToChest(c_EnhAI ent, int x, int y, int z) {
    	
    	TileEntityChest chest = (TileEntityChest)ent.worldObj.getBlockTileEntity(x, y, z);
		if (chest != null) {
			ent.openHomeChest();
			ent.job.getPrimaryJobClass().transferItems(ent.inventory, chest, -1, -1, true);
			return true;
		}
		return false;
    }
    
    public static void setItems_JobGather(c_PlayerProxy ent) {
    	ent.wantedItems.add(Block.wood.blockID);
    }
    
    public static void setItems_JobFish(c_PlayerProxy ent) {
    	getTropiItemRefl("dagger", dagger);
    	if (dagger != null) ent.inventory.addItemStackToInventory(new ItemStack(dagger, 1));
		//Ranged slot
    	getTropiItemRefl("fishingRodTropical", fishingRodTropical);
    	if (fishingRodTropical != null) ent.inventory.addItemStackToInventory(new ItemStack(fishingRodTropical, 1));
		
		ent.wantedItems.add(Item.fishRaw.itemID);
		ent.wantedItems.add(Item.fishCooked.itemID);
    }
    
    public static boolean isServer() {
    	return false;
    }
	
    public static EntityPlayer getFirstPlayer() {
    	//if (mc == null) mc = ModLoader.getMinecraftInstance();
    	//return mc.thePlayer;
    	return null;
    }
    
    public static boolean isChest(int id) {
    	if (id == 0) return false;
    	Block block = Block.blocksList[id];
    	if (block != null) {
    		if (block instanceof BlockChest) return true;
    	}
		return false;
	}
    
    public static PathEntityEx pathToEntity; //compile compatible method, aiplayer uses watcher on this
    public static boolean newPath = false;
    
    public static void playerPathfindCallback(PathEntityEx pathEx) {
    	/*c_AIP.i.*/pathToEntity = pathEx;
    	newPath = true;
    }
    
    public static Entity getEntByPersistantID(World world, int id) {
		try {
			for (int i = 0; i < world.loadedEntityList.size(); i++) {
				Entity ent = (Entity)world.loadedEntityList.get(i);
				if (ent instanceof ICoroAI) {
					if (((ICoroAI) ent).getAIAgent().entID == id && id != -1) {
						return (Entity)ent;
					}
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return null;
	}
    
    public static int getAge(EntityLiving ent) { return ent.entityAge; }
    public static void addAge(EntityLiving ent, int offsetAge) { ent.entityAge += offsetAge; }
    public static void despawnEntity(EntityLiving ent) { ent.despawnEntity(); }
    public static float getMoveSpeed(EntityLiving ent) { return ent.moveSpeed; }
    public static void setMoveSpeed(EntityLiving ent, float speed) { ent.moveSpeed = speed; }
    public static void setHealth(EntityLiving ent, int health) { ent.health = health; }
    public static void jump(EntityLiving ent) { ent.jump(); }
    public static boolean chunkExists(World world, int x, int z) { return world.getChunkProvider().chunkExists(x, z); } //fixed for 1.5
    
    public static ChunkCoordinates entToCoord(Entity ent) { return new ChunkCoordinates((int)ent.posX, (int)ent.posY, (int)ent.posZ); }
    public static double getDistance(Entity ent, ChunkCoordinates coords) { return ent.getDistance(coords.posX, coords.posY, coords.posZ); }
    public static double getDistanceXZ(Entity ent, ChunkCoordinates coords) { return ent.getDistance(coords.posX, ent.posY, coords.posZ); }
    public static double getDistanceXZ(ChunkCoordinates coords, ChunkCoordinates coords2) { return Math.sqrt(coords.getDistanceSquared(coords2.posX, coords.posY, coords2.posZ)); }
    public static boolean canEntSeeCoords (Entity ent, double posX, double posY, double posZ) {	return ent.worldObj.rayTraceBlocks(ent.worldObj.getWorldVec3Pool().getVecFromPool(ent.posX, ent.boundingBox.minY + (double)ent.getEyeHeight(), ent.posZ), ent.worldObj.getWorldVec3Pool().getVecFromPool(posX, posY, posZ)) == null; }
    public static boolean canCoordsSeeCoords (World world, double posX, double posY, double posZ, double posX2, double posY2, double posZ2) {	return world.rayTraceBlocks(world.getWorldVec3Pool().getVecFromPool(posX, posY, posZ), world.getWorldVec3Pool().getVecFromPool(posX2, posY2, posZ2)) == null; }
    //public static void dropItems(EntityLiving ent, boolean what, int what2) { ent.dropFewItems(what, what2); }
}
