package tropicraft.world.worldgen;

import net.minecraft.block.material.Material;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;

import java.util.Random;

import tropicraft.blocks.TropicraftBlocks;

public class WorldGenBamboo extends WorldGenerator
{

    public WorldGenBamboo()
    {
    }

    @Override
    public boolean generate(World world, Random random, int i, int j, int k)
    {
        for(int l = 0; l < 50; l++)
        {
            int i1 = (i + random.nextInt(4)) - random.nextInt(4);
            int j1 = j;
            int k1 = (k + random.nextInt(4)) - random.nextInt(4);
            if(!world.isAirBlock(i1, j1, k1) && world.getBlockMaterial(i1 + 1, j1 - 1, k1) != Material.water && world.getBlockMaterial(i1, j1 - 1, k1 - 1) != Material.water && world.getBlockMaterial(i1, j1 - 1, k1 + 1) != Material.water)
            {
                continue;
            }
            int l1 = 2 + random.nextInt(random.nextInt(3) + 1);
            for(int i2 = 0; i2 < l1 + 5; i2++)
            {
                if(TropicraftBlocks.bambooChute.canBlockStay(world, i1, j1 + i2, k1))
                {
                    world.setBlock(i1, j1 + i2, k1, TropicraftBlocks.bambooChute.blockID);
                }
            }

        }

        return true;
    }
}
