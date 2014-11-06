package tropicraft.blocks.tileentities.renderers;

import org.lwjgl.opengl.GL11;

import tropicraft.ModInfo;
import tropicraft.TropicraftUtils;
import tropicraft.blocks.tileentities.ModelBambooMug;
import tropicraft.blocks.tileentities.TileEntityBambooMug;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import tropicraft.drinks.Drink;
import tropicraft.client.entities.models.ModelUmbrella;
import tropicraft.items.ItemCocktail;

public class TileEntityBambooMugRenderer extends TileEntitySpecialRenderer {
	private ModelBambooMug modelBambooMug = new ModelBambooMug();
	private ModelUmbrella modelUmbrella = new ModelUmbrella();

	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y, double z, float partialTicks) {
		TileEntityBambooMug mug = (TileEntityBambooMug) tileentity;
		TropicraftUtils.bindTextureTE("bamboomug");
		GL11.glPushMatrix();
		GL11.glTranslatef((float)x+0.5f,(float)y+1.5f,(float)z+0.5f);
		GL11.glRotatef(180f, 1f, 0f, 1f);
		int meta = mug.getMetadata();
		
		if (meta == 2) {
			GL11.glRotatef(0f, 0f, 1f, 0f);
		} else if (meta == 3) {
			GL11.glRotatef(180f, 0f, 1f, 0f);
		} else if (meta == 4) {
			GL11.glRotatef(270f, 0f, 1f, 0f);
		} else if (meta == 5) {
			GL11.glRotatef(90f, 0f, 1f, 0f);
		}
		
		if (!mug.isEmpty()) {
			modelBambooMug.renderLiquid = true;
			modelBambooMug.liquidColor = ItemCocktail.getCocktailColor(mug.cocktail);
		} else {
			modelBambooMug.renderLiquid = false;
		}
		
		modelBambooMug.renderBambooMug();
		
		if (!mug.isEmpty()) {
			Drink drink = ItemCocktail.getDrink(mug.cocktail);
			if (drink != null && drink.hasUmbrella) {
				GL11.glTranslatef(0f, 1.175f, 0f);
				GL11.glRotatef(30f, 1f, 0f, 1f);
				GL11.glScalef(0.25f, 0.25f, 0.25f);
				TropicraftUtils.bindTextureEntity("beachstuff/umbrellatextred");
				modelUmbrella.renderUmbrella();
			}
		}
		
		GL11.glPopMatrix();
	}
}
