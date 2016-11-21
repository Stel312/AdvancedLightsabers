package fiskfille.lightsabers.client.model.lightsaber;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.entity.Entity;
import org.lwjgl.opengl.GL11;

public class ModelSwitchSectionFury extends ModelBase
{
    public ModelRenderer body1;
    public ModelRenderer ring1;
    public ModelRenderer body2;
    public ModelRenderer body3;
    public ModelRenderer body4;
    public ModelRenderer body5;
    public ModelRenderer body6;
    public ModelRenderer body7;
    public ModelRenderer body8;
    public ModelRenderer ring2;
    public ModelRenderer ring3;
    public ModelRenderer ring4;
    public ModelRenderer ring5;
    public ModelRenderer ring6;
    public ModelRenderer ring7;
    public ModelRenderer ring8;
    public ModelRenderer ring9;
    public ModelRenderer ring10;
    public ModelRenderer ring11;
    public ModelRenderer ring12;
    public ModelRenderer ring13;
    public ModelRenderer ring14;
    public ModelRenderer ring15;
    public ModelRenderer ring16;
    public ModelRenderer ring17;
    public ModelRenderer ring18;
    public ModelRenderer ring19;
    public ModelRenderer ring20;
    public ModelRenderer ring21;
    public ModelRenderer ring22;
    public ModelRenderer ring23;
    public ModelRenderer ring24;
    public ModelRenderer ring25;
    public ModelRenderer ring26;
    public ModelRenderer ring27;
    public ModelRenderer ring28;
    public ModelRenderer ring29;
    public ModelRenderer ring30;
    public ModelRenderer ring31;
    public ModelRenderer ring32;

    public ModelSwitchSectionFury()
    {
        textureWidth = 64;
        textureHeight = 32;
        body4 = new ModelRenderer(this, 0, 2);
        body4.setRotationPoint(0.0F, 0.0F, 0.0F);
        body4.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body4, 0.0F, 2.356194490192345F, 0.0F);
        ring4 = new ModelRenderer(this, 0, 0);
        ring4.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring4.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring4, 0.0F, 2.356194490192345F, 0.0F);
        ring8 = new ModelRenderer(this, 0, 0);
        ring8.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring8.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring8, 0.0F, -0.7853981633974483F, 0.0F);
        ring22 = new ModelRenderer(this, 0, 0);
        ring22.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring22.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring22, 0.0F, -2.356194490192345F, 0.0F);
        ring14 = new ModelRenderer(this, 0, 0);
        ring14.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring14.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring14, 0.0F, -2.356194490192345F, 0.0F);
        ring25 = new ModelRenderer(this, 0, 0);
        ring25.setRotationPoint(0.0F, -2.0F, 0.0F);
        ring25.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        ring29 = new ModelRenderer(this, 0, 0);
        ring29.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring29.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring29, 0.0F, 3.141592653589793F, 0.0F);
        body7 = new ModelRenderer(this, 0, 2);
        body7.setRotationPoint(0.0F, 0.0F, 0.0F);
        body7.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body7, 0.0F, -1.5707963267948966F, 0.0F);
        ring6 = new ModelRenderer(this, 0, 0);
        ring6.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring6.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring6, 0.0F, -2.356194490192345F, 0.0F);
        ring23 = new ModelRenderer(this, 0, 0);
        ring23.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring23.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring23, 0.0F, -1.5707963267948966F, 0.0F);
        body3 = new ModelRenderer(this, 0, 2);
        body3.setRotationPoint(0.0F, 0.0F, 0.0F);
        body3.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body3, 0.0F, 1.5707963267948966F, 0.0F);
        ring19 = new ModelRenderer(this, 0, 0);
        ring19.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring19.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring19, 0.0F, 1.5707963267948966F, 0.0F);
        ring26 = new ModelRenderer(this, 0, 0);
        ring26.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring26.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring26, 0.0F, 0.7853981633974483F, 0.0F);
        ring32 = new ModelRenderer(this, 0, 0);
        ring32.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring32.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring32, 0.0F, -0.7853981633974483F, 0.0F);
        ring12 = new ModelRenderer(this, 0, 0);
        ring12.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring12.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring12, 0.0F, 2.356194490192345F, 0.0F);
        ring27 = new ModelRenderer(this, 0, 0);
        ring27.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring27.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring27, 0.0F, 1.5707963267948966F, 0.0F);
        ring10 = new ModelRenderer(this, 0, 0);
        ring10.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring10.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring10, 0.0F, 0.7853981633974483F, 0.0F);
        ring30 = new ModelRenderer(this, 0, 0);
        ring30.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring30.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring30, 0.0F, -2.356194490192345F, 0.0F);
        ring31 = new ModelRenderer(this, 0, 0);
        ring31.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring31.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring31, 0.0F, -1.5707963267948966F, 0.0F);
        ring16 = new ModelRenderer(this, 0, 0);
        ring16.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring16.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring16, 0.0F, -0.7853981633974483F, 0.0F);
        ring13 = new ModelRenderer(this, 0, 0);
        ring13.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring13.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring13, 0.0F, 3.141592653589793F, 0.0F);
        ring18 = new ModelRenderer(this, 0, 0);
        ring18.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring18.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring18, 0.0F, 0.7853981633974483F, 0.0F);
        body1 = new ModelRenderer(this, 0, 2);
        body1.setRotationPoint(0.0F, -0.1F, 0.0F);
        body1.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        ring28 = new ModelRenderer(this, 0, 0);
        ring28.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring28.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring28, 0.0F, 2.356194490192345F, 0.0F);
        ring3 = new ModelRenderer(this, 0, 0);
        ring3.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring3.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring3, 0.0F, 1.5707963267948966F, 0.0F);
        ring15 = new ModelRenderer(this, 0, 0);
        ring15.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring15.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring15, 0.0F, -1.5707963267948966F, 0.0F);
        ring7 = new ModelRenderer(this, 0, 0);
        ring7.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring7.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring7, 0.0F, -1.5707963267948966F, 0.0F);
        ring24 = new ModelRenderer(this, 0, 0);
        ring24.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring24.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring24, 0.0F, -0.7853981633974483F, 0.0F);
        body2 = new ModelRenderer(this, 0, 2);
        body2.setRotationPoint(0.0F, 0.0F, 0.0F);
        body2.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body2, 0.0F, 0.7853981633974483F, 0.0F);
        body8 = new ModelRenderer(this, 0, 2);
        body8.setRotationPoint(0.0F, 0.0F, 0.0F);
        body8.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body8, 0.0F, -0.7853981633974483F, 0.0F);
        ring1 = new ModelRenderer(this, 0, 0);
        ring1.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring1.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        body6 = new ModelRenderer(this, 0, 2);
        body6.setRotationPoint(0.0F, 0.0F, 0.0F);
        body6.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body6, 0.0F, -2.356194490192345F, 0.0F);
        ring17 = new ModelRenderer(this, 0, 0);
        ring17.setRotationPoint(0.0F, -2.0F, 0.0F);
        ring17.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        body5 = new ModelRenderer(this, 0, 2);
        body5.setRotationPoint(0.0F, 0.0F, 0.0F);
        body5.addBox(-1.5F, -6.0F, 2.62F, 3, 6, 1, 0.0F);
        setRotateAngle(body5, 0.0F, 3.141592653589793F, 0.0F);
        ring11 = new ModelRenderer(this, 0, 0);
        ring11.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring11.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring11, 0.0F, 1.5707963267948966F, 0.0F);
        ring20 = new ModelRenderer(this, 0, 0);
        ring20.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring20.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring20, 0.0F, 2.356194490192345F, 0.0F);
        ring2 = new ModelRenderer(this, 0, 0);
        ring2.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring2.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring2, 0.0F, 0.7853981633974483F, 0.0F);
        ring21 = new ModelRenderer(this, 0, 0);
        ring21.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring21.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring21, 0.0F, 3.141592653589793F, 0.0F);
        ring9 = new ModelRenderer(this, 0, 0);
        ring9.setRotationPoint(0.0F, -2.0F, 0.0F);
        ring9.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        ring5 = new ModelRenderer(this, 0, 0);
        ring5.setRotationPoint(0.0F, 0.0F, 0.0F);
        ring5.addBox(-1.5F, -1.0F, 2.62F, 3, 1, 1, 0.0F);
        setRotateAngle(ring5, 0.0F, 3.141592653589793F, 0.0F);
        body1.addChild(body4);
        ring1.addChild(ring4);
        ring1.addChild(ring8);
        ring17.addChild(ring22);
        ring9.addChild(ring14);
        ring17.addChild(ring25);
        ring25.addChild(ring29);
        body1.addChild(body7);
        ring1.addChild(ring6);
        ring17.addChild(ring23);
        body1.addChild(body3);
        ring17.addChild(ring19);
        ring25.addChild(ring26);
        ring25.addChild(ring32);
        ring9.addChild(ring12);
        ring25.addChild(ring27);
        ring9.addChild(ring10);
        ring25.addChild(ring30);
        ring25.addChild(ring31);
        ring9.addChild(ring16);
        ring9.addChild(ring13);
        ring17.addChild(ring18);
        ring25.addChild(ring28);
        ring1.addChild(ring3);
        ring9.addChild(ring15);
        ring1.addChild(ring7);
        ring17.addChild(ring24);
        body1.addChild(body2);
        body1.addChild(body8);
        body1.addChild(body6);
        ring9.addChild(ring17);
        body1.addChild(body5);
        ring9.addChild(ring11);
        ring17.addChild(ring20);
        ring1.addChild(ring2);
        ring17.addChild(ring21);
        ring1.addChild(ring9);
        ring1.addChild(ring5);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5)
    {
        GL11.glPushMatrix();
        GL11.glTranslatef(body1.offsetX, body1.offsetY, body1.offsetZ);
        GL11.glTranslatef(body1.rotationPointX * f5, body1.rotationPointY * f5, body1.rotationPointZ * f5);
        GL11.glScaled(0.9D, 0.9D, 0.9D);
        GL11.glTranslatef(-body1.offsetX, -body1.offsetY, -body1.offsetZ);
        GL11.glTranslatef(-body1.rotationPointX * f5, -body1.rotationPointY * f5, -body1.rotationPointZ * f5);
        body1.render(f5);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glTranslatef(ring1.offsetX, ring1.offsetY, ring1.offsetZ);
        GL11.glTranslatef(ring1.rotationPointX * f5, ring1.rotationPointY * f5, ring1.rotationPointZ * f5);
        GL11.glScaled(1.0D, 0.8D, 1.0D);
        GL11.glTranslatef(-ring1.offsetX, -ring1.offsetY, -ring1.offsetZ);
        GL11.glTranslatef(-ring1.rotationPointX * f5, -ring1.rotationPointY * f5, -ring1.rotationPointZ * f5);
        ring1.render(f5);
        GL11.glPopMatrix();
    }

    public void setRotateAngle(ModelRenderer modelRenderer, float x, float y, float z)
    {
        modelRenderer.rotateAngleX = x;
        modelRenderer.rotateAngleY = y;
        modelRenderer.rotateAngleZ = z;
    }
}
