package net.tropicraft.items;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;
import net.tropicraft.mods.TropicraftMod;
import net.tropicraft.world.teleporter.TeleporterTropics;

import cpw.mods.fml.common.FMLCommonHandler;

public class ItemTropicsPortalStarter extends ItemTropicraft {

    private boolean isTestItem;

    public ItemTropicsPortalStarter(int i, boolean b) {
        super(i);
        isTestItem = b;
        maxStackSize = 1;
        if (!isTestItem) {
            setMaxDamage(3);
        }
    }

    @Override
    public ItemStack onItemRightClick(ItemStack itemstack, World world, EntityPlayer entityplayer) {
        if (isTestItem) {
            int destination = entityplayer.dimension == 0 ? TropicraftMod.tropicsDimensionID : 0;
            TropicraftMod.teleportPlayerToTropics((EntityPlayerMP) entityplayer);
            return itemstack;
        }

        // Portal starter only usable in overworld & tropics
        if (entityplayer.dimension != 0 && entityplayer.dimension != TropicraftMod.tropicsDimensionID) {
            return itemstack;
        }

        double playerX = entityplayer.prevPosX + (entityplayer.posX - entityplayer.prevPosX);
        double playerY = entityplayer.prevPosY + (entityplayer.posY - entityplayer.prevPosY) + 1.62D - (double) entityplayer.yOffset;
        double playerZ = entityplayer.prevPosZ + (entityplayer.posZ - entityplayer.prevPosZ);

        MovingObjectPosition target = this.getMovingObjectPositionFromPlayer(world, entityplayer, true);

        if (target == null) {
            return itemstack;
        } else {
            if (target.typeOfHit == EnumMovingObjectType.TILE) {
                int x = target.blockX;
                int y = target.blockY;
                int z = target.blockZ;
                int l = world.getBlockMetadata(x, y, z);

                if (!world.canMineBlock(entityplayer, x, y, z)) {
                    return itemstack;
                }

                if (!entityplayer.canPlayerEdit(x, y, z, l, itemstack)) {
                    return itemstack;
                }

                boolean found = false;
                for (int searchZ = -1; searchZ <= 1 && !found; searchZ++) {
                    for (int searchX = -1; searchX <= 1 && !found; searchX++) {
                        if (canGen(world, x + searchX, y, z + searchZ)) {
                            found = true;
                            entityplayer.swingItem();
                            (new TeleporterTropics(FMLCommonHandler.instance().getMinecraftServerInstance().worldServerForDimension(entityplayer.dimension))).buildTeleporterAt(x + searchX, y, z + searchZ, entityplayer);
                            //ModLoader.getMinecraftInstance().effectRenderer.addEffect(new EntitySplashFX(ModLoader.getMinecraftInstance().theWorld, playerX, playerY, playerZ, 0D, 0D, 0D));
                            itemstack.damageItem(1, entityplayer);
                        }
                    }
                }
            }
        }

        return itemstack;

    }

    public boolean canGen(World world, int x, int y, int z) {

        if (y < 9) {
            return false;
        }

        for (int offsetZ = -2; offsetZ < 3; offsetZ++) {
            for (int offsetX = -2; offsetX < 3; offsetX++) {
                if (offsetX == -2 || offsetX == 2 || offsetZ == -2 || offsetZ == 2) {
                    int bID = world.getBlockId(x + offsetX, y, z + offsetZ);
                    if (bID != Block.sandStone.blockID && bID != TropicraftMod.portalWall.blockID) {
                        return false;
                    }
                } else {
                    if (world.getBlockMaterial(x + offsetX, y, z + offsetZ) != Material.water) {
                        return false;
                    }
                    if (!world.isAirBlock(x + offsetX, y + 1, z + offsetZ)) {
                        return false;
                    }
                }
            }
        }

        return true;
    }

    @Override
    public boolean isFull3D() {
        return true;
    }
}
