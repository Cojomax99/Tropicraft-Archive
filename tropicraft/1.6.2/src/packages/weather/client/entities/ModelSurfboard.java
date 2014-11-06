// Date: 1/3/2013 6:39:11 AM
// Template version 1.1
// Java generated by Techne
// Keep in mind that you still need to fill in some blanks
// - ZeuX






package weather.client.entities;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

public class ModelSurfboard extends ModelBase
{
  //fields
    ModelRenderer Base;
    ModelRenderer FirstPlateLeft;
    ModelRenderer SecondPlateLeft;
    ModelRenderer ThirdPlateLeft;
    ModelRenderer LastPlateLeft;
    ModelRenderer FirstPlateRight;
    ModelRenderer SecondPlateRight;
    ModelRenderer ThirdPlateRight;
    ModelRenderer LastPlateRight;
    ModelRenderer FirstFin;
    ModelRenderer SecondFin;
    ModelRenderer ThirdFin;
    ModelRenderer LastFin;
  
  public ModelSurfboard()
  {
    textureWidth = 128;
    textureHeight = 128;
    
      Base = new ModelRenderer(this, 0, 0);
      Base.addBox(0F, 0F, 0F, 10, 1, 27);
      Base.setRotationPoint(0F, 6F, -13F);
      Base.setTextureSize(128, 128);
      Base.mirror = true;
      setRotation(Base, 0F, 0F, 0F);
      FirstPlateLeft = new ModelRenderer(this, 75, 0);
      FirstPlateLeft.addBox(0F, 0F, 0F, 8, 1, 1);
      FirstPlateLeft.setRotationPoint(1F, 6F, -14F);
      FirstPlateLeft.setTextureSize(128, 128);
      FirstPlateLeft.mirror = true;
      setRotation(FirstPlateLeft, 0F, 0F, 0F);
      SecondPlateLeft = new ModelRenderer(this, 75, 6);
      SecondPlateLeft.addBox(0F, 0F, 0F, 6, 1, 1);
      SecondPlateLeft.setRotationPoint(2F, 6F, -15F);
      SecondPlateLeft.setTextureSize(128, 128);
      SecondPlateLeft.mirror = true;
      setRotation(SecondPlateLeft, 0F, 0F, 0F);
      ThirdPlateLeft = new ModelRenderer(this, 75, 12);
      ThirdPlateLeft.addBox(0F, 0F, 0F, 4, 1, 1);
      ThirdPlateLeft.setRotationPoint(3F, 6F, -16F);
      ThirdPlateLeft.setTextureSize(128, 128);
      ThirdPlateLeft.mirror = true;
      setRotation(ThirdPlateLeft, 0F, 0F, 0F);
      LastPlateLeft = new ModelRenderer(this, 75, 18);
      LastPlateLeft.addBox(0F, 0F, 0F, 2, 1, 1);
      LastPlateLeft.setRotationPoint(4F, 6F, -17F);
      LastPlateLeft.setTextureSize(128, 128);
      LastPlateLeft.mirror = true;
      setRotation(LastPlateLeft, 0F, 0F, 0F);
      FirstPlateRight = new ModelRenderer(this, 75, 3);
      FirstPlateRight.addBox(0F, 0F, 0F, 8, 1, 1);
      FirstPlateRight.setRotationPoint(1F, 6F, 14F);
      FirstPlateRight.setTextureSize(128, 128);
      FirstPlateRight.mirror = true;
      setRotation(FirstPlateRight, 0F, 0F, 0F);
      SecondPlateRight = new ModelRenderer(this, 75, 9);
      SecondPlateRight.addBox(0F, 0F, 0F, 6, 1, 1);
      SecondPlateRight.setRotationPoint(2F, 6F, 15F);
      SecondPlateRight.setTextureSize(128, 128);
      SecondPlateRight.mirror = true;
      setRotation(SecondPlateRight, 0F, 0F, 0F);
      ThirdPlateRight = new ModelRenderer(this, 75, 15);
      ThirdPlateRight.addBox(0F, 0F, 0F, 4, 1, 1);
      ThirdPlateRight.setRotationPoint(3F, 6F, 16F);
      ThirdPlateRight.setTextureSize(128, 128);
      ThirdPlateRight.mirror = true;
      setRotation(ThirdPlateRight, 0F, 0F, 0F);
      LastPlateRight = new ModelRenderer(this, 75, 21);
      LastPlateRight.addBox(0F, 0F, 0F, 2, 1, 1);
      LastPlateRight.setRotationPoint(4F, 6F, 17F);
      LastPlateRight.setTextureSize(128, 128);
      LastPlateRight.mirror = true;
      setRotation(LastPlateRight, 0F, 0F, 0F);
      FirstFin = new ModelRenderer(this, 100, 0);
      FirstFin.addBox(0F, 0F, 0F, 1, 3, 1);
      FirstFin.setRotationPoint(4F, 7F, -9F);
      FirstFin.setTextureSize(128, 128);
      FirstFin.mirror = true;
      setRotation(FirstFin, 0F, 0F, 0F);
      SecondFin = new ModelRenderer(this, 105, 0);
      SecondFin.addBox(0F, 0F, 0F, 1, 2, 1);
      SecondFin.setRotationPoint(4F, 7F, -10F);
      SecondFin.setTextureSize(128, 128);
      SecondFin.mirror = true;
      setRotation(SecondFin, 0F, 0F, 0F);
      ThirdFin = new ModelRenderer(this, 110, 0);
      ThirdFin.addBox(0F, 0F, 0F, 1, 1, 1);
      ThirdFin.setRotationPoint(4F, 7F, -11F);
      ThirdFin.setTextureSize(128, 128);
      ThirdFin.mirror = true;
      setRotation(ThirdFin, 0F, 0F, 0F);
      LastFin = new ModelRenderer(this, 115, 0);
      LastFin.addBox(0F, 0F, 0F, 1, 4, 1);
      LastFin.setRotationPoint(4F, 7F, -12F);
      LastFin.setTextureSize(128, 128);
      LastFin.mirror = true;
      setRotation(LastFin, 0.7853982F, 0F, 0F);
  }
  
  public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.render(entity, f, f1, f2, f3, f4, f5);
    setRotationAngles(f, f1, f2, f3, f4, f5);
    Base.render(f5);
    FirstPlateLeft.render(f5);
    SecondPlateLeft.render(f5);
    ThirdPlateLeft.render(f5);
    LastPlateLeft.render(f5);
    FirstPlateRight.render(f5);
    SecondPlateRight.render(f5);
    ThirdPlateRight.render(f5);
    LastPlateRight.render(f5);
    FirstFin.render(f5);
    SecondFin.render(f5);
    ThirdFin.render(f5);
    LastFin.render(f5);
  }
  
  private void setRotation(ModelRenderer model, float x, float y, float z)
  {
    model.rotateAngleX = x;
    model.rotateAngleY = y;
    model.rotateAngleZ = z;
  }
  
  public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5)
  {
    super.setRotationAngles(f, f1, f2, f3, f4, f5, null);
  }

}
