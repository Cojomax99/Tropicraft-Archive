package extendedrenderer.render;

import net.minecraft.block.Block;
import net.minecraft.client.particle.EffectRenderer;
import net.minecraft.client.particle.EntityDiggingFX;
import net.minecraft.client.particle.EntityFX;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.RenderHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.src.ModLoader;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import extendedrenderer.particle.entity.EntityRotFX;
@SideOnly(Side.CLIENT)
public class RotatingEffectRenderer
{
    public int layers = 6;
    public World worldObj;
    public List[] fxLayers = new List[layers];
    public RenderEngine renderer;
    public Random rand = new Random();
    public float hmm = 0F;

    public RotatingEffectRenderer(World var1, RenderEngine var2)
    {
        if (var1 != null)
        {
            this.worldObj = var1;
        }

        this.renderer = var2;

        for (int var3 = 0; var3 < layers; ++var3)
        {
            this.fxLayers[var3] = new ArrayList();
        }
    }

    public void addEffect(EntityFX var1)
    {
        int var2 = var1.getFXLayer();

        if (this.fxLayers[var2].size() >= 4000)
        {
            this.fxLayers[var2].remove(0);
        }

        this.fxLayers[var2].add(var1);
    }

    public void updateEffects()
    {
        for (int var1 = 0; var1 < layers; ++var1)
        {
            for (int var2 = 0; var2 < this.fxLayers[var1].size(); ++var2)
            {
                EntityFX var3 = (EntityFX)this.fxLayers[var1].get(var2);

                if (var3 == null)
                {
                    //System.out.println("Null particle!");
                    continue;
                }

                //if (var3 instanceof EntityRotFX) {
                //Prevents double updates if you are adding particles to the mc weatherEffects list
                if (!((EntityRotFX)var3).weatherEffect)
                {
                    var3.onUpdate();
                }

                //} else {
                //var3.onUpdate();
                //}

                if (var3.getDistanceToEntity(ModLoader.getMinecraftInstance().thePlayer) > 64F)
                {
                    //var3.setDead();
                }

                if (var3.isDead)
                {
                    this.fxLayers[var1].remove(var2--);
                }
            }
        }
    }

    public void renderParticles(Entity var1, float var2)
    {
        //if (true) return;
        //float var3 = MathHelper.cos(hmm * (float)Math.PI / 180.0F);
        //float var4 = MathHelper.sin(hmm * (float)Math.PI / 180.0F);
        EntityFX.interpPosX = var1.lastTickPosX + (var1.posX - var1.lastTickPosX) * (double)var2;
        EntityFX.interpPosY = var1.lastTickPosY + (var1.posY - var1.lastTickPosY) * (double)var2;
        EntityFX.interpPosZ = var1.lastTickPosZ + (var1.posZ - var1.lastTickPosZ) * (double)var2;

        try
        {
            for (int var8 = 0; var8 < layers; ++var8)
            {
                if (var8 == 3)
                {
                    continue;
                }

                if (this.fxLayers[var8].size() != 0)
                {
                    int var9 = 0;

                    if (var8 == 0)
                    {
                        var9 = this.renderer.getTexture("/particles.png");
                    }

                    if (var8 == 1)
                    {
                        var9 = this.renderer.getTexture("/terrain.png");
                    }

                    if (var8 == 2)
                    {
                        var9 = this.renderer.getTexture("/gui/items.png");
                    }

                    if (var8 == 4)
                    {
                        var9 = this.renderer.getTexture("/coro/weather/particles_64.png");
                    }
                    
                    if (var8 == 5)
                    {
                        var9 = this.renderer.getTexture("/coro/weather/particles_16.png");
                    }

                    GL11.glBindTexture(GL11.GL_TEXTURE_2D, var9);
                    Tessellator var10 = Tessellator.instance;
                    GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                    GL11.glDepthMask(false);
                    GL11.glEnable(GL11.GL_BLEND);
                    GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.003921569F);
                    GL11.glDisable(GL11.GL_CULL_FACE);
                    //GL11.glRotatef(180.0F - RenderManager.instance.playerViewY, 0.0F, 1.0F, 0.0F);
                    //GL11.glRotatef(-RenderManager.instance.playerViewX, 1.0F, 0.0F, 0.0F);
                    //GL11.glPushMatrix();
                    
                    //RenderHelper.enableStandardItemLighting();
                    
                    var10.startDrawingQuads();
                    //var10.setBrightness(15728880);
                    //GL11.glDisable(GL11.GL_ALPHA_TEST);
                    
                    //GL11.glDisable(GL11.GL_BLEND);

                    //GL11.glEnable(GL11.GL_BLEND);
                    //GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                    //GL11.glAlphaFunc(GL11.GL_GREATER, 0.0F);
                    //GL11.glRotatef(90F, 1.0F, 0.0F, 0.0F);
                    //GL11.glRotatef(hmm, 0.0F, 1.0F, 0.0F);

                    //int brightness = 0;
                    if (ModLoader.getMinecraftInstance().thePlayer != null)
                    {
                        //brightness = ModLoader.getMinecraftInstance().thePlayer.getBrightnessForRender(var2);
                        //ExtendedRenderer.plBrightness = ModLoader.getMinecraftInstance().thePlayer.getBrightnessForRender(var2);
                    }

                    for (int var11 = 0; var11 < this.fxLayers[var8].size(); ++var11)
                    {
                        EntityFX var12 = (EntityFX)this.fxLayers[var8].get(var11);

                        if (var12 == null)
                        {
                            //System.out.println("Null particle2!");
                            continue;
                        }
                        
                        boolean render = true;
                        
                        if (var12 instanceof EntityRotFX) {
                        	if (var1.getDistanceToEntity(var12) > ((EntityRotFX) var12).maxRenderRange()) render = false;
                        }

                        //System.out.println("bn " + ModLoader.getMinecraftInstance().thePlayer.getBrightness(var2));
                        //caaaaache
                        //var10.setBrightness(mod_ExtendedRenderer.plBrightness);
                        //no cache
                        //var10.setBrightness(var12.getBrightnessForRender(var2));
                        if (render) {
	                        float var3 = MathHelper.cos(var12.rotationYaw * (float)Math.PI / 180.0F);
	                        float var4 = MathHelper.sin(var12.rotationYaw * (float)Math.PI / 180.0F);
	                        float var5 = -var4 * MathHelper.sin(var12.rotationPitch * (float)Math.PI / 180.0F);
	                        float var6 = var3 * MathHelper.sin(var12.rotationPitch * (float)Math.PI / 180.0F);
	                        float var7 = MathHelper.cos(var12.rotationPitch * (float)Math.PI / 180.0F);
	                        var12.renderParticle(var10, var2, var3, var7, var4, var5, var6);
                        }
                    }

                    var10.draw();
                    
                    //RenderHelper.disableStandardItemLighting();
                    
                    //GL11.glPopMatrix();
                    //GL11.glRotatef(-hmm, 0.0F, 1.0F, 0.0F);
                    //GL11.glRotatef(-90F, 1.0F, 0.0F, 0.0F);
                    //GL11.glDisable(GL11.GL_BLEND);
                    GL11.glEnable(GL11.GL_CULL_FACE);
                    GL11.glDisable(GL11.GL_BLEND);
                    GL11.glDepthMask(true);
                    GL11.glAlphaFunc(GL11.GL_GREATER, 0.1F);
                    //GL11.glEnable(GL11.GL_ALPHA_TEST);
                    hmm += 1F;

                    if (hmm < -180)
                    {
                        hmm = 180;
                    }

                    if (hmm > 180)
                    {
                        hmm = -180;
                    }
                }
            }
        }
        catch (IndexOutOfBoundsException ex)
        {
            //super small thread desync, caught and ignored
        }
    }

    public void clearEffects(World var1)
    {
        this.worldObj = var1;

        for (int var2 = 0; var2 < layers; ++var2)
        {
            this.fxLayers[var2].clear();
        }
    }

    public void addBlockDestroyEffects(int var1, int var2, int var3, int var4, int var5)
    {
        if (var4 != 0)
        {
            Block var6 = Block.blocksList[var4];
            byte var7 = 4;

            for (int var8 = 0; var8 < var7; ++var8)
            {
                for (int var9 = 0; var9 < var7; ++var9)
                {
                    for (int var10 = 0; var10 < var7; ++var10)
                    {
                        double var11 = (double)var1 + ((double)var8 + 0.5D) / (double)var7;
                        double var13 = (double)var2 + ((double)var9 + 0.5D) / (double)var7;
                        double var15 = (double)var3 + ((double)var10 + 0.5D) / (double)var7;
                        int var17 = this.rand.nextInt(6);
                        //this.addEffect((new EntityDiggingFX(this.worldObj, var11, var13, var15, var11 - (double)var1 - 0.5D, var13 - (double)var2 - 0.5D, var15 - (double)var3 - 0.5D, var6, var17, var5)).func_70596_a(var1, var2, var3));
                    }
                }
            }
        }
    }

    public String getStatistics()
    {
        return "" + (this.fxLayers[0].size() + this.fxLayers[1].size() + this.fxLayers[2].size());
    }
}
