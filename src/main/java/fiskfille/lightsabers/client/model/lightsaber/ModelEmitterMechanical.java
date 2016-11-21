package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;

import org.lwjgl.opengl.GL11;

public class ModelEmitterMechanical extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer guard1;
    public ModelRenderer guard3;
    public ModelRenderer guard4;
    public ModelRenderer guard5;
    public ModelRenderer guard6;
    public ModelRenderer panel1;
    public ModelRenderer panel2;
    public ModelRenderer frontButton1;
    public ModelRenderer body2;
    public ModelRenderer body6;
    public ModelRenderer body10;
    public ModelRenderer body12;
    public ModelRenderer body13;
    public ModelRenderer body15;
    public ModelRenderer body19;
    public ModelRenderer body28;
    public ModelRenderer body29;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer body9;
    public ModelRenderer body11;
    public ModelRenderer body14;
    public ModelRenderer body16;
    public ModelRenderer body17;
    public ModelRenderer body18;
    public ModelRenderer body20;
    public ModelRenderer body21;
    public ModelRenderer body22;
    public ModelRenderer guard2;
    public ModelRenderer frontButton2;
    public ModelRenderer frontButton3;

    public ModelEmitterMechanical()
    {
        this.textureWidth = 64;
        this.textureHeight = 32;
        this.body28 = new ModelRenderer(this, 0, 30);
        this.body28.setRotationPoint(-0.8F, -19.76F, 3.1F);
        this.body28.addBox(-1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(body28, 0.296705972839036F, -0.2617993877991494F, -0.20943951023931953F);
        this.body12 = new ModelRenderer(this, 0, 0);
        this.body12.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body12.addBox(-1.5F, -15.0F, 2.62F, 3, 15, 1, 0.0F);
        this.setRotateAngle(body12, 0.0F, 3.141592653589793F, 0.0F);
        this.body16 = new ModelRenderer(this, 8, 19);
        this.body16.setRotationPoint(-0.91F, -15.19F, 3.12F);
        this.body16.addBox(0.0F, -1.0F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(body16, 0.0F, 0.0F, -0.6283185307179586F);
        this.guard1 = new ModelRenderer(this, 34, 17);
        this.guard1.setRotationPoint(0.0F, -12.0F, -2.5F);
        this.guard1.addBox(-4.0F, -1.0F, 0.0F, 8, 2, 7, 0.0F);
        this.body10 = new ModelRenderer(this, 8, 0);
        this.body10.mirror = true;
        this.body10.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body10.addBox(-1.5F, -15.0F, 2.62F, 3, 15, 1, 0.0F);
        this.setRotateAngle(body10, 0.0F, 2.356194490192345F, 0.0F);
        this.body18 = new ModelRenderer(this, 4, 21);
        this.body18.setRotationPoint(-0.8F, -0.2F, 0.0F);
        this.body18.addBox(-0.4F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.body11 = new ModelRenderer(this, 0, 19);
        this.body11.mirror = true;
        this.body11.setRotationPoint(1.16F, -14.06F, 3.12F);
        this.body11.addBox(-3.0F, -1.0F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(body11, 0.0F, 0.0F, 0.33161255787892263F);
        this.body13 = new ModelRenderer(this, 8, 0);
        this.body13.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body13.addBox(-1.5F, -15.0F, 2.62F, 3, 15, 1, 0.0F);
        this.setRotateAngle(body13, 0.0F, -2.356194490192345F, 0.0F);
        this.body5 = new ModelRenderer(this, 8, 21);
        this.body5.mirror = true;
        this.body5.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.body5.addBox(-1.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.frontButton3 = new ModelRenderer(this, 41, 0);
        this.frontButton3.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontButton3.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(frontButton3, 0.0F, -2.1816615649929116F, 0.0F);
        this.body22 = new ModelRenderer(this, 8, 21);
        this.body22.setRotationPoint(0.0F, 1.0F, 0.0F);
        this.body22.addBox(0.0F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.guard3 = new ModelRenderer(this, 44, 21);
        this.guard3.setRotationPoint(-2.6F, -13.0F, -3.94F);
        this.guard3.addBox(0.0F, 0.0F, 0.0F, 5, 2, 3, 0.0F);
        this.body7 = new ModelRenderer(this, 8, 19);
        this.body7.mirror = true;
        this.body7.setRotationPoint(0.91F, -15.19F, 3.12F);
        this.body7.addBox(-3.0F, -1.0F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(body7, 0.0F, 0.0F, 0.6283185307179586F);
        this.guard6 = new ModelRenderer(this, 40, 17);
        this.guard6.setRotationPoint(-4.0F, -11.0F, 2.5F);
        this.guard6.addBox(0.0F, 0.0F, 0.0F, 8, 2, 2, 0.0F);
        this.body4 = new ModelRenderer(this, 0, 24);
        this.body4.mirror = true;
        this.body4.setRotationPoint(0.13F, -0.94F, 0.0F);
        this.body4.addBox(0.0F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(body4, 0.0F, 0.0F, -0.767944870877505F);
        this.body17 = new ModelRenderer(this, 0, 21);
        this.body17.setRotationPoint(1.0F, -15.9F, 3.12F);
        this.body17.addBox(-0.5F, -1.5F, -0.5F, 1, 2, 1, 0.0F);
        this.frontButton2 = new ModelRenderer(this, 41, 0);
        this.frontButton2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.frontButton2.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(frontButton2, 0.0F, 2.1816615649929116F, 0.0F);
        this.body2 = new ModelRenderer(this, 24, 0);
        this.body2.mirror = true;
        this.body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body2.addBox(-1.5F, -18.0F, 2.62F, 3, 18, 1, 0.0F);
        this.setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        this.body29 = new ModelRenderer(this, 0, 30);
        this.body29.mirror = true;
        this.body29.setRotationPoint(0.8F, -19.76F, 3.1F);
        this.body29.addBox(-1.0F, -0.5F, -0.5F, 2, 1, 1, 0.0F);
        this.setRotateAngle(body29, 0.296705972839036F, 0.2617993877991494F, 0.20943951023931953F);
        this.guard2 = new ModelRenderer(this, 44, 21);
        this.guard2.setRotationPoint(-4.0F, 0.0F, 0.0F);
        this.guard2.addBox(0.0F, -1.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(guard2, 0.0F, 0.7958701389094143F, 0.0F);
        this.body1 = new ModelRenderer(this, 32, 0);
        this.body1.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body1.addBox(-1.5F, -20.0F, 2.62F, 3, 20, 1, 0.0F);
        this.guard5 = new ModelRenderer(this, 40, 10);
        this.guard5.setRotationPoint(-4.0F, -11.12F, 0.38F);
        this.guard5.addBox(0.0F, 0.0F, 0.0F, 8, 3, 2, 0.0F);
        this.setRotateAngle(guard5, 0.7853981633974483F, 0.0F, 0.0F);
        this.body9 = new ModelRenderer(this, 4, 21);
        this.body9.mirror = true;
        this.body9.setRotationPoint(0.8F, -0.2F, 0.0F);
        this.body9.addBox(-0.6F, -0.5F, -0.5F, 1, 1, 1, 0.0F);
        this.frontButton1 = new ModelRenderer(this, 41, 0);
        this.frontButton1.setRotationPoint(1.8F, -6.0F, 2.0F);
        this.frontButton1.addBox(-1.5F, -4.0F, -0.38F, 3, 4, 4, 0.0F);
        this.setRotateAngle(frontButton1, 1.5707963267948966F, -2.356194490192345F, 0.0F);
        this.panel2 = new ModelRenderer(this, 26, 22);
        this.panel2.setRotationPoint(-1.0F, -16.0F, 3.0F);
        this.panel2.addBox(0.0F, 0.0F, 0.0F, 2, 3, 1, 0.0F);
        this.body14 = new ModelRenderer(this, 0, 19);
        this.body14.setRotationPoint(-1.16F, -14.06F, 3.12F);
        this.body14.addBox(0.0F, -1.0F, -0.5F, 3, 1, 1, 0.0F);
        this.setRotateAngle(body14, 0.0F, 0.0F, -0.33161255787892263F);
        this.body8 = new ModelRenderer(this, 0, 21);
        this.body8.mirror = true;
        this.body8.setRotationPoint(-1.0F, -15.9F, 3.12F);
        this.body8.addBox(-0.5F, -1.5F, -0.5F, 1, 2, 1, 0.0F);
        this.body21 = new ModelRenderer(this, 0, 24);
        this.body21.setRotationPoint(-0.13F, -0.94F, 0.0F);
        this.body21.addBox(-1.0F, -0.5F, -0.5F, 1, 3, 1, 0.0F);
        this.setRotateAngle(body21, 0.0F, 0.0F, 0.767944870877505F);
        this.guard4 = new ModelRenderer(this, 44, 21);
        this.guard4.setRotationPoint(2.61F, -13.0F, -3.94F);
        this.guard4.addBox(0.0F, 0.0F, 0.0F, 2, 2, 3, 0.0F);
        this.setRotateAngle(guard4, 0.0F, -0.8028514559173915F, 0.0F);
        this.body6 = new ModelRenderer(this, 16, 0);
        this.body6.mirror = true;
        this.body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body6.addBox(-1.5F, -16.0F, 2.62F, 3, 16, 1, 0.0F);
        this.setRotateAngle(body6, 0.0F, 1.5707963267948966F, 0.0F);
        this.body3 = new ModelRenderer(this, 4, 23);
        this.body3.mirror = true;
        this.body3.setRotationPoint(-1.0F, -18.0F, 3.12F);
        this.body3.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.body19 = new ModelRenderer(this, 24, 0);
        this.body19.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body19.addBox(-1.5F, -18.0F, 2.62F, 3, 18, 1, 0.0F);
        this.setRotateAngle(body19, 0.0F, -0.7853981633974483F, 0.0F);
        this.body20 = new ModelRenderer(this, 4, 23);
        this.body20.setRotationPoint(1.0F, -18.0F, 3.12F);
        this.body20.addBox(-0.5F, -2.0F, -0.5F, 1, 2, 1, 0.0F);
        this.panel1 = new ModelRenderer(this, 26, 22);
        this.panel1.setRotationPoint(-1.0F, -9.0F, 3.0F);
        this.panel1.addBox(0.0F, 0.0F, 0.0F, 2, 5, 1, 0.0F);
        this.body15 = new ModelRenderer(this, 16, 0);
        this.body15.setRotationPoint(0.0F, 0.0F, 0.0F);
        this.body15.addBox(-1.5F, -16.0F, 2.62F, 3, 16, 1, 0.0F);
        this.setRotateAngle(body15, 0.0F, -1.5707963267948966F, 0.0F);
        this.body1.addChild(this.body28);
        this.body1.addChild(this.body12);
        this.body15.addChild(this.body16);
        this.body1.addChild(this.body10);
        this.body17.addChild(this.body18);
        this.body10.addChild(this.body11);
        this.body1.addChild(this.body13);
        this.body4.addChild(this.body5);
        this.frontButton1.addChild(this.frontButton3);
        this.body21.addChild(this.body22);
        this.body6.addChild(this.body7);
        this.body3.addChild(this.body4);
        this.body15.addChild(this.body17);
        this.frontButton1.addChild(this.frontButton2);
        this.body1.addChild(this.body2);
        this.body1.addChild(this.body29);
        this.guard1.addChild(this.guard2);
        this.body8.addChild(this.body9);
        this.body13.addChild(this.body14);
        this.body6.addChild(this.body8);
        this.body20.addChild(this.body21);
        this.body1.addChild(this.body6);
        this.body2.addChild(this.body3);
        this.body1.addChild(this.body19);
        this.body19.addChild(this.body20);
        this.body1.addChild(this.body15);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    { 
        this.guard1.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.guard3.offsetX, this.guard3.offsetY, this.guard3.offsetZ);
        GL11.glTranslatef(this.guard3.rotationPointX * f5, this.guard3.rotationPointY * f5, this.guard3.rotationPointZ * f5);
        GL11.glScaled(1.04D, 1.0D, 1.0D);
        GL11.glTranslatef(-this.guard3.offsetX, -this.guard3.offsetY, -this.guard3.offsetZ);
        GL11.glTranslatef(-this.guard3.rotationPointX * f5, -this.guard3.rotationPointY * f5, -this.guard3.rotationPointZ * f5);
        this.guard3.render(f5);
        GL11.glPopMatrix();
        this.guard6.render(f5);
        this.body1.render(f5);
        this.guard5.render(f5);
        GL11.glPushMatrix();
        GL11.glTranslatef(this.frontButton1.offsetX, this.frontButton1.offsetY, this.frontButton1.offsetZ);
        GL11.glTranslatef(this.frontButton1.rotationPointX * f5, this.frontButton1.rotationPointY * f5, this.frontButton1.rotationPointZ * f5);
        GL11.glScaled(0.3D, 0.3D, 0.3D);
        GL11.glTranslatef(-this.frontButton1.offsetX, -this.frontButton1.offsetY, -this.frontButton1.offsetZ);
        GL11.glTranslatef(-this.frontButton1.rotationPointX * f5, -this.frontButton1.rotationPointY * f5, -this.frontButton1.rotationPointZ * f5);
        this.frontButton1.render(f5);
        GL11.glPopMatrix();
        this.panel2.render(f5);
        this.guard4.render(f5);
        this.panel1.render(f5);
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
