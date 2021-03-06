package net.tropicraft.world.worldgen;

import net.minecraft.block.Block;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.tropicraft.blocks.BlockTropicraftLog;
import net.tropicraft.mods.TropicraftMod;

import java.util.Random;

public class WorldGenTropicraftNormalPalms extends WorldGenerator
{

	boolean notify;

	public WorldGenTropicraftNormalPalms()
	{
		super();
		notify = false;
	}

	public WorldGenTropicraftNormalPalms(boolean flag)
	{
		super(flag);
		notify = flag;
	}

	@Override
	public boolean generate(World world, Random random, int i, int j, int k)
	{
		int b = random.nextInt(2);
		byte height = (byte)(random.nextInt(4) + 6);
		boolean flag = true;
		if(j < 1 || j + height + 1 > 128)
		{
			return false;
		}
		for(int l = j; l <= j + 1 + height; l++)
		{
			byte byte1 = 1;
			if(l == j)
			{
				byte1 = 0;
			}
			if(l >= (j + 1 + height) - 2)
			{
				byte1 = 2;
			}
			for(int k1 = i - byte1; k1 <= i + byte1 && flag; k1++)
			{
				for(int i2 = k - byte1; i2 <= k + byte1 && flag; i2++)
				{
					if(l >= 0 && l < 128)
					{
						int j2 = world.getBlockId(k1, l, i2);
						if(j2 != 0 && j2 != TropicraftMod.tropicLeaves.blockID)
						{
							flag = false;
						}
					} else
					{
						flag = false;
					}
				}

			}

		}

		if(!flag)
		{
			return false;
		}
		int i1 = world.getBlockId(i, j - 1, k);
		if(i1 != Block.sand.blockID || j >= 128 - height - 1)
		{
			int ground = world.getHeightValue(i, k);
			i1 = world.getBlockId(i, ground - 1, k);
			if(i1 != Block.sand.blockID || j >= 128 - height - 1)
			{
				return false;
			}
			j = ground;
		}
		setBlockAndMetadata(world, i, j - 1, k, Block.sand.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 2, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 1, k + 1, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 1, k + 2, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 1, k + 3, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height, k + 4, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 1, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 1, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + height + 1, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 4, j + height, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 1, k - 1, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 1, k - 2, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height + 1, k - 3, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i, j + height, k - 4, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + height + 1, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + height + 1, k - 1, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 1, j + height + 1, k + 1, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 1, k - 1, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 1, j + height + 1, k + 1, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 2, j + height + 1, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 3, j + height + 1, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 4, j + height, k, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 1, k + 2, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 2, j + height + 1, k - 2, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 2, j + height + 1, k + 2, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 2, j + height + 1, k - 2, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + height, k + 3, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i + 3, j + height, k - 3, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 3, j + height, k + 3, TropicraftMod.tropicLeaves.blockID, 0);
		setBlockAndMetadata(world, i - 3, j + height, k - 3, TropicraftMod.tropicLeaves.blockID, 0);
		for(int j1 = 0; j1 < height + 4; j1++)
		{
			int l1 = world.getBlockId(i, j + j1, k);
			if(l1 != 0 && l1 != TropicraftMod.tropicLeaves.blockID)
			{
				continue;
			}
			setBlockAndMetadata(world, i, (j + j1) - 2, k, TropicraftMod.tropicalWood.blockID, 0);
			BlockTropicraftLog.spawnCoconuts(world, i, (j + j1) - 2, k, random, 2);
			if(j1 <= height - 1 || j1 >= height + 2)
			{
				continue;
			}
			//if(b > 0)
			// {

			// setBlockAndMetadata(world, i, j + j1 + 1, k - 1, mod_tropicLeaves.coconut.blockID, 0);
			// setBlockAndMetadata(world, i - 1, j + j1 + 1, k - 1, mod_tropicLeaves.coconut.blockID, 0);
			// } else
			//{
			// setBlockAndMetadata(world, i, j + j1 + 1, k - 1, mod_tropicLeaves.coconut.blockID, 0);
			//setBlockAndMetadata(world, i - 1, j + j1 + 1, k - 1, mod_tropicLeaves.coconut.blockID, 0);
			//}
		}

		return true;
	}


	@Override
	/**
	 * Sets the block in the world, notifying neighbors if enabled.
	 */
	protected void setBlockAndMetadata(World par1World, int par2, int par3, int par4, int par5, int par6)
	{
		//if (par1World.isAirBlock(par2, par3, par4))
		if (notify)
		{
			par1World.setBlockAndMetadataWithNotify(par2, par3, par4, par5, par6);
		}
		else
		{
			par1World.setBlockAndMetadata(par2, par3, par4, par5, par6);
		}
	}
}
