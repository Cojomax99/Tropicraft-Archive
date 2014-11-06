package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;

import org.lwjgl.opengl.GL11;

import tropicraft.entities.passive.water.EntityTurtleEgg;

public class RenderTurtleEgg extends RenderLiving {

    public RenderTurtleEgg(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    public void renderTurtleEgg(EntityTurtleEgg entityTurtleEgg, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entityTurtleEgg, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderTurtleEgg((EntityTurtleEgg) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderTurtleEgg((EntityTurtleEgg) entity, d, d1, d2, f, f1);
    }

    protected void preRenderScale(EntityTurtleEgg entitymarlin, float f) {

        GL11.glScalef(.5F, .5F, .5F);
    }

    @Override
    protected void preRenderCallback(EntityLiving entityliving, float f) {
        preRenderScale((EntityTurtleEgg) entityliving, f);
    }
}