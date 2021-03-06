package tropicraft.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import tropicraft.entities.hostile.land.TreeFrog;

public class RenderTreeFrog extends RenderLiving {

    public RenderTreeFrog(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    public void renderTreeFrog(TreeFrog entitytreefrog, double d, double d1, double d2,
            float f, float f1) {
        super.doRenderLiving(entitytreefrog, d, d1, d2, f, f1);
    }

    @Override
    public void doRenderLiving(EntityLiving entityliving, double d, double d1, double d2,
            float f, float f1) {
        renderTreeFrog((TreeFrog) entityliving, d, d1, d2, f, f1);
    }

    @Override
    public void doRender(Entity entity, double d, double d1, double d2,
            float f, float f1) {
        renderTreeFrog((TreeFrog) entity, d, d1, d2, f, f1);
    }
}
