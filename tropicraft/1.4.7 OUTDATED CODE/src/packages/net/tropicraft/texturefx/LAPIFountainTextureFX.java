// Decompiled by Jad v1.5.8g. Copyright 2001 Pavel Kouznetsov.
// Jad home page: http://www.kpdus.com/jad.html
// Decompiler options: packimports(3) braces deadcode fieldsfirst 

package net.tropicraft.texturefx;

import net.minecraft.client.renderer.RenderEngine;
import net.minecraftforge.client.ForgeHooksClient;
import net.tropicraft.blocks.liquids.LAPI;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.FMLTextureFX;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

// Referenced classes of package net.minecraft.src:
//            RenderEngine

@SideOnly(Side.CLIENT)
public class LAPIFountainTextureFX extends FMLTextureFX
{
	private static final String SPRITE_SHEET_LOC = "/tropicalmod/fountain_flow.png";
	
    protected float[] red = new float[256];
    protected float[] blue = new float[256];
    protected float[] green = new float[256];
    protected float[] alpha = new float[256];
    private int tickCounter = 0;
	private boolean isAnimated;

    public LAPIFountainTextureFX(int i, boolean isAnimatedLiquid)
    {
        super(i);   
        
        if (isAnimatedLiquid) {
        //    tileSize = 2;
        } else {
       //     tileSize = 1;
        }
        
        isAnimated = isAnimatedLiquid;
    //    tileImage = 2;
        setup();

    }
    @Override
    public void setup()
    {
        super.setup();
        red = new float[tileSizeSquare];
        blue = new float[tileSizeSquare];
        green = new float[tileSizeSquare];
        alpha = new float[tileSizeSquare];
        tickCounter = 0;
    }
    
    public void onTick()
    {
        ++this.tickCounter;
        int var1;
        int var2;
        float var3;
        int var5;
        int var6;

        for (var1 = 0; var1 < tileSizeBase; ++var1)
        {
            for (var2 = 0; var2 < tileSizeBase; ++var2)
            {
                var3 = 0.0F;

                for (int var4 = var2 - 2; var4 <= var2; ++var4)
                {
                    var5 = var1 & tileSizeMask;
                    var6 = var4 & tileSizeMask;
                    var3 += this.red[var5 + var6 * tileSizeBase];
                }

                this.blue[var1 + var2 * tileSizeBase] = var3 / 3.2F + this.green[var1 + var2 * tileSizeBase] * 0.8F;
            }
        }

        for (var1 = 0; var1 < tileSizeBase; ++var1)
        {
            for (var2 = 0; var2 < tileSizeBase; ++var2)
            {
                this.green[var1 + var2 * tileSizeBase] += this.alpha[var1 + var2 * tileSizeBase] * 0.05F;

                if (this.green[var1 + var2 * tileSizeBase] < 0.0F)
                {
                    this.green[var1 + var2 * tileSizeBase] = 0.0F;
                }

                this.alpha[var1 + var2 * tileSizeBase] -= 0.3F;

                if (Math.random() < 0.2D)
                {
                    this.alpha[var1 + var2 * tileSizeBase] = 0.5F;
                }
            }
        }

        float[] var12 = this.blue;
        this.blue = this.red;
        this.red = var12;

        for (var2 = 0; var2 < tileSizeSquare; ++var2)
        {
            var3 = this.red[var2 - this.tickCounter * tileSizeBase & tileSizeSquareMask];

            if (var3 > 1.0F)
            {
                var3 = 1.0F;
            }

            if (var3 < 0.0F)
            {
                var3 = 0.0F;
            }

            float var13 = var3 * var3;
            var5 = (int)(32.0F + var13 * 32.0F);
            var6 = (int)(50.0F + var13 * 64.0F);
            int var7 = 255;
            int var8 = (int)(146.0F + var13 * 50.0F);

            if (this.anaglyphEnabled)
            {
                int var9 = (var5 * 30 + var6 * 59 + var7 * 11) / 100;
                int var10 = (var5 * 30 + var6 * 70) / 100;
                int var11 = (var5 * 30 + var7 * 70) / 100;
                var5 = var9;
                var6 = var10;
                var7 = var11;
            }

            this.imageData[var2 * 4 + 0] = (byte)var5;
            this.imageData[var2 * 4 + 1] = (byte)(var6);
            this.imageData[var2 * 4 + 2] = (byte)var7;
            this.imageData[var2 * 4 + 3] = (byte)var8;
        }
    }

	@Override
    public void bindImage(RenderEngine par1RenderEngine)
    {
		ForgeHooksClient.bindTexture(SPRITE_SHEET_LOC, 0);
    }

}
