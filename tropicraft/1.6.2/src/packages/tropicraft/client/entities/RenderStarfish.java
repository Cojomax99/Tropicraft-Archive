package tropicraft.client.entities;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.ImageIO;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cpw.mods.fml.client.FMLClientHandler;

import tropicraft.entities.passive.water.EntitySeaUrchin;
import tropicraft.entities.passive.water.EntityStarfish;
import tropicraft.entities.passive.water.StarfishType;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.ResourceLocation;

public class RenderStarfish extends Render {
	/**
	 * Amount freshly hatched starfish are scaled down while rendering.
	 */
	public static final float BABY_RENDER_SCALE = 0.25f;

	/**
	 * Amount mature starfish are scaled down while rendering.
	 */
	public static final float ADULT_RENDER_SCALE = 1f;


	@Override

	/**
	 * Returns the location of an entity's texture. Doesn't seem to be called unless you call Render.bindEntityTexture.
	 */
	protected ResourceLocation getEntityTexture(Entity entity) {
		return null; //Using manual texture bind for loop below instead of bindEntityTexture method
	}
	
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2, float yaw, float partialTicks) {
		EntityStarfish starfish = (EntityStarfish) entity;
		StarfishType type = starfish.getStarfishType();
		
		float f = 0f;
		float f1 = 1f;
		float f2 = 0f;
		float f3 = 1f;
		float f1shifted = 1;
		float f3shifted = 1;

        Tessellator tessellator = Tessellator.instance;
        
        GL11.glPushMatrix();
        //renderOffsetAABB(starfish.boundingBox, d0 - starfish.lastTickPosX, d1 - starfish.lastTickPosY, d2 - starfish.lastTickPosZ);
        GL11.glTranslatef((float)d0-0.5f, (float)d1, (float)d2-0.5f);
        //GL11.glRotatef(yaw, 0.0F, 1.0F, 0.0F);
        GL11.glRotatef(90f, 1f, 0f, 0f);

		float growthProgress = starfish.getGrowthProgress();
		float scale = BABY_RENDER_SCALE + growthProgress*(ADULT_RENDER_SCALE-BABY_RENDER_SCALE);
		
		GL11.glScalef(scale, scale, scale);
        
        GL11.glEnable(GL12.GL_RESCALE_NORMAL);
        for (int i = 0; i < type.getLayerCount(); i++) {
        	renderManager.renderEngine.bindTexture(new ResourceLocation(type.getTexturePaths().get(i)));
        	//loadTexture(type.getTexturePaths().get(i));
        	popper(tessellator, f1, f2, f, f3, f1shifted, f3shifted, type.getLayerHeights()[i]);
        	GL11.glTranslatef(0f, 0f, -type.getLayerHeights()[i]);
        }

        GL11.glDisable(GL12.GL_RESCALE_NORMAL);
        GL11.glPopMatrix();
	}
	
    private void popper(Tessellator tessellator, float f, float f1, float f2, float f3, float f1shifted, float f3shifted, float layerHeight) {
        float f4 = 1.0F;
        float f5 = layerHeight;
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, 1.0F);

        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, f, f3shifted);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0D, f2, f3shifted);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0D, f2, f1shifted);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, f, f1shifted);

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 0.0F, -1F);
        tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - f5, f, f1);
        tessellator.addVertexWithUV(f4, 1.0D, 0.0F - f5, f2, f1);
        tessellator.addVertexWithUV(f4, 0.0D, 0.0F - f5, f2, f3);
        tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - f5, f, f3);
        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(-1F, 0.0F, 0.0F);
        for (int i = 0; i < 32; i++) {
            float f6 = (float) i / 32F;
            float f10 = (f + (f2 - f) * f6) - 0.001953125F;
            float f14 = f4 * f6;
            tessellator.addVertexWithUV(f14, 0.0D, 0.0F - f5, f10, f3);
            tessellator.addVertexWithUV(f14, 0.0D, 0.0D, f10, f3);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0D, f10, f1);
            tessellator.addVertexWithUV(f14, 1.0D, 0.0F - f5, f10, f1);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(1.0F, 0.0F, 0.0F);
        for (int j = 0; j < 32; j++) {
            float f7 = (float) j / 32F;
            float f11 = (f + (f2 - f) * f7) - 0.001953125F;
            float f15 = f4 * f7 + 0.03125F;
            tessellator.addVertexWithUV(f15, 1.0D, 0.0F - f5, f11, f1);
            tessellator.addVertexWithUV(f15, 1.0D, 0.0D, f11, f1);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0D, f11, f3);
            tessellator.addVertexWithUV(f15, 0.0D, 0.0F - f5, f11, f3);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, 1.0F, 0.0F);
        for (int k = 0; k < 32; k++) {
            float f8 = (float) k / 32F;
            float f12 = (f3 + (f1 - f3) * f8) - 0.001953125F;
            float f16 = f4 * f8 + 0.03125F;
            tessellator.addVertexWithUV(0.0D, f16, 0.0D, f, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0D, f2, f12);
            tessellator.addVertexWithUV(f4, f16, 0.0F - f5, f2, f12);
            tessellator.addVertexWithUV(0.0D, f16, 0.0F - f5, f, f12);
        }

        tessellator.draw();
        tessellator.startDrawingQuads();
        tessellator.setNormal(0.0F, -1F, 0.0F);
        for (int l = 0; l < 32; l++) {
            float f9 = (float) l / 32F;
            float f13 = (f3 + (f1 - f3) * f9) - 0.001953125F;
            float f17 = f4 * f9;
            tessellator.addVertexWithUV(f4, f17, 0.0D, f2, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0D, f, f13);
            tessellator.addVertexWithUV(0.0D, f17, 0.0F - f5, f, f13);
            tessellator.addVertexWithUV(f4, f17, 0.0F - f5, f2, f13);
        }

        tessellator.draw();
    }
}
